/*
 * Copyright (C) 2011-2016 clueminer.org
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
package org.clueminer.clustering.explorer;

import java.awt.BorderLayout;
import java.util.Collection;
import org.clueminer.clustering.api.Clustering;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@TopComponent.Description(
        preferredID = "ClusterExplorerTopComponent",
        iconBase = "org/clueminer/clustering/explorer/clustering16.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "org.clueminer.clustering.explorer.ClusterExplorerTopComponent")
@ActionReference(path = "Menu/Window", position = 125)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ClusterExplorerAction",
        preferredID = "ClusterExplorerTopComponent")
public final class ClusterExplorerTopComponent extends TopComponent implements ExplorerManager.Provider, LookupListener {

    private static final long serialVersionUID = -1398240134373744788L;
    private Lookup.Result<Clustering> result = null;
    private ClustersNode root;
    private final ExplorerManager mgr = new ExplorerManager();
    private BeanTreeView treeView;

    public ClusterExplorerTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(ClusterExplorerTopComponent.class, "CTL_ClusterExplorerTopComponent"));
        setToolTipText(NbBundle.getMessage(ClusterExplorerTopComponent.class, "HINT_ClusterExplorerTopComponent"));

        associateLookup(ExplorerUtils.createLookup(mgr, getActionMap()));

        setLayout(new BorderLayout());
        this.treeView = new BeanTreeView();
        //treeView.setRootVisible(false);
        add(treeView, BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        result = Utilities.actionsGlobalContext().lookupResult(Clustering.class);
        result.addLookupListener(this);
    }

    @Override
    public void componentClosed() {
        result.removeLookupListener(this);
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
    public ExplorerManager getExplorerManager() {
        return mgr;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        //treeView.removeAll();
        Collection<? extends Clustering> allClusterings = result.allInstances();
        for (Clustering c : allClusterings) {
            root = new ClustersNode(c);
        }
        mgr.setRootContext(root);
    }
}
