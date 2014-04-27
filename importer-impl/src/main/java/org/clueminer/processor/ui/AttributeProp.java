package org.clueminer.processor.ui;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import org.clueminer.attributes.BasicAttrRole;
import org.clueminer.dataset.api.AttributeRole;
import org.clueminer.io.importer.api.AttributeDraft;
import org.clueminer.spi.ImporterUI;

/**
 *
 * @author deric
 */
public class AttributeProp extends javax.swing.JPanel {

    private static final long serialVersionUID = 4808266192954985430L;
    private final AttributeDraft attr;
    private final ImporterUI importerUI;
    private boolean changingRole = false;
    private boolean changingType = false;

    /**
     * Creates new form AttributeProp
     */
    public AttributeProp(AttributeDraft atrd, ImporterUI importerUI) {
        this.attr = atrd;
        this.importerUI = importerUI;
        initComponents();
        setType(atrd.getType());
        setRole(atrd.getRole().toString());
        setAttrName(atrd.getName());
    }

    public final void setAttrName(String name) {
        tfName.setText(name);
    }

    public final void setType(Class<?> type) {
        //event invoked by app (not user)
        changingType = true;
        cbType.setSelectedItem(classToString(type));
    }

    public final void setRole(String role) {
        System.out.println("trying to change attr's " + attr.getIndex() + " role to: " + role.toLowerCase());
        changingRole = true;
        cbRole.setSelectedItem(role.toLowerCase());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbType = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cbRole = new javax.swing.JComboBox();
        chckImport = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(AttributeProp.class, "AttributeProp.jLabel1.text")); // NOI18N

        tfName.setText(org.openide.util.NbBundle.getMessage(AttributeProp.class, "AttributeProp.tfName.text")); // NOI18N
        tfName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNameActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(AttributeProp.class, "AttributeProp.jLabel2.text")); // NOI18N

        cbType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "double", "int", "float", "long", "string" }));
        cbType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTypeItemStateChanged(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(AttributeProp.class, "AttributeProp.jLabel3.text")); // NOI18N

        cbRole.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "input", "meta", "label", "class", "id" }));
        cbRole.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbRoleItemStateChanged(evt);
            }
        });

        chckImport.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(chckImport, org.openide.util.NbBundle.getMessage(AttributeProp.class, "AttributeProp.chckImport.text")); // NOI18N
        chckImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chckImportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chckImport)
                            .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chckImport)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tfNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNameActionPerformed
        attr.setName(tfName.getName());
        //TODO: call reload
        importerUI.fireImporterChanged();
    }//GEN-LAST:event_tfNameActionPerformed

    private void chckImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chckImportActionPerformed
        attr.setSkipped(chckImport.isSelected());
        importerUI.fireImporterChanged();
    }//GEN-LAST:event_chckImportActionPerformed

    private void cbTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTypeItemStateChanged
        System.out.println("type change: " + evt.toString());
        /**
         * Sometimes current event might help to determine if it was triggered
         * by an user
         */
        //AWTEvent curr = EventQueue.getCurrentEvent();

        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String item = (String) cbType.getSelectedItem();
            attr.setType(stringToClass(item));
            if (changingType) {
                //do not trigger extra event
                changingType = false;
            } else {
                System.out.println("fireing type changed");
                importerUI.fireImporterChanged();
            }
        }

    }//GEN-LAST:event_cbTypeItemStateChanged

    private void cbRoleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbRoleItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            AWTEvent curr = EventQueue.getCurrentEvent();
            System.out.println("awt thread: " + curr);
            if (curr.getSource() == cbRole) {
                System.out.println("source is cbRole");
            } else {
                System.out.println("source is " + curr.getSource());
            }
            String strRole = (String) cbRole.getSelectedItem();
            AttributeRole role = BasicAttrRole.valueOf(strRole.toUpperCase());
            attr.setRole(role);
            if (changingRole) {
                changingRole = false;
            } else {
                importerUI.fireImporterChanged();
            }
        }
    }//GEN-LAST:event_cbRoleItemStateChanged

    protected Class<?> stringToClass(String type) {
        if (type.equals("double")) {
            return Double.class;
        } else if (type.equals("float")) {
            return Float.class;
        } else if (type.equals("int") || type.equals("integer")) {
            return Integer.class;
        } else if (type.equals("long")) {
            return Long.class;
        } else if (type.equals("string")) {
            return String.class;
        }
        throw new RuntimeException("type '" + type + "' is not supported");
    }

    protected String classToString(Class<?> type) {
        if (type == Double.class) {
            return "double";
        } else if (type.equals(Integer.class)) {
            return "int";
        } else if (type.equals(Float.class)) {
            return "float";
        } else if (type.equals(Long.class)) {
            return "long";
        } else if (type.equals(String.class)) {
            return "string";
        }
        throw new RuntimeException("type '" + type + "' is not supported");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbRole;
    private javax.swing.JComboBox cbType;
    private javax.swing.JCheckBox chckImport;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField tfName;
    // End of variables declaration//GEN-END:variables
}
