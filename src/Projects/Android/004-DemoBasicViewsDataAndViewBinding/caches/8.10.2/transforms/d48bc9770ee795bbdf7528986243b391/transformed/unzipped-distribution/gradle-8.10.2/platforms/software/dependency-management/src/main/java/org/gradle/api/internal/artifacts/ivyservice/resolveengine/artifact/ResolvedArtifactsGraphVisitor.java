/*
 * Copyright 2015 the original author or authors.
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

package org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.graph.DependencyGraphEdge;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.graph.DependencyGraphNode;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.graph.DependencyGraphVisitor;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.graph.RootGraphNode;
import org.gradle.api.internal.artifacts.type.ArtifactTypeRegistry;
import org.gradle.api.internal.attributes.AttributesSchemaInternal;
import org.gradle.internal.component.local.model.LocalFileDependencyMetadata;
import org.gradle.internal.component.model.ComponentGraphResolveState;
import org.gradle.internal.component.model.GraphVariantSelector;
import org.gradle.internal.component.model.VariantGraphResolveState;
import org.gradle.internal.model.CalculatedValueContainerFactory;
import org.gradle.internal.resolve.resolver.ArtifactResolver;
import org.gradle.internal.resolve.resolver.DefaultVariantArtifactResolver;
import org.gradle.internal.resolve.resolver.VariantArtifactResolver;

import java.util.function.LongFunction;

/**
 * Adapts a {@link DependencyArtifactsVisitor} to a {@link DependencyGraphVisitor}. Calculates the artifacts contributed by each edge in the graph and forwards the results to the artifact visitor.
 */
public class ResolvedArtifactsGraphVisitor implements DependencyGraphVisitor {
    private int nextId;
    private final Long2ObjectMap<ArtifactsForNode> artifactsByNodeId = new Long2ObjectOpenHashMap<>();
    private final DependencyArtifactsVisitor artifactResults;
    private final ArtifactTypeRegistry artifactTypeRegistry;
    private final CalculatedValueContainerFactory calculatedValueContainerFactory;
    private final VariantArtifactResolver variantResolver;
    private final GraphVariantSelector graphVariantSelector;
    private final AttributesSchemaInternal consumerSchema;

    public ResolvedArtifactsGraphVisitor(
        DependencyArtifactsVisitor artifactsBuilder,
        ArtifactTypeRegistry artifactTypeRegistry,
        CalculatedValueContainerFactory calculatedValueContainerFactory,
        ArtifactResolver artifactResolver,
        ResolvedVariantCache resolvedVariantCache,
        GraphVariantSelector graphVariantSelector,
        AttributesSchemaInternal consumerSchema
    ) {
        this.artifactResults = artifactsBuilder;
        this.artifactTypeRegistry = artifactTypeRegistry;
        this.calculatedValueContainerFactory = calculatedValueContainerFactory;
        this.variantResolver = new DefaultVariantArtifactResolver(artifactResolver, artifactTypeRegistry, resolvedVariantCache);
        this.graphVariantSelector = graphVariantSelector;
        this.consumerSchema = consumerSchema;
    }

    @Override
    public void visitNode(DependencyGraphNode node) {
        artifactResults.visitNode(node);
    }

    @Override
    public void visitEdges(DependencyGraphNode node) {
        boolean hasTransitiveIncomingEdge = false;
        for (DependencyGraphEdge dependency : node.getIncomingEdges()) {
            hasTransitiveIncomingEdge |= dependency.isTransitive();
            if (dependency.contributesArtifacts()) {
                DependencyGraphNode parent = dependency.getFrom();
                ArtifactsForNode artifacts = resolveVariantArtifacts(dependency, node);
                artifactResults.visitArtifacts(parent, node, artifacts.artifactSetId, artifacts.artifactSet);
            }
        }

        if (node.isRoot() || hasTransitiveIncomingEdge) {
            // Since file dependencies are not modeled as actual edges, we need to verify
            // there are edges to this node that would follow this file dependency.
            for (LocalFileDependencyMetadata fileDependency : node.getOutgoingFileEdges()) {
                int id = nextId++;
                artifactResults.visitArtifacts(node, fileDependency, id, new FileDependencyArtifactSet(fileDependency, artifactTypeRegistry, calculatedValueContainerFactory));
            }
        }
    }

    @Override
    public void finish(RootGraphNode root) {
        artifactResults.finishArtifacts(root);
        artifactsByNodeId.clear();
    }

    private ArtifactsForNode resolveVariantArtifacts(DependencyGraphEdge dependency, DependencyGraphNode toNode) {
        ComponentGraphResolveState component = toNode.getOwner().getResolveState();
        VariantGraphResolveState variant = toNode.getResolveState();

        // Do not share an ArtifactSet if the artifacts are modified by the dependency.
        if (!dependency.getDependencyMetadata().getArtifacts().isEmpty() ||
            !dependency.getAttributes().isEmpty() ||
            dependency.getExclusions().mayExcludeArtifacts()
        ) {
            int id = nextId++;
            return new ArtifactsForNode(id, new VariantResolvingArtifactSet(variantResolver, component, variant, dependency, graphVariantSelector, consumerSchema));
        }

        return artifactsByNodeId.computeIfAbsent(toNode.getNodeId(), (LongFunction<ArtifactsForNode>) value -> {
            int id = nextId++;
            return new ArtifactsForNode(id, new VariantResolvingArtifactSet(variantResolver, component, variant, dependency, graphVariantSelector, consumerSchema));
        });
    }

    private static class ArtifactsForNode {
        private final int artifactSetId;
        private final ArtifactSet artifactSet;

        ArtifactsForNode(int artifactSetId, ArtifactSet artifactSet) {
            this.artifactSetId = artifactSetId;
            this.artifactSet = artifactSet;
        }
    }
}
