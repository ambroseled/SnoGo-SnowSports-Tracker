package seng202.team5.DataManipulation;

import seng202.team5.Model.Activity;
import seng202.team5.Model.Alert;
import seng202.team5.Model.Goal;
import seng202.team5.Model.User;

import java.util.ArrayList;
import java.util.Date;


/**
 * This class is used to check if goals have been completed or expired.
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
        // Looping over the users goals
        for (Goal goal: user.getGoals()) {
            if (!goal.isCompleted() && !goal.isExpired()) {
                // Checking if the goal is completed as it is currently marked as incomplete
                if (checkExpired(goal)) {
                    System.out.println("Goal expired");
                    // Marking the goal as expired
                    goal.setExpired(true);
                    // Updating goal in the database
                    db.updateGoal(goal);
                    // Creating an alert and storing the alert in the database
                    Alert goalAlert = CheckAlerts.expiredGoalAlert(goal.getName());
                    db.storeAlert(goalAlert, user.getId());
                    user.addAlert(goalAlert);
                } else if (checkGoal(goal, activities, user)) {
                    // Goal is completed
                    // Updating goal in database
                    System.out.println("Goal completed");
                    goal.setCompleted(true);
                    db.updateGoal(goal);
                    // Creating an alert
                    Alert goalAlert = CheckAlerts.newGoalAlert(goal.getName());
                    db.storeAlert(goalAlert, user.getId());
                    user.addAlert(goalAlert);
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
        String metric = goal.getMetric();
        double value = goal.getMetricGoal();
        if (!goal.isGlobal()) {
            if (metric.equals("Distance Traveled")) {
                for (Activity x : activities) {
                    if ((value * 1000) <= x.getDataSet().getTotalDistance()) {
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
                    if ((value * 1000) <= x.getDataSet().getVerticalDistance()) {
                        return true;
                    }
                }
            } else if (metric.equals("Calories Burned")) {
                for (Activity x : activities) {
                    if (value <= x.getDataSet().getCaloriesBurned()) {
                        return true;
                    }
                }
            } else { // Metric is average heart rate
                for (Activity x : activities) {
                    if ((int) value == x.getDataSet().getAvgHeartRate()) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return checkGlobal(goal, user);
        }
    }


    /**
     * This method checks if a passed goal has been completed or not. This class
     * only checks goals that are for all of the users data not just a single activity.
     * @param goal The goal to check.
     * @return A boolean flag holding if the goal has been completed.
     */
    private static boolean checkGlobal(Goal goal, User user) {
        if (goal.getMetric().equals("Distance Traveled")) {
            double totalDist = 0.0;
            for (Activity activity : user.getActivities()) {
                totalDist += activity.getDataSet().getTotalDistance();
            }
            return (goal.getMetricGoal() * 1000) <= totalDist;
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
            return (goal.getMetricGoal() * 1000) <= vertDist;
        } else if (goal.getMetric().equals("Calories Burned")) {
            double cals = 0.0;
            for (Activity activity : user.getActivities()) {
                cals += activity.getDataSet().getCaloriesBurned();
            }
            return goal.getMetricGoal() <= cals;
        } else { // Metric is average heart rate
            int avgRate = 0;
            for (Activity x : user.getActivities()) {
                avgRate += x.getDataSet().getAvgHeartRate();
            }
            avgRate = avgRate / user.getActivities().size();
            return (int) goal.getMetricGoal() == avgRate;
        }
    }


    /**
     * This method checks if a given goal has expired.
     * @param goal The goal to check.
     * @return A boolena flag holding if the goal is expired.
     */
    public static boolean checkExpired(Goal goal) {
        Date current = new Date();
        if (goal.getCompletionDate().getTime() < current.getTime()) {
            return true;
        }
        return false;
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
