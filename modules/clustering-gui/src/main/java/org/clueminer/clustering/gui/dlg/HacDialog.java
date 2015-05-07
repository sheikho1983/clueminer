package org.clueminer.clustering.gui.dlg;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import org.clueminer.chameleon.Chameleon;
import org.clueminer.clustering.api.AgglParams;
import org.clueminer.clustering.api.AgglomerativeClustering;
import org.clueminer.clustering.api.ClusteringAlgorithm;
import org.clueminer.clustering.api.factory.CutoffStrategyFactory;
import org.clueminer.clustering.api.factory.InternalEvaluatorFactory;
import org.clueminer.clustering.api.factory.LinkageFactory;
import org.clueminer.clustering.gui.ClusterAnalysis;
import org.clueminer.clustering.gui.ClusteringDialog;
import org.clueminer.distance.api.DistanceFactory;
import org.clueminer.math.StandardisationFactory;
import org.clueminer.utils.Props;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author deric
 */
@ServiceProvider(service = ClusteringDialog.class)
public class HacDialog extends JPanel implements ClusteringDialog {

    /**
     * Creates new form HacDialog
     */
    public HacDialog() {
        initComponents();
        comboDistance.setSelectedItem("Euclidean");
        comboStandardisation.setSelectedItem("Min-Max");
        comboLinkage.setSelectedItem("Complete Linkage");
        comboCutMethod.setSelectedItem("naive cutoff");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbStandardization = new javax.swing.JLabel();
        comboStandardisation = new javax.swing.JComboBox();
        lbDistance = new javax.swing.JLabel();
        comboDistance = new javax.swing.JComboBox();
        lbLinkage = new javax.swing.JLabel();
        comboLinkage = new javax.swing.JComboBox();
        chkBoxLogScale = new javax.swing.JCheckBox();
        lbCutoff = new javax.swing.JLabel();
        comboCutoff = new javax.swing.JComboBox();
        chckColumns = new javax.swing.JCheckBox();
        lbCutMethod = new javax.swing.JLabel();
        comboCutMethod = new javax.swing.JComboBox();

        org.openide.awt.Mnemonics.setLocalizedText(lbStandardization, org.openide.util.NbBundle.getMessage(HacDialog.class, "HacDialog.lbStandardization.text")); // NOI18N

        comboStandardisation.setModel(new DefaultComboBoxModel(initStandardisation()));

        org.openide.awt.Mnemonics.setLocalizedText(lbDistance, org.openide.util.NbBundle.getMessage(HacDialog.class, "HacDialog.lbDistance.text")); // NOI18N

        comboDistance.setModel(new DefaultComboBoxModel(initDistance()));

        org.openide.awt.Mnemonics.setLocalizedText(lbLinkage, org.openide.util.NbBundle.getMessage(HacDialog.class, "HacDialog.lbLinkage.text")); // NOI18N

        comboLinkage.setModel(new DefaultComboBoxModel(initLinkage()));

        org.openide.awt.Mnemonics.setLocalizedText(chkBoxLogScale, org.openide.util.NbBundle.getMessage(HacDialog.class, "HacDialog.chkBoxLogScale.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbCutoff, org.openide.util.NbBundle.getMessage(HacDialog.class, "HacDialog.lbCutoff.text")); // NOI18N

        comboCutoff.setModel(new DefaultComboBoxModel(initCutoff()));

        chckColumns.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(chckColumns, org.openide.util.NbBundle.getMessage(HacDialog.class, "HacDialog.chckColumns.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lbCutMethod, org.openide.util.NbBundle.getMessage(HacDialog.class, "HacDialog.lbCutMethod.text")); // NOI18N

        comboCutMethod.setModel(new DefaultComboBoxModel(initCutoffMethod()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbLinkage)
                    .addComponent(lbStandardization)
                    .addComponent(lbCutoff)
                    .addComponent(lbDistance)
                    .addComponent(lbCutMethod))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboCutMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboStandardisation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chckColumns)
                            .addComponent(chkBoxLogScale)))
                    .addComponent(comboDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboLinkage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboCutoff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbStandardization)
                            .addComponent(comboStandardisation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkBoxLogScale))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbDistance))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbLinkage)
                            .addComponent(comboLinkage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addComponent(lbCutoff))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboCutoff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chckColumns)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboCutMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCutMethod))
                .addContainerGap(182, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chckColumns;
    private javax.swing.JCheckBox chkBoxLogScale;
    private javax.swing.JComboBox comboCutMethod;
    private javax.swing.JComboBox comboCutoff;
    private javax.swing.JComboBox comboDistance;
    private javax.swing.JComboBox comboLinkage;
    private javax.swing.JComboBox comboStandardisation;
    private javax.swing.JLabel lbCutMethod;
    private javax.swing.JLabel lbCutoff;
    private javax.swing.JLabel lbDistance;
    private javax.swing.JLabel lbLinkage;
    private javax.swing.JLabel lbStandardization;
    // End of variables declaration//GEN-END:variables

    private Object[] initStandardisation() {
        return StandardisationFactory.getInstance().getProvidersArray();
    }

    private Object[] initDistance() {
        return DistanceFactory.getInstance().getProvidersArray();
    }

    @Override
    public String getName() {
        return "HAC dialog";
    }

    @Override
    public Props getParams() {
        Props params = new Props();
        params.put(AgglParams.DIST, (String) comboDistance.getSelectedItem());
        params.put(AgglParams.LINKAGE, (String) comboLinkage.getSelectedItem());
        params.put(AgglParams.STD, (String) comboStandardisation.getSelectedItem());
        params.put(AgglParams.CUTOFF_SCORE, (String) comboCutoff.getSelectedItem());
        params.put(AgglParams.CUTOFF_STRATEGY, (String) comboCutMethod.getSelectedItem());
        if (chkBoxLogScale.isSelected()) {
            params.putBoolean(AgglParams.LOG, true);
        }
        if (chckColumns.isSelected()) {
            params.putBoolean(AgglParams.CLUSTER_COLUMNS, true);
        }

        return params;
    }

    @Override
    public void setParent(ClusterAnalysis clust) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClusteringAlgorithm getAlgorithm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public boolean isUIfor(ClusteringAlgorithm algorithm) {
        return algorithm instanceof AgglomerativeClustering && !(algorithm instanceof Chameleon);
    }

    private Object[] initLinkage() {
        return LinkageFactory.getInstance().getProvidersArray();
    }

    private Object[] initCutoff() {
        return InternalEvaluatorFactory.getInstance().getProvidersArray();
    }

    @Override
    public void updateAlgorithm(ClusteringAlgorithm algorithm) {

    }

    private Object[] initCutoffMethod() {
        return CutoffStrategyFactory.getInstance().getProvidersArray();
    }
}
