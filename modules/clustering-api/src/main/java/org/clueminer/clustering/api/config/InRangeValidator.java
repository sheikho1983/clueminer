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
package org.clueminer.clustering.api.config;

import org.clueminer.clustering.api.config.annotation.Range;

/**
 *
 * @author Tomas Barton
 */
public class InRangeValidator implements ConstraintValidator<Range, Number> {

    private double low = Double.MIN_VALUE;
    private double high = Double.MAX_VALUE;

    @Override
    public void initialize(Range annotation) {
        if (annotation.from() >= annotation.to()) {
            throw new ConfigException(
                    "Invalid range (" + annotation.from() + " -> " + annotation.to()
                    + "). Property cannot be set.");
        }

        low = annotation.from();
        high = annotation.to();
    }

    @Override
    public boolean isValid(Number input) {
        if (input == null) {
            return true;
        }

        return input.doubleValue() >= low && input.doubleValue() <= high;
    }
}
