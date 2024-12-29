/*
 * Copyright 2019 the original author or authors.
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

package org.gradle.internal.reflect.annotations;

import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

public interface PropertyAnnotationMetadata extends Comparable<PropertyAnnotationMetadata> {
    Method getGetter();

    String getPropertyName();

    boolean isAnnotationPresent(Class<? extends Annotation> annotationType);

    <T extends Annotation> Optional<T> getAnnotation(Class<T> annotationType);

    ImmutableMap<AnnotationCategory, Annotation> getAnnotations();

    TypeToken<?> getDeclaredType();

    @Nullable
    Object getPropertyValue(Object object);
}
