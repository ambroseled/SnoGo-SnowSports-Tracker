package seng202.team5.Model;


import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;



//TODO: Make corrupt and invalid test files to be tested
//TODO: More tests to check the functionality of the parser

public class InputDataParserTest {


    private static ArrayList<Activity> activities;


    @BeforeClass
    public static void beforeAll() {
        InputDataParser parser = new InputDataParser();
        activities = parser.parseCSVToActivities("TestFiles/dataAnalysisTests.csv");
    }


    @Test
    /**
     * Testing that the parser finds the correct amount of activities.
     */
    public void testNumberOfActivities() {
        assertEquals(4, activities.size());
    }

}