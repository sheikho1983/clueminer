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
package org.clueminer.rserve;

import org.clueminer.r.api.RException;
import org.clueminer.r.api.RExpr;
import org.clueminer.utils.Props;
import org.rosuda.JRI.Rengine;

/**
 * Test requires running RServe backend.
 *
 * @see https://github.com/s-u/Rserve
 * @author deric
 */
public class RServeTest {

    public static void main(String[] args) throws RException, InterruptedException {
        Props conf = new Props();
        RServe server = new RServe(conf);
        RExpr expr = server.eval("sqrt(9)");

        System.out.println("sqrt(9) = " + expr.asDouble());

        System.out.println("R_HOME: " + System.getenv("R_HOME"));
        System.out.println("path: " + System.getProperty("java.library.path"));

        // Create an R vector in the form of a string.
        String javaVector = "c(1,2,3,4,5)";

        // Start Rengine.
        Rengine engine = new Rengine(new String[]{"--no-save"}, false, null);

        // The vector that was created in JAVA context is stored in 'rVector' which is a variable in R context.
        engine.eval("rVector=" + javaVector);

        //Calculate MEAN of vector using R syntax.
        engine.eval("meanVal=mean(rVector)");

        //Retrieve MEAN value
        double mean = engine.eval("meanVal").asDouble();

        //Print output values
        System.out.println("Mean of given vector is=" + mean);


    }

}
