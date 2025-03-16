/*
 * Copyright 2018 the original author or authors.
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

@file:Suppress(
    "unused",
    "nothing_to_inline",
    "useless_cast",
    "unchecked_cast",
    "extension_shadowed_by_member",
    "redundant_projection",
    "RemoveRedundantBackticks",
    "ObjectPropertyName",
    "deprecation",
    "detekt:all"
)
@file:org.gradle.api.Generated

package org.gradle.kotlin.dsl

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec


/**
 * The `com` plugin group.
 */
@org.gradle.api.Generated
class `ComPluginGroup`(internal val plugins: PluginDependenciesSpec)


/**
 * Plugin ids starting with `com`.
 */
val `PluginDependenciesSpec`.`com`: `ComPluginGroup`
    get() = `ComPluginGroup`(this)


/**
 * The `com.gradle` plugin group.
 */
@org.gradle.api.Generated
class `ComGradlePluginGroup`(internal val plugins: PluginDependenciesSpec)


/**
 * Plugin ids starting with `com.gradle`.
 */
val `ComPluginGroup`.`gradle`: `ComGradlePluginGroup`
    get() = `ComGradlePluginGroup`(plugins)


/**
 * The `com.gradle.build-scan` plugin implemented by [com.gradle.scan.plugin.BuildScanPlugin].
 */
val `ComGradlePluginGroup`.`build-scan`: PluginDependencySpec
    get() = plugins.id("com.gradle.build-scan")


/**
 * The `com.gradle.develocity` plugin implemented by [com.gradle.develocity.agent.gradle.DevelocityPlugin].
 */
val `ComGradlePluginGroup`.`develocity`: PluginDependencySpec
    get() = plugins.id("com.gradle.develocity")


/**
 * The `com.gradle.enterprise` plugin implemented by [com.gradle.enterprise.gradleplugin.GradleEnterprisePlugin].
 */
val `ComGradlePluginGroup`.`enterprise`: PluginDependencySpec
    get() = plugins.id("com.gradle.enterprise")
