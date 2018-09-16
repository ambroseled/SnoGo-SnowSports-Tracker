package seng202.team5.Model;

import org.junit.Before;
import org.junit.Test;
import seng202.team5.DataManipulation.InputDataParser;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.*;


/**
 *
 */
public class CheckGoalsTest {

    private Method[] methods;
    private Object checkObject;
    private Date date = new Date();
    private User user = new User ("Test", 15, 1.7, 60, date);
    private InputDataParser parser = new InputDataParser();

    @Before
    /**
     * Getting a list of all the methods to be tested. Also setting up
     * the dummy User which is used for testing.
     */
    public void getMethods() {
        try {
            Class<CheckGoals> check = CheckGoals.class;
            checkObject = check.newInstance();

            methods = check.getDeclaredMethods();

            for (Method method : methods) {
                method.setAccessible(true);
            }
        } catch (Exception e) {
            fail();
        }
        user.setActivities(parser.parseCSVToActivities("TestFiles/checkGoalTestData.csv"));
    }


    @Test
    /**
     * Testing the convertDate method. Done using the java reflection api
     * as the method is private.
     */
    public void testConvertDate() {
        try {
            String[] date = {"06", "09", "2018"};
            int[] values = (int[]) methods[3].invoke(checkObject, (Object) date);
            assertEquals(6, values[0]);
            assertEquals(9, values[1]);
            assertEquals(2018, values[2]);
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }



    @Test
    public void testFalseDistance() {
        Goal goal = new Goal("test", "Distance Traveled", 0.5, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        try {
            assertFalse((boolean) methods[1].invoke(checkObject, goal, activities, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }



    @Test
    public void testTrueDistance() {
        Goal goal = new Goal("test", "Distance Traveled", 0.4, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        try {
            assertTrue((boolean) methods[1].invoke(checkObject, goal, activities, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            System.out.println("Exception: " + e.getLocalizedMessage());
            fail();
        }
    }



    @Test
    public void testFalseDistanceGlobal() {
        Goal goal = new Goal("test", "Distance Traveled", 1, "06/07/2100", true);
        try {
            assertFalse((boolean) methods[2].invoke(checkObject, goal, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }



    @Test
    public void testTrueDistanceGlobal() {
        Goal goal = new Goal("test", "Distance Traveled", 0.5, "06/07/2100", true);
        try {
            assertTrue((boolean) methods[2].invoke(checkObject, goal, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testFalseDate() {
        Goal goal = new Goal("test", "Distance Traveled", 0.4, "05/06/1999", true);
        try {
            assertFalse((boolean) methods[2].invoke(checkObject, goal, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testFalseTopSpeed() {
        Goal goal = new Goal("test", "Top Speed", 9.5, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(1));
        try {
            assertFalse((boolean) methods[1].invoke(checkObject, goal, activities, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testTrueTopSpeed() {
        Goal goal = new Goal("test", "Top Speed", 12.6, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(1));
        try {
            assertTrue((boolean) methods[1].invoke(checkObject, goal, activities, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testTrueTopSpeedGlobal() {
        Goal goal = new Goal("test", "Top Speed", 21.5, "06/07/2100", true);
        try {
            assertTrue((boolean) methods[2].invoke(checkObject, goal, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testFalseTopSpeedGlobal() {
        Goal goal = new Goal("test", "Top Speed", 12.6, "06/07/2100", true);
        try {
            assertFalse((boolean) methods[2].invoke(checkObject, goal, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testFalseVertical() {
        Goal goal = new Goal("test", "Vertical Distance", 0.1, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(1));
        try {
            assertFalse((boolean) methods[1].invoke(checkObject, goal, activities, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testTrueVertical() {
        Goal goal = new Goal("test", "Vertical Distance", 0.03, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(1));
        try {
            assertTrue((boolean) methods[1].invoke(checkObject, goal, activities, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testFalseVerticalGlobal() {
        Goal goal = new Goal("test", "Vertical Distance", 0.5, "06/07/2100", true);
        try {
            assertFalse((boolean) methods[2].invoke(checkObject, goal, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException  e) {
            fail();
        }
    }


    @Test
    public void testTrueVerticalGlobal() {
        Goal goal = new Goal("test", "Vertical Distance", 0.1, "06/07/2100", true);
        try {
            assertTrue((boolean) methods[2].invoke(checkObject, goal, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testFalseCalories() {
        Goal goal = new Goal("test", "Calories Burned", 9.4, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        try {
            assertFalse((boolean) methods[1].invoke(checkObject, goal, activities, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testTrueCalories() {
        Goal goal = new Goal("test", "Calories Burned", 8.6, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        try {
            assertTrue((boolean) methods[1].invoke(checkObject, goal, activities, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testFalseCaloriesGlobal() {
        Goal goal = new Goal("test", "Calories Burned", 17.87, "06/07/2100", true);
        try {
            assertFalse((boolean) methods[2].invoke(checkObject, goal , user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }



    @Test
    public void testTrueCaloriesGlobal() {
        Goal goal = new Goal("test", "Calories Burned", 9.4, "06/07/2100", true);
        try {
            assertTrue((boolean) methods[2].invoke(checkObject, goal, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testFalseHeart() {
        Goal goal = new Goal("test", "Average Heart Rate", 143, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        try {
            assertFalse((boolean) methods[1].invoke(checkObject, goal, activities, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testTrueHeart() {
        Goal goal = new Goal("test", "Average Heart Rate", 146.0, "06/07/2100", false);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        try {
            assertTrue((boolean) methods[1].invoke(checkObject, goal, activities, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testFalseHeartGlobal() {
        Goal goal = new Goal("test", "Average Heart Rate", 143, "06/07/2100", true);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        try {
            assertFalse((boolean) methods[1].invoke(checkObject, goal, activities, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }


    @Test
    public void testTrueHeartGlobal() {
        Goal goal = new Goal("test", "Average Heart Rate", 145, "06/07/2100", true);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(user.getActivities().get(0));
        try {
            assertTrue((boolean) methods[1].invoke(checkObject, goal, activities, user));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            fail();
        }
    }

}