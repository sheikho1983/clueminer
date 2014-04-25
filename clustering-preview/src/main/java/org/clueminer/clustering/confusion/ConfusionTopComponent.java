package org.clueminer.clustering.confusion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import java.util.Iterator;
import org.clueminer.clustering.api.Clustering;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.clueminer.clustering.confusion//Confusion//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "ConfusionTopComponent",
        iconBase = "org/clueminer/clustering/confusion/conf16.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "leftSlidingSide", openAtStartup = false)
@ActionID(category = "Window", id = "org.clueminer.clustering.confusion.ConfusionTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ConfusionAction",
        preferredID = "ConfusionTopComponent"
)
@Messages({
    "CTL_ConfusionAction=Confusion",
    "CTL_ConfusionTopComponent=Confusion Window",
    "HINT_ConfusionTopComponent=This is a Confusion window"
})
public final class ConfusionTopComponent extends TopComponent implements LookupListener {

    private static final long serialVersionUID = -812734476374687722L;

    private final ConfusionMatrix matrix;
    private Lookup.Result<Clustering> result = null;

    public ConfusionTopComponent() {
        initComponents();
        setName(Bundle.CTL_ConfusionTopComponent());
        setToolTipText(Bundle.HINT_ConfusionTopComponent());
        matrix = new ConfusionMatrix();
        contentPane.setLayout(new GridBagLayout());
        contentPane.add(matrix, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPane = new javax.swing.JPanel();
        lbStatus = new javax.swing.JLabel();

        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(lbStatus, org.openide.util.NbBundle.getMessage(ConfusionTopComponent.class, "ConfusionTopComponent.lbStatus.text")); // NOI18N
        lbStatus.setToolTipText(org.openide.util.NbBundle.getMessage(ConfusionTopComponent.class, "ConfusionTopComponent.lbStatus.toolTipText")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lbStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(contentPane, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPane;
    private javax.swing.JLabel lbStatus;
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
    public void resultChanged(LookupEvent ev) {
        Collection<? extends Clustering> clusterings = result.allInstances();
        if (!clusterings.isEmpty()) {
            if (clusterings.size() == 2) {
                lbStatus.setText("");
                Iterator<? extends Clustering> it = clusterings.iterator();
                Clustering clustA = it.next();
                Clustering clustB = it.next();
                matrix.setClusterings(clustA, clustB);
                matrix.setSize(getSize().width, getSize().height);
            } else {
                lbStatus.setText("[ please select 2 clusterings ]");
            }
        } else {
            lbStatus.setText("[ no clusterings available ]");
        }
    }
}
