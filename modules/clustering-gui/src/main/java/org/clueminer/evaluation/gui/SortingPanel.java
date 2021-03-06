/*
 * Copyright (C) 2011-2018 clueminer.org
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
package org.clueminer.evaluation.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import org.clueminer.clustering.api.Clustering;
import org.clueminer.clustering.api.factory.EvaluationFactory;
import org.clueminer.clustering.api.factory.ExternalEvaluatorFactory;
import org.clueminer.clustering.api.factory.InternalEvaluatorFactory;
import org.clueminer.dataset.api.Dataset;
import org.clueminer.dataset.api.Instance;
import org.clueminer.export.api.ClusteringExport;
import org.clueminer.export.api.ClusteringExporterFactory;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Tomas Barton
 */
public class SortingPanel<E extends Instance> extends JPanel {

    private static final long serialVersionUID = 8757022805479436474L;

    private JComboBox comboEvaluatorX;
    private JComboBox comboEvaluatorY;
    private SortedClusterings plot;
    private JButton export;

    public SortingPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        //setSize(new Dimension(800, 600));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1.0;
        //component in last row should be streatched to fill space at the bottom
        c.weighty = 0.1;
        c.insets = new java.awt.Insets(5, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 10);

        comboEvaluatorX = new JComboBox();
        comboEvaluatorX.setModel(new EvaluatorComboBox(ExternalEvaluatorFactory.getInstance().getProvidersArray()));
        comboEvaluatorX.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEvaluatorXActionPerformed(evt);
            }
        });
        add(comboEvaluatorX, c);

        comboEvaluatorY = new JComboBox();
        comboEvaluatorY.setModel(new EvaluatorComboBox(InternalEvaluatorFactory.getInstance().getProvidersArray()));
        comboEvaluatorY.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEvaluatorYActionPerformed(evt);
            }
        });
        c.insets = new Insets(5, 0, 5, 5);
        c.gridx = 1;
        add(comboEvaluatorY, c);

        export = new JButton(ImageUtilities.loadImageIcon("org/clueminer/evaluation/gui/save16.png", false));
        export.setToolTipText("Export current results");
        c.gridx = 2;
        add(export, c);

        export.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                ClusteringExporterFactory cef = ClusteringExporterFactory.getInstance();
                ClusteringExport<E> exp = (ClusteringExport<E>) cef.getProvider("Export to CSV");
                exp.setDataset((Dataset<E>) plot.getDataset());
                exp.setResults(plot.getResults());
                exp.setClusterings(plot.getClusterings());
                exp.setReference(plot.cLeft.getEvaluator());
                exp.showDialog();
            }
        });

        //left list
        plot = new SortedClusterings();
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridx = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(0, 0, 0, 0);
        add(plot, c);

        revalidate();
        validate();
        repaint();
    }

    private void comboEvaluatorXActionPerformed(java.awt.event.ActionEvent evt) {
        String item = (String) comboEvaluatorX.getSelectedItem();
        if (item != null) {
            plot.setEvaluatorX(EvaluationFactory.getInstance().getProvider(item));
        }
    }

    private void comboEvaluatorYActionPerformed(java.awt.event.ActionEvent evt) {
        String item = (String) comboEvaluatorY.getSelectedItem();
        if (item != null) {
            plot.setEvaluatorY(EvaluationFactory.getInstance().getProvider(item));
        }
    }

    public void setClusterings(Collection<? extends Clustering> clusterings) {
        if (clusterings != null && clusterings.size() > 1) {
            plot.setClusterings((Collection<Clustering>) clusterings);
        }
    }

    public Collection<? extends Clustering> getClusterings() {
        return plot.getClusterings();
    }

}
