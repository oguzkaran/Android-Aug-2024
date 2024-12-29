/*
 * Copyright 2009 the original author or authors.
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
package org.gradle.api.internal.artifacts.ivyservice;

import com.google.common.annotations.VisibleForTesting;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.LenientConfiguration;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.artifacts.ResolveException;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedDependency;
import org.gradle.api.artifacts.UnresolvedDependency;
import org.gradle.api.artifacts.component.BuildIdentifier;
import org.gradle.api.artifacts.component.ComponentIdentifier;
import org.gradle.api.internal.artifacts.ConfigurationResolver;
import org.gradle.api.internal.artifacts.DefaultResolverResults;
import org.gradle.api.internal.artifacts.ImmutableModuleIdentifierFactory;
import org.gradle.api.internal.artifacts.Module;
import org.gradle.api.internal.artifacts.ResolveContext;
import org.gradle.api.internal.artifacts.ResolverResults;
import org.gradle.api.internal.artifacts.component.ComponentIdentifierFactory;
import org.gradle.api.internal.artifacts.dsl.dependencies.DependencyLockingProvider;
import org.gradle.api.internal.artifacts.dsl.dependencies.DependencyLockingState;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.ArtifactSelectionSpec;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.ArtifactVisitor;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.SelectedArtifactSet;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.VisitedArtifactSet;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.graph.results.DefaultVisitedGraphResults;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.graph.results.VisitedGraphResults;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.result.ResolutionResultGraphBuilder;
import org.gradle.api.internal.artifacts.repositories.ResolutionAwareRepository;
import org.gradle.api.internal.artifacts.result.MinimalResolutionResult;
import org.gradle.api.internal.attributes.ImmutableAttributes;
import org.gradle.api.internal.tasks.TaskDependencyResolveContext;
import org.gradle.api.specs.Spec;
import org.gradle.api.specs.Specs;
import org.gradle.internal.deprecation.DeprecationLogger;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ShortCircuitEmptyConfigurationResolver implements ConfigurationResolver {
    private final ConfigurationResolver delegate;
    private final ComponentIdentifierFactory componentIdentifierFactory;
    private final ImmutableModuleIdentifierFactory moduleIdentifierFactory;
    private final BuildIdentifier thisBuild;

    public ShortCircuitEmptyConfigurationResolver(ConfigurationResolver delegate, ComponentIdentifierFactory componentIdentifierFactory, ImmutableModuleIdentifierFactory moduleIdentifierFactory, BuildIdentifier thisBuild) {
        this.delegate = delegate;
        this.componentIdentifierFactory = componentIdentifierFactory;
        this.moduleIdentifierFactory = moduleIdentifierFactory;
        this.thisBuild = thisBuild;
    }

    @Override
    public List<ResolutionAwareRepository> getAllRepositories() {
        return delegate.getAllRepositories();
    }

    @Override
    public ResolverResults resolveBuildDependencies(ResolveContext resolveContext) {
        if (resolveContext.hasDependencies()) {
            return delegate.resolveBuildDependencies(resolveContext);
        }

        resolveContext.markAsObserved();
        VisitedGraphResults graphResults = emptyGraphResults(resolveContext);
        return DefaultResolverResults.buildDependenciesResolved(graphResults, EmptyResults.INSTANCE,
            DefaultResolverResults.DefaultLegacyResolverResults.buildDependenciesResolved(EmptyResults.INSTANCE)
        );
    }

    @Override
    public ResolverResults resolveGraph(ResolveContext resolveContext) throws ResolveException {
        if (resolveContext.hasDependencies()) {
            return delegate.resolveGraph(resolveContext);
        }

        if (resolveContext.getResolutionStrategy().isDependencyLockingEnabled()) {
            DependencyLockingProvider dependencyLockingProvider = resolveContext.getResolutionStrategy().getDependencyLockingProvider();
            DependencyLockingState lockingState = dependencyLockingProvider.loadLockState(resolveContext.getDependencyLockingId(), resolveContext.getResolutionHost().displayName());
            if (lockingState.mustValidateLockState() && !lockingState.getLockedDependencies().isEmpty()) {
                // Invalid lock state, need to do a real resolution to gather locking failures
                return delegate.resolveGraph(resolveContext);
            }
            dependencyLockingProvider.persistResolvedDependencies(resolveContext.getDependencyLockingId(), resolveContext.getResolutionHost().displayName(), Collections.emptySet(), Collections.emptySet());
        }

        resolveContext.markAsObserved();
        VisitedGraphResults graphResults = emptyGraphResults(resolveContext);
        ResolvedConfiguration resolvedConfiguration = new DefaultResolvedConfiguration(
            graphResults, resolveContext.getResolutionHost(), EmptyResults.INSTANCE, new EmptyLenientConfiguration()
        );
        return DefaultResolverResults.graphResolved(graphResults, EmptyResults.INSTANCE,
            DefaultResolverResults.DefaultLegacyResolverResults.graphResolved(
                EmptyResults.INSTANCE, resolvedConfiguration
            )
        );
    }

    private VisitedGraphResults emptyGraphResults(ResolveContext resolveContext) {
        Module module = resolveContext.getModule();
        ModuleVersionIdentifier id = moduleIdentifierFactory.moduleWithVersion(module.getGroup(), module.getName(), module.getVersion());
        ComponentIdentifier componentIdentifier = componentIdentifierFactory.createComponentIdentifier(module);
        MinimalResolutionResult emptyResult = ResolutionResultGraphBuilder.empty(id, componentIdentifier, resolveContext.getAttributes().asImmutable());
        return new DefaultVisitedGraphResults(emptyResult, Collections.emptySet(), null);
    }

    private static class EmptyResults implements VisitedArtifactSet, SelectedArtifactSet, ResolverResults.LegacyResolverResults.LegacyVisitedArtifactSet {
        private static final EmptyResults INSTANCE = new EmptyResults();

        @Override
        public SelectedArtifactSet select(ArtifactSelectionSpec spec) {
            return this;
        }

        @Override
        public SelectedArtifactSet select(Spec<? super Dependency> dependencySpec) {
            return this;
        }

        @Override
        public void visitDependencies(TaskDependencyResolveContext context) {
        }

        @Override
        public void visitArtifacts(ArtifactVisitor visitor, boolean continueOnSelectionFailure) {
        }
    }

    @VisibleForTesting
    public static class EmptyLenientConfiguration implements LenientConfigurationInternal {

        @Override
        public ArtifactSelectionSpec getImplicitSelectionSpec() {
            return new ArtifactSelectionSpec(
                ImmutableAttributes.EMPTY, Specs.satisfyAll(), false, false, ResolutionStrategy.SortOrder.DEFAULT
            );
        }

        @Override
        public SelectedArtifactSet select(Spec<? super Dependency> dependencySpec) {
            return EmptyResults.INSTANCE;
        }

        @Override
        public Set<ResolvedDependency> getFirstLevelModuleDependencies() {
            return Collections.emptySet();
        }

        @Override
        @Deprecated
        public Set<ResolvedDependency> getFirstLevelModuleDependencies(Spec<? super Dependency> dependencySpec) {
            DeprecationLogger.deprecateMethod(LenientConfiguration.class, "getFirstLevelModuleDependencies(Spec)")
                .withAdvice("Use getFirstLevelModuleDependencies() instead.")
                .willBeRemovedInGradle9()
                .withUpgradeGuideSection(8, "deprecate_filtered_configuration_file_and_filecollection_methods")
                .nagUser();

            return Collections.emptySet();
        }

        @Override
        public Set<ResolvedDependency> getAllModuleDependencies() {
            return Collections.emptySet();
        }

        @Override
        public Set<UnresolvedDependency> getUnresolvedModuleDependencies() {
            return Collections.emptySet();
        }

        @Override
        public Set<File> getFiles() {
            return Collections.emptySet();
        }

        @Override
        @Deprecated
        public Set<File> getFiles(Spec<? super Dependency> dependencySpec) {
            DeprecationLogger.deprecateMethod(LenientConfiguration.class, "getFiles(Spec)")
                .withAdvice("Use a lenient ArtifactView with a componentFilter instead.")
                .willBeRemovedInGradle9()
                .withUpgradeGuideSection(8, "deprecate_filtered_configuration_file_and_filecollection_methods")
                .nagUser();

            return Collections.emptySet();
        }

        @Override
        public Set<ResolvedArtifact> getArtifacts() {
            return Collections.emptySet();
        }

        @Override
        @Deprecated
        public Set<ResolvedArtifact> getArtifacts(Spec<? super Dependency> dependencySpec) {
            DeprecationLogger.deprecateMethod(LenientConfiguration.class, "getArtifacts(Spec)")
                .withAdvice("Use a lenient ArtifactView with a componentFilter instead.")
                .willBeRemovedInGradle9()
                .withUpgradeGuideSection(8, "deprecate_filtered_configuration_file_and_filecollection_methods")
                .nagUser();

            return Collections.emptySet();
        }
    }
}
