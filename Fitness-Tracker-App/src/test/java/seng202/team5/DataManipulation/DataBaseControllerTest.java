package seng202.team5.DataManipulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.Model.Activity;
import seng202.team5.Model.Alert;
import seng202.team5.Model.Goal;
import seng202.team5.Model.User;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataBaseControllerTest {

    DataBaseController db;

    @Before
    public void setUp() {
        db = new DataBaseController();
    }

    @After
    public void tearDown() {
        db.closeConnection();
    }

    @Test
    /**
     * This test is testing the functionality of pulling users out of
     * the database. This is done by getting all users and then checking that
     * the first user in the list is correct. This is because the number of users
     * will change so that cannot be relied on.
     *
     * For this test it is assumed that the assert statements will be updated if the
     * first user in the database is altered or removed.
     */
    public void getUsers() {
        ArrayList<User> users = db.getUsers();
        User user = users.get(0);
        assertEquals("John Stevens", user.getName());
        assertEquals(25, user.getAge());
        assertEquals(1.8, user.getHeight(), 0.0);
        assertEquals(75.8, user.getWeight(), 0.0);
    }

    @Test
    /**
     * This test is testing the functionality of pulling activities out of
     * the database. This is done by getting all activities for a user and then
     * checking that there is the correct number of activities.
     *
     * For this test it is assumed that the assert statements will be updated if the
     * number of activities of the first user in the database is altered.
     */
    public void getActivities() {
        ArrayList<User> users = db.getUsers();
        ArrayList<Activity> activities = db.getActivities(users.get(0).getId());
        int count = 0;
        for (Activity x : activities) {
            count++;
        }
        assertEquals(count, users.get(0).getActivities().size());
    }


    //TODO: Change this maybe
    @Test
    /**
     * This test is testing the functionality of pulling Goals out of
     * the database. This is done by getting all goals for a selected user and then checking that
     * the first goal in the list is of type Goal.
     *
     * For this test it is assumed that the assert statements will be updated if the
     * first goal for the user in the database is altered or removed.
     */
    public void getGoals() {
        ArrayList<Goal> goals = db.getGoals(1);
        assertTrue(goals.get(0) instanceof Goal);
    }


    @Test
    /**
     * This test is testing the functionality of pulling Alerts out of
     * the database. This is done by getting all alerts for a selected user and then checking that
     * the first alert in the list is correct. This is because the number of alerts for a user
     * will change so that cannot be relied on.
     *
     * For this test it is assumed that the assert statements will be updated if the
     * first alert for the user in the database is altered or removed.
     */
    public void getAlerts() {
        ArrayList<Alert> alerts = db.getAlerts(1);
        Alert alert = alerts.get(0);
        assertEquals("Testing", alert.getName());
        assertEquals("This is a test",  alert.getMessage());
        assertEquals("www.test.com", alert.getWebLink());
        assertEquals("06/07/2018", alert.getDateString());
    }
}