/*
 * Copyright (C) 2011-2017 clueminer.org
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
package org.clueminer.explorer.gui;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.clueminer.clustering.api.Cluster;
import org.clueminer.clustering.api.ClusterEvaluation;
import org.clueminer.clustering.api.factory.ExternalEvaluatorFactory;
import org.clueminer.clustering.api.factory.InternalEvaluatorFactory;
import org.clueminer.clustering.gui.ClusteringVisualizationFactory;
import org.clueminer.dataset.api.Instance;

/**
 *
 * @author deric
 */
public class EvalFuncPanel<E extends Instance, C extends Cluster<E>> extends javax.swing.JPanel {

    private static final long serialVersionUID = -3509452685974315163L;

    /**
     * Creates new form EvalFuncPanel
     */
    public EvalFuncPanel() {
        initComponents();
        cbEval.setSelectedItem("NMI");
    }

    private String[] initEvaluator() {
        ExternalEvaluatorFactory ef = ExternalEvaluatorFactory.getInstance();
        List<String> list = ef.getProviders();
        return list.toArray(new String[list.size()]);
    }

    public ClusterEvaluation getEvaluator() {
        String eval;
        ClusterEvaluation evaluator;
        if (rbExternal.isSelected()) {
            eval = (String) cbEval.getSelectedItem();
            evaluator = ExternalEvaluatorFactory.getInstance().getProvider(eval);
        } else {
            eval = (String) cbInternal.getSelectedItem();
            InternalEvaluatorFactory<E, C> ief = InternalEvaluatorFactory.getInstance();
            evaluator = ief.getProvider(eval);
        }

        return evaluator;
    }

    private String[] initInternalEvaluator() {
        InternalEvaluatorFactory<E, C> ef = InternalEvaluatorFactory.getInstance();
        List<String> list = ef.getProviders();
        return list.toArray(new String[0]);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        lbFunc = new javax.swing.JLabel();
        cbEval = new javax.swing.JComboBox();
        rbExternal = new javax.swing.JRadioButton();
        rbInternal = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbInternal = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cbVisualize = new javax.swing.JComboBox<>();

        org.openide.awt.Mnemonics.setLocalizedText(lbFunc, org.openide.util.NbBundle.getMessage(EvalFuncPanel.class, "EvalFuncPanel.lbFunc.text")); // NOI18N

        cbEval.setModel(new DefaultComboBoxModel(initEvaluator()));

        buttonGroup1.add(rbExternal);
        rbExternal.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(rbExternal, org.openide.util.NbBundle.getMessage(EvalFuncPanel.class, "EvalFuncPanel.rbExternal.text")); // NOI18N

        buttonGroup1.add(rbInternal);
        org.openide.awt.Mnemonics.setLocalizedText(rbInternal, org.openide.util.NbBundle.getMessage(EvalFuncPanel.class, "EvalFuncPanel.rbInternal.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(EvalFuncPanel.class, "EvalFuncPanel.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(EvalFuncPanel.class, "EvalFuncPanel.jLabel2.text")); // NOI18N

        cbInternal.setModel(new DefaultComboBoxModel(initInternalEvaluator()));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(EvalFuncPanel.class, "EvalFuncPanel.jLabel3.text")); // NOI18N

        cbVisualize.setModel(new DefaultComboBoxModel(initVisualization()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(rbExternal)
                    .addComponent(rbInternal)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(lbFunc)))
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbVisualize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbEval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbInternal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbExternal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFunc)
                    .addComponent(cbEval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbInternal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbInternal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(cbVisualize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbEval;
    private javax.swing.JComboBox cbInternal;
    private javax.swing.JComboBox<String> cbVisualize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lbFunc;
    private javax.swing.JRadioButton rbExternal;
    private javax.swing.JRadioButton rbInternal;
    // End of variables declaration//GEN-END:variables

    private Object[] initVisualization() {
        ClusteringVisualizationFactory cv = ClusteringVisualizationFactory.getInstance();
        List<String> list = cv.getProviders();
        return list.toArray(new String[0]);
    }

}
