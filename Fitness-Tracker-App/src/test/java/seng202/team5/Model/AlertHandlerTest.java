package seng202.team5.Model;


import org.junit.Test;
import seng202.team5.DataManipulation.InputDataParser;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.*;


/**
 * This test class provides unit tests for the AlertHandler class.
 */
public class AlertHandlerTest {


    private Date date = new Date();
    private static DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
    private InputDataParser parser = new InputDataParser();
    private User user = new User ("Test", 15, 1.7, 60, date);


    @Test
    /**
     * Testing the newGoalAlert function.
     */
    public void testGoalAlert() {
        Alert alert = AlertHandler.newGoalAlert("test goal");
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
        Alert countAlert = AlertHandler.activityAlert(user);
        assertEquals(dateTimeFormat.format(date), countAlert.getDateString());
        assertEquals("5 activities uploaded", countAlert.getMessage());
        assertEquals("Activity count", countAlert.getType());
    }


    @Test
    /**
     * Testing the activityAlert function with a user that has 10 activities.
     */
    public void testCountAlert10Activities() {
        ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/10Activites.csv");
        user.setActivities(activities);
        Alert countAlert = AlertHandler.activityAlert(user);
        assertEquals(dateTimeFormat.format(date), countAlert.getDateString());
        assertEquals("10 activities uploaded", countAlert.getMessage());
        assertEquals("Activity count", countAlert.getType());
    }




    @Test
    /**
     * Testing the activityAlert function with a user that has 0 activities.
     */
    public void testCountAlertNull0() {
        Alert countAlert = AlertHandler.activityAlert(user);
        assertNull(countAlert);
    }


    @Test
    /**
     * Testing the activityAlert function with a user that has 4 activities.
     */
    public void testCountAlertNull() {
        ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/dataAnalysisTests.csv");
        user.setActivities(activities);
        Alert countAlert = AlertHandler.activityAlert(user);
        assertNull(countAlert);
    }


    @Test
    /**
     * Testing the expiredGoalAlert function.
     */
    public void testExpiredGoalAlert() {
        Alert alert = AlertHandler.expiredGoalAlert("test goal");
        assertEquals(dateTimeFormat.format(date), alert.getDateString());
        assertEquals("Goal: test goal expired", alert.getMessage());
        assertEquals("Goal expired", alert.getType());
    }


}