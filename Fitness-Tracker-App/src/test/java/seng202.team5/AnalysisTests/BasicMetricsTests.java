package seng202.team5.AnalysisTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.Model.DataAnalysis.BasicMetrics;

import static org.junit.Assert.*;

public class BasicMetricsTests {

    @Before
    public void setUp() throws Exception {
      BasicMetrics metrics = new BasicMetrics();
    }


    /**
     * Testing the cartesian function which is a helper function
     * of the oneDist function.
     */
    @Test
    public void testCartesian() {

    }


    /**
     * Testing the oneDist function. This test case tests that
     * the function works correctly when the two location points
     * passed correspond to no movement.
     */
    @Test
    public void testZero() {

    }


    /**
     * Testing the oneDist function. This test case tests that
     * the function works correctly when the two location points
     * passed correspond to movement backwards.
     */
    @Test
    public void testBackwards() {

    }

    /**
     * Testing the oneDist function. This test case tests that
     * the function works correctly when the two location points
     * passed correspond to movement forwards.
     */
    @Test
    public void testForwards() {

    }


    /**
     * Testing the oneDist function. This test case tests that
     * the function works correctly when the two location points
     * passed have change only in their altitude values.
     */
    @Test
    public void testAltitude() {

    }


    /**
     * Testing the oneDist function. This test case tests that
     * the function works correctly when the two location points
     * passed have change only in their longitude values.
     */
    @Test
    public void testLongitude() {

    }



    /**
     * Testing the oneDist function. This test case tests that
     * the function works correctly when the two location points
     * passed have change only in their latitude values.
     */
    @Test
    public void testLatitude() {

    }


    @Test
    public void testAppendDistance() {

    }



    @Test
    public void testOneSpeed() {

    }



    @Test
    public void testCalcAvgHeart() {

    }



    @Test
    public void testOneAlt() {

    }



    @Test
    public void testCalcVertical() {

    }

}