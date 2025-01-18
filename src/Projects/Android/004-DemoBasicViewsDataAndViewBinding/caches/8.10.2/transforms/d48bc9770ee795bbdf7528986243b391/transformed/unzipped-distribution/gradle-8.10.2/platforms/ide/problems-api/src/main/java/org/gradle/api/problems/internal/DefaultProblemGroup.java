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

package org.gradle.api.problems.internal;

import org.gradle.api.Incubating;
import org.gradle.api.problems.ProblemGroup;

import javax.annotation.Nullable;
import java.io.Serializable;

@Incubating
public class DefaultProblemGroup implements ProblemGroup, Serializable {

    private final String name;
    private final String displayName;
    private final ProblemGroup parent;

    public DefaultProblemGroup(String groupId, String displayName) {
        this(groupId, displayName, null);
    }

    public DefaultProblemGroup(String name, String displayName, @Nullable ProblemGroup parent) {
        this.name = name;
        this.displayName = displayName;
        this.parent = parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Nullable
    @Override
    public ProblemGroup getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass().isAssignableFrom(ProblemGroup.class)) {
            return false;
        }

        ProblemGroup that = (ProblemGroup) o;

        if (!name.equals(that.getName())) {
            return false;
        }
        return parent != null ? parent.equals(that.getParent()) : that.getParent() == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        return result;
    }
}
