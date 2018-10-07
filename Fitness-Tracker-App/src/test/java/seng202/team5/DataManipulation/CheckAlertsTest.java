package seng202.team5.DataManipulation;


import org.junit.Test;
import seng202.team5.DataManipulation.CheckAlerts;
import seng202.team5.DataManipulation.InputDataParser;
import seng202.team5.Model.Activity;
import seng202.team5.Model.Alert;
import seng202.team5.Model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.*;


/**
 * This test class provides unit tests for the CheckAlerts class.
 */
public class CheckAlertsTest {


    private Date date = new Date();
    private static DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    private InputDataParser parser = new InputDataParser();
    private User user = new User ("Test", 15, 1.7, 60, date);


    @Test
    /**
     * Testing the newGoalAlert function.
     */
    public void testGoalAlert() {
        Alert alert = CheckAlerts.newGoalAlert("test goal");
        assertEquals(dateTimeFormat.format(date), alert.getDateString());
        assertEquals("Goal: test goal completed", alert.getMessage());
        assertEquals("Goal completed", alert.getType());
    }


    @Test
    /**
     * Testing the activityAlert function with a user that has 5 activities.
     */
    public void testCountAlert5Activities() {
        ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/alertGoalTestData.csv");
        user.setActivities(activities);
        Alert countAlert = CheckAlerts.activityAlert(user);
        assertEquals(dateTimeFormat.format(date), countAlert.getDateString());
        assertEquals("5+ activities uploaded", countAlert.getMessage());
        assertEquals("Activity count", countAlert.getType());
    }


    @Test
    /**
     * Testing the activityAlert function with a user that has 10 activities.
     */
    public void testCountAlert10Activities() {
        ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/10Activites.csv");
        user.setActivities(activities);
        Alert countAlert = CheckAlerts.activityAlert(user);
        assertEquals(dateTimeFormat.format(date), countAlert.getDateString());
        assertEquals("10+ activities uploaded", countAlert.getMessage());
        assertEquals("Activity count", countAlert.getType());
    }




    @Test
    /**
     * Testing the activityAlert function with a user that has 0 activities.
     */
    public void testCountAlertNull0() {
        Alert countAlert = CheckAlerts.activityAlert(user);
        assertNull(countAlert);
    }


    @Test
    /**
     * Testing the activityAlert function with a user that has 4 activities.
     */
    public void testCountAlertNull() {
        ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/alertTestsLessThan5.csv");
        user.setActivities(activities);
        Alert countAlert = CheckAlerts.activityAlert(user);
        assertNull(countAlert);
    }


    @Test
    /**
     * Testing the expiredGoalAlert function.
     */
    public void testExpiredGoalAlert() {
        Alert alert = CheckAlerts.expiredGoalAlert("test goal");
        assertEquals(dateTimeFormat.format(date), alert.getDateString());
        assertEquals("Goal: test goal expired", alert.getMessage());
        assertEquals("Goal expired", alert.getType());
    }



    @Test
    public void testBmiNormal() {
        User user = new User("Test", 25, 1.7, 55, new Date());
        Alert alert = CheckAlerts.bmiAlert(user);
        assertNull(alert);
    }


    @Test
    public void testBmiObese() {
        User user = new User("Test", 25, 1.3, 105, new Date());
        Alert alert = CheckAlerts.bmiAlert(user);
        assertEquals("BMI Category: Obese", alert.getMessage());
    }


    @Test
    public void testBmiOverWeight() {
        User user = new User("Test", 25, 1.8, 90, new Date());
        Alert alert = CheckAlerts.bmiAlert(user);
        assertEquals("BMI Category: Overweight", alert.getMessage());
    }


    @Test
    public void testBmiUnderWeight() {
        User user = new User("Test", 25, 1.7, 50, new Date());
        Alert alert = CheckAlerts.bmiAlert(user);
        assertEquals("BMI Category: Under weight", alert.getMessage());
    }



    @Test
    public void testBmiSeverelyUnderWeight() {
        User user = new User("Test", 25, 1.7, 30, new Date());
        Alert alert = CheckAlerts.bmiAlert(user);
        assertEquals("BMI Category: Severely Underweight", alert.getMessage());
    }

}