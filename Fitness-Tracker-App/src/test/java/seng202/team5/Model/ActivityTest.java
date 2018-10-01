package seng202.team5.Model;

import org.junit.Test;
import seng202.team5.DataManipulation.DataValidator;
import seng202.team5.DataManipulation.InputDataParser;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ActivityTest {

    /**
     * Test for when no activity name given next to the start tag
     */
    @Test
    public void dataSetEquality() {
        InputDataParser parser = new InputDataParser();
        ArrayList<Activity> activities = parser.parseCSVToActivities("TestFiles/dataSetEqualityTest.csv");

            assertEquals(true, activities.get(0).getDataSet().equals(activities.get(4).getDataSet()));
            assertEquals(false, activities.get(0).getDataSet().equals(activities.get(1).getDataSet()));
            assertEquals(false, activities.get(1).getDataSet().equals(activities.get(2).getDataSet()));
            assertEquals(true, activities.get(2).getDataSet().equals(activities.get(2).getDataSet()));


    }

    @Test
    public void dataSetEqualityAfterValidation() {
        InputDataParser parser = new InputDataParser();
        ArrayList<Activity> activities = parser.parseCSVToActivities("TestFiles/validationTestFiles/missingDataValues.csv");
        activities.add(parser.parseCSVToActivities("TestFiles/validationTestFiles/missingDataValues.csv").get(0));

        DataValidator validator = new DataValidator();
        validator.validateActivity(activities.get(0));

        assertEquals(true, activities.get(0).getDataSet().equals(activities.get(1).getDataSet()));
    }

}
