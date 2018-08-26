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
    public void testOneDistZero() {

    }


    /**
     * Testing the oneDist function. This test case tests that
     * the function works correctly when the two location points
     * passed correspond to movement backwards.
     */
    @Test
    public void testOneDistBackward() {

    }

    /**
     * Testing the oneDist function. This test case tests that
     * the function works correctly when the two location points
     * passed correspond to movement forwards.
     */
    @Test
    public void testOneDistForward() {

    }


    /**
     * Testing the oneDist function. This test case tests that
     * the function works correctly when the two location points
     * passed have change only in their altitude values.
     */
    @Test
    public void testOneDistAlt() {

    }


    /**
     * Testing the oneDist function. This test case tests that
     * the function works correctly when the two location points
     * passed have change only in their longitude values.
     */
    @Test
    public void testOneDistLong() {

    }



    /**
     * Testing the oneDist function. This test case tests that
     * the function works correctly when the two location points
     * passed have change only in their latitude values.
     */
    @Test
    public void testOneDistLat() {

    }


    @Test
    public void testAppendDistance() {

    }


    /**
     * Testing the oneSpeed function. The test checks that
     * the function operates correctly with normal data.
     */
    @Test
    public void testOneSpeed() {
        double result = BasicMetrics.oneSpeed(10.0, 15.0, 10.0, 15.0);
        assertEquals(1, result, 0.0);

    }


    /**
     * Testing the oneSpeed function. The test checks that
     * the function operates correctly when the data relates to
     * a speed of zero.
     */
    @Test
    public void testOneSpeedZero() {
        // Getting the result of the speed when the distance change is zero
        double result = BasicMetrics.oneSpeed(10.0, 10.0, 34.0, 35.0);
        // Getting the result of the speed when the time change is zero
        double result2 = BasicMetrics.oneSpeed(10.0, 15.0, 34.0, 34.0);
        assertEquals(0.0, result, 0.0);
        assertEquals(0.0, result2, 0.0);
    }



    @Test
    public void testCalcAvgHeart() {

    }


    /**
     * Testing the oneAlt function. Test checks that the function
     * operates correctly when normal data is passed.
     */
    @Test
    public void testOneAlt() {
        double result = BasicMetrics.oneAlt(10, 15);
        assertEquals(5, result, 0.0);
    }


    /**
     * Testing the oneAlt function. Test checks that the function
     * operates correctly when data that gives a negative value is passed.
     */
    @Test
    public void testOneAltNeg() {
        double result = BasicMetrics.oneAlt(15, 10);
        assertEquals(5, result, 0.0);
    }



    /**
     * Testing the oneAlt function. Test checks that the function
     * operates correctly when data that gives no change in altitude
     * is passed.
     */
    @Test
    public void testOneAltZero() {
        double result = BasicMetrics.oneAlt(15, 15);
        assertEquals(0, result, 0.0);
    }



    @Test
    public void testCalcVertical() {

    }

}