package org.clueminer.clustering.preview;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.clueminer.clustering.api.Clustering;
import org.clueminer.dataset.api.Dataset;
import org.clueminer.dataset.api.Instance;
import org.clueminer.project.api.ProjectController;
import org.clueminer.project.api.Workspace;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import static org.clueminer.clustering.preview.Bundle.*;
import org.clueminer.project.api.Project;
import org.clueminer.project.api.WorkspaceListener;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.clueminer.clusterpreview//ClusterPreview//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "ClusterPreviewTopComponent",
        iconBase = "org/clueminer/clusterpreview/chart16.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "properties", openAtStartup = true)
@ActionID(category = "Window", id = "org.clueminer.clusterpreview.ClusterPreviewTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ClusterPreviewAction",
        preferredID = "ClusterPreviewTopComponent")
@Messages({
    "CTL_ClusterPreviewAction=ClusterPreview",
    "CTL_ClusterPreviewTopComponent=ClusterPreview Window",
    "HINT_ClusterPreviewTopComponent=This is a ClusterPreview window"
})
public final class ClusterPreviewTopComponent extends TopComponent implements LookupListener, Lookup.Provider {

    private static final long serialVersionUID = -9187536896216095176L;
    private final InstanceContent content = new InstanceContent();
    private Lookup.Result<Clustering> result = null;
    private final ClusterPreviewFrame frame;
    private Dataset<Instance> dataset;
    private static final Logger logger = Logger.getLogger(ClusterPreviewTopComponent.class.getName());

    public ClusterPreviewTopComponent() {
        initComponents();
        //Add the dynamic object to the TopComponent Lookup:
        associateLookup(new AbstractLookup(content));
        setName(CTL_ClusterPreviewAction());
        setToolTipText(Bundle.HINT_ClusterPreviewTopComponent());
        putClientProperty(TopComponent.PROP_KEEP_PREFERRED_SIZE_WHEN_SLIDED_IN, Boolean.TRUE);
        frame = new ClusterPreviewFrame();
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

        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        Workspace workspace = pc.getCurrentWorkspace();
        if (workspace != null) {
            System.out.println("got result (dataset)");
            dataset = workspace.getLookup().lookup(Dataset.class);
            if (dataset != null) {
                System.out.println("dataset size = " + dataset.size());
            }
            workspace.add(frame.getViewer());
            content.add(frame.getViewer());
        }
        pc.addWorkspaceListener(new WorkspaceListener() {

            @Override
            public void initialize(Workspace workspace) {
                //add preview class to lookup
                workspace.add(frame.getViewer());
            }

            @Override
            public void select(Workspace workspace) {

            }

            @Override
            public void unselect(Workspace workspace) {

            }

            @Override
            public void close(Workspace workspace) {
                workspace.remove(frame.getViewer());
            }

            @Override
            public void disable() {

            }

            @Override
            public void projectActivated(Project project) {

            }
        });

        result = Utilities.actionsGlobalContext().lookupResult(Clustering.class);
        result.addLookupListener(this);
        resultChanged(new LookupEvent(result));
    }

    @Override
    public void componentClosed() {
        result.removeLookupListener(this);
        result = null;
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        p.setProperty("chart_size", String.valueOf(frame.getChartSize()));
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
        int chartSize = Integer.valueOf(p.getProperty("chart_size", "0"));
        frame.setChartSize(chartSize);
    }


    /*  @Override
     public void projectOpened(ProjectEvent evt) {
     System.out.println("cluster preview frame opened");
     System.out.println("looking up for DendrogramViewer class");
     viewer = Lookup.getDefault().lookup(DendrogramViewer.class);
     if (viewer != null) {
     viewer.addClusteringListener(frame.getViewer());
     System.out.println("Cluster preview registrered listener to " + viewer.getName());
     }else{
     System.out.println("failed to find dendrogram viewer");
     }
     repaint();
     }*/
    @Override
    public void resultChanged(LookupEvent ev) {
        Collection<? extends Clustering> allClusterings = result.allInstances();
        logger.log(Level.INFO, "clustering lookup: got {0} clusterings", allClusterings.size());
        for (Clustering c : allClusterings) {
            frame.setClustering(c);
        }

    }
}
