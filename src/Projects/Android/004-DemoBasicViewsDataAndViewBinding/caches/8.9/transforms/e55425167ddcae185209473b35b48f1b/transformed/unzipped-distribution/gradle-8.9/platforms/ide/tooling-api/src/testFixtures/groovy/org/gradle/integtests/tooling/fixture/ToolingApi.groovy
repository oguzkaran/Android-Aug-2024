/*
 * Copyright 2011 the original author or authors.
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
package org.gradle.integtests.tooling.fixture

import org.apache.commons.io.output.TeeOutputStream
import org.gradle.integtests.fixtures.daemon.DaemonLogsAnalyzer
import org.gradle.integtests.fixtures.daemon.DaemonsFixture
import org.gradle.integtests.fixtures.executer.GradleContextualExecuter
import org.gradle.integtests.fixtures.executer.GradleDistribution
import org.gradle.integtests.fixtures.executer.GradleExecuter
import org.gradle.integtests.fixtures.executer.IntegrationTestBuildContext
import org.gradle.integtests.fixtures.executer.ResultAssertion
import org.gradle.internal.service.DefaultServiceRegistry
import org.gradle.test.fixtures.file.TestDirectoryProvider
import org.gradle.test.fixtures.file.TestFile
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.LongRunningOperation
import org.gradle.tooling.ModelBuilder
import org.gradle.tooling.ProjectConnection
import org.gradle.tooling.internal.consumer.ConnectorServices
import org.gradle.tooling.internal.consumer.DefaultGradleConnector
import org.gradle.tooling.model.build.BuildEnvironment
import org.gradle.util.GradleVersion
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.concurrent.TimeUnit

class ToolingApi implements TestRule {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolingApi)

    private GradleDistribution dist
    private TestDirectoryProvider testWorkDirProvider
    private TestFile gradleUserHomeDir
    private TestFile daemonBaseDir
    private boolean useSeparateDaemonBaseDir
    private boolean requiresDaemon
    private boolean requireIsolatedDaemons
    private DefaultServiceRegistry isolatedToolingClient
    private context = new IntegrationTestBuildContext()

    private final List<Closure> connectorConfigurers = []
    boolean verboseLogging = LOGGER.debugEnabled
    private final OutputStream stdout
    private final OutputStream stderr

    ToolingApi(GradleDistribution dist, TestDirectoryProvider testWorkDirProvider, OutputStream stdout = System.out, OutputStream stderr = System.err) {
        this.stderr = stderr
        this.stdout = stdout
        this.dist = dist
        this.useSeparateDaemonBaseDir = DefaultGradleConnector.metaClass.respondsTo(null, "daemonBaseDir")
        this.gradleUserHomeDir = context.gradleUserHomeDir
        this.daemonBaseDir = context.daemonBaseDir
        this.requiresDaemon = !GradleContextualExecuter.embedded
        this.testWorkDirProvider = testWorkDirProvider
    }

    void setDist(GradleDistribution dist) {
        this.dist = dist
    }

    /**
     * Specifies that the test use its own Gradle user home dir and daemon registry.
     */
    void requireIsolatedUserHome() {
        withUserHome(testWorkDirProvider.testDirectory.file("user-home-dir"))
    }

    GradleExecuter createExecuter() {
        def executer = dist.executer(testWorkDirProvider, context)
            .withGradleUserHomeDir(gradleUserHomeDir)
            .withDaemonBaseDir(daemonBaseDir)
        if (requiresDaemon) {
            executer.requireDaemon()
        }
        return executer
    }

    void withUserHome(TestFile userHomeDir) {
        gradleUserHomeDir = userHomeDir
        useSeparateDaemonBaseDir = false
    }

    TestFile getDaemonBaseDir() {
        return useSeparateDaemonBaseDir ? daemonBaseDir : gradleUserHomeDir.file("daemon")
    }

    void requireIsolatedToolingApi() {
        requireIsolatedDaemons()
        isolatedToolingClient = new ConnectorServices.ConnectorServiceRegistry()
    }

    void close() {
        assert isolatedToolingClient != null
        isolatedToolingClient.close()
    }

    /**
     * Specifies that the test use real daemon processes (not embedded) and a test-specific daemon registry. Uses a shared Gradle user home dir
     */
    void requireIsolatedDaemons() {
        if (useSeparateDaemonBaseDir) {
            daemonBaseDir = testWorkDirProvider.testDirectory.file("daemons")
        } else {
            gradleUserHomeDir = testWorkDirProvider.testDirectory.file("user-home-dir")
        }
        requireIsolatedDaemons = true
        requiresDaemon = true
    }

    /**
     * Specifies that the test use real daemon processes (not embedded).
     */
    void requireDaemons() {
        requiresDaemon = true
    }

    DaemonsFixture getDaemons() {
        return DaemonLogsAnalyzer.newAnalyzer(getDaemonBaseDir(), dist.version.version)
    }

    void withConnector(@DelegatesTo(GradleConnector) Closure cl) {
        connectorConfigurers << cl
    }

    def <T> T withConnection(@DelegatesTo(ProjectConnection) Closure<T> cl) {
        def connector = connector()
        withConnection(connector, cl)
    }

    def <T> T withConnection(connector, @DelegatesTo(ProjectConnection) Closure<T> cl) {
        return withConnectionRaw(connector, cl)
    }

    def <T> T loadToolingLeanModel(Class<T> modelClass, @DelegatesTo(ModelBuilder<T>) Closure configurator = {}) {
        withConnection {
            def builder = it.model(modelClass)
            builder.tap(configurator)
            collectOutputs(builder)
            builder.get()
        }
    }

    def <T> T loadValidatedToolingModel(Class<T> modelClass, @DelegatesTo(ModelBuilder<T>) Closure configurator = {}) {
        def result = loadToolingLeanModel(modelClass, configurator)
        validateOutput()
        result
    }

    void collectOutputs(LongRunningOperation op) {
        op.setStandardOutput(new TeeOutputStream(stdout, System.out))
        op.setStandardError(new TeeOutputStream(stderr, System.err))
    }

    def validateOutput() {
        def assertion = new ResultAssertion(0, [], false, true, true)
        assertion.validate(stdout.toString(), "stdout")
        assertion.validate(stderr.toString(), "stderr")
        true
    }

    private validate(Throwable throwable) {
        if (dist.version != GradleVersion.current()) {
            return
        }

        // Verify that the exception carries the calling thread's stack information
        def currentThreadStack = Thread.currentThread().stackTrace as List
        while (!currentThreadStack.empty && (currentThreadStack[0].className != ToolingApi.name || currentThreadStack[0].methodName != 'withConnectionRaw')) {
            currentThreadStack.remove(0)
        }
        assert currentThreadStack.size() > 1
        currentThreadStack.remove(0)
        String currentThreadStackStr = currentThreadStack.join("\n")

        def throwableStack = throwable.stackTrace.join("\n")

        assert throwableStack.endsWith(currentThreadStackStr)
    }

    private <T> T withConnectionRaw(connector, @DelegatesTo(ProjectConnection) Closure<T> cl) {
        try (def connection = connector.connect()) {
            return connection.with(cl)
        } catch (Throwable t) {
            validate(t)
            throw t
        }
    }

    ToolingApiConnector connector(projectDir = testWorkDirProvider.testDirectory) {
        DefaultGradleConnector connector = createConnector()

        connector.forProjectDirectory(projectDir)

        if (embedded) {
            connector.useClasspathDistribution()
        } else {
            connector.useInstallation(dist.gradleHomeDir.absoluteFile)
        }
        connector.embedded(embedded)

        if (GradleVersion.version(dist.getVersion().version) < GradleVersion.version("6.0")) {
            connector.searchUpwards(false)
        } else {
            // buildSrc builds are allowed to be missing their settings file
            if (projectDir.name != "buildSrc") {
                def settingsFile = projectDir.file('settings.gradle')
                def settingsFileKts = projectDir.file('settings.gradle.kts')
                def settingsFileDcl = projectDir.file('settings.gradle.dcl')
                assert (settingsFile.exists() || settingsFileKts.exists() || settingsFileDcl.exists()): "the build must have a settings file"
            }
        }
        if (useSeparateDaemonBaseDir) {
            connector.daemonBaseDir(new File(daemonBaseDir.path))
        }
        connector.daemonMaxIdleTime(120, TimeUnit.SECONDS)
        if (connector.metaClass.hasProperty(connector, 'verboseLogging')) {
            connector.verboseLogging = verboseLogging
        }

        if (gradleUserHomeDir != context.gradleUserHomeDir) {
            // When using an isolated user home, first initialise the Gradle instance using the default user home dir
            // This sets some static state that uses files from the user home dir, such as DLLs
            connector.useGradleUserHomeDir(new File(context.gradleUserHomeDir.path))

            try (def connection = connector.connect()) {
                connection.getModel(BuildEnvironment.class)
            }
        }

        isolateFromGradleOwnBuild(connector)

        connector.useGradleUserHomeDir(new File(gradleUserHomeDir.path))
        connectorConfigurers.each {
            connector.with(it)
        }

        return new ToolingApiConnector(connector, stdout, stderr)
    }

    private createConnector() {
        if (isolatedToolingClient != null) {
            return isolatedToolingClient.getFactory(DefaultGradleConnector).create()
        }
        return GradleConnector.newConnector() as DefaultGradleConnector
    }

    private void isolateFromGradleOwnBuild(DefaultGradleConnector connector) {
        // override the `user.dir` property in order to isolate tests from the Gradle directory
        try (def connection = connector.connect()) {
            connection.action(new SetWorkingDirectoryAction(testWorkDirProvider.testDirectory.absolutePath))
        }
    }

    /**
     * Only 'current->[some-version]' can run embedded.
     * If running '[other-version]->current' the other Gradle version does not know how to start Gradle from the embedded classpath.
     */
    boolean isEmbedded() {
        // Use in-process build when running tests in embedded mode and daemon is not required
        return GradleContextualExecuter.embedded && !requiresDaemon && GradleVersion.current() == dist.version
    }

    @Override
    Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            void evaluate() throws Throwable {
                try {
                    base.evaluate()
                } finally {
                    cleanUpIsolatedDaemonsAndServices()
                }
            }
        }
    }

    def cleanUpIsolatedDaemonsAndServices() {
        if (isolatedToolingClient != null) {
            isolatedToolingClient.close()
        }
        if (requireIsolatedDaemons) {
            try {
                getDaemons().killAll()
            } catch (RuntimeException ex) {
                //TODO once we figured out why pid from logfile can be null we should remove this again
                LOGGER.warn("Unable to kill daemon(s)", ex)
            }
        }
        if (gradleUserHomeDir != context.gradleUserHomeDir) {
            // When the user home directory is not the default for int tests, then the Gradle instance that was used during the test will still be holding some services open in the user home dir (this is by design), so kill off the Gradle instance that was used.
            // If we ran in embedded mode, shutdown the embedded services
            // If we used the daemons, kill the daemons
            // Either way, this is expensive
            if (embedded) {
                ConnectorServices.reset()
            } else {
                getDaemons().killAll()
            }
        }
    }

}
