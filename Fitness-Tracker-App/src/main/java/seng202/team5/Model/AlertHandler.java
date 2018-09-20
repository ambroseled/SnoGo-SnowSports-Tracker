package seng202.team5.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlertHandler {

    // Date format used to create new alerts
    private static DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");


    /**
     * This method creates an alert for a goal that has just been completed.
     * @param goalName The name of the goal
     * @return The alert created
     */
    public static Alert newGoalAlert(String goalName) {
        // Creating the alert
        String date = dateTimeFormat.format(new Date());
        String message = "Goal: " + goalName + " completed";
        Alert alert = new Alert(date, message, "Goal completed");
        // Returning the alert
        return alert;
    }


    //TODO: Re think calc method
    /**
     * This method creates an alert based on the amount of activities that
     * the current user has uploaded. An alert is created every 5 activity
     * uploads.
     * @param user The current user
     * @return The alert created or null if no alert was created
     */
    public static Alert activityAlert(User user) {
        int actCount = user.getActivities().size();
        String date = dateTimeFormat.format(new Date());
        String message = null;
        Alert actAlert = null;
        if (actCount % 5 == 0 & actCount != 0) {
            message = String.format("%d activities uploaded", actCount);
        }
        if (message != null) {
            actAlert = new Alert(date, message, "Activity count");
        }
        return actAlert;
    }


    public static Alert expiredGoalAlert(String goalName) {
        String date = dateTimeFormat.format(new Date());
        String message = "Goal: " + goalName + " expired";
        Alert alert = new Alert(date, message, "Goal expired");
        return alert;
    }


}
