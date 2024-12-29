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


plugins {
    id("gradlebuild.distribution.api-java")
}

description = "Contains the Test Report Aggregation plugin"

dependencies {
    api(projects.javaLanguageExtensions)
    api(project(":core-api"))
    api(project(":platform-jvm"))

    api(libs.inject)

    implementation(project(":base-services"))
    implementation(project(":core"))
    implementation(project(":plugins-java-base"))
    implementation(project(":plugins-jvm-test-suite"))
    implementation(project(":reporting"))
    implementation(project(":testing-base"))
    implementation(project(":testing-jvm"))
    implementation(project(":test-suites-base"))

    integTestDistributionRuntimeOnly(project(":distributions-jvm"))
}
