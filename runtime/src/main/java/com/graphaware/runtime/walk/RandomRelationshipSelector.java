/*
 * Copyright (c) 2014 GraphAware
 *
 * This file is part of GraphAware.
 *
 * GraphAware is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.graphaware.runtime.walk;

import com.graphaware.common.strategy.*;
import com.graphaware.common.util.ReservoirSampler;
import com.graphaware.runtime.strategy.IncludeAllBusinessNodes;
import com.graphaware.runtime.strategy.IncludeAllBusinessRelationships;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

/**
 * {@link RelationshipSelector} that selects a {@link org.neo4j.graphdb.Relationship} at random from all the given
 * {@link org.neo4j.graphdb.Node}'s {@link org.neo4j.graphdb.Relationship}s, such that match the selected
 * {@link org.neo4j.graphdb.Relationship} matches the provided {@link com.graphaware.common.strategy.RelationshipInclusionStrategy}.
 * <p/>
 * This is an O(n) algorithm.
 */
public class RandomRelationshipSelector implements RelationshipSelector {

    private final NodeCentricRelationshipInclusionStrategy relationshipInclusionStrategy;

    /**
     * Constructs a new {@link RandomRelationshipSelector} that selects any relationship that isn't
     * framework-internal and doesn't link to a framework-internal node.
     */
    public RandomRelationshipSelector() {
        this(IncludeAllBusinessRelationships.getInstance());
    }

    /**
     * Constructs a new {@link RandomRelationshipSelector} that chooses relationships in accordance with the given
     * {@link com.graphaware.common.strategy.ObjectInclusionStrategy}.
     *
     * @param relationshipInclusionStrategy The {@link com.graphaware.common.strategy.ObjectInclusionStrategy} used to select relationships to follow.
     */
    public RandomRelationshipSelector(NodeCentricRelationshipInclusionStrategy relationshipInclusionStrategy) {
        this.relationshipInclusionStrategy = relationshipInclusionStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Relationship selectRelationship(Node node) {
        ReservoirSampler<Relationship> randomSampler = new ReservoirSampler<>(1);
        for (Relationship relationship : node.getRelationships()) {
            if (this.relationshipInclusionStrategy.include(relationship, node)) {
                randomSampler.sample(relationship);
            }
        }

        if (randomSampler.isEmpty()) {
            return null;
        }

        return randomSampler.getSamples().iterator().next();
    }

}
