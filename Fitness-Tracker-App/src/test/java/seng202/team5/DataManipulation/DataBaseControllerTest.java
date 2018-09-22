package seng202.team5.DataManipulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.Model.*;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;


/**
 * This test class provides unit tests for the DatabaseController class.
 */
public class DataBaseControllerTest {

    private DataBaseController db;
    private User user = new User("test user", 15, 1.7, 56, new Date());
    private InputDataParser parser = new InputDataParser();
    private ArrayList<Activity> activities = parser.parseCSVToActivities("src/main/resources/TestFiles/dataBaseTest.csv");


    @Before
    public void  before() {
        db = new DataBaseController();
    }


    @After
    public void after() {
        db.closeConnection();
    }


    @Test
    public void testUserFunc() {
        db.storeNewUser(user);
        ArrayList<User> users = db.getUsers();
        User testUser = users.get(users.size() - 1);

        assertEquals(user.getName(), testUser.getName());

        testUser.setName("Steve");
        db.updateUser(testUser);
        users = db.getUsers();
        testUser = users.get(users.size() - 1);

        assertEquals("Steve", testUser.getName());

        db.removeUser(testUser);

        assertEquals(users.size() - 1, db.getUsers().size());
    }


    @Test
    public void testActivityFunc() {
        Activity activity = activities.get(0);
        db.storeNewUser(user);
        db.storeActivity(activity, user.getId());
        ArrayList<Activity> dbActs = db.getActivities(user.getId());
        Activity testAct = dbActs.get(dbActs.size() - 1);

        assertEquals(activity.getName(), testAct.getName());

        db.removeActivity(testAct);

        assertEquals(dbActs.size() - 1, db.getActivities(user.getId()).size());

        db.removeUser(user);
    }


    @Test
    public void testDataSetFunc() {
        // Call is made to store an activity not a DataSet as storing a DataSet is private functionality which is
        // called through a call to store an activity
        Activity activity = activities.get(0);
        db.storeNewUser(user);
        db.storeActivity(activity, user.getId());
        ArrayList<DataSet> dataSets = new ArrayList<>();
        for (Activity act : db.getActivities(user.getId())) {
            dataSets.add(act.getDataSet());
        }
        DataSet testSet = dataSets.get(dataSets.size() - 1);

        assertEquals(activities.get(activities.size() - 1).getDataSet().getAvgHeartRate(), testSet.getAvgHeartRate());

        // Only a call to remove activity is made as the DataSet table is set to cascade on delete and will therefore
        // be deleted with the activity
        db.removeActivity(activity);
        int count = dataSets.size() - 1;
        dataSets = new ArrayList<>();
        for (Activity act : db.getActivities(user.getId())) {
            dataSets.add(act.getDataSet());
        }

        assertEquals(count, dataSets.size());

        db.removeUser(user);
    }


    @Test
    public void testDataPointFunc() {
        // Call is made to store an activity not a DataPoint as storing a DataPoint is private functionality which is
        // called through a call to store an activity
        Activity activity = activities.get(0);
        db.storeNewUser(user);
        db.storeActivity(activity, user.getId());
        ArrayList<ArrayList<DataPoint>> dataPoints = new ArrayList<>();
        for (Activity act : db.getActivities(user.getId())) {
            dataPoints.add(act.getDataSet().getDataPoints());
        }
        ArrayList<DataPoint> dbPoints = dataPoints.get(dataPoints.size() - 1);

        assertEquals(activities.get(activities.size() - 1).getDataSet().getDataPoints().get(0).toString(),
                dbPoints.get(0).toString());


        // Only a call to remove activity is made as the DataPoint table is set to cascade on delete and will therefore
        // be deleted with the activity
        db.removeActivity(activity);
        int count = dataPoints.size() - 1;
        dataPoints = new ArrayList<>();
        for (Activity act : db.getActivities(user.getId())) {
            dataPoints.add(act.getDataSet().getDataPoints());
        }

        assertEquals(count, dataPoints.size());

        db.removeUser(user);
    }


    @Test
    public void testGoalFunc() {
        Goal goal = new Goal("test goal", "test", 5, "08/08/2018", false);
        db.storeNewUser(user);
        db.storeGoal(goal, user.getId());
        ArrayList<Goal> goals = db.getGoals(user.getId());
        System.out.println(goals.get(0));
        System.out.println("size " + goals.size());

        assertEquals(goal.getName(), goals.get(goals.size() - 1).getName());

        goal.setExpired(true);
        db.updateGoal(goal);

        assertEquals(true, db.getGoals(user.getId()).get(goals.size() - 1).isExpired());

        db.removeGoal(goal);

        assertEquals(goals.size() - 1, db.getGoals(user.getId()).size());

        db.removeUser(user);
    }


    @Test
    public void testAlertFunc() {
        Alert alert = new Alert("06/06/2018", "test", "test");
        db.storeNewUser(user);
        db.storeAlert(alert, user.getId());
        ArrayList<Alert> alerts = db.getAlerts(user.getId());

        assertEquals(alert.getMessage(), alerts.get(alerts.size() - 1).getMessage());

        db.removeAlert(alert);

        assertEquals(alerts.size() - 1, db.getGoals(user.getId()).size());
    }
}