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

package org.gradle.testing.base.plugins;

import org.gradle.api.Incubating;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.testing.base.TestingExtension;
import org.gradle.testing.base.internal.DefaultTestingExtension;

/**
 * Base test suite functionality. Makes an extension named "testing" available to the project.
 *
 * @since 7.3
 */
@Incubating
public abstract class TestSuiteBasePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getExtensions().create(TestingExtension.class, "testing", DefaultTestingExtension.class);
    }
}
