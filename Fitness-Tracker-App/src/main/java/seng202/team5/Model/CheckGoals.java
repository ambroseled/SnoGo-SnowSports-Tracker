package seng202.team5.Model;

import seng202.team5.Data.DataBaseController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * This class is used to check if a given goal has been completed by the user.
 */
public class CheckGoals {


    /**
     * This method checks all of the users incomplete goals and marks them as completed
     * if they have been completed.
     * @param user The user whose goals will be checked.
     * @param db An instance of DataBaseController.
     * @param activities The newly added activities to use to check the users goals.
     */
    public static void markGoals(User user, DataBaseController db, ArrayList<Activity> activities) {
        for (Goal goal: db.getGoals(user.getId())) {
            if (!goal.isCompleted()) {
                if (checkGoal(goal, activities)) {
                    // Update db with goal marked as true
                }
            }
        }
    }


    /**
     * This method checks if a passed goal has been completed or not.
     * @param goal The goal to check.
     * @param activities The activities to use to check the goal.
     * @return A boolean flag holding if the goal has been completed.
     */
    public static boolean checkGoal(Goal goal, ArrayList<Activity> activities) {
        // Getting the current date string
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        int[] currentDate = convertDate(formatter.format(date).split("/"));
        int[] compDate = convertDate(formatter.format(goal.getCompletionDate()).split("/"));

        if ((currentDate[0] > compDate[0] && currentDate[1] > compDate[1] && currentDate[2] > compDate[2])
                || currentDate[0] > compDate[0] || (currentDate[0] > compDate[0] && currentDate[1] > compDate[1])) {
            return false;
        }
        String metric = goal.getMetric();
        double value = goal.getMetricGoal();

        if (metric.equals("Distance Traveled")) {
            for (Activity x : activities) {
                if (value >= x.getDataSet().getTotalDistance()) {
                    return true;
                }
            }
        } else if (metric.equals("Top Speed")) {
            for (Activity x : activities) {
                if (value >= x.getDataSet().getTopSpeed()) {
                    return true;
                }
            }
        } else if (metric.equals("Vertical Distance")) {
            for (Activity x : activities) {
                if (value >= x.getDataSet().getVerticalDistance()) {
                    return true;
                }
            }
        } else { // Metric is "Calories Burned"
            for (Activity x : activities) {
                if (value >= x.getDataSet().getCaloriesBurned()) {
                    return true;
                }
            }
        }
        return true;
    }


    /**
     * This method is a helper method which converts a list of
     * the values of a date into a list of ints.
     * @param date The list of the date.
     * @return A list of the passed string values converted to ints.
     */
    public static int[] convertDate(String[] date) {
        int[] values = new int[3];
        values[0] = Integer.parseInt(date[0]);
        values[1] = Integer.parseInt(date[1]);
        values[2] = Integer.parseInt(date[2]);
        return values;
    }
}
