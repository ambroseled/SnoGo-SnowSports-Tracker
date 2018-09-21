package seng202.team5.DataManipulation;


import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.Assert.*;



//TODO: Make corrupt and invalid test files to be tested
//TODO: More tests to check the functionality of the parser

public class InputDataParserTest {


    private static ArrayList<Activity> activities;


    @BeforeClass
    public static void beforeAll() {
        InputDataParser parser = new InputDataParser();
        activities = parser.parseCSVToActivities("src/main/resources/TestFiles/parserTestData.csv");
    }


    @Test
    /**
     * Testing that the parser finds the correct amount of activities.
     */
    public void testNumberOfActivities() {
        assertEquals(4, activities.size());
    }



    @Test
    /**
     * Testing that the parser reads the correct number of lines for a given activity
     */
    public void testNumberOfDataPoints() {
        assertEquals(13, activities.get(0).getDataSet().getDataPoints().size());
    }



    @Test
    public void testOneLine() {
        DataPoint point = activities.get(0).getDataSet().getDataPoints().get(0);

        DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
        String dateTime = dateTimeFormat.format(point.getDateTime());

        assertEquals("07/06/18 10:07:47", dateTime);
    }




    @Test
    /**
     * Testing that the correct heart rate value is read by the parser.
     */
    public void testHeartRate() {
        DataPoint point = activities.get(0).getDataSet().getDataPoints().get(0);
        assertEquals(132, point.getHeartRate());
    }


    @Test
    /**
     * Testing that the correct latitude value is read by the parser.
     */
    public void testLatitude() {
        DataPoint point = activities.get(0).getDataSet().getDataPoints().get(0);
        assertEquals(-43.49153, point.getLatitude(), 0.0);
    }


    @Test
    /**
     * Testing that the correct longitude value is read by the parser.
     */
    public void testLongitude() {
        DataPoint point = activities.get(0).getDataSet().getDataPoints().get(0);
        assertEquals(171.538148, point.getLongitude(), 0.0);
    }


    @Test
    /**
     * Testing that the correct altitude value is read by the parser.
     */
    public void testElevation() {
        DataPoint point = activities.get(0).getDataSet().getDataPoints().get(0);
        assertEquals(1732.54, point.getElevation(), 0.0);
    }


    @Test
    /**
     * Testing that the correct name for an activity is read by the parser.
     */
    public void testName() {
        assertEquals("Not Riding", activities.get(0).getName());
    }


}