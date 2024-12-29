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

package org.gradle.internal.buildtree;

import org.gradle.api.internal.GradleInternal;
import org.gradle.api.internal.project.ProjectState;
import org.gradle.internal.build.BuildState;
import org.gradle.tooling.provider.model.internal.ToolingModelScope;

import java.util.List;
import java.util.function.Supplier;

public interface BuildTreeModelController {
    /**
     * Returns the mutable model, configuring if necessary.
     */
    GradleInternal getConfiguredModel();

    ToolingModelScope locateBuilderForDefaultTarget(String modelName, boolean param);

    ToolingModelScope locateBuilderForTarget(BuildState target, String modelName, boolean param);

    ToolingModelScope locateBuilderForTarget(ProjectState target, String modelName, boolean param);

    boolean queryModelActionsRunInParallel();

    /**
     * Runs the given actions, possibly in parallel.
     *
     * @see #queryModelActionsRunInParallel()
     */
    <T> List<T> runQueryModelActions(List<Supplier<T>> actions);
}
