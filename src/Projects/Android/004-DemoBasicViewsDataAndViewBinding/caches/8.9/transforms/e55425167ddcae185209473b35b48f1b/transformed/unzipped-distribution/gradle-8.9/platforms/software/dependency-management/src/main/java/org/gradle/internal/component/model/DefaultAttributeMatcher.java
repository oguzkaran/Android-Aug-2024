/*
 * Copyright 2016 the original author or authors.
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.primitives.Ints;
import org.gradle.api.attributes.Attribute;
import org.gradle.api.attributes.HasAttributes;
import org.gradle.api.internal.attributes.AttributeContainerInternal;
import org.gradle.api.internal.attributes.AttributeValue;
import org.gradle.api.internal.attributes.ImmutableAttributes;
import org.gradle.internal.Cast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * An {@link AttributeMatcher}, which optimizes for the case of only comparing 0 or 1 candidates
 * and delegates to {@link MultipleCandidateMatcher} for all other cases.
 */
public class DefaultAttributeMatcher implements AttributeMatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAttributeMatcher.class);

    private final AttributeSelectionSchema schema;

    /**
     * Attribute matching can be very expensive. In case there are multiple candidates, we
     * cache the result of the query, because it's often the case that we ask for the same
     * disambiguation of attributes several times in a row (but with different candidates).
     */
    private final ConcurrentMap<CachedQuery, int[]> cachedQueries = new ConcurrentHashMap<>();

    public DefaultAttributeMatcher(AttributeSelectionSchema schema) {
        this.schema = schema;
    }

    @Override
    public AttributeSelectionSchema getSelectionSchema() {
        return schema;
    }

    @Override
    public boolean isMatching(AttributeContainerInternal candidate, AttributeContainerInternal requested) {
        if (requested.isEmpty() || candidate.isEmpty()) {
            return true;
        }

        ImmutableAttributes requestedAttributes = requested.asImmutable();
        ImmutableAttributes candidateAttributes = candidate.asImmutable();

        for (Attribute<?> attribute : requestedAttributes.keySet()) {
            AttributeValue<?> requestedValue = requestedAttributes.findEntry(attribute);
            AttributeValue<?> candidateValue = candidateAttributes.findEntry(attribute.getName());
            if (candidateValue.isPresent()) {
                Object coercedValue = candidateValue.coerce(attribute);
                boolean match = schema.matchValue(attribute, requestedValue.get(), coercedValue);
                if (!match) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public <T> boolean isMatching(Attribute<T> attribute, T candidate, T requested) {
        return schema.matchValue(attribute, requested, candidate);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<AttributeMatcher.MatchingDescription<?>> describeMatching(AttributeContainerInternal candidate, AttributeContainerInternal requested) {
        if (requested.isEmpty() || candidate.isEmpty()) {
            return Collections.emptyList();
        }

        ImmutableAttributes requestedAttributes = requested.asImmutable();
        ImmutableAttributes candidateAttributes = candidate.asImmutable();

        ImmutableSet<Attribute<?>> attributes = requestedAttributes.keySet();
        List<AttributeMatcher.MatchingDescription<?>> result = new ArrayList<>(attributes.size());
        for (Attribute<?> attribute : attributes) {
            AttributeValue<?> requestedValue = requestedAttributes.findEntry(attribute);
            AttributeValue<?> candidateValue = candidateAttributes.findEntry(attribute.getName());
            if (candidateValue.isPresent()) {
                Object coercedValue = candidateValue.coerce(attribute);
                boolean match = schema.matchValue(attribute, requestedValue.get(), coercedValue);
                result.add(new AttributeMatcher.MatchingDescription(attribute, requestedValue, candidateValue, match));
            } else {
                result.add(new AttributeMatcher.MatchingDescription(attribute, requestedValue, candidateValue, false));
            }
        }
        return result;
    }

    @Override
    public <T extends HasAttributes> List<T> matches(Collection<? extends T> candidates, AttributeContainerInternal requested, AttributeMatchingExplanationBuilder explanationBuilder) {
        if (candidates.size() == 0) {
            explanationBuilder.noCandidates(requested);
            return ImmutableList.of();
        }

        if (candidates.size() == 1) {
            T candidate = candidates.iterator().next();
            if (isMatching((AttributeContainerInternal) candidate.getAttributes(), requested)) {
                explanationBuilder.singleMatch(candidate, candidates, requested);
                return Collections.singletonList(candidate);
            }
            explanationBuilder.candidateDoesNotMatchAttributes(candidate, requested);
            return ImmutableList.of();
        }

        ImmutableAttributes requestedAttributes = requested.asImmutable();
        List<T> candidateList = (candidates instanceof List) ? Cast.uncheckedCast(candidates) : ImmutableList.copyOf(candidates);

        // Often times, collections of candidates will themselves differ even though their attributes are the same.
        // Disambiguating two different candidate lists which map to the same attribute lists in reality performs
        // the same work, so instead we cache disambiguation results based on the attributes being disambiguated.
        // The result of this is a list of indices into the original candidate list from which the
        // attributes-to-disambiguate are derived. When retrieving a result from the cache, we use the resulting
        // indices to index back into the original candidates list.
        CachedQuery query = CachedQuery.from(requestedAttributes, candidateList);
        int[] indices = cachedQueries.compute(query, (key, value) -> {
            if (value == null || !explanationBuilder.canSkipExplanation()) {
                int[] matches = new MultipleCandidateMatcher<>(schema, candidateList, requestedAttributes, explanationBuilder).getMatches();
                LOGGER.debug("Selected matches {} from candidates {} for {}", Ints.asList(matches), candidateList, requested);
                return matches;
            }
            return value;
        });

        return CachedQuery.getMatchesFromCandidateIndices(indices, candidateList);
    }

    private static class CachedQuery {
        private final ImmutableAttributes requestedAttributes;
        private final ImmutableAttributes[] candidates;
        private final int hashCode;

        private CachedQuery(ImmutableAttributes requestedAttributes, ImmutableAttributes[] candidates) {
            this.requestedAttributes = requestedAttributes;
            this.candidates = candidates;
            this.hashCode = computeHashCode(requestedAttributes, candidates);
        }

        private static int computeHashCode(ImmutableAttributes requestedAttributes, ImmutableAttributes[] candidates) {
            int hash = requestedAttributes.hashCode();
            for (ImmutableAttributes candidate : candidates) {
                hash = 31 * hash + candidate.hashCode();
            }
            return hash;
        }

        public static <T extends HasAttributes> CachedQuery from(ImmutableAttributes requestedAttributes, List<T> candidates) {
            ImmutableAttributes[] attributes = new ImmutableAttributes[candidates.size()];
            for (int i = 0; i < candidates.size(); i++) {
                attributes[i] = ((AttributeContainerInternal) candidates.get(i).getAttributes()).asImmutable();
            }
            return new CachedQuery(requestedAttributes, attributes);
        }

        public static <T extends HasAttributes> List<T> getMatchesFromCandidateIndices(int[] indices, List<? extends T> candidates) {
            if (indices.length == 0) {
                return Collections.emptyList();
            }

            List<T> matches = new ArrayList<>(indices.length);
            for (int index : indices) {
                matches.add(candidates.get(index));
            }

            return matches;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CachedQuery that = (CachedQuery) o;
            return hashCode == that.hashCode &&
                requestedAttributes.equals(that.requestedAttributes) &&
                Arrays.equals(candidates, that.candidates);
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public String toString() {
            return "CachedQuery{" +
                "requestedAttributes=" + requestedAttributes +
                ", candidates=" + Arrays.toString(candidates) +
                '}';
        }
    }
}
