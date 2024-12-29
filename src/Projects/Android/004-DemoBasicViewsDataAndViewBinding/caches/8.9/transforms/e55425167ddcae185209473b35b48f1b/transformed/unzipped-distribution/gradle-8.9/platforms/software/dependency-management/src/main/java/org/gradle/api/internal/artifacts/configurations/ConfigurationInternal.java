/*
 * Copyright 2023 the original author or authors.
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
package org.gradle.api.internal.artifacts.configurations;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ExcludeRule;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.internal.artifacts.ResolveContext;
import org.gradle.api.internal.attributes.AttributeContainerInternal;
import org.gradle.api.internal.attributes.ImmutableAttributes;
import org.gradle.internal.DisplayName;
import org.gradle.internal.deprecation.DeprecatableConfiguration;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

public interface ConfigurationInternal extends ResolveContext, DeprecatableConfiguration, Configuration {
    enum InternalState {
        UNRESOLVED,
        BUILD_DEPENDENCIES_RESOLVED,
        GRAPH_RESOLVED,

        // This state should be removed, but it is referenced by nebula gradle-resolution-rules-plugin.
        // https://github.com/nebula-plugins/gradle-resolution-rules-plugin/blob/623bbbcd4f187101bc233e46c4d9ec960c02e1a7/src/main/kotlin/nebula/plugin/resolutionrules/configurations.kt#L62
        @Deprecated
        ARTIFACTS_RESOLVED
    }

    String getDisplayName();

    @Override
    AttributeContainerInternal getAttributes();

    /**
     * Runs any registered dependency actions for this Configuration, and any parent Configuration.
     * Actions may mutate the dependency set for this configuration.
     * After execution, all actions are de-registered, so execution will only occur once.
     */
    void runDependencyActions();

    void markAsObserved(InternalState requestedState);

    void addMutationValidator(MutationValidator validator);

    void removeMutationValidator(MutationValidator validator);

    /**
     * Visits the variants of this configuration.
     */
    void collectVariants(VariantVisitor visitor);

    boolean isCanBeMutated();

    /**
     * Gets the complete set of exclude rules including those contributed by
     * superconfigurations.
     */
    Set<ExcludeRule> getAllExcludeRules();

    /**
     * @implSpec Usage: This method should only be called on resolvable configurations and should throw an exception if
     * called on a configuration that does not permit this usage.
     */
    @Nullable
    ConfigurationInternal getConsistentResolutionSource();

    /**
     * Test if this configuration can either be declared against or extends another
     * configuration which can be declared against.
     *
     * @return {@code true} if so; {@code false} otherwise
     */
    default boolean isDeclarableByExtension() {
        return isDeclarableByExtension(this);
    }

    /**
     * Returns the role used to create this configuration and set its initial allowed usage.
     */
    ConfigurationRole getRoleAtCreation();

    /**
     * Indicates if the allowed usages of this configuration (consumable, resolvable, declarable) can be changed.
     *
     * @return {@code true} if so; {@code false} otherwise
     */
    boolean usageCanBeMutated();

    /**
     * Update a configuration's allowed and disallowed usage to match the given role
     *
     * This method does <strong>NOT</strong> warn.  This method does <strong>NOT</strong> modify deprecation status.  It
     * is only meant to be called by the container.
     *
     * @param role the role specifying the usage the conf should possess
     */
     void setAllowedUsageFromRole(ConfigurationRole role);

    /**
     * Test if the given configuration can either be declared against or extends another
     * configuration which can be declared against.
     * This method should probably be made {@code private} when upgrading to Java 9.
     *
     * @param configuration the configuration to test
     * @return {@code true} if so; {@code false} otherwise
     */
    static boolean isDeclarableByExtension(ConfigurationInternal configuration) {
        if (configuration.isCanBeDeclared()) {
            return true;
        } else {
            return configuration.getExtendsFrom().stream()
                    .map(ConfigurationInternal.class::cast)
                    .anyMatch(ci -> ci.isDeclarableByExtension());
        }
    }

    interface VariantVisitor {
        // This configuration as a variant. May not always be present
        void visitOwnVariant(DisplayName displayName, ImmutableAttributes attributes, Collection<? extends PublishArtifact> artifacts);

        // A child variant. May not always be present
        void visitChildVariant(String name, DisplayName displayName, ImmutableAttributes attributes, Collection<? extends PublishArtifact> artifacts);
    }
}
