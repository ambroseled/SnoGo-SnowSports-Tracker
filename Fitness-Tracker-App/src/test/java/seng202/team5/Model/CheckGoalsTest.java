package seng202.team5.Model;

import org.junit.Before;
import org.junit.Test;
import seng202.team5.DataManipulation.InputDataParser;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.*;


/**
 * This class provides tests for the CheckGoals class.
 */
public class CheckGoalsTest {

    private Date date = new Date();
    private User user = new User ("Test", 15, 1.7, 60, date);
    private InputDataParser parser = new InputDataParser();

    @Before
    /**
     * Getting a list of all the methods to be tested. Also setting up
     * the dummy User which is used for testing.
     */
    public void before() {
        user.setActivities(parser.parseCSVToActivities("TestFiles/alertGoalTestData.csv"));
    }


    @Test
    /**
     * Testing the convertDate method. Done using the java reflection api
     * as the method is private.
     */
    public void testConvertDate() {
        String[] date = {"06", "09", "2018"};
        int[] values = CheckGoals.convertDate(date);
        assertEquals(6, values[0]);
        assertEquals(9, values[1]);
        assertEquals(2018, values[2]);
    }



    @Test
    public void testFalseDate() {
        Goal goal = new Goal("test", "Distance Traveled", 0.4, "05/06/1999", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertFalse(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testFalseDistance() {
        Goal goal = new Goal("test", "Distance Traveled", 0.7, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertFalse(CheckGoals.checkGoal(goal, activities, user));
    }



    @Test
    public void testTrueDistance() {
        Goal goal = new Goal("test", "Distance Traveled", 0.4, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertTrue(CheckGoals.checkGoal(goal, activities, user));
    }



    @Test
    public void testFalseDistanceGlobal() {
        Goal goal = new Goal("test", "Distance Traveled", 1.2, "06/07/2100", true);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        System.out.println(activities.get(0).getDataSet().getTotalDistance());
        assertFalse(CheckGoals.checkGoal(goal, activities, user));
    }



    @Test
    public void testTrueDistanceGlobal() {
        Goal goal = new Goal("test", "Distance Traveled", 0.5, "06/07/2100", true);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertTrue(CheckGoals.checkGoal(goal, activities, user));
    }



    @Test
    public void testFalseTopSpeed() {
        Goal goal = new Goal("test", "Top Speed", 9.5, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(1));
        assertFalse(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testTrueTopSpeed() {
        Goal goal = new Goal("test", "Top Speed", 12.6, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(1));
        assertTrue(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testTrueTopSpeedGlobal() {
        Goal goal = new Goal("test", "Top Speed", 21.5, "06/07/2100", true);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertTrue(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testFalseTopSpeedGlobal() {
        Goal goal = new Goal("test", "Top Speed", 12.6, "06/07/2100", true);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertFalse(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testFalseVertical() {
        Goal goal = new Goal("test", "Vertical Distance", 0.1, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(1));
        assertFalse(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testTrueVertical() {
        Goal goal = new Goal("test", "Vertical Distance", 0.03, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(1));
        assertTrue(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testFalseVerticalGlobal() {
        Goal goal = new Goal("test", "Vertical Distance", 0.5, "06/07/2100", true);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertFalse(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testTrueVerticalGlobal() {
        Goal goal = new Goal("test", "Vertical Distance", 0.1, "06/07/2100", true);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertTrue(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testFalseCalories() {
        Goal goal = new Goal("test", "Calories Burned", 9.4, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertFalse(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testTrueCalories() {
        Goal goal = new Goal("test", "Calories Burned", 5.4, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertTrue(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testFalseCaloriesGlobal() {
        Goal goal = new Goal("test", "Calories Burned", 110, "06/07/2100", true);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        System.out.println(CheckGoals.checkGoal(goal, activities, user));
        assertFalse(CheckGoals.checkGoal(goal, activities, user));
    }



    @Test
    public void testTrueCaloriesGlobal() {
        Goal goal = new Goal("test", "Calories Burned", 9.4, "06/07/2100", true);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertTrue(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testFalseHeart() {
        Goal goal = new Goal("test", "Average Heart Rate", 143, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertFalse(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testTrueHeart() {
        Goal goal = new Goal("test", "Average Heart Rate", 146.0, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertTrue(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testFalseHeartGlobal() {
        Goal goal = new Goal("test", "Average Heart Rate", 143, "06/07/2100", true);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertFalse(CheckGoals.checkGoal(goal, activities, user));
    }


    @Test
    public void testTrueHeartGlobal() {
        Goal goal = new Goal("test", "Average Heart Rate", 145, "06/07/2100", true);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        assertTrue(CheckGoals.checkGoal(goal, activities, user));
    }

}