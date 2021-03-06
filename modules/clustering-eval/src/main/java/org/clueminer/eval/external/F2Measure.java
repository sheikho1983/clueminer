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
package org.clueminer.eval.external;

import org.clueminer.clustering.api.ExternalEvaluator;
import org.clueminer.eval.utils.PairMatch;
import org.clueminer.utils.Props;
import org.openide.util.lookup.ServiceProvider;

/**
 * Same as {@link F1Measure} just using beta = 2
 *
 * @author deric
 */
@ServiceProvider(service = ExternalEvaluator.class)
public class F2Measure extends AbstractCountingPairs {

    private static final String NAME = "F2-measure";
    private static final long serialVersionUID = 5075558180348805172L;
    private double beta = 2.0;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double countScore(PairMatch pm, Props params) {
        double b = params.getDouble("beta", beta);
        double squareBeta = Math.pow(b, 2);
        return (1 + squareBeta) * pm.tp / ((1.0 + squareBeta) * pm.tp + squareBeta * pm.fn + pm.fp);
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }
}
