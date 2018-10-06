package seng202.team5.DataManipulation;

import org.junit.Test;
import seng202.team5.Model.Activity;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataExporterTest {

    @Test
    public void exportData() {
        InputDataParser parser = new InputDataParser();
        ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/alertGoalTestData.csv");
        boolean exported = DataExporter.exportData(activities, "exporterTest.csv");
        assertTrue(exported);
    }
}