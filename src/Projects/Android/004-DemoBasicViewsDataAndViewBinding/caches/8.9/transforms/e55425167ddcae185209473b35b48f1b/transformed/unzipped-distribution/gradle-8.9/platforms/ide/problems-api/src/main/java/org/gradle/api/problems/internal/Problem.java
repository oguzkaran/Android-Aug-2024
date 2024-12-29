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

package org.gradle.api.problems.internal;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Interface for describing structured information about a problem.
 */
public interface Problem {

    /**
     * Returns the problem definition, i.e. the data that is independent of the report context.
     */
    ProblemDefinition getDefinition();

    @Nullable
    String getContextualLabel();

    /**
     * Returns solutions and advice that contain context-sensitive data, e.g. the message contains references to variables, locations, etc.
     */
    List<String> getSolutions();

    /**
     * A long description detailing the problem.
     * <p>
     * Details can elaborate on the problem, and provide more information about the problem.
     * They can be multiple lines long, but should not detail solutions; for that, use {@link #getSolutions()}.
     */
    @Nullable
    String getDetails();

    /**
     * Return the location data associated available for this problem.
     */
    List<ProblemLocation> getLocations();

    /**
     * The exception that caused the problem.
     */
    @Nullable
    RuntimeException getException();

    /**
     * Additional data attached to the problem.
     * <p>
     * The supported types are listed on {@link AdditionalData}.
     */
    @Nullable
    AdditionalData getAdditionalData();

    /**
     * Returns a problem builder with fields initialized with values from this instance.
     */
    InternalProblemBuilder toBuilder();
}
