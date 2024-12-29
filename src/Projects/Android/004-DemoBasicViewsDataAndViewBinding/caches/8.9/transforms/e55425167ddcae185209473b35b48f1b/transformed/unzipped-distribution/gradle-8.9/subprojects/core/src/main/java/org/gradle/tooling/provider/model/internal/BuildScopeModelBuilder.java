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

package org.gradle.tooling.provider.model.internal;

import org.gradle.internal.build.BuildState;

public interface BuildScopeModelBuilder {
    /**
     * Creates the model for the given target. The target build will not necessarily have been configured.
     * This method is responsible for transitioning the target into the appropriate state required to create the model.
     *
     * No synchronization is applied to the target, so this method may be called for a given target concurrently by multiple threads.
     * Other threads may also be doing work with the target when this method is called.
     * This method is responsible for any synchronization required to create the model.
     */
    Object create(BuildState target);
}
