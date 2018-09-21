package seng202.team5.DataManipulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.Model.*;
import java.util.ArrayList;
import static org.junit.Assert.*;


/**
 *
 */
public class DataBaseControllerTest {

    private DataBaseController db;

    @Before
    public void setUp() {
        db = new DataBaseController();
    }

    @After
    public void tearDown() {
        db.closeConnection();
    }


  //  @Test
    /**
     *
     */
    public void testGetActivities() {
        ArrayList<User> users = db.getUsers();
        ArrayList<Activity> activities = db.getActivities(users.get(0).getId());
        assertEquals(activities.size(), users.get(0).getActivities().size());
    }


  //  @Test
    /**
     *
     */
    public void testGetGoals() {
        ArrayList<User> users = db.getUsers();
        ArrayList<Goal> goals = db.getGoals(users.get(0).getId());
        assertEquals(goals.size(), users.get(0).getGoals().size());
    }


   // @Test
    /**
     *
     */
    public void testGetAlerts() {
        ArrayList<User> users = db.getUsers();
        ArrayList<Alert> alerts = db.getAlerts(users.get(0).getId());
        assertEquals(alerts.size(), users.get(0).getAlerts().size());
    }


   // @Test
    /**
     *
     */
    public void testGetDataPoints() {
        DataSet dataSet = db.getDataSet(db.getActivities(db.getUsers().get(0).getId()).get(0).getId());
        ArrayList<DataPoint> dataPoints = db.getDataPoints(dataSet.getId());
        assertEquals(dataPoints.size(), dataSet.getDataPoints().size());
    }

}