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
package org.clueminer.clustering.api.dendrogram;

import java.util.EventListener;
import org.clueminer.clustering.api.HierarchicalResult;

public interface TreeListener extends EventListener {

    /**
     * Invoked when a new cluster is selected.
     *
     * @param source
     * @param cluster
     * @param data
     */
    void clusterSelected(DendrogramTree source, TreeCluster cluster, DendrogramMapping data);

    /**
     * Called when tree changes its size
     *
     * @param source
     * @param width
     * @param height
     */
    void treeUpdated(DendrogramTree source, int width, int height);

    /**
     * Triggered when tree order optimization finishes
     *
     * @param source
     * @param mapping
     */
    void leafOrderUpdated(Object source, HierarchicalResult mapping);
}
