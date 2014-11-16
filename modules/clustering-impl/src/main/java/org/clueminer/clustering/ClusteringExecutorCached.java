package org.clueminer.clustering;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.clueminer.clustering.aggl.HACLW;
import org.clueminer.clustering.api.AgglParams;
import org.clueminer.clustering.api.Cluster;
import org.clueminer.clustering.api.Clustering;
import org.clueminer.clustering.api.CutoffStrategy;
import org.clueminer.clustering.api.Executor;
import org.clueminer.clustering.api.HierarchicalResult;
import org.clueminer.clustering.api.dendrogram.DendrogramMapping;
import org.clueminer.clustering.api.dendrogram.OptimalTreeOrder;
import org.clueminer.clustering.order.MOLO;
import org.clueminer.clustering.struct.DendrogramData2;
import org.clueminer.dataset.api.Dataset;
import org.clueminer.dataset.api.Instance;
import org.clueminer.std.Scaler;
import org.clueminer.utils.Props;

/**
 * Executor should be responsible of converting dataset into appropriate input
 * (e.g. a dense matrix) and then joining the original inputs with appropriate
 * clustering result
 *
 * Should reduce circa 50% of memory during evolution (trying all combinations
 * of standardizations)
 *
 * @author Tomas Barton
 */
public class ClusteringExecutorCached extends AbstractExecutor implements Executor {

    private static final Logger logger = Logger.getLogger(ClusteringExecutorCached.class.getName());
    private Map<Dataset<? extends Instance>, StdStorage> storage;
    private OptimalTreeOrder treeOrder = new MOLO();

    public ClusteringExecutorCached() {
        algorithm = new HACLW();
    }

    @Override
    public HierarchicalResult hclustRows(Dataset<? extends Instance> dataset, Props params) {
        StdStorage store = getStorage(dataset);
        Dataset<? extends Instance> norm = store.get(params.get(AgglParams.STD, Scaler.NONE), params.getBoolean(AgglParams.LOG, false));
        params.putBoolean(AgglParams.CLUSTER_ROWS, true);
        HierarchicalResult rowsResult = algorithm.hierarchy(norm, params);
        //TODO: tree ordering might break assigning items to clusters
        //treeOrder.optimize(rowsResult, true);
        return rowsResult;
    }

    @Override
    public HierarchicalResult hclustColumns(Dataset<? extends Instance> dataset, Props params) {
        StdStorage store = getStorage(dataset);
        Dataset<? extends Instance> norm = store.get(params.get(AgglParams.STD, Scaler.NONE), params.getBoolean(AgglParams.LOG, false));
        params.putBoolean(AgglParams.CLUSTER_ROWS, false);
        HierarchicalResult columnsResult = algorithm.hierarchy(norm, params);
        //treeOrder.optimize(columnsResult, true);
        //CutoffStrategy strategy = getCutoffStrategy(params);
        //columnsResult.findCutoff(strategy);
        return columnsResult;
    }

    private void checkInput(Dataset<? extends Instance> dataset) {
        if (dataset == null || dataset.isEmpty()) {
            throw new NullPointerException("no data to process");
        }
    }

    private StdStorage getStorage(Dataset<? extends Instance> dataset) {
        checkInput(dataset);
        StdStorage stdStore;

        if (storage == null) {
            storage = new HashMap<>(2);
        }
        if (storage.containsKey(dataset)) {
            stdStore = storage.get(dataset);
        } else {
            stdStore = new StdStorage(dataset);
            storage.put(dataset, stdStore);
        }
        return stdStore;
    }

    @Override
    public Clustering<Cluster> clusterRows(Dataset<? extends Instance> dataset, Props params) {
        HierarchicalResult rowsResult = hclustRows(dataset, params);

        findCutoff(rowsResult, params);
        DendrogramMapping mapping = new DendrogramData2(dataset, rowsResult);

        Clustering clustering = rowsResult.getClustering();
        clustering.mergeParams(params);
        clustering.lookupAdd(mapping);
        return clustering;
    }

    public void findCutoff(HierarchicalResult rowsResult, Props params) {
        CutoffStrategy strategy = getCutoffStrategy(params);
        double cut = rowsResult.findCutoff(strategy);
        logger.log(Level.INFO, "found cutoff {0} with strategy {1}", new Object[]{cut, strategy.getName()});
        params.putDouble(AgglParams.CUTOFF, cut);
    }

    /**
     * Cluster both - rows and columns
     *
     * @param dataset data to be clustered
     * @param params
     * @return
     */
    @Override
    public DendrogramMapping clusterAll(Dataset<? extends Instance> dataset, Props params) {
        HierarchicalResult rowsResult = hclustRows(dataset, params);
        findCutoff(rowsResult, params);
        HierarchicalResult columnsResult = hclustColumns(dataset, params);

        DendrogramMapping mapping = new DendrogramData2(dataset, rowsResult, columnsResult);
        Clustering clustering = rowsResult.getClustering();
        clustering.lookupAdd(mapping);
        clustering.lookupAdd(rowsResult);
        clustering.lookupAdd(columnsResult);
        clustering.mergeParams(params);
        return mapping;
    }

}
