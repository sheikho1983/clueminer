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
package org.clueminer.eval;

import org.clueminer.clustering.api.Cluster;
import org.clueminer.clustering.api.Clustering;
import org.clueminer.dataset.api.Instance;
import org.clueminer.distance.EuclideanDistance;
import org.clueminer.distance.api.Distance;
import org.clueminer.utils.Props;

/**
 * Corrected Tau index (ties are accounted)
 *
 * The metric is based on Kendall's Tau correlation:
 *
 * Kendall, M. G. A New Measure of Rank Correlation. Biometrika, volume 30, no.
 * 1/2, 1938: pp. pp. 81–93, ISSN 00063444
 *
 * The concept of counting concordant and disconcordant pairs is the same as in
 * case of {@link Gamma}.
 *
 *
 * @author Tomas Barton
 * @param <E>
 * @param <C>
 * @note Intentionally not offered as the computing time limits usefulness of
 * this objective.
 */
public class Tau<E extends Instance, C extends Cluster<E>> extends Gamma<E, C> {

    private static String NAME = "Tau";
    private static final long serialVersionUID = 7019129875909018702L;

    public Tau() {
        dm = EuclideanDistance.getInstance();
    }

    public Tau(Distance dist) {
        this.dm = dist;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double score(Clustering<E, C> clusters, Props params) {
        double nw = numW(clusters);
        double nt = numT(clusters);
        double nb = nt - nw;

        Sres s = computeSTable(clusters);
        return (s.plus - s.minus) / Math.sqrt(nb * nw * (nt * (nt - 1) / 2.0));
    }

    @Override
    public boolean isBetter(double score1, double score2) {
        // should be maximized
        return score1 > score2;
    }

    @Override
    public boolean isMaximized() {
        return true;
    }

    @Override
    public double getMin() {
        return 0;
    }

    @Override
    public double getMax() {
        return Double.POSITIVE_INFINITY;
    }

}
