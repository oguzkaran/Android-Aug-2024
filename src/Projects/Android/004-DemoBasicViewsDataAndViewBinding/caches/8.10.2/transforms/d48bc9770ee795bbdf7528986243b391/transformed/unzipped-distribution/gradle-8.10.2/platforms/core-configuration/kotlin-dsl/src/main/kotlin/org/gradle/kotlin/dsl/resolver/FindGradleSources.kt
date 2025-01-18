/*
 * Copyright 2017 the original author or authors.
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

package org.gradle.kotlin.dsl.resolver

import org.gradle.api.artifacts.transform.InputArtifact
import org.gradle.api.artifacts.transform.TransformAction
import org.gradle.api.artifacts.transform.TransformOutputs
import org.gradle.api.artifacts.transform.TransformParameters
import org.gradle.api.file.FileSystemLocation
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.IgnoreEmptyDirectories
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.support.unzipTo
import org.gradle.work.DisableCachingByDefault
import java.io.File


/**
 * This dependency transform is responsible for extracting the sources from
 * a downloaded ZIP of the Gradle sources, and will return the list of main sources
 * subdirectories for all subprojects.
 */
@DisableCachingByDefault(because = "Only filters the input artifact")
internal
abstract class FindGradleSources : TransformAction<TransformParameters.None> {
    @get:IgnoreEmptyDirectories
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:InputArtifact
    abstract val input: Provider<FileSystemLocation>

    override fun transform(outputs: TransformOutputs) {
        registerSourceDirectories(outputs)
    }

    private
    fun registerSourceDirectories(outputs: TransformOutputs) {
        unzippedProjectDirectories()
            .flatMap { projectDir -> subDirsOf(projectDir.resolve("src/main")) }
            .forEach { outputs.dir(it) }
    }

    private
    fun unzippedProjectDirectories(): Collection<File> =
        unzippedDistroDir()?.let { distroDir ->
            unzippedSubprojectsDirectories(distroDir) + unzippedPlatformProjectsDirectories(distroDir)
        } ?: emptyList()

    private
    fun unzippedSubprojectsDirectories(distroDir: File): Collection<File> =
        subDirsOf(distroDir.resolve("subprojects"))

    private
    fun unzippedPlatformProjectsDirectories(distroDir: File): Collection<File> =
        subDirsOf(distroDir.resolve("platforms"))
            .flatMap { platform -> subDirsOf(platform) }

    private
    fun unzippedDistroDir(): File? =
        input.get().asFile.listFiles().singleOrNull()
}


@DisableCachingByDefault(because = "Not worth caching")
internal
abstract class UnzipDistribution : TransformAction<TransformParameters.None> {
    @get:PathSensitive(PathSensitivity.NONE)
    @get:InputArtifact
    abstract val input: Provider<FileSystemLocation>

    override fun transform(outputs: TransformOutputs) {
        unzipTo(outputs.dir("unzipped-distribution"), input.get().asFile)
    }
}
