package seng202.team5.Model;

import seng202.team5.Control.DataBaseController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckGoals {


    /**
     *
     * @param user
     * @param db
     * @param activities
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
     *
     * @param goal
     * @param activities
     * @return
     */
    public static boolean checkGoal(Goal goal, ArrayList<Activity> activities) {
        // Getting the current date string
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        int[] currentDate = convertDate(formatter.format(date).split("/"));
        int[] compDate = convertDate(formatter.format(goal.getCompletionDate()).split("/"));

        if (currentDate[0] > compDate[0] && currentDate[1] > compDate[1] && currentDate[2] > compDate[2]) {
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

        } else if (metric.equals("Vertical Distance")) {

        } else { // Avg heart rate

        }
        return true;
    }


    /**
     *
     * @param date
     * @return
     */
    public static int[] convertDate(String[] date) {
        int[] values = new int[3];
        values[0] = Integer.parseInt(date[0]);
        values[1] = Integer.parseInt(date[1]);
        values[2] = Integer.parseInt(date[2]);
        return values;
    }
}
