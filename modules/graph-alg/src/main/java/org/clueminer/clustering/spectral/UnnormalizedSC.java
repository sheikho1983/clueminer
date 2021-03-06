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
package org.clueminer.clustering.spectral;

import org.clueminer.dataset.api.Dataset;
import org.clueminer.dataset.api.Instance;
import org.clueminer.dataset.impl.ArrayDataset;
import org.clueminer.math.Matrix;
import org.clueminer.math.matrix.SymmetricMatrixDiag;
import org.openide.util.lookup.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mikusmi1
 */
@ServiceProvider(service = EigenVectorConvertor.class)
public class UnnormalizedSC<E extends Instance> implements EigenVectorConvertor<E> {

    private static final Logger LOG = LoggerFactory.getLogger(SpectralClustering.class);
    private static final String NAME = "Unnormalized SP";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Dataset<E> buildEigVecsMatrix(Matrix neighborhoodMatrix, int k) {
        LOG.debug("computing laplacian matrix");
        SymmetricMatrixDiag laplacianMatrix = buildSymmetricUnnormalizedLaplacianMatrix(neighborhoodMatrix);
        LOG.info("laplacian matrix rank: {}", laplacianMatrix.rank());

        LOG.info("2) Decomposition - compute eigenvectors");
        LOG.debug("computing eigen vectors");
        return computeEigenVectors(laplacianMatrix, k);
    }

    private SymmetricMatrixDiag buildSymmetricUnnormalizedLaplacianMatrix(Matrix similarityMatrix) {
        SymmetricMatrixDiag laplacianMatrix = new SymmetricMatrixDiag(similarityMatrix.rowsCount(), similarityMatrix.columnsCount(), 0);
        for (int i = 0; i < similarityMatrix.rowsCount(); i++) {
            for (int j = 0; j < similarityMatrix.columnsCount(); j++) {
                if (i == j) {
                    // Sum row
                    double digWeight = 0.0;
                    for (int columnIndex = 0; columnIndex < similarityMatrix.columnsCount(); columnIndex++) {
                        digWeight += similarityMatrix.get(i, columnIndex);
                    }

                    //Set diagonal by sum
                    laplacianMatrix.set(i, j, digWeight);
                    continue;
                }

                double weight = similarityMatrix.get(i, j);
                laplacianMatrix.set(i, j, 0.0 - weight);
            }
        }
        return laplacianMatrix;
    }

    private Dataset<E> computeEigenVectors(SymmetricMatrixDiag laplacianMatrix, int k) {
        Matrix eigMatrix = laplacianMatrix.eig().getV();
        LOG.info("eigen matrix rank: {}", eigMatrix.rank());

        // Convert eigen matrix to dataset
        double[][] eigArrayTemp = eigMatrix.getArrayCopy();
        double[][] eigArray = new double[eigArrayTemp.length][k];

        for (int i = 0; i < eigArrayTemp.length; i++) {
            System.arraycopy(eigArrayTemp[i], 0, eigArray[i], 0, k);
        }

        return new ArrayDataset<>(eigArray);
    }
}
