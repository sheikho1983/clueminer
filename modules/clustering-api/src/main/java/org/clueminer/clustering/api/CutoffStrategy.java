/*
 * Copyright (C) 2011-2018 clueminer.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.clueminer.clustering.api;

import org.clueminer.dataset.api.Instance;
import org.clueminer.utils.Props;

/**
 *
 * @author Tomas Barton
 * @param <E>
 * @param <C>
 */
public interface CutoffStrategy<E extends Instance, C extends Cluster<E>> {

    /**
     * A human readable name of cutoff strategy
     *
     * @return name of the strategy (must be unique)
     */
    String getName();

    /**
     * Finds optimal dendrogram cutoff according to given strategy
     *
     * @param hclust hierarchical clustering result
     * @param params optional parameter of the method
     * @return tree cutoff
     */
    double findCutoff(HierarchicalResult hclust, Props params);

    /**
     * Set evaluation function
     *
     * @param evaluator
     */
    void setEvaluator(InternalEvaluator<E, C> evaluator);

    /**
     * Measures that require proximity for computation should return TRUE.
     *
     * @return whether proximity matrix is needed for computation
     */
    boolean isProximityRequired();
}
