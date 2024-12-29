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

package org.gradle.integtests.resolve

import groovy.test.NotYetImplemented
import org.gradle.integtests.fixtures.AbstractIntegrationSpec
import org.gradle.integtests.fixtures.UnsupportedWithConfigurationCache
import org.gradle.test.precondition.Requires
import org.gradle.test.preconditions.UnitTestPreconditions
import spock.lang.Ignore
import spock.lang.Issue

/**
 * Common location for resolution tests that are related to user-reported issues.
 *
 * Once these issues are resolved, we should make sure to  move these tests to a
 * file more appropriate for the feature they are testing.
 */
class ResolutionIssuesIntegrationTest extends AbstractIntegrationSpec {

    @NotYetImplemented
    def "resolution hits isConstraint assertion"() {
        mavenRepo.module("jaxen", "jaxen", "1.1.6").publish()
        mavenRepo.module("dom4j", "dom4j", "1.6.1").publish()

        mavenRepo.module("org.hibernate", "hibernate-core", "5.4.18.Final")
            .dependsOn("org.dom4j", "dom4j", "2.1.3", null, null, null, [[group: "*", module: "*"]])
            .publish()
        mavenRepo.module("jaxen", "jaxen", "1.1.1")
            .dependsOn("dom4j", "dom4j", "1.6.1")
            .publish()
        mavenRepo.module("org.dom4j", "dom4j", "2.1.3").publish()
            .dependsOn("jaxen", "jaxen", "1.1.6")
            .publish()

        buildFile << """
            plugins {
                id("java-library")
            }

            ${mavenTestRepository()}

            configurations.runtimeClasspath {
                resolutionStrategy {
                    capabilitiesResolution {
                        withCapability("org.dom4j:dom4j") {
                            selectHighestVersion()
                        }
                    }
                }
            }

            dependencies.components.withModule('dom4j:dom4j') {
                allVariants {
                    withCapabilities {
                        addCapability('org.dom4j', 'dom4j', id.version)
                    }
                }
            }

            dependencies {
                implementation 'org.hibernate:hibernate-core:5.4.18.Final'
                implementation 'jaxen:jaxen:1.1.1'
            }
        """

        expect:
        succeeds("dependencies", "--configuration", "runtimeClasspath", "--stacktrace")
    }

    @NotYetImplemented
    @Issue("https://github.com/gradle/gradle/issues/14220#issuecomment-1283947029")
    def "resolution result represents failure to resolve dynamic selected module version when platform has constraint on that module"() {
        mavenRepo.module("test", "module1", "11.1.0.1").publish()

        settingsFile << "include 'plat'"
        file("plat/build.gradle") << """
            plugins {
                id("java-platform")
            }

            dependencies {
                constraints {
                    api "test:module1:11.1.0.1"
                }
            }
        """

        buildFile << """
            ${header}
            ${mavenTestRepository()}

            dependencies {
                implementation 'test:module1:11.2.0.+'
                implementation platform(project(":plat"))
            }
        """

        expect:
        succeeds("resolve")
    }

    @NotYetImplemented
    @Issue("https://github.com/gradle/gradle/issues/26145")
    @Issue("https://github.com/ljacomet/logging-capabilities/issues/33")
    def "capability conflict in logging capabilities plugin causes corrupt resolution result"() {
        buildFile << """
            plugins {
                id 'java-library'
                id 'dev.jacomet.logging-capabilities' version '0.11.1'
            }

            ${mavenCentralRepository()}

            dependencies {
                implementation 'eu.medsea.mimeutil:mime-util:2.1.3'
                implementation 'org.slf4j:slf4j-api:2.0.7'
                runtimeOnly 'ch.qos.logback:logback-classic:1.3.11'
            }

            loggingCapabilities {
                enforceLogback()
            }

            tasks.register("resolve") {
                def root = configurations.runtimeClasspath.incoming.resolutionResult.rootComponent
                doLast {
                    println root.get()
                }
            }
        """

        expect:
        succeeds("resolve", "--stacktrace")
    }

    @Ignore("Original reproducer. Minified version below")
    @Requires(UnitTestPreconditions.Jdk17OrLater)
    @Issue("https://github.com/gradle/gradle/issues/26145#issuecomment-1957776331")
    def "original reproducer -- android project causes corrupt serialized resolution result"() {
        settingsFile << """
            pluginManagement {
                ${mavenCentralRepository()}
                repositories {
                    google()
                }
            }

            dependencyResolutionManagement {
                ${mavenCentralRepository()}
                repositories {
                    google()
                }
            }


            rootProject.name = "androidx"

            include(":compose:ui:ui")
            include(":lifecycle:lifecycle-livedata-core")
            include(":lifecycle:lifecycle-runtime")
            include(":lifecycle:lifecycle-viewmodel")
            include(":lifecycle:lifecycle-viewmodel-savedstate")
        """

        propertiesFile << "android.useAndroidX=true"

        buildFile << """
            plugins {
                id("com.android.library") version "8.2.2" apply false
                id("org.jetbrains.kotlin.multiplatform") version "1.9.20" apply false
            }
        """

        file("lifecycle/lifecycle-livedata-core/build.gradle") << """
            plugins {
                id("com.android.library")
            }

            version = "2.8.0-alpha02"
            group = "androidx.lifecycle"

            android {
                namespace "androidx.lifecycle.livedata.core"
                compileSdkVersion "android-34"
            }
        """

        file("lifecycle/lifecycle-runtime/build.gradle") << """
            plugins {
                id("com.android.library")
                id("org.jetbrains.kotlin.multiplatform")
            }

            group = "lifecycle"

            kotlin {
                android()
            }
            android {
                namespace "androidx.lifecycle.runtime"
                compileSdkVersion "android-34"
            }

            configurations {
                groupConstraints
            }

            project.configurations.configureEach { conf ->
                if (conf != configurations.groupConstraints) {
                    conf.extendsFrom(configurations.groupConstraints)
                }
            }

            dependencies {
                constraints {
                    groupConstraints project(":lifecycle:lifecycle-livedata-core")
                    groupConstraints project(":lifecycle:lifecycle-viewmodel")
                    groupConstraints project(":lifecycle:lifecycle-viewmodel-savedstate")
                }
            }
        """

        file("lifecycle/lifecycle-viewmodel-savedstate/build.gradle") << """
            plugins {
                id("com.android.library")
            }

            android {
                namespace "androidx.lifecycle.viewmodel.savedstate"
                compileSdkVersion "android-34"
            }
        """

        file("lifecycle/lifecycle-viewmodel/build.gradle") << """
            plugins {
                id("com.android.library")
                id("org.jetbrains.kotlin.multiplatform")
            }

            version = "2.8.0-alpha02"
            group = "androidx.lifecycle"

            kotlin {
                android()
            }

            android {
                namespace "androidx.lifecycle.viewmodel"
                compileSdkVersion "android-34"
            }

            configurations {
                groupConstraints
            }

            project.configurations.configureEach { conf ->
                if (conf != configurations.groupConstraints) {
                    conf.extendsFrom(configurations.groupConstraints)
                }
            }

            dependencies {
                constraints {
                    groupConstraints project(":lifecycle:lifecycle-livedata-core")
                }
            }
        """

        file("compose/ui/ui/build.gradle") << """
            plugins {
                id("com.android.library")
                id("org.jetbrains.kotlin.multiplatform")
            }

            kotlin {
                android()
                jvm("desktop")
                sourceSets {
                    commonMain {
                        dependencies {
                            implementation(project(":lifecycle:lifecycle-runtime"))
                        }
                    }
                    androidInstrumentedTest {
                        dependencies {
                            implementation("androidx.appcompat:appcompat:1.3.0")
                            implementation("androidx.fragment:fragment-testing:1.4.1")
                        }
                    }
                }
            }

            android {
                namespace "androidx.compose.ui"
                compileSdkVersion "android-34"
            }

            tasks.register("resolve") {
                def root = configurations.commonMainResolvableDependenciesMetadata.incoming.resolutionResult.rootComponent
                doLast {
                    println root.get()
                }
            }
        """

        expect:
        succeeds(":compose:ui:ui:resolve", "--stacktrace")
    }

    @NotYetImplemented
    @Issue("https://github.com/gradle/gradle/issues/26145#issuecomment-1957776331")
    def "partially minimized reproducer -- android project causes corrupt serialized resolution result"() {
        settingsFile << """
            pluginManagement {
                ${mavenCentralRepository()}
                repositories {
                    google()
                }
            }

            dependencyResolutionManagement {
                ${mavenCentralRepository()}
                repositories {
                    google()
                }
            }

            include(":lifecycle:lifecycle-livedata-core")
            include(":lifecycle:lifecycle-viewmodel")
            include(":lifecycle:lifecycle-viewmodel-savedstate")
        """

        file("lifecycle/lifecycle-livedata-core/build.gradle") << """
            version = "2.8.0-alpha02"
            group = "androidx.lifecycle"

            // There is some failure while resolving this component.
            // We can do this many ways, but one way to do it is with an ambiguous variant selection.
            configurations {
                consumable("debugApiElements") {
                    attributes {
                        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage, Usage.JAVA_API))
                        attribute(Attribute.of("build-type", String), "debug")
                    }
                }
                consumable("releaseApiElements") {
                    attributes {
                        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage, Usage.JAVA_API))
                        attribute(Attribute.of("build-type", String), "release")
                    }
                }
            }
        """

        file("lifecycle/lifecycle-viewmodel-savedstate/build.gradle") << """
            version = "2.8.0-alpha02"
            group="androidx.lifecycle"

            // There is some failure while resolving this component.
            // We can do this many ways, but one way to do it is with an ambiguous variant selection.
            configurations {
                consumable("debugApiElements") {
                    attributes {
                        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage, Usage.JAVA_API))
                        attribute(Attribute.of("build-type", String), "debug")
                    }
                }
                consumable("releaseApiElements") {
                    attributes {
                        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage, Usage.JAVA_API))
                        attribute(Attribute.of("build-type", String), "release")
                    }
                }
            }
        """

        file("lifecycle/lifecycle-viewmodel/build.gradle") << """
            version = "2.8.0-alpha02"
            group = "androidx.lifecycle"

            configurations {
                dependencyScope("deps")
                consumable("metadataApiElements") {
                    extendsFrom(deps)
                    attributes {
                        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category, Category.LIBRARY))
                        attribute(TargetJvmEnvironment.TARGET_JVM_ENVIRONMENT_ATTRIBUTE, objects.named(TargetJvmEnvironment, "non-jvm"))
                        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage, "kotlin-metadata"))
                        attribute(Attribute.of("org.jetbrains.kotlin.platform.type", String), "common")
                    }
                }
            }

            dependencies {
                constraints {
                    deps project(":lifecycle:lifecycle-livedata-core")
                }
            }
        """

        buildFile << """
            plugins {
                // This plugin is needed in order to get the proper AttributeSchema compatibility/disambiguation
                // rules. Once we understand this reproducer better, we can likely construct a graph that does not
                // need these resolution rules.
                id("org.jetbrains.kotlin.jvm") version "1.9.20"
            }

            configurations {
                dependencyScope("deps")
                resolvable("res") {
                    extendsFrom(deps)
                    attributes {
                        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category, Category.LIBRARY))
                        attribute(TargetJvmEnvironment.TARGET_JVM_ENVIRONMENT_ATTRIBUTE, objects.named(TargetJvmEnvironment, "non-jvm"))
                        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage, "kotlin-metadata"))
                    }
                }
            }

            dependencies {
                deps "androidx.appcompat:appcompat:1.3.0"
                deps "androidx.fragment:fragment-testing:1.4.1"

                constraints {
                    deps project(":lifecycle:lifecycle-livedata-core")
                    deps project(":lifecycle:lifecycle-viewmodel")
                    deps project(":lifecycle:lifecycle-viewmodel-savedstate")
                }
            }

            tasks.register("resolve") {
                def root = configurations.res.incoming.resolutionResult.rootComponent
                doLast {
                    println root.get()
                }
            }
        """

        expect:
        succeeds(":resolve", "--stacktrace")
//        succeeds(":compose:ui:ui:dependencies", "--configuration", "commonMainResolvableDependenciesMetadata", "--stacktrace")
    }

    @NotYetImplemented
    @Issue("https://github.com/gradle/gradle/issues/14220#issuecomment-1423804572")
    def "capability conflict causes corrupt graph"() {
        buildFile << """
            ${header}
            ${mavenCentralRepository()}

            ${selectHighest("org.dom4j:dom4j")}
            ${withModules("dom4j:dom4j").addCapability("org.dom4j", "dom4j")}
            ${withModules("org.hibernate:hibernate").addCapability("org.hibernate", "hibernate-core")}

            dependencies {
                implementation 'org.hibernate:hibernate-core:5.4.18.Final'
                implementation 'org.hibernate:hibernate-entitymanager:5.4.18.Final'
                implementation 'jaxen:jaxen:1.1.1'
                implementation 'dom4j:dom4j:1.6'
                implementation 'org.unitils:unitils-database:3.3'
            }
        """

        expect:
        succeeds("resolve", "--stacktrace")
    }

    @NotYetImplemented
    def "capability conflict hits assertions in resolution engine 2"() {
        buildFile << """
            ${header}
            ${mavenCentralRepository()}

            ${withModules("org.dom4j:dom4j").addCapability("dom4j", "dom4j")}
            ${selectHighest("dom4j:dom4j")}

            dependencies {
                implementation 'org.hibernate:hibernate-core:5.4.18.Final'
                implementation 'org.hibernate:hibernate-entitymanager:5.4.18.Final'

                implementation ('jaxen:jaxen:1.1.1') {
                    exclude group: 'com.ibm.icu', module: 'icu4j'
                }

                implementation 'org.unitils:unitils-database:3.3'
            }
        """

        expect:
        succeeds(":resolve", "--stacktrace")
    }

    @NotYetImplemented
    def "caability conflict causes cannot decrease hard edge count assertion"() {
        buildFile << """
            ${header}
            ${mavenCentralRepository()}

            ${selectHighest("org.dom4j:dom4j")}
            ${withModules("dom4j:dom4j").addCapability("org.dom4j", "dom4j")}
            ${withModules("org.hibernate:hibernate").addCapability("org.hibernate", "hibernate-core")}

            dependencies {
                implementation 'org.hibernate:hibernate-core:5.4.18.Final'

                implementation ('jaxen:jaxen:1.1.1') {
                    exclude group: 'com.ibm.icu', module: 'icu4j'
                }

                implementation 'org.unitils:unitils-database:3.3'
            }
        """

        expect:
        succeeds(":resolve", "--stacktrace")
    }

    @NotYetImplemented
    @Issue("https://github.com/gradle/gradle/issues/14220#issuecomment-1967573024")
    def "conflict resolution causes graph to lose track of declared first-level dependencies"() {
        buildFile << """
            plugins {
                id("java-library")
            }

            ${mavenCentralRepository()}

            ${withModules(
            "org.bouncycastle:bcprov-jdk14",
            "org.bouncycastle:bcprov-jdk18on",
        ).addCapability("foo", "bcprov")}
            ${withModules(
            "org.bouncycastle:bctls-fips",
            "org.bouncycastle:bctls-jdk14",
            "org.bouncycastle:bctls-jdk18on",
        ).addCapability("foo", "bctls")}
            ${selectHighest("foo:bcprov")}
            ${selectHighest("foo:bctls")}

            dependencies {
                implementation("org.bouncycastle:bcprov-jdk14:1.70")
                implementation("org.bouncycastle:bcprov-jdk18on:1.71")
                implementation("org.bouncycastle:bctls-fips:1.0.9")
                implementation("org.bouncycastle:bctls-jdk14:1.70")
                implementation("org.bouncycastle:bctls-jdk18on:1.72")
            }
        """

        when:
        succeeds(":dependencies", "--configuration", "runtimeClasspath")

        then:
        [
            "org.bouncycastle:bcprov-jdk14:1.70",
            "org.bouncycastle:bcprov-jdk18on:1.71",
            "org.bouncycastle:bctls-fips:1.0.9",
            "org.bouncycastle:bctls-jdk14:1.70",
            "org.bouncycastle:bctls-jdk18on:1.72"
        ].each {
            assert output.readLines().any {
                it.startsWith("+--- ${it}")
            }
        }
    }

    @NotYetImplemented
    @Issue("https://github.com/gradle/gradle/issues/22326")
    def "capability conflict skip"() {
        settingsFile << """
            include("app", "extension")

            dependencyResolutionManagement {
                ${mavenCentralRepository()}
                components {
                    withModule("org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec") {
                        allVariants {
                            withCapabilities {
                                addCapability("javax.transaction", "javax.transaction-api", id.version)
                            }
                        }
                    }
                }
            }
        """

        file("app/build.gradle") << """
            plugins {
                id("java-library")
            }

            dependencies {
                // Change the order of these two dependencies to have the capability conflict detected
                runtimeOnly("org.liquibase.ext:liquibase-hibernate5:4.4.3")
                runtimeOnly("org.eclipse.jetty.aggregate:jetty-all:9.4.35.v20201120")
            }
        """

        file("extension/build.gradle") << """
            plugins {
                id("java-library")
            }

            configurations.all {
                resolutionStrategy.capabilitiesResolution.withCapability("javax.transaction:javax.transaction-api") {
                    select("javax.transaction:javax.transaction-api:0")
                }
            }

            dependencies {
                implementation("org.hibernate:hibernate-core:5.5.7.Final")
                implementation(project(":app"))
            }
        """

        when:
        succeeds(":extension:dependencies", "--configuration=runtimeClasspath")

        then:
        outputContains("org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final -> javax.transaction:javax.transaction-api:1.3")
    }

    @Ignore("Original reproducer. Minified version below")
    @Requires(UnitTestPreconditions.Jdk17OrLater)
    @Issue("https://github.com/gradle/gradle/issues/22326#issuecomment-1617422240")
    def "guava issue"() {
        settingsFile << """
            pluginManagement {
                ${mavenCentralRepository()}
                repositories {
                    google()
                    maven { url 'https://jitpack.io' }
                }
            }

            dependencyResolutionManagement {
                ${mavenCentralRepository()}
                repositories {
                    google()
                    maven { url 'https://jitpack.io' }
                }
            }
        """

        propertiesFile << "android.useAndroidX=true"

        file("src/main/AndroidManifest.xml") << """<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"></manifest>
"""

        buildFile << '''
            plugins {
                id("com.android.application") version "8.2.2"
            }

            android {
                namespace 'org.zephyrsoft.trackworktime'
                compileSdkVersion 33
            }

            dependencies {
                implementation 'androidx.appcompat:appcompat:1.6.1'
                implementation "ch.acra:acra-http:5.10.1"
                implementation 'com.google.guava:guava:32.1.1-android'
            }
        '''

        expect:
        succeeds("checkDebugDuplicateClasses")
    }

    @NotYetImplemented
    @Issue("https://github.com/gradle/gradle/issues/22326#issuecomment-1617422240")
    def "guava issue -- minimized"() {
        // We shadow the real android libraries with java libraries so that we don't need to
        // apply the android plugin.
        def androidxCore = mavenRepo.module("androidx.core", "core", "1.8.0")
            .dependsOn("androidx.concurrent", "concurrent-futures", "1.1.0")
            .publish()
        mavenRepo.module("androidx.activity", "activity", "1.6.0")
            .dependsOn(androidxCore)
            .publish()

        mavenRepo.module("ch.acra", "acra-http", "5.10.1")
            .dependsOn("com.google.auto", "auto-common", "1.2.1")
            .publish()

        settingsFile << """
            pluginManagement {
                ${mavenCentralRepository()}
            }

            dependencyResolutionManagement {
                ${mavenTestRepository()}
                ${mavenCentralRepository()}
                repositories {
                    google()
                }
            }
        """

        buildFile << '''
            plugins {
                id("java-library")
            }

            dependencies {
                implementation 'androidx.activity:activity:1.6.0'
                implementation "ch.acra:acra-http:5.10.1"
                implementation 'com.google.guava:guava:32.1.1-android'
            }

            task checkDebugDuplicateClasses {
                def files = configurations.runtimeClasspath
                doLast {
                    assert files*.name.contains("guava-32.1.1-jre.jar")
                    assert !files*.name.contains("listenablefuture-1.0.jar")
                }
            }
        '''

        expect:
        succeeds("checkDebugDuplicateClasses")
    }

    @NotYetImplemented
    def "corrupt serialized resolution result"() {
        buildFile << """
            plugins {
                id("de.jjohannes.java-ecosystem-capabilities") version "0.8"
                id("java-library")
            }

            ${mavenCentralRepository()}

            dependencies {
                implementation("org.codehaus.woodstox:wstx-asl:4.0.6")
                implementation("javax.xml.stream:stax-api:1.0")
                implementation("org.codehaus.woodstox:wstx-lgpl:3.2.9")
                implementation("org.codehaus.woodstox:woodstox-core-asl:4.4.1")
                implementation("stax:stax-api:1.0.1")
                implementation("woodstox:wstx-asl:2.9.3")
                implementation("org.codehaus.woodstox:woodstox-core-lgpl:4.4.0")
            }
            repositories {
                mavenCentral()
            }
        """

        expect:
        succeeds("dependencies", "--configuration", "runtimeClasspath", "--stacktrace")
    }

    @NotYetImplemented
    @UnsupportedWithConfigurationCache(because = "Uses allDependencies")
    @Issue("https://github.com/gradle/gradle/pull/26016#issuecomment-1795491970")
    def "conflict between two nodes in the same component causes edge without target node"() {
        settingsFile << "include 'producer'"
        file("producer/build.gradle") << """
            configurations {
                consumable("one") {
                    outgoing {
                        capability('o:n:e')
                    }
                    attributes {
                        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.class, "foo"))
                    }
                }
                consumable("one-preferred") {
                    outgoing {
                        capability('o:n:e')
                        capability('g:one-preferred:v')
                    }
                    attributes {
                        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.class, "foo"))
                    }
                }
            }
        """
        buildFile << """
            configurations {
                dependencyScope("implementation")
                resolvable("classpath") {
                    extendsFrom(implementation)
                    attributes {
                        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.class, "foo"))
                    }
                }
            }

            configurations.classpath {
                resolutionStrategy.capabilitiesResolution.all { details ->
                    def selection =
                        details.candidates.find { it.variantName.endsWith('preferred') }
                    println("Selecting \$selection from \${details.candidates}")
                    details.select(selection)
                }
            }

            dependencies {
                implementation(project(':producer')) {
                    capabilities {
                        requireCapability('o:n:e')
                    }
                }
                implementation(project(':producer')) {
                    capabilities {
                        requireCapability('o:n:e')
                        requireCapability('g:one-preferred:v')
                    }
                }
            }

            task resolve {
                def result = configurations.classpath.incoming.resolutionResult
                doLast {
                    result.allDependencies {
                        assert it.selectedVariant != null
                    }
                }
            }
        """

        expect:
        succeeds("resolve")
    }

    def getHeader() {
        """
            plugins {
                id("jvm-ecosystem")
            }

            configurations {
                dependencyScope("implementation")
                resolvable("runtimeClasspath") {
                    extendsFrom implementation
                }
            }

            tasks.register('resolve') {
                def root = configurations.runtimeClasspath.incoming.resolutionResult.rootComponent
                doLast {
                    println root.get()
                }
            }
        """
    }

    def selectHighest(String module) {
        """
            configurations.runtimeClasspath {
                resolutionStrategy {
                    capabilitiesResolution {
                        withCapability("$module") {
                            selectHighestVersion()
                        }
                    }
                }
            }
        """
    }

    def withModules(String... modules) {
        return new Object() {
            def addCapability(String group, String module) {
                modules.collect {
                    """
                        dependencies.components.withModule('$it') {
                            allVariants {
                                withCapabilities {
                                    addCapability('$group', '$module', id.version)
                                }
                            }
                        }
                    """
                }.join("\n")
            }
        }
    }
}
