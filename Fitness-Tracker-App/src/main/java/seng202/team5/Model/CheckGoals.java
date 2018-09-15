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
                if (checkGoal(goal, activities, user)) {
                    goal.setCompleted(true);
                    db.updateGoal(goal);
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
    public static boolean checkGoal(Goal goal, ArrayList<Activity> activities, User user) {
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
        if (!goal.isGlobal()) {
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
            } else if (metric.equals("Calories Burned")) {
                for (Activity x : activities) {
                    if (value >= x.getDataSet().getCaloriesBurned()) {
                        return true;
                    }
                }
            } else { // Metric is average heart rate
                for (Activity x : activities) {
                    if (value >= x.getDataSet().getAvgHeartRate()) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return checkGlobal(goal, user);
        }
    }



    public static boolean checkGlobal(Goal goal, User user) {
        if (goal.getMetric().equals("Distance Traveled")) {
            double totalDist = 0.0;
            for (Activity activity : user.getActivities()) {
                totalDist += activity.getDataSet().getTotalDistance();
            }
            return goal.getMetricGoal() >= totalDist;
        } else if (goal.getMetric().equals("Top Speed")) {
            double topSpeed = 0.0;
            for (Activity activity : user.getActivities()) {
                if (activity.getDataSet().getTopSpeed() > topSpeed) {
                    topSpeed = activity.getDataSet().getTopSpeed();
                }
            }
            return goal.getMetricGoal() >= topSpeed;
        } else if (goal.getMetric().equals("Vertical Distance")) {
            double vertDist = 0.0;
            for (Activity activity : user.getActivities()) {
                vertDist += activity.getDataSet().getVerticalDistance();
            }
            return goal.getMetricGoal() >= vertDist;
        } else if (goal.getMetric().equals("Calories Burned")) {
            double vertDist = 0.0;
            for (Activity activity : user.getActivities()) {
                vertDist += activity.getDataSet().getVerticalDistance();
            }
            return goal.getMetricGoal() >= vertDist;
        } else { // Metric is average heart rate
            int avgRate = 0;
            for (Activity x : user.getActivities()) {
                avgRate += x.getDataSet().getAvgHeartRate();
            }
            avgRate = avgRate / user.getActivities().size();
            return goal.getMetricGoal() == avgRate;
        }
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
