package seng202.team5.DataManipulation;


import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team5.Model.*;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;


/**
 * This test class provides unit tests for the DataAnalyser class.
 */
public class DataAnalyserTest {


    private static ArrayList<Activity> activities;
    private static DataAnalyser dataAnalyser = new DataAnalyser();
    private static ArrayList<User> users = new ArrayList<>();


    /**
     * Setup before all tests. Reads the test activities from the
     * dataAnalysisTests file into the arrayList of activities, activities.
     */
    @BeforeClass
    public static void beforeAll() {
        Date date = new Date();
        User user = new User("Test", 25, 1.8, 75.8, date);
        dataAnalyser.setCurrentUser(user);
        InputDataParser parser = new InputDataParser();
        activities = parser.parseCSVToActivities("src/main/resources/TestFiles/dataAnalysisTests.csv");
        for (Activity activity : activities) {
            dataAnalyser.analyseActivity(activity);
        }
        users.add(new User("test", 1, 45, 1.7, new Date()));
        users.add(new User("test", 3, 45, 1.7, new Date()));
        users.add(new User("test", 6, 45, 1.7, new Date()));
        users.add(new User("test", 10, 45, 1.7, new Date()));
        users.add(new User("test", 14, 45, 1.7, new Date()));
        users.add(new User("test", 70, 45, 1.7, new Date()));
    }


    /**
     * Testing the markActive() and checkInactive() functions. This test cases
     * passes an activity where all DataPoints are inactive.
     */
    // Broken
    @Test
    public void testAllInactive() {
        Activity activity = activities.get(0);
        // Getting the dataSet out of the activity
        DataSet dataSet = activity.getDataSet();
        // Getting the data points out of the dataSet
        ArrayList<DataPoint> dataPoints = dataSet.getDataPoints();
        int count = 0;
        for (DataPoint point : dataPoints) {
            String string = Double.toString(point.getLongitude()) + Double.toString(point.getLatitude());
            if (!point.isActive()) {
                count++;
            }
        }
        assertEquals(dataPoints.size(), count);
    }


    /**
     * Testing the markActive() and checkInactive() functions. This test cases
     * passes an activity where all DataPoints are active.
     */
    @Test
    public void testAllActive() {
        Activity activity = activities.get(1);
        // Getting the dataSet out of the activity
        DataSet dataSet = activity.getDataSet();
        // Getting the data points out of the dataSet
        ArrayList<DataPoint> dataPoints = dataSet.getDataPoints();
        int count = 0;
        for (DataPoint point : dataPoints) {
            if (point.isActive()) {
                count++;
            }
        }
        assertEquals(dataPoints.size(), count);
    }


    /**
     * Testing the calcAvgHeart() function. This test cases passes
     * an activity where the heart rate remains constant.
     */
    @Test
    public void testAvgHeartAllSame() {
        Activity activity = activities.get(2);
        // Getting the dataSet out of the activity
        DataSet dataSet = activity.getDataSet();
        assertEquals(132, dataSet.getAvgHeartRate(), 0.0);
    }


    /**
     * Testing the calcAvgHeart() function. This test cases passes
     * an activity where the heart rate varies.
     */
    @Test
    public void testAvgHeartDiff() {
        Activity activity = activities.get(1);
        // Getting the dataSet out of the activity
        double[] rates = {132, 156, 154, 151, 146, 139, 141, 149, 154, 149, 146, 142, 138};
        int average = 0;
        for (int i = 0; i < rates.length; i++){
            average += rates[i];
        }
        average = average / rates.length;
        DataSet dataSet = activity.getDataSet();
        assertEquals(average, dataSet.getAvgHeartRate());
    }


    /**
     * Testing the calcVertical() function. This test case passes
     * an activity where the user is moving upwards the whole time
     * and is therefore inactive and the vertical distance is not recorded.
     */
    @Test
    public void testVerticalUp() {
        Activity activity = activities.get(0);
        // Getting the dataSet out of the activity
        DataSet dataSet = activity.getDataSet();
        assertEquals(0, dataSet.getVerticalDistance(), 0);
    }


    /**
     * Testing the calcVertical() function. This test case passes
     * an activity where the user is moving downwards the whole time
     * and is therefore active and the vertical distance calculated.
     */
    @Test
    public void testVerticalDown() {
        Activity activity = activities.get(1);
        // Getting the dataSet out of the activity
        DataSet dataSet = activity.getDataSet();
        double vertical = DataAnalyser.roundNum(1802.69 - 1792.66);
        assertEquals(vertical, dataSet.getVerticalDistance(), 0);
    }


    /**
     * Testing the topSpeed function. This also tests the appendSpeed and
     * oneSpeed functions as they are used prior to the topSpeed function.
     */
    @Test
    public void testTopSpeed() {
        Activity activity = activities.get(1);
        // Getting the dataSet out of the activity
        DataSet dataSet = activity.getDataSet();
        assertEquals(12.15, dataSet.getTopSpeed(), 0.0);
    }


    @Test
    /**
    * Testing the calcAvgSpeed function. This also tests the appendSpeed and
    * oneSpeed functions as they are used prior to the calcAvgSpeed function.
    */
     public void testAvgSpeed() {
         Activity activity = activities.get(1);
        // Getting the dataSet out of the activity
         DataSet dataSet = activity.getDataSet();
         assertEquals(7.17, dataSet.getAvgSpeed(), 0.0);
     }


    /**
     * Testing the calcBMI function.
     */
    @Test
    public void testBMI() {
        double height = 1.75;
        double weight = 70;
        double bmi = DataAnalyser.calcBMI(height, weight);
        assertEquals(22.86, bmi, 0.0);
    }


    @Test
    /**
     * Testing the calcCalBurned function.
     */
    public void testCalories() {
        assertEquals(3.25, activities.get(3).getDataSet().getCaloriesBurned(), 0.0);
    }


    @Test
    public void testCheckBradycardiaFalseByAge() {
        dataAnalyser.setCurrentUser(users.get(5));
        Alert alert = dataAnalyser.checkBradycardia(activities.get(0));
        assertNull(alert);
    }


    @Test
    public void testCheckBradycardiaFalseByRate() {
        dataAnalyser.setCurrentUser(users.get(0));
        Alert alert = dataAnalyser.checkBradycardia(activities.get(0));
        assertNull(alert);
    }


    @Test
    public void testCheckBradycardiaTrue() {
        dataAnalyser.setCurrentUser(users.get(5));
        Alert alert = dataAnalyser.checkBradycardia(activities.get(4));
        assertEquals("Risk of BradycardiaHeart risk warning", alert.getMessage() + alert.getType());
    }


    @Test
    public void testCheckCardiovascularMortalityFalse() {
        dataAnalyser.setCurrentUser(users.get(0));
        Alert alert = dataAnalyser.checkCardiovascularMortality(activities.get(1));
        assertNull(alert);
    }


    @Test
    public void testCheckCardiovascularMortalityTrue() {
        dataAnalyser.setCurrentUser(users.get(5));
        Alert alert = dataAnalyser.checkCardiovascularMortality(activities.get(0));
        assertEquals("Your heart rate is abnormally high.Heart risk warning", alert.getMessage() + alert.getType());
    }


    @Test
    public void testCheckTachycardiaFalse() {
        dataAnalyser.setCurrentUser(users.get(0));
        Alert alert = dataAnalyser.checkTachycardia(activities.get(1));
        assertNull(alert);
    }


    @Test
    public void testCheckTachycardiaUnder4() {
        dataAnalyser.setCurrentUser(users.get(0));
        Alert alert = dataAnalyser.checkTachycardia(activities.get(5));
        assertEquals("Risk of TachycardiaHeart risk warning", alert.getMessage() + alert.getType());
    }


    @Test
    public void testCheckTachycardiaUnder7() {
        dataAnalyser.setCurrentUser(users.get(1));
        Alert alert = dataAnalyser.checkTachycardia(activities.get(5));
        assertEquals("Risk of TachycardiaHeart risk warning", alert.getMessage() + alert.getType());
    }


    @Test
    public void testCheckTachycardiaUnder11() {
        dataAnalyser.setCurrentUser(users.get(2));
        Alert alert = dataAnalyser.checkTachycardia(activities.get(5));
        assertEquals("Risk of TachycardiaHeart risk warning", alert.getMessage() + alert.getType());
    }


    @Test
    public void testCheckTachycardiaUnder15() {
        dataAnalyser.setCurrentUser(users.get(3));
        Alert alert = dataAnalyser.checkTachycardia(activities.get(5));
        assertEquals("Risk of TachycardiaHeart risk warning", alert.getMessage() + alert.getType());
    }


    @Test
    public void testCheckTachycardiaOver15() {
        dataAnalyser.setCurrentUser(users.get(4));
        Alert alert = dataAnalyser.checkTachycardia(activities.get(5));
        assertEquals("Risk of TachycardiaHeart risk warning", alert.getMessage() + alert.getType());
    }


}