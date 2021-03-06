package org.clueminer.scatter;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;
import org.clueminer.clustering.api.Clustering;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Top component which displays scatterplot.
 */
@ConvertAsProperties(
        dtd = "-//org.clueminer.scatter//Scatter//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "ScatterTopComponent",
        iconBase = "org/clueminer/scatter/scatter16.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "leftSlidingSide", openAtStartup = false)
@ActionID(category = "Window", id = "org.clueminer.scatter.ScatterTopComponent")
@ActionReference(path = "Menu/Window" /* , position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ScatterAction",
        preferredID = "ScatterTopComponent"
)
@Messages({
    "CTL_ScatterAction=Scatter",
    "CTL_ScatterTopComponent=Scatter",
    "HINT_ScatterTopComponent=Scatter plot - 2D projection"
})
public final class ScatterTopComponent extends TopComponent implements LookupListener {

    private static final long serialVersionUID = 1752389678088215586L;

    private Lookup.Result<Clustering> result = null;
    private final ScatterPlot frame;
    private static final Logger logger = Logger.getLogger(ScatterTopComponent.class.getName());

    public ScatterTopComponent() {
        initComponents();
        setName(Bundle.CTL_ScatterTopComponent());
        setToolTipText(Bundle.HINT_ScatterTopComponent());
        frame = new ScatterPlot();
        add(frame, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        result = Utilities.actionsGlobalContext().lookupResult(Clustering.class);
        result.addLookupListener(this);
        resultChanged(new LookupEvent(result));
    }

    @Override
    public void componentClosed() {
        if (result != null) {
            result.removeLookupListener(this);
        }
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Collection<? extends Clustering> allClusterings = result.allInstances();
        if (allClusterings != null && allClusterings.size() > 0) {

            Iterator<? extends Clustering> it = allClusterings.iterator();
            if (allClusterings.size() == 2) {
                Clustering clustA = it.next();
                Clustering clustB = it.next();
                frame.setClusterings(clustA, clustB);
            } else if (it.hasNext()) {
                Clustering clust = it.next();
                if (clust != null) {
                    frame.setClustering(clust);
                }
            }
        }

    }
}
