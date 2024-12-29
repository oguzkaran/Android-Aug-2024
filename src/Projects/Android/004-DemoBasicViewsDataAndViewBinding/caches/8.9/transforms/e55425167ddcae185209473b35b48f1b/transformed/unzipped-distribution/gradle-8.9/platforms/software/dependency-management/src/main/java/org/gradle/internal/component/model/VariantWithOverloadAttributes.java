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

package org.gradle.internal.component.model;

import org.gradle.api.internal.attributes.ImmutableAttributes;

public class VariantWithOverloadAttributes implements VariantResolveMetadata.Identifier {
    private final VariantResolveMetadata.Identifier variantIdentifier;
    private final ImmutableAttributes targetVariant;
    private final int hashCode;

    public VariantWithOverloadAttributes(VariantResolveMetadata.Identifier variantIdentifier, ImmutableAttributes targetVariant) {
        this.variantIdentifier = variantIdentifier;
        this.targetVariant = targetVariant;
        this.hashCode = computeHashCode();
    }

    private int computeHashCode() {
        return variantIdentifier.hashCode() ^ targetVariant.hashCode();
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        VariantWithOverloadAttributes other = (VariantWithOverloadAttributes) obj;
        return variantIdentifier.equals(other.variantIdentifier) && targetVariant.equals(other.targetVariant);
    }
}
