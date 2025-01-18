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
package org.gradle.problems.internal.impl

import org.gradle.api.internal.DocumentationRegistry
import org.gradle.api.internal.StartParameterInternal
import org.gradle.api.internal.file.temp.TemporaryFileProvider
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.problems.internal.FileLocation
import org.gradle.api.problems.internal.Problem
import org.gradle.internal.buildoption.InternalOptions
import org.gradle.internal.cc.impl.problems.BuildNameProvider
import org.gradle.internal.cc.impl.problems.JsonSource
import org.gradle.internal.cc.impl.problems.JsonWriter
import org.gradle.internal.concurrent.ExecutorFactory
import org.gradle.internal.configuration.problems.CommonReport
import org.gradle.internal.configuration.problems.FailureDecorator
import org.gradle.internal.configuration.problems.StructuredMessage
import org.gradle.internal.configuration.problems.writeError
import org.gradle.internal.configuration.problems.writeStructuredMessage
import org.gradle.internal.logging.ConsoleRenderer
import org.gradle.internal.operations.OperationIdentifier
import org.gradle.internal.problems.failure.FailureFactory
import org.gradle.problems.buildtree.ProblemReporter
import org.gradle.problems.internal.ProblemReportCreator
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

val logger: Logger = Logging.getLogger(DefaultProblemsReportCreator::class.java)

class DefaultProblemsReportCreator(
    executorFactory: ExecutorFactory,
    temporaryFileProvider: TemporaryFileProvider,
    internalOptions: InternalOptions,
    startParameter: StartParameterInternal,
    private val failureFactory: FailureFactory,
    private val buildNameProvider: BuildNameProvider
) : ProblemReportCreator {

    private val report = CommonReport(executorFactory, temporaryFileProvider, internalOptions, "problem report", "problem-report")
    private val taskNames: List<String> = startParameter.taskNames
    private val problemCount = AtomicInteger(0)

    private val failureDecorator = FailureDecorator()

    override fun getId(): String {
        return "DefaultProblemsReportCreator"
    }

    override fun report(reportDir: File, validationFailures: ProblemReporter.ProblemConsumer) {
        report.writeReportFileTo(reportDir, object : JsonSource {
            override fun writeToJson(jsonWriter: JsonWriter) {
                with(jsonWriter) {
                    property("reportContext", "problem report")
                    property("totalProblemCount", problemCount.toString())
                    buildNameProvider.buildName()?.let { property("buildName", it) }
                    property("requestedTasks", taskNames.joinToString(" "))
                    property("documentationLink", DocumentationRegistry().getDocumentationFor("problem-report"))
                    property("documentationLinkCaption", "Problem report")
                }
            }
        })?.let {
            val url = ConsoleRenderer().asClickableFileUrl(it)
            logger.warn("Problems report is available at: $url")
        }
    }

    override fun emit(problem: Problem, id: OperationIdentifier?) {
        problemCount.incrementAndGet()
        report.onProblem(object : JsonSource {
            override fun writeToJson(jsonWriter: JsonWriter) {
                with(jsonWriter) {
                    jsonObject {
                        property("trace") {
                            jsonObjectList(listOf(getFirstFileLocation())) { path ->
                                property("kind", "Project")
                                property("path", path)
                            }
                        }

                        property("problem") {
                            writeStructuredMessage(StructuredMessage.forText(problem.definition.id.displayName))
                        }
                        problem.details?.let {
                            property("problemDetails") {
                                writeStructuredMessage(
                                    StructuredMessage.Builder()
                                        .text(it).build()
                                )
                            }
                        }
                        problem.definition.documentationLink?.let {
                            property("documentationLink", it.url)
                        }
                        problem.exception?.let {
                            writeError(failureDecorator.decorate(failureFactory.create(it)))
                        }
                    }
                }
            }

            private fun getFirstFileLocation(): String {
                return problem.locations
                    .find { it is FileLocation }
                    ?.let { it as FileLocation }?.path ?: "<N/A>"
            }
        })
    }
}
