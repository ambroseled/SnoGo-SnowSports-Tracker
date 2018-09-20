package seng202.team5.Model;


import org.junit.Test;
import seng202.team5.DataManipulation.InputDataParser;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class AlertHandlerTest {
    private Date date = new Date();
    private static DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
    private InputDataParser parser = new InputDataParser();
    private User user = new User ("Test", 15, 1.7, 60, date);

    @Test
    public void testGoalAlert() {
        Alert alert = AlertHandler.newGoalAlert("test goal");
        assertEquals(dateTimeFormat.format(date), alert.getDateString());
        assertEquals("Goal: test goal completed", alert.getMessage());
        assertEquals("Goal completed", alert.getType());
    }

    /*
    @Test
    public void testCountAlertTrue() {
        ArrayList<Activity> activities = parser.parseCSVToActivities("TestFiles/alertGoalTestData.csv");
        user.setActivities(activities);
        Alert countAlert = AlertHandler.activityAlert(user);
        assertEquals(dateTimeFormat.format(date), countAlert.getDateString());
        assertEquals("5 activities uploaded", countAlert.getMessage());
        assertEquals("Activity count", countAlert.getType());
    }*/


    @Test
    public void testCountAlertNull0() {
        Alert countAlert = AlertHandler.activityAlert(user);
        assertNull(countAlert);
    }


    @Test
    public void testCountAlertNull() {
        ArrayList<Activity> activities = parser.parseCSVToActivities("TestFiles/dataAnalysisTests.csv");
        user.setActivities(activities);
        Alert countAlert = AlertHandler.activityAlert(user);
        assertNull(countAlert);
    }


}