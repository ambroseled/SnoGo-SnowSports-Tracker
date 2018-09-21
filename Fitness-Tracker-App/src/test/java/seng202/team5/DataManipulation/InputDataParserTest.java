package seng202.team5.DataManipulation;


import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static org.junit.Assert.*;


//TODO: Test overlapping time files

/**
 *
 */
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



    /**
     * Testing with data that would be one activity, however start tag missing
     */
    @Test
    public void testNoStartTagWithData() {
        InputDataParser parser = new InputDataParser();

        try {
            ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/parserTestFiles/noStartWithData.csv");
            assertEquals(0, activities.size());

        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Testing with multiple activities, however one of their start tags is missing
     */
    @Test
    public void testNoStartTagWithOtherActivities() {
        InputDataParser parser = new InputDataParser();

        try {
            ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/parserTestFiles/noStartWithActivities.csv");
            assertEquals(3, activities.size());
            assertEquals(17, activities.get(0).getDataSet().getDataPoints().size());
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Test with a blank csv
     */
    @Test
    public void testEmptyCSV() {
        InputDataParser parser = new InputDataParser();

        try {
            ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/parserTestFiles/emptyCSV.csv");
            assertEquals(0, activities.size());
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Test with a random file type
     */
    @Test
    public void testWrongFileType() {
        InputDataParser parser = new InputDataParser();

        try {
            ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/parserTestFiles/randomFile.jpeg");
            assertEquals(0, activities.size());
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Test with a heavily mixed up csv file
     */
    @Test
    public void testCorruptFile() {
        InputDataParser parser = new InputDataParser();

        try {
            ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/parserTestFiles/corruptFile.csv");
            assertEquals(2, activities.size());
            assertEquals(17, activities.get(0).getDataSet().getDataPoints().size());
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Test with blank gaps between rows
     */
    @Test
    public void testGapsInFile() {
        InputDataParser parser = new InputDataParser();

        try {
            ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/parserTestFiles/gapInFile.csv");
            assertEquals(4, activities.size());
            assertEquals(13, activities.get(0).getDataSet().getDataPoints().size());
            assertEquals(17, activities.get(1).getDataSet().getDataPoints().size());
            assertEquals(18, activities.get(2).getDataSet().getDataPoints().size());
            assertEquals(22, activities.get(3).getDataSet().getDataPoints().size());
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Test that when the format for a data point isn't correct
     */
    @Test
    public void testWrongFormats() {
        InputDataParser parser = new InputDataParser();

        try {
            ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/parserTestFiles/wrongFormats.csv");
            assertEquals(1, activities.size());
            assertEquals(null, activities.get(0).getDataSet().getDataPoints().get(4).getDateTime());
            assertEquals(0, activities.get(0).getDataSet().getDataPoints().get(5).getHeartRate());
            assertEquals(171.538081, activities.get(0).getDataSet().getDataPoints().get(6).getLongitude(), 0.000001);
            assertEquals(8851, activities.get(0).getDataSet().getDataPoints().get(9).getElevation(), 0.01);
        }
        catch (Exception e) {
            assert(false);
        }
    }

    /**
     * Test for when no activity name given next to the start tag
     */
    @Test
    public void missingName() {
        InputDataParser parser = new InputDataParser();

        try {
            ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/parserTestFiles/missingName.csv");
            assertEquals(4, activities.size());
            assertEquals("", activities.get(1).getName());
        }
        catch (Exception e) {
            assert(false);
        }
    }



}