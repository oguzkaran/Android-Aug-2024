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

package org.gradle.internal.component.resolution.failure.type;

/**
 * An abstract {@link ResolutionFailure} that represents the situation when a configuration is requested
 * by name on a project dependency and does not exist on the target project.
 */
public abstract class AbstractConfigurationSelectionFailure implements ResolutionFailure {
    private final String requestedConfigurationName;
    private final String requestedComponentName;

    public AbstractConfigurationSelectionFailure(String requestedConfigurationName, String requestedComponentName) {
        this.requestedConfigurationName = requestedConfigurationName;
        this.requestedComponentName = requestedComponentName;
    }

    @Override
    public String getRequestedName() {
        return requestedConfigurationName;
    }

    public String getRequestedComponentDisplayName() {
        return requestedComponentName;
    }
}
