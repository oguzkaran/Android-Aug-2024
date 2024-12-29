/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.internal.cc.impl

import org.gradle.api.GradleException
import org.gradle.api.configuration.BuildFeatures
import org.gradle.api.internal.BuildType
import org.gradle.api.internal.StartParameterInternal
import org.gradle.api.internal.artifacts.ivyservice.projectmodule.LocalComponentCache
import org.gradle.api.internal.configuration.DefaultBuildFeatures
import org.gradle.api.logging.LogLevel
import org.gradle.internal.cc.impl.fingerprint.ConfigurationCacheFingerprintController
import org.gradle.internal.cc.impl.initialization.ConfigurationCacheInjectedClasspathInstrumentationStrategy
import org.gradle.internal.cc.impl.initialization.ConfigurationCacheStartParameter
import org.gradle.internal.cc.impl.initialization.DefaultConfigurationCacheProblemsListener
import org.gradle.internal.cc.impl.initialization.InstrumentedExecutionAccessListenerRegistry
import org.gradle.internal.cc.impl.initialization.VintageInjectedClasspathInstrumentationStrategy
import org.gradle.internal.cc.impl.models.DefaultToolingModelParameterCarrierFactory
import org.gradle.internal.cc.impl.problems.ConfigurationCacheProblems
import org.gradle.internal.cc.impl.services.ConfigurationCacheBuildTreeModelSideEffectExecutor
import org.gradle.internal.cc.impl.services.VintageEnvironmentChangeTracker
import org.gradle.execution.selection.BuildTaskSelector
import org.gradle.initialization.StartParameterBuildOptions
import org.gradle.internal.build.BuildStateRegistry
import org.gradle.internal.buildoption.DefaultInternalOptions
import org.gradle.internal.buildoption.InternalFlag
import org.gradle.internal.buildtree.BuildActionModelRequirements
import org.gradle.internal.buildtree.BuildModelParameters
import org.gradle.internal.buildtree.BuildTreeModelControllerServices
import org.gradle.internal.buildtree.BuildTreeWorkGraphPreparer
import org.gradle.internal.buildtree.DefaultBuildTreeModelSideEffectExecutor
import org.gradle.internal.buildtree.DefaultBuildTreeWorkGraphPreparer
import org.gradle.internal.buildtree.RunTasksRequirements
import org.gradle.internal.configuration.problems.DefaultProblemFactory
import org.gradle.internal.cc.base.logger
import org.gradle.internal.cc.base.services.ConfigurationCacheEnvironmentChangeTracker
import org.gradle.internal.scripts.ProjectScopedScriptResolution
import org.gradle.internal.serialize.beans.services.DefaultBeanStateReaderLookup
import org.gradle.internal.serialize.beans.services.DefaultBeanStateWriterLookup
import org.gradle.internal.serialize.codecs.core.jos.JavaSerializationEncodingLookup
import org.gradle.internal.serialize.graph.BeanStateReaderLookup
import org.gradle.internal.serialize.graph.BeanStateWriterLookup
import org.gradle.internal.service.Provides
import org.gradle.internal.service.ServiceRegistration
import org.gradle.internal.service.ServiceRegistrationProvider
import org.gradle.internal.snapshot.ValueSnapshotter
import org.gradle.tooling.provider.model.internal.ToolingModelParameterCarrier
import org.gradle.util.internal.IncubationLogger


class DefaultBuildTreeModelControllerServices : BuildTreeModelControllerServices {
    companion object {
        private
        val parallelBuilding = InternalFlag("org.gradle.internal.tooling.parallel", true)

        private
        val invalidateCoupledProjects = InternalFlag("org.gradle.internal.invalidate-coupled-projects", true)

        /**
         * If model dependencies between projects should be treated as project dependencies.
         * Model dependency is observed when a project requests a model from another project.
         */
        private
        val modelProjectDependencies = InternalFlag("org.gradle.internal.model-project-dependencies", true)
    }

    override fun servicesForBuildTree(requirements: BuildActionModelRequirements): BuildTreeModelControllerServices.Supplier {
        val startParameter = requirements.startParameter

        // Isolated projects also implies configuration cache
        if (startParameter.isolatedProjects.get() && !startParameter.configurationCache.get()) {
            if (startParameter.configurationCache.isExplicit) {
                throw GradleException("The configuration cache cannot be disabled when isolated projects is enabled.")
            }
        }

        val options = DefaultInternalOptions(startParameter.systemPropertiesArgs)
        val isolatedProjects = startParameter.isolatedProjects.get()
        val parallelProjectExecution = isolatedProjects || requirements.startParameter.isParallelProjectExecutionEnabled
        val parallelToolingActions = parallelProjectExecution && options.getOption(parallelBuilding).get()
        val invalidateCoupledProjects = isolatedProjects && options.getOption(invalidateCoupledProjects).get()
        val modelAsProjectDependency = isolatedProjects && options.getOption(modelProjectDependencies).get()
        val configurationCacheLogLevel = if (startParameter.isConfigurationCacheQuiet) LogLevel.INFO else LogLevel.LIFECYCLE
        val modelParameters = if (requirements.isCreatesModel) {
            // When creating a model, disable certain features - only enable configure on demand and configuration cache when isolated projects is enabled
            BuildModelParameters(
                parallelProjectExecution,
                isolatedProjects,
                isolatedProjects,
                isolatedProjects,
                true,
                isolatedProjects,
                parallelToolingActions,
                invalidateCoupledProjects,
                modelAsProjectDependency,
                configurationCacheLogLevel
            )
        } else {
            val configurationCache = isolatedProjects || startParameter.configurationCache.get()
            val configureOnDemand = isolatedProjects || startParameter.isConfigureOnDemand

            fun disabledConfigurationCacheBuildModelParameters(buildOptionReason: String): BuildModelParameters {
                logger.log(configurationCacheLogLevel, "{} as configuration cache cannot be reused due to --{}", requirements.actionDisplayName.capitalizedDisplayName, buildOptionReason)
                return BuildModelParameters(
                    parallelProjectExecution,
                    configureOnDemand,
                    false,
                    false,
                    false,
                    false,
                    parallelToolingActions,
                    invalidateCoupledProjects,
                    modelAsProjectDependency,
                    configurationCacheLogLevel
                )
            }

            when {
                configurationCache && startParameter.writeDependencyVerifications.isNotEmpty() -> disabledConfigurationCacheBuildModelParameters(StartParameterBuildOptions.DependencyVerificationWriteOption.LONG_OPTION)
                configurationCache && startParameter.isExportKeys -> disabledConfigurationCacheBuildModelParameters(StartParameterBuildOptions.ExportKeysOption.LONG_OPTION)
                else -> BuildModelParameters(
                    parallelProjectExecution,
                    configureOnDemand,
                    configurationCache,
                    isolatedProjects,
                    false,
                    false,
                    parallelToolingActions,
                    invalidateCoupledProjects,
                    modelAsProjectDependency,
                    configurationCacheLogLevel
                )
            }
        }

        if (!startParameter.isConfigurationCacheQuiet) {
            if (modelParameters.isIsolatedProjects) {
                IncubationLogger.incubatingFeatureUsed("Isolated projects")
            }
        }
        if (!modelParameters.isIsolatedProjects && modelParameters.isConfigureOnDemand) {
            IncubationLogger.incubatingFeatureUsed("Configuration on demand")
        }

        val buildFeatures = DefaultBuildFeatures(startParameter, modelParameters)

        return BuildTreeModelControllerServices.Supplier { registration ->
            val buildType = if (requirements.isRunsTasks) BuildType.TASKS else BuildType.MODEL
            registration.add(BuildType::class.java, buildType)
            registerCommonBuildTreeServices(registration, modelParameters, buildFeatures, requirements)
        }
    }

    override fun servicesForNestedBuildTree(startParameter: StartParameterInternal): BuildTreeModelControllerServices.Supplier {
        return BuildTreeModelControllerServices.Supplier { registration ->
            registration.add(BuildType::class.java, BuildType.TASKS)
            // Configuration cache is not supported for nested build trees
            val buildModelParameters =
                BuildModelParameters(
                    startParameter.isParallelProjectExecutionEnabled,
                    startParameter.isConfigureOnDemand,
                    false,
                    false,
                    true,
                    false,
                    false,
                    false,
                    false,
                    LogLevel.LIFECYCLE
                )
            val buildFeatures = DefaultBuildFeatures(startParameter, buildModelParameters)
            val requirements = RunTasksRequirements(startParameter)
            registerCommonBuildTreeServices(registration, buildModelParameters, buildFeatures, requirements)
        }
    }

    private
    fun registerCommonBuildTreeServices(registration: ServiceRegistration, modelParameters: BuildModelParameters, buildFeatures: DefaultBuildFeatures, requirements: BuildActionModelRequirements) {
        registration.add(BuildModelParameters::class.java, modelParameters)
        registration.add(BuildFeatures::class.java, buildFeatures)
        registration.add(BuildActionModelRequirements::class.java, requirements)
        registration.addProvider(SharedBuildTreeScopedServices())
        registration.add(BeanStateWriterLookup::class.java, DefaultBeanStateWriterLookup::class.java)
        registration.add(BeanStateReaderLookup::class.java, DefaultBeanStateReaderLookup::class.java)
        registration.add(JavaSerializationEncodingLookup::class.java)
        if (modelParameters.isConfigurationCache) {
            registration.add(ConfigurationCacheBuildTreeLifecycleControllerFactory::class.java)
            registration.add(ConfigurationCacheStartParameter::class.java)
            registration.add(ConfigurationCacheClassLoaderScopeRegistryListener::class.java)
            registration.add(ConfigurationCacheInjectedClasspathInstrumentationStrategy::class.java)
            registration.add(ConfigurationCacheEnvironmentChangeTracker::class.java)
            registration.add(DefaultConfigurationCacheProblemsListener::class.java)
            registration.add(DefaultProblemFactory::class.java)
            registration.add(ConfigurationCacheProblems::class.java)
            registration.add(DefaultConfigurationCache::class.java)
            registration.add(InstrumentedExecutionAccessListenerRegistry::class.java)
            registration.add(ConfigurationCacheFingerprintController::class.java)
            registration.addProvider(ConfigurationCacheBuildTreeProvider())
            registration.add(ConfigurationCacheBuildTreeModelSideEffectExecutor::class.java)
        } else {
            registration.add(VintageInjectedClasspathInstrumentationStrategy::class.java)
            registration.add(VintageBuildTreeLifecycleControllerFactory::class.java)
            registration.add(VintageEnvironmentChangeTracker::class.java)
            registration.add(ProjectScopedScriptResolution::class.java, ProjectScopedScriptResolution.NO_OP)
            registration.addProvider(VintageBuildTreeProvider())
            registration.add(DefaultBuildTreeModelSideEffectExecutor::class.java)
        }
        if (modelParameters.isIntermediateModelCache) {
            registration.addProvider(ConfigurationCacheModelProvider())
        } else {
            registration.addProvider(VintageModelProvider())
        }
    }

    private
    class SharedBuildTreeScopedServices : ServiceRegistrationProvider {
        @Provides
        fun createToolingModelParameterCarrierFactory(valueSnapshotter: ValueSnapshotter): ToolingModelParameterCarrier.Factory {
            return DefaultToolingModelParameterCarrierFactory(valueSnapshotter)
        }
    }

    private
    class ConfigurationCacheModelProvider : ServiceRegistrationProvider {
        @Provides
        fun createLocalComponentCache(cache: BuildTreeConfigurationCache): LocalComponentCache = ConfigurationCacheAwareLocalComponentCache(cache)
    }

    private
    class VintageModelProvider : ServiceRegistrationProvider {
        @Provides
        fun createLocalComponentCache(): LocalComponentCache = LocalComponentCache.NO_CACHE
    }

    private
    class ConfigurationCacheBuildTreeProvider : ServiceRegistrationProvider {
        @Provides
        fun createBuildTreeWorkGraphPreparer(buildRegistry: BuildStateRegistry, buildTaskSelector: BuildTaskSelector, cache: BuildTreeConfigurationCache): BuildTreeWorkGraphPreparer {
            return ConfigurationCacheAwareBuildTreeWorkGraphPreparer(DefaultBuildTreeWorkGraphPreparer(buildRegistry, buildTaskSelector), cache)
        }
    }

    private
    class VintageBuildTreeProvider : ServiceRegistrationProvider {
        @Provides
        fun createBuildTreeWorkGraphPreparer(buildRegistry: BuildStateRegistry, buildTaskSelector: BuildTaskSelector): BuildTreeWorkGraphPreparer {
            return DefaultBuildTreeWorkGraphPreparer(buildRegistry, buildTaskSelector)
        }
    }
}
