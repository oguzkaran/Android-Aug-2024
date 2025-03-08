/*
 * Copyright 2016 the original author or authors.
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

package org.gradle.api.internal.artifacts.transform;

import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.internal.artifacts.ResolverResults;
import org.gradle.api.internal.artifacts.configurations.ResolutionHost;
import org.gradle.api.internal.artifacts.configurations.ResolutionResultProvider;
import org.gradle.api.internal.attributes.ImmutableAttributes;
import org.gradle.api.internal.attributes.immutable.ImmutableAttributesSchema;
import org.gradle.operations.dependencies.configurations.ConfigurationIdentity;

import javax.annotation.Nullable;

public interface VariantSelectorFactory {

    /**
     * Returns a selector that selects using variant aware attribute matching and performs
     * artifact transforms if the requested artifact variant is not available.
     */
    ArtifactVariantSelector create(
        ResolutionHost resolutionHost,
        ImmutableAttributes requestAttributes,
        ImmutableAttributesSchema consumerSchema,
        @Nullable ConfigurationIdentity configurationId,
        ResolutionStrategy.SortOrder artifactDependencySortOrder,
        ResolutionResultProvider<ResolverResults> resolverResults,
        ResolutionResultProvider<ResolverResults> strictResolverResults
    );

}
