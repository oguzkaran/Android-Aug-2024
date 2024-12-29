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

package org.gradle.api.problems.internal;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.gradle.api.problems.ProblemReporter;
import org.gradle.internal.operations.CurrentBuildOperationRef;
import org.gradle.internal.service.scopes.Scope;
import org.gradle.internal.service.scopes.ServiceScope;
import org.gradle.problems.buildtree.ProblemStream;

import static org.gradle.api.problems.internal.DefaultProblemCategory.GRADLE_CORE_NAMESPACE;

@ServiceScope(Scope.BuildTree.class)
public class DefaultProblems implements InternalProblems {

    private final ProblemStream problemStream;
    private final CurrentBuildOperationRef currentBuildOperationRef;
    private final ProblemEmitter emitter;
    private final InternalProblemReporter internalReporter;
    private final Multimap<Throwable, Problem> problemsForThrowables = Multimaps.synchronizedMultimap(HashMultimap.<Throwable, Problem>create());

    public DefaultProblems(ProblemEmitter emitter, CurrentBuildOperationRef currentBuildOperationRef) {
        this(emitter, null, currentBuildOperationRef);
    }
    public DefaultProblems(ProblemEmitter emitter) {
        this(emitter, null, CurrentBuildOperationRef.instance());
    }

    public DefaultProblems(ProblemEmitter emitter, ProblemStream problemStream, CurrentBuildOperationRef currentBuildOperationRef) {
        this.emitter = emitter;
        this.problemStream = problemStream;
        this.currentBuildOperationRef = currentBuildOperationRef;
        internalReporter = createReporter(emitter, problemStream, problemsForThrowables);
    }

    @Override
    public ProblemReporter forNamespace(String namespace) {
        if (GRADLE_CORE_NAMESPACE.equals(namespace)) {
            throw new IllegalStateException("Cannot use " + GRADLE_CORE_NAMESPACE + " namespace. Reserved for internal use.");
        }
        return createReporter(emitter, problemStream, problemsForThrowables);
    }

    private DefaultProblemReporter createReporter(ProblemEmitter emitter, ProblemStream problemStream, Multimap<Throwable, Problem> problems) {
        return new DefaultProblemReporter(emitter, problemStream, currentBuildOperationRef, problems);
    }

    @Override
    public InternalProblemReporter getInternalReporter() {
        return internalReporter;
    }

    @Override
    public Multimap<Throwable, Problem> getProblemsForThrowables() {
        return problemsForThrowables;
    }
}
