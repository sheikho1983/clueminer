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
package org.clueminer.clustering.aggl;

import org.clueminer.attributes.BasicAttrType;
import org.clueminer.cluster.FakeClustering;
import org.clueminer.clustering.aggl.linkage.AverageLinkage;
import org.clueminer.clustering.aggl.linkage.CompleteLinkage;
import org.clueminer.clustering.aggl.linkage.SingleLinkage;
import org.clueminer.clustering.api.AlgParams;
import org.clueminer.clustering.api.ClusteringType;
import org.clueminer.clustering.api.HierarchicalResult;
import org.clueminer.clustering.api.dendrogram.DendroNode;
import org.clueminer.clustering.api.dendrogram.DendroTreeData;
import org.clueminer.dataset.api.Dataset;
import org.clueminer.dataset.api.Instance;
import org.clueminer.dataset.impl.ArrayDataset;
import org.clueminer.math.Matrix;
import org.clueminer.utils.PropType;
import org.clueminer.utils.Props;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author deric
 */
public class HCLWTest {

    private final HCLW subject = new HCLW();
    private static final double DELTA = 1e-9;

    private Dataset<? extends Instance> simpleData() {
        Dataset<Instance> data = new ArrayDataset<>(4, 2);
        data.attributeBuilder().create("x", BasicAttrType.NUMERIC);
        data.attributeBuilder().create("y", BasicAttrType.NUMERIC);
        data.builder().create(new double[]{0, 0}, "A");
        data.builder().create(new double[]{1, 3}, "B");
        data.builder().create(new double[]{2, 2}, "C");
        data.builder().create(new double[]{2, 1}, "D");
        return data;
    }

    @Test
    public void testUpdateProximity() {
        Dataset<? extends Instance> dataset = simpleData();
        Props pref = new Props();
        pref.put(AlgParams.LINKAGE, SingleLinkage.name);
        pref.put(AlgParams.CLUSTERING_TYPE, ClusteringType.ROWS_CLUSTERING);
        pref.put(PropType.PERFORMANCE, AlgParams.KEEP_PROXIMITY, true);
        HierarchicalResult result = subject.hierarchy(dataset, pref);
        Matrix similarityMatrix = result.getProximityMatrix();
        assertNotNull(similarityMatrix);
        assertEquals(similarityMatrix.rowsCount(), dataset.size());
        assertEquals(similarityMatrix.columnsCount(), dataset.size());
        System.out.println("simple data - 4 points");
        similarityMatrix.printLower(5, 2);
        result.getTreeData().print();
    }

    @Test
    public void testSingleLinkage() {
        Dataset<? extends Instance> dataset = FakeClustering.kumarData();
        assertEquals(6, dataset.size());
        Props pref = new Props();
        pref.put(AlgParams.LINKAGE, SingleLinkage.name);
        pref.put(AlgParams.CLUSTERING_TYPE, ClusteringType.ROWS_CLUSTERING);
        pref.put(PropType.PERFORMANCE, AlgParams.KEEP_PROXIMITY, true);
        HierarchicalResult result = subject.hierarchy(dataset, pref);
        Matrix similarityMatrix = result.getProximityMatrix();
        assertNotNull(similarityMatrix);
        assertEquals(similarityMatrix.rowsCount(), dataset.size());
        assertEquals(similarityMatrix.columnsCount(), dataset.size());
        System.out.println("kumar - single");
        DendroTreeData tree = result.getTreeData();
        tree.print();
        //kumar - single
        //                         /----- #1 - 2
        //                 /----- #7 (0.14)
        //                 |       \----- #4 - 5
        //         /----- #8 (0.14)
        //         |       |       /----- #2 - 3
        //         |       \----- #6 (0.10)
        //         |               \----- #5 - 6
        // /----- #9 (0.16)
        // |       \----- #3 - 4
        //#10 (0.22)
        // \----- #0 - 1
        assertEquals(dataset.size(), tree.numLeaves());
        DendroNode root = tree.getRoot();
        assertEquals(0.21587033144922907, root.getHeight(), DELTA);
    }

    @Test
    public void testSingleLinkageSchool() {
        Dataset<? extends Instance> dataset = FakeClustering.schoolData();
        assertEquals(17, dataset.size());
        Props pref = new Props();
        pref.put(AlgParams.LINKAGE, SingleLinkage.name);
        pref.put(AlgParams.CLUSTERING_TYPE, ClusteringType.ROWS_CLUSTERING);
        pref.put(PropType.PERFORMANCE, AlgParams.KEEP_PROXIMITY, true);
        HierarchicalResult result = subject.hierarchy(dataset, pref);
        System.out.println("school - single");
        DendroTreeData tree = result.getTreeData();
        Matrix similarityMatrix = result.getProximityMatrix();
        similarityMatrix.printLower(5, 2);
        tree.print();
        assertEquals(dataset.size(), tree.numLeaves());
        DendroNode root = tree.getRoot();
        assertEquals(32.542734980330046, root.getHeight(), DELTA);
    }

    @Test
    public void testCompleteLinkage() {
        Dataset<? extends Instance> dataset = FakeClustering.kumarData();
        assertEquals(6, dataset.size());
        Props pref = new Props();
        pref.put(AlgParams.LINKAGE, CompleteLinkage.name);
        pref.put(AlgParams.CLUSTERING_TYPE, ClusteringType.ROWS_CLUSTERING);
        pref.put(PropType.PERFORMANCE, AlgParams.KEEP_PROXIMITY, true);
        HierarchicalResult result = subject.hierarchy(dataset, pref);
        Matrix similarityMatrix = result.getProximityMatrix();
        assertNotNull(similarityMatrix);
        assertEquals(similarityMatrix.rowsCount(), dataset.size());
        assertEquals(similarityMatrix.columnsCount(), dataset.size());
        System.out.println("kumar - complete");
        DendroTreeData tree = result.getTreeData();
        tree.print();
        //kumar - complete
        //                 /----- #1 - 2
        //         /----- #7 (0.14)
        //         |       \----- #4 - 5
        // /----- #9 (0.34)
        // |       \----- #0 - 1
        //#10 (0.39)
        // |               /----- #2 - 3
        // |       /----- #6 (0.10)
        // |       |       \----- #5 - 6
        // \----- #8 (0.22)
        //         \----- #3 - 4

        assertEquals(dataset.size(), tree.numLeaves());
        DendroNode root = tree.getRoot();
        assertEquals(0.38600518131237566, root.getHeight(), DELTA);
    }

    @Test
    public void testAverageLinkage() {
        Dataset<? extends Instance> dataset = FakeClustering.kumarData();
        assertEquals(6, dataset.size());
        Props pref = new Props();
        pref.put(AlgParams.LINKAGE, AverageLinkage.name);
        pref.put(AlgParams.CLUSTERING_TYPE, ClusteringType.ROWS_CLUSTERING);
        pref.put(PropType.PERFORMANCE, AlgParams.KEEP_PROXIMITY, true);
        HierarchicalResult result = subject.hierarchy(dataset, pref);
        Matrix similarityMatrix = result.getProximityMatrix();
        assertNotNull(similarityMatrix);
        assertEquals(similarityMatrix.rowsCount(), dataset.size());
        assertEquals(similarityMatrix.columnsCount(), dataset.size());
        System.out.println("kumar - average");
        DendroTreeData tree = result.getTreeData();
        tree.print();
        //kumar - averate

        assertEquals(dataset.size(), tree.numLeaves());
        DendroNode root = tree.getRoot();
        assertEquals(0.27900110873498624, root.getHeight(), DELTA);
    }

}
