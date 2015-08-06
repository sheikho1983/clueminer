/*
 * Copyright (C) 2011-2015 clueminer.org
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
package org.clueminer.eval;

import org.clueminer.clustering.api.Cluster;
import org.clueminer.clustering.api.ClusterEvaluation;
import org.clueminer.clustering.api.Clustering;
import org.clueminer.clustering.api.InternalEvaluator;
import org.clueminer.dataset.api.Instance;
import org.clueminer.distance.api.DistanceMeasure;
import org.clueminer.utils.Props;

/**
 *
 * @author deric
 *
 * @cite Halkidi, Maria, and Michalis Vazirgiannis. "Clustering validity
 * assessment: Finding the optimal partitioning of a data set." Data Mining,
 * 2001. ICDM 2001, Proceedings IEEE International Conference on. IEEE, 2001.
 */
public class SDbw extends SDindex implements InternalEvaluator, ClusterEvaluation {

    private static final String name = "S_Dbw";
    private static final long serialVersionUID = 2687565191321472835L;

    @Override
    public String getName() {
        return name;
    }

    public SDbw() {
        super();
    }

    public SDbw(DistanceMeasure dist) {
        this.dm = dist;
    }

    @Override
    public double score(Clustering<? extends Cluster> clusters, Props params) {
        double score = 0.0;
        double std_dev = stddev(clusters);

        return score;
    }

    @Override
    public boolean isBetter(double score1, double score2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isMaximized() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getMin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getMax() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private double stddev(Clustering<? extends Cluster> clusters) {
        double sdev = 0;
        Cluster first = clusters.get(0);
        Instance mean = first.builder().build(first.attributeCount());
        Instance[] centroids = new Instance[clusters.size()];
        for (int i = 0; i < clusters.size(); i++) {
            centroids[i] = clusters.get(i).getCentroid();
        }

        return sdev;
    }

}
