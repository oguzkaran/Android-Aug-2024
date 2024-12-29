/*
 * Copyright 2020 the original author or authors.
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

package org.gradle.internal.jvm.inspection;

import org.gradle.api.JavaVersion;
import org.gradle.internal.os.OperatingSystem;
import org.gradle.internal.serialization.Cached;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

public interface JvmInstallationMetadata {

    enum JavaInstallationCapability {
        JAVA_COMPILER, J9_VIRTUAL_MACHINE
    }

    static DefaultJvmInstallationMetadata from(
        File javaHome,
        String javaVersion,
        String javaVendor,
        String runtimeName,
        String runtimeVersion,
        String jvmName,
        String jvmVersion,
        String jvmVendor,
        String architecture
    ) {
        return new DefaultJvmInstallationMetadata(javaHome, javaVersion, javaVendor, runtimeName, runtimeVersion, jvmName, jvmVersion, jvmVendor, architecture);
    }

    static JvmInstallationMetadata failure(File javaHome, String errorMessage) {
        return new FailureInstallationMetadata(javaHome, errorMessage, null);
    }

    static JvmInstallationMetadata failure(File javaHome, Exception cause) {
        return new FailureInstallationMetadata(javaHome, cause.getMessage(), cause);
    }

    Path getJavaHome();

    /**
     * Parsed equivalent of {@link #getJavaVersion()}.
     */
    JavaVersion getLanguageVersion();

    /**
     * A wrapper around the raw value of the toolchain vendor.
     * <p>
     * @see org.gradle.internal.jvm.inspection.ProbedSystemProperty#JAVA_VENDOR
     */
    JvmVendor getVendor();

    /**
     * @see org.gradle.internal.jvm.inspection.ProbedSystemProperty#JAVA_VERSION
     */
    String getJavaVersion();

    /**
     * @see org.gradle.internal.jvm.inspection.ProbedSystemProperty#RUNTIME_NAME
     */
    String getRuntimeName();

    /**
     * @see org.gradle.internal.jvm.inspection.ProbedSystemProperty#RUNTIME_VERSION
     */
    String getRuntimeVersion();

    /**
     * @see org.gradle.internal.jvm.inspection.ProbedSystemProperty#VM_NAME
     */
    String getJvmName();

    /**
     * @see org.gradle.internal.jvm.inspection.ProbedSystemProperty#VM_VERSION
     */
    String getJvmVersion();

    /**
     * @see org.gradle.internal.jvm.inspection.ProbedSystemProperty#VM_VENDOR
     */
    String getJvmVendor();

    /**
     * @see org.gradle.internal.jvm.inspection.ProbedSystemProperty#OS_ARCH
     */
    String getArchitecture();

    String getDisplayName();

    boolean hasCapability(JavaInstallationCapability capability);

    String getErrorMessage();

    Throwable getErrorCause();

    boolean isValidInstallation();

    class DefaultJvmInstallationMetadata implements JvmInstallationMetadata {

        private final Path javaHome;
        private final JavaVersion languageVersion;
        private final String javaVersion;
        private final String javaVendor;
        private final String runtimeName;
        private final String runtimeVersion;
        private final String jvmName;
        private final String jvmVersion;
        private final String jvmVendor;
        private final String architecture;

        private final Cached<Set<JavaInstallationCapability>> capabilities = Cached.of(this::gatherCapabilities);

        private DefaultJvmInstallationMetadata(
            File javaHome,
            String javaVersion,
            String javaVendor,
            String runtimeName,
            String runtimeVersion,
            String jvmName,
            String jvmVersion,
            String jvmVendor,
            String architecture
        ) {
            this.javaHome = javaHome.toPath();
            this.languageVersion = JavaVersion.toVersion(javaVersion);
            this.javaVersion = javaVersion;
            this.javaVendor = javaVendor;
            this.runtimeName = runtimeName;
            this.runtimeVersion = runtimeVersion;
            this.jvmName = jvmName;
            this.jvmVersion = jvmVersion;
            this.jvmVendor = jvmVendor;
            this.architecture = architecture;
        }

        @Override
        public Path getJavaHome() {
            return javaHome;
        }

        @Override
        public JavaVersion getLanguageVersion() {
            return languageVersion;
        }

        @Override
        public JvmVendor getVendor() {
            return JvmVendor.fromString(javaVendor);
        }

        @Override
        public String getJavaVersion() {
            return javaVersion;
        }

        @Override
        public String getRuntimeName() {
            return runtimeName;
        }

        @Override
        public String getRuntimeVersion() {
            return runtimeVersion;
        }

        @Override
        public String getJvmName() {
            return jvmName;
        }

        @Override
        public String getJvmVersion() {
            return jvmVersion;
        }

        @Override
        public String getJvmVendor() {
            return jvmVendor;
        }

        @Override
        public String getArchitecture() {
            return architecture;
        }

        @Override
        public String getDisplayName() {
            final String vendor = determineVendorName();
            String installationType = determineInstallationType(vendor);
            return MessageFormat.format("{0}{1}", vendor, installationType);
        }

        private String determineVendorName() {
            JvmVendor.KnownJvmVendor vendor = getVendor().getKnownVendor();
            if (vendor == JvmVendor.KnownJvmVendor.ORACLE) {
                if (jvmName != null && jvmName.contains("OpenJDK")) {
                    return "OpenJDK";
                }
            }
            return getVendor().getDisplayName();
        }

        private String determineInstallationType(String vendor) {
            if (hasCapability(JavaInstallationCapability.JAVA_COMPILER)) {
                if (!vendor.toLowerCase().contains("jdk")) {
                    return " JDK";
                }
                return "";
            }
            return " JRE";
        }

        @Override
        public boolean hasCapability(JavaInstallationCapability capability) {
            return capabilities.get().contains(capability);
        }

        private Set<JavaInstallationCapability> gatherCapabilities() {
            final Set<JavaInstallationCapability> capabilities = new HashSet<>(2);
            final File javaCompiler = new File(new File(javaHome.toFile(), "bin"), OperatingSystem.current().getExecutableName("javac"));
            if (javaCompiler.exists()) {
                capabilities.add(JavaInstallationCapability.JAVA_COMPILER);
            }
            boolean isJ9vm = jvmName.contains("J9");
            if (isJ9vm) {
                capabilities.add(JavaInstallationCapability.J9_VIRTUAL_MACHINE);
            }
            return capabilities;
        }

        @Override
        public String getErrorMessage() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Throwable getErrorCause() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isValidInstallation() {
            return true;
        }

        @Override
        public String toString() {
            return "DefaultJvmInstallationMetadata{" +
                    "languageVersion=" + languageVersion +
                    ", javaVersion='" + javaVersion + '\'' +
                    ", javaVendor='" + javaVendor + '\'' +
                    ", runtimeName='" + runtimeName + '\'' +
                    ", runtimeVersion='" + runtimeVersion + '\'' +
                    ", jvmName='" + jvmName + '\'' +
                    ", jvmVersion='" + jvmVersion + '\'' +
                    ", jvmVendor='" + jvmVendor + '\'' +
                    ", architecture='" + architecture + '\'' +
                    '}';
        }
    }

    class FailureInstallationMetadata implements JvmInstallationMetadata {

        private final File javaHome;
        private final String errorMessage;
        @Nullable
        private final Exception cause;

        private FailureInstallationMetadata(File javaHome, String errorMessage, @Nullable Exception cause) {
            this.javaHome = javaHome;
            this.errorMessage = errorMessage;
            this.cause = cause;
        }

        @Override
        public Path getJavaHome() {
            return javaHome.toPath();
        }

        @Override
        public JavaVersion getLanguageVersion() {
            throw unsupportedOperation();
        }

        @Override
        public JvmVendor getVendor() {
            throw unsupportedOperation();
        }

        @Override
        public String getJavaVersion() {
            throw unsupportedOperation();
        }

        @Override
        public String getRuntimeName() {
            throw unsupportedOperation();
        }

        @Override
        public String getRuntimeVersion() {
            throw unsupportedOperation();
        }

        @Override
        public String getJvmName() {
            throw unsupportedOperation();
        }

        @Override
        public String getJvmVersion() {
            throw unsupportedOperation();
        }

        @Override
        public String getJvmVendor() {
            throw unsupportedOperation();
        }

        @Override
        public String getArchitecture() {
            throw unsupportedOperation();
        }

        @Override
        public String getDisplayName() {
            return "Invalid installation: " + getErrorMessage();
        }

        @Override
        public boolean hasCapability(JavaInstallationCapability capability) {
            return false;
        }

        private UnsupportedOperationException unsupportedOperation() {
            return new UnsupportedOperationException("Installation is not valid. Original error message: " + getErrorMessage());
        }

        @Override
        public String getErrorMessage() {
            return errorMessage;
        }

        @Override
        public Throwable getErrorCause() {
            return cause;
        }

        @Override
        public boolean isValidInstallation() {
            return false;
        }
    }

}
