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
package org.clueminer.dendrogram.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.clueminer.clustering.api.HierarchicalResult;
import org.clueminer.clustering.api.dendrogram.DendrogramDataEvent;
import org.clueminer.clustering.api.dendrogram.DendrogramDataListener;
import org.clueminer.clustering.api.dendrogram.DendrogramMapping;
import org.clueminer.clustering.api.dendrogram.DendrogramTree;
import org.clueminer.clustering.api.dendrogram.TreeCluster;
import org.clueminer.clustering.api.dendrogram.TreeListener;

/**
 * A slider which allows manual setting of hierarchical clustering
 *
 * @author Tomas Barton
 */
public class CutoffSlider extends JPanel implements DendrogramDataListener, TreeListener, ChangeListener {

    private static final long serialVersionUID = 660065260598083707L;
    private JSlider slider;
    /**
     * horizontal or vertical
     *
     * @see SwingConstants.HORIZONTAL
     */
    private int orientation;
    private CutoffLine cutoffLine;
    private JPanel parent;
    /**
     * false - min on left, max on right
     */
    private boolean inverted = true;
    private int sliderDiameter;
    private int max = 100;
    private int min = 0;
    private HierarchicalResult hclust;

    /**
     *
     * @param panel
     * @param orientation -- SwingConstants.HORIZONTAL
     * @param cutoff
     * @param sliderDiam
     */
    public CutoffSlider(JPanel panel, int orientation, CutoffLine cutoff, int sliderDiam) {
        this.parent = panel;
        this.orientation = orientation;
        this.cutoffLine = cutoff;
        this.sliderDiameter = sliderDiam;
        initComponents();
    }

    /**
     *
     * @param panel
     * @param orientation SwingConstants.HORIZONTAL
     * @param invert true - left-to-right | false - right-to-left
     * @param cutoff
     */
    public CutoffSlider(JPanel panel, int orientation, boolean invert, CutoffLine cutoff) {
        this.parent = panel;
        this.orientation = orientation;
        this.cutoffLine = cutoff;
        this.inverted = invert;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints verticalFill = new GridBagConstraints();
        verticalFill.anchor = GridBagConstraints.NORTH;
        verticalFill.fill = GridBagConstraints.VERTICAL;
        verticalFill.weighty = 1.0;

        slider = new JSlider(orientation);
        slider.setInverted(inverted);
        slider.setMinimum(min);
        slider.setMaximum(max);
        slider.setValue(100);
        slider.addChangeListener(this);
        slider.setBackground(parent.getBackground());
        slider.setUI(new CustomSliderUI(slider, sliderDiameter));

        GridBagConstraints c = new GridBagConstraints();

        if (orientation == SwingConstants.HORIZONTAL) {
            c.fill = GridBagConstraints.HORIZONTAL;
        } else {
            c.fill = GridBagConstraints.VERTICAL;
        }
        if (inverted) {
            c.anchor = GridBagConstraints.NORTHEAST;
            slider.setValue(100);
        } else {
            c.anchor = GridBagConstraints.NORTHWEST;
            slider.setValue(0);
        }
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.insets = new java.awt.Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = GridBagConstraints.REMAINDER;
        add(Box.createVerticalGlue(), verticalFill);
        add(slider, c);
    }

    @Override
    public void datasetChanged(DendrogramDataEvent evt, DendrogramMapping dataset) {
        hclust = dataset.getRowsResult();
        updatePosition();
    }

    @Override
    public void cellWidthChanged(DendrogramDataEvent evt, int width, boolean isAdjusting) {
        //we don't care
    }

    @Override
    public void cellHeightChanged(DendrogramDataEvent evt, int height, boolean isAdjusting) {
        //we don't care
    }

    @Override
    public void clusterSelected(DendrogramTree source, TreeCluster cluster, DendrogramMapping data) {
        //we don't care
    }

    /**
     * Slider accepts only integers, so the cutoff represents percentage of the
     * tree
     *
     * @param source
     * @param width
     * @param height
     */
    @Override
    public void treeUpdated(DendrogramTree source, int width, int height) {
        updatePosition();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        cutoffLine.setCutoff(slider.getValue(), slider.getValueIsAdjusting());
    }

    public boolean isInverted() {
        return inverted;
    }

    public void updatePosition() {
        if (hclust != null) {
            int pos = cutoffLine.computePosition(hclust, min, max);
            slider.setValue(pos);
            //System.out.println("slider pos " + pos + " from cutoff " + hclust.getCutoff());
        }
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
        if (slider != null) {
            slider.setInverted(inverted);
        }
    }

    @Override
    public void leafOrderUpdated(Object source, HierarchicalResult mapping) {
        //nothing to do
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
