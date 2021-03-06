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
package org.clueminer.graph.api;

import java.util.Collection;
import java.util.LinkedHashMap;
import org.clueminer.utils.ServiceFactory;
import org.openide.util.Lookup;

/**
 *
 * @author deric
 */
public class GraphConvertorFactory extends ServiceFactory<GraphConvertor> {

    private static GraphConvertorFactory instance;

    /**
     * What type of edges will be included in the graph. Acceptable values:
     * - any
     * - bidirect
     * - single (single direction)
     */
    public static final String INCLUDE_EDGES = "include-edges";

    public static GraphConvertorFactory getInstance() {
        if (instance == null) {
            instance = new GraphConvertorFactory();
        }
        return instance;
    }

    private GraphConvertorFactory() {
        providers = new LinkedHashMap<>();
        Collection<? extends GraphConvertor> list = Lookup.getDefault().lookupAll(GraphConvertor.class);
        for (GraphConvertor g : list) {
            providers.put(g.getName(), g);
        }
        sort();
    }

    @Override
    public GraphConvertor[] getAllArray() {
        return providers.values().toArray(new GraphConvertor[0]);
    }
}
