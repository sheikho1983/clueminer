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
package org.clueminer.dataset.api;

import java.util.Iterator;
import org.clueminer.math.Vector;
import org.clueminer.utils.Props;

/**
 *
 * @author Tomas Barton
 * @param <T>
 * @param <E>
 */
public abstract class DataRow<T extends Number> extends AbstractInstance<T> implements Instance<T> {

    private static final long serialVersionUID = 7076642394603104341L;
    protected Instance ancestor;
    protected double[] meta;

    public DataRow(Object classValue) {
        setClassValue(classValue);
    }

    /**
     * Returns the value for the given index.
     *
     * @param index
     * @param defaultValue
     * @return
     */
    protected abstract double getValue(int index, double defaultValue);

    /**
     * Sets the given data for the given index.
     *
     * @param index
     * @param value
     * @param defaultValue
     */
    protected abstract void setValue(int index, double value, double defaultValue);

    /**
     * Trims the number of columns to the actually needed number.
     */
    public abstract void trim();

    @Override
    public double[] getMetaNum() {
        return meta;
    }

    @Override
    public void setMetaNum(double[] meta) {
        this.meta = meta;
    }

    /**
     * Returns a string representation for this data row.
     *
     * @return
     */
    @Override
    public abstract String toString();

    @Override
    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        if (getId() != null) {
            sb.append(getId()).append(" - ");
        }
        return sb.append(getName()).toString();
    }

    /**
     * Returns the value stored at the given {@link Attribute}'s index. Returns
     * Double.NaN if the given attribute is null.
     *
     * @param attribute
     * @return
     */
    public double getValue(Attribute attribute) {
        if (attribute == null) {
            return Double.NaN;
        } else {
            try {
                return this.value(attribute.getIndex());
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException("DataRow: of Attribute " + attribute.getName() + " is out of bounds.");
            }
        }
    }

    @Override
    public Plotter getPlotter(Props props) {
        PlotterFactory factory = PlotterFactory.getInstance();
        for (Plotter p : factory.getAll()) {
            if (p.isSupported(DataType.DISCRETE)) {
                p.addInstance(this);
                return p;
            }
        }
        throw new RuntimeException("No visualization found for data type " + this.getClass().getName());
    }

    /**
     * Sets the value of the {@link Attribute} to <code>value</code>.
     *
     * @param attribute
     * @param value
     */
    public void setValue(Attribute attribute, double value) {
        set(attribute.getIndex(), value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double dot(Vector v) {
        if (this.size() != v.size()) {
            throw new ArithmeticException("Vectors must have the same length" + this.size() + " != " + v.size());
        }
        double dot = 0.0;
        for (int i = 0; i < this.size(); i++) {
            dot += this.get(i) * v.get(i);
        }

        return dot;
    }

    @Override
    public double pNorm(double p) {
        double norm = 0;
        for (int i = 0; i < size(); i++) {
            norm += Math.pow(Math.abs(get(i)), p);
        }

        return Math.pow(norm, 1.0 / p);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Vector<T> add(double num) {
        Vector<T> res = duplicate();
        for (int i = 0; i < this.size(); i++) {
            res.set(i, this.get(i) + num);
        }
        return res;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Vector<T> minus(double num) {
        Vector<T> res = duplicate();
        for (int i = 0; i < this.size(); i++) {
            res.set(i, this.get(i) - num);
        }
        return res;
    }

    @Override
    public double mean() {
        double m = 0;
        for (int i = 0; i < size(); i++) {
            m += get(i);
        }
        return m / size();
    }

    @Override
    public double variance() {
        return Math.sqrt(variance());
    }

    @Override
    public double stdDev() {
        double mu = mean();
        double variance = 0;

        double N = size();

        int used = 0;
        for (int i = 0; i < N; i++) {
            used++;
            variance += Math.pow(get(i) - mu, 2) / N;
        }
        //Now add all the zeros we skipped into it
        variance += (N - used) * Math.pow(0 - mu, 2) / N;

        return variance;
    }

    /**
     * When preprocessing data sometimes we need to display reference to
     * original data
     *
     * @return Instance from which was this one derived
     */
    @Override
    public Instance getAncestor() {
        return ancestor;
    }

    /**
     * Set reference to original data row
     *
     * @param instance
     */
    @Override
    public void setAncestor(Instance instance) {
        this.ancestor = instance;
    }

    @Override
    public Iterator<? extends Object> values() {
        return new InstanceValueIterator();
    }

    private class InstanceValueIterator implements Iterator<Double> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public Double next() {
            index++;
            return value(index - 1);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from instance using the iterator.");

        }
    }

    @Override
    public String[] toStringArray() {
        String[] res = new String[size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = String.valueOf(getValue(i, Double.NaN));
        }
        return res;
    }
}
