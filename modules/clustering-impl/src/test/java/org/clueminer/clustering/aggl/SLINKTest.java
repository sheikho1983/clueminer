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

import org.clueminer.cluster.FakeClustering;
import org.clueminer.dataset.api.Dataset;
import org.clueminer.dataset.api.Instance;
import org.clueminer.utils.Props;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author deric
 */
public class SLINKTest {

    private static Dataset<? extends Instance> school;
    private final SLINK subject = new SLINK();

    public SLINKTest() {
    }

    @Before
    public void setUp() {
        school = FakeClustering.schoolData();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetName() {
    }

    @Test
    public void testCluster_Matrix_Props() {
    }

    @Test
    public void testCluster_Dataset() {
    }

    @Test
    public void testHierarchy_Dataset_Props() {
        subject.hierarchy(school, new Props());

    }

    @Test
    public void testHierarchy_3args() {
    }

    @Test
    public void testHierarchy_Matrix_Props() {
    }

}
