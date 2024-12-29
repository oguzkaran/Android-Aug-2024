/*
 * Copyright 2024 the original author or authors.
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

package org.gradle.internal.component.resolution.failure.exception;

import org.gradle.api.internal.artifacts.transform.AttributeMatchingArtifactVariantSelector;
import org.gradle.internal.component.resolution.failure.type.ResolutionFailure;

import java.util.List;

/**
 * Represents a failure during variant selection when a variant of a component cannot be selected
 * by the {@link AttributeMatchingArtifactVariantSelector AttributeMatchingArtifactVariantSelector}.
 *
 * Note: Temporarily non-{@code final}, so long as {@link org.gradle.internal.component.AmbiguousVariantSelectionException} is not yet removed.
 */
@SuppressWarnings("deprecation")
public class ArtifactVariantSelectionException extends AbstractResolutionFailureException {
    public ArtifactVariantSelectionException(String message, ResolutionFailure failure, List<String> resolutions) {
        super(message, failure, resolutions);
    }

    public ArtifactVariantSelectionException(String message, ResolutionFailure failure, List<String> resolutions, Throwable cause) {
        super(message, failure, resolutions, cause);
    }
}
