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

package org.gradle.interal.buildconfiguration.tasks

import groovy.test.NotYetImplemented
import org.gradle.api.JavaVersion
import org.gradle.buildconfiguration.tasks.UpdateDaemonJvm
import org.gradle.integtests.fixtures.AbstractIntegrationSpec
import org.gradle.integtests.fixtures.AvailableJavaHomes
import org.gradle.internal.buildconfiguration.DaemonJvmPropertiesDefaults
import org.gradle.internal.buildconfiguration.fixture.DaemonJvmPropertiesFixture
import org.gradle.internal.jvm.Jvm
import org.gradle.test.precondition.Requires
import org.gradle.test.preconditions.IntegTestPreconditions

class UpdateDaemonJvmIntegrationTest extends AbstractIntegrationSpec implements DaemonJvmPropertiesFixture {

    def "root project has an updateDaemonJvm task only"() {
        buildFile << """
            def updateDaemonJvm = tasks.named("updateDaemonJvm").get()
            assert updateDaemonJvm instanceof ${UpdateDaemonJvm.class.name}
            assert updateDaemonJvm.description == "Generates or updates the Gradle Daemon JVM criteria."
        """
        settingsFile << """
            rootProject.name = 'root'
            include("sub")
        """
        file("sub").mkdir()

        expect:
        succeeds("help", "--task", "updateDaemonJvm")
        fails(":sub:updateDaemonJvm") // should not exist
    }

    def "When execute updateDaemonJvm without options Then build properties are populated with default values"() {
        when:
        run "updateDaemonJvm"

        then:
        assertJvmCriteria(Jvm.current().javaVersion)
        outputContains("Daemon JVM criteria is an incubating feature.")
    }

    def "When execute updateDaemonJvm for valid version Then build properties are populated with expected values"() {
        when:
        run "updateDaemonJvm", "--jvm-version=${version.majorVersion}"

        then:
        assertJvmCriteria(version)

        where:
        version << [JavaVersion.VERSION_11, JavaVersion.VERSION_15, JavaVersion.VERSION_HIGHER]
    }

    def "When execute updateDaemonJvm for valid Java 8 versions Then build properties are populated with expected values"() {
        when:
        run "updateDaemonJvm", "--jvm-version=${version}"

        then:
        assertJvmCriteria(JavaVersion.VERSION_1_8)

        where:
        version << ["1.8", "8"]
    }

    def "When execute updateDaemonJvm with invalid argument --jvm-version option Then fails with expected exception message"() {
        when:
        fails "updateDaemonJvm", "--jvm-version=$invalidVersion"

        then:
        failureDescriptionContains("Problem configuring option 'jvm-version' on task ':updateDaemonJvm' from command line.")
        failureHasCause("Could not determine Java version from '${invalidVersion}'")

        where:
        invalidVersion << ["0", "-10", 'asdf']
    }

    def "When execute updateDaemonJvm with unsupported Java version Then fails with expected exception message"() {
        when:
        fails "updateDaemonJvm", "--jvm-version=7"

        then:
        failureDescriptionContains("Execution failed for task ':updateDaemonJvm'")
        failureHasCause("Unsupported Java version '7' provided for the 'jvm-version' option. Gradle can only run with Java 8 and above.")
    }

    def "When execute updateDaemonJvm with unsupported future Java version"() {
        // Captures current, but maybe not desired behavior
        expect:
        succeeds( "updateDaemonJvm", "--jvm-version=10000")
    }

    @NotYetImplemented
    def "When execute updateDaemonJvm for valid vendor option Then build properties are populated with expected values"() {
        when:
        run "updateDaemonJvm", "--toolchain-vendor=$vendor"

        then:
        assertJvmCriteria(DaemonJvmPropertiesDefaults.TOOLCHAIN_VERSION, vendor)

        where:
        vendor << ["ADOPTIUM", "ADOPTOPENJDK", "AMAZON", "APPLE", "AZUL", "BELLSOFT", "GRAAL_VM", "HEWLETT_PACKARD", "IBM", "JETBRAINS", "MICROSOFT", "ORACLE", "SAP", "TENCENT", "UNKNOWN"]
    }

    @NotYetImplemented
    def "When execute updateDaemonJvm for valid implementation option Then build properties are populated with expected values"() {
        when:
        run "updateDaemonJvm", "--toolchain-implementation=$implementation"

        then:
        assertJvmCriteria(DaemonJvmPropertiesDefaults.TOOLCHAIN_VERSION, null, implementation)

        where:
        implementation << ["VENDOR_SPECIFIC", "J9"]
    }

    @NotYetImplemented
    def "When execute updateDaemonJvm specifying different options Then build properties are populated with expected values"() {
        when:
        run "updateDaemonJvm", "--jvm-version=17", "--toolchain-vendor=IBM", "--toolchain-implementation=J9"

        then:
        assertJvmCriteria(JavaVersion.VERSION_17, "IBM", "J9")
    }

    @NotYetImplemented
    def "When execute updateDaemonJvm specifying different options in lower case Then build properties are populated with expected values"() {
        when:
        run "updateDaemonJvm", "--jvm-version=17", "--toolchain-vendor=ibm", "--toolchain-implementation=j9"

        then:
        assertJvmCriteria(JavaVersion.VERSION_17, "IBM", "J9")
    }

    @NotYetImplemented
    def "When execute updateDaemonJvm with unexpected --toolchain-vendor option Then fails with expected exception message"() {
        when:
        fails "updateDaemonJvm", "--toolchain-vendor=unknown-vendor"

        then:
        failureDescriptionContains("Problem configuring option 'toolchain-vendor' on task ':updateDaemonJvm' from command line.")
        failureHasCause("Cannot convert string value 'unknown-vendor' to an enum value of type 'org.gradle.internal.jvm.inspection.JvmVendor\$KnownJvmVendor' " +
            "(valid case insensitive values: ADOPTIUM, ADOPTOPENJDK, AMAZON, APPLE, AZUL, BELLSOFT, GRAAL_VM, HEWLETT_PACKARD, IBM, JETBRAINS, MICROSOFT, ORACLE, SAP, TENCENT, UNKNOWN)")
    }

    @NotYetImplemented
    def "When execute updateDaemonJvm with unexpected --toolchain-implementation option Then fails with expected exception message"() {
        when:
        fails "updateDaemonJvm", "--toolchain-implementation=unknown-implementation"

        then:
        failureDescriptionContains("Problem configuring option 'toolchain-implementation' on task ':updateDaemonJvm' from command line.")
        failureHasCause("Cannot convert string value 'unknown-implementation' to an enum value of type 'org.gradle.jvm.toolchain.JvmImplementation' " +
            "(valid case insensitive values: VENDOR_SPECIFIC, J9)")
    }

    def "Given already existing build properties When execute updateDaemonJvm with different criteria Then criteria get modified"() {
        given:
        def otherJvm = AvailableJavaHomes.differentVersion
        writeJvmCriteria(Jvm.current())

        when:
        run "updateDaemonJvm", "--jvm-version=${otherJvm.javaVersion.majorVersion}"

        then:
        assertJvmCriteria(otherJvm.javaVersion)
    }

    @NotYetImplemented
    def "Given defined invalid criteria When execute updateDaemonJvm with different criteria Then criteria get modified using java home"() {
        def currentJvm = JavaVersion.current()

        given:
        writeJvmCriteria(currentJvm, "invalidVendor")

        expect:
        succeeds("updateDaemonJvm", "--jvm-version=20", "--toolchain-vendor=AZUL")
        assertJvmCriteria(JavaVersion.VERSION_20, "AZUL")
    }

    @NotYetImplemented
    @Requires(IntegTestPreconditions.JavaHomeWithDifferentVersionAvailable)
    def "Given defined valid criteria matching with local toolchain When execute updateDaemonJvm with different criteria Then criteria get modified using the expected local toolchain"() {
        def otherJvm = AvailableJavaHomes.differentVersion
        def otherMetadata = AvailableJavaHomes.getJvmInstallationMetadata(otherJvm)

        given:
        writeJvmCriteria(otherJvm.javaVersion, otherMetadata.vendor.knownVendor.name())

        expect:
        succeeds("updateDaemonJvm", "--jvm-version=20", "--toolchain-vendor=AZUL")
        assertJvmCriteria(JavaVersion.VERSION_20, "AZUL")
    }
}
