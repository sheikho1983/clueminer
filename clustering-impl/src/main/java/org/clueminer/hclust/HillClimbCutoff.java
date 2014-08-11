package org.clueminer.hclust;

import org.clueminer.clustering.api.ClusterEvaluator;
import org.clueminer.clustering.api.Clustering;
import org.clueminer.clustering.api.CutoffStrategy;
import org.clueminer.clustering.api.HierarchicalResult;

/**
 *
 * @author Tomas Barton
 */
public class HillClimbCutoff implements CutoffStrategy {

    private ClusterEvaluator evaluator;
    private Clustering clust;

    public HillClimbCutoff(ClusterEvaluator eval) {
        this.evaluator = eval;
    }

    @Override
    public double findCutoff(HierarchicalResult hclust) {
        double cutoff;
        double score, prev = Double.NaN, oldcut = 0;
        int level = hclust.treeLevels() - 1;
        boolean isClimbing = true;
        String evalName;
        int clustNum;
        do {
            hclust.cutTreeByLevel(level);
            clust = hclust.getClustering();
            System.out.println("clust = " + clust);
            cutoff = hclust.getCutoff();
            evalName = evaluator.getName();
            clustNum = clust.size();
            System.out.println("we have " + clust.size() + " clusters");
            if(hclust.isScoreCached(evalName, clustNum)){
                System.out.println("score cached");
                score = hclust.getScore(evalName, clustNum);
            }else{
                score = evaluator.score(clust, hclust.getDataset());
            }
            System.out.println("score = " + score + " prev= " + prev);
            if (!Double.isNaN(prev)) {
                if (!evaluator.compareScore(score, prev)) {
                    isClimbing = false;
                    System.out.println("function is not climbing anymore");
                    hclust.updateCutoff(oldcut);
                }
            }
            hclust.setScores(evaluator.getName(), clust.size(), score);
            prev = score;
            oldcut = cutoff;
            level--;

        } while (isClimbing && !Double.isNaN(score));
        return cutoff;
    }

    public ClusterEvaluator getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(ClusterEvaluator evaluator) {
        this.evaluator = evaluator;
    }
}
