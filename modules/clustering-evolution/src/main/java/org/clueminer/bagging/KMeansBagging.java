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
package org.clueminer.bagging;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.clueminer.clustering.ClusteringExecutorCached;
import org.clueminer.clustering.algorithm.KMeans;
import org.clueminer.clustering.api.AbstractClusteringAlgorithm;
import org.clueminer.clustering.api.Cluster;
import org.clueminer.clustering.api.Clustering;
import org.clueminer.clustering.api.ClusteringAlgorithm;
import org.clueminer.clustering.api.Consensus;
import org.clueminer.clustering.api.PartitioningClustering;
import org.clueminer.clustering.api.config.annotation.Param;
import org.clueminer.clustering.api.factory.ConsensusFactory;
import org.clueminer.dataset.api.Dataset;
import org.clueminer.dataset.api.Instance;
import org.clueminer.eval.AIC;
import org.clueminer.eval.SDindex;
import org.clueminer.evolution.mo.MoSolution;
import org.clueminer.utils.Props;
import org.openide.util.lookup.ServiceProvider;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author deric
 */
@ServiceProvider(service = ClusteringAlgorithm.class)
public class KMeansBagging extends AbstractClusteringAlgorithm implements PartitioningClustering {

    private static final String name = "K-Means bagging";

    public static final String BAGGING = "bagging";

    public static final String INIT_METHOD = "init_set";

    public static final String CONSENSUS = "consensus";

    public static final String FIXED_K = "rand_k";

    @Param(name = KMeansBagging.BAGGING, description = "number of independent k-means runs", required = false)
    private int bagging;

    private static final Logger logger = Logger.getLogger(KMeansBagging.class.getName());

    @Override
    public String getName() {
        return name;
    }

    private Clustering[] randClusters(AbstractClusteringAlgorithm alg, Dataset<? extends Instance> dataset, Props props) {
        Clustering[] clusts = new Clustering[bagging];
        for (int i = 0; i < bagging; i++) {
            clusts[i] = alg.cluster(dataset, props);
        }
        return clusts;
    }

    @Override
    public Clustering<? extends Cluster> cluster(Dataset<? extends Instance> dataset, Props props) {
        bagging = props.getInt(BAGGING, 5);
        String initSet = props.get(INIT_METHOD, "RANDOM");
        //String initSet = props.get("init_set", "MO");
        KMeans alg = new KMeans();

        //mapper
        Clustering[] clusts;

        //result store
        String consensus = props.get(CONSENSUS, "co-association HAC");
        Consensus reducer = ConsensusFactory.getInstance().getProvider(consensus);
        logger.log(Level.INFO, "consensus:{0}", consensus);
        Clustering<? extends Cluster> res = null;
        //Clustering<? extends Cluster> res = Clusterings.newList(k);
        switch (initSet) {
            case "RANDOM":
                //map
                clusts = randClusters(alg, dataset, props);
                //reduce
                res = reducer.reduce(clusts, alg, colorGenerator, props);
                break;
            case "MO":
                KmEvolution km = new KmEvolution(new ClusteringExecutorCached(alg));
                km.setGenerations(5);
                km.setDataset(dataset);
                km.addObjective(new AIC());
                km.addObjective(new SDindex());
                if (props.getBoolean(FIXED_K, false)) {
                    Props p = new Props();
                    p.put(KMeans.K, props.get(KMeans.K));
                    km.setDefaultProps(p);
                }

                km.run();
                List<Solution> sol = km.getSolution();
                clusts = new Clustering[sol.size()];
                for (int i = 0; i < sol.size(); i++) {
                    clusts[i] = ((MoSolution) sol.get(i)).getClustering();
                }
                res = reducer.reduce(clusts, alg, colorGenerator, props);
                break;
            default:
                throw new RuntimeException("unknown method " + initSet);
        }

        if (res != null) {
            //add original dataset to lookup (for external evaluation)
            res.lookupAdd(dataset);
        }

        return res;
    }

    @Override
    public Clustering<? extends Cluster> partition(Dataset<? extends Instance> dataset, Props params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
