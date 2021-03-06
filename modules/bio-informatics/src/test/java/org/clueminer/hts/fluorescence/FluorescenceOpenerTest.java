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
package org.clueminer.hts.fluorescence;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.clueminer.fixtures.BioFixture;
import org.clueminer.hts.api.HtsInstance;
import org.clueminer.hts.api.HtsPlate;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.util.Exceptions;

/**
 *
 * @author deric
 */
public class FluorescenceOpenerTest {

    private static BioFixture fixture;
    private static FluorescenceOpener test;

    public FluorescenceOpenerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        fixture = new BioFixture();
        test = new FluorescenceOpener();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of detectMIME method, of class FluorescenceOpener.
     */
    @Test
    public void testDetectMIME() {
        try {
            Collection res = test.detectMIME(fixture.apData());
            assertEquals(true, res.toString().contains("x-tex"));
            System.out.println("res: " + res);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Test
    public void testOpen() {
        try {
            File file = fixture.apData();
            boolean res = test.openFile(file);
            assertTrue(res);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Test
    public void testOpenFile() {
        try {
            System.out.println("openFile");
            FluorescenceOpener opener = new FluorescenceOpener();
            FluorescenceImporter importer = new FluorescenceImporter(fixture.apData());
            ProgressHandle ph = ProgressHandle.createHandle("Opening file " + importer.getFile().getName());
            importer.setProgressHandle(ph);
            importer.run();
            HtsPlate<HtsInstance> plate = importer.getDataset();

            //Dataset<FluorescenceInstance> normalized = opener.normalize(plate);
            // System.out.println(normalized);
            //    assertEquals(expResult, result);
            //    fail("The test case is a prototype.");
            //    fail("The test case is a prototype.");
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Test of normalize method, of class FluorescenceOpener.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        FluorescenceDataset plate = null;
        FluorescenceOpener instance = new FluorescenceOpener();
        // instance.normalize(plate);
        // TODO review the generated test code and remove the default call to fail.
        //  fail("The test case is a prototype.");
    }

    /**
     * Test of translatePosition method, of class FluorescenceOpener.
     */
    @Test
    public void testTranslatePosition() throws Exception {
        System.out.println("translatePosition");
        int ord = 0;
        int col = 0;
        int colCnt = 32;
        FluorescenceOpener instance = new FluorescenceOpener();
        int expResult = 0;
        Normalization norm = new QuadruplicateNormalization();
        int result = norm.translatePosition(ord, col, colCnt);
        assertEquals(expResult, result);
        //last well
        result = norm.translatePosition(47, 31, colCnt);
        assertEquals(1535, result);
        // TODO review the generated test code and remove the default call to fail.
        //   fail("The test case is a prototype.");
    }

    /**
     * Test of saveDataset method, of class FluorescenceOpener.
     */
    @Test
    public void testSaveDataset() throws IOException {
        FluorescenceOpener opener = new FluorescenceOpener();
        FluorescenceImporter importer = new FluorescenceImporter(fixture.apData());
        ProgressHandle ph = ProgressHandle.createHandle("Opening file " + importer.getFile().getName());
        importer.setProgressHandle(ph);
        importer.run();
        HtsPlate<HtsInstance> plate = importer.getDataset();
        /* opener.saveDataset(plate, "test", false);
         * Dataset<FluorescenceInstance> normalized = opener.normalize(plate);
         * opener.saveDataset((FluorescenceDataset) normalized, "test-norm", true); */
    }
}
