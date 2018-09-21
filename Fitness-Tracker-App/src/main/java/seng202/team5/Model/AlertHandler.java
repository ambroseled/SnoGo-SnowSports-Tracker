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
        if (actCount >= 50) {
            actAlert = new Alert(date, "50 activities uploaded", "Activity count");
        } else if (actCount >= 45) {
            actAlert = new Alert(date, "45 activities uploaded", "Activity count");
        } else if (actCount >= 40) {
            actAlert = new Alert(date, "40 activities uploaded", "Activity count");
        } else if (actCount >= 35) {
            actAlert = new Alert(date, "35 activities uploaded", "Activity count");
        } else if (actCount >= 30) {
            actAlert = new Alert(date, "30 activities uploaded", "Activity count");
        } else if (actCount >= 25) {
            actAlert = new Alert(date, "25 activities uploaded", "Activity count");
        } else if (actCount >= 20) {
            actAlert = new Alert(date, "20 activities uploaded", "Activity count");
        } else if (actCount >= 15) {
            actAlert = new Alert(date, "15 activities uploaded", "Activity count");
        } else if (actCount >= 10) {
            actAlert = new Alert(date, "10 activities uploaded", "Activity count");
        } else if (actCount >= 5) {
            actAlert = new Alert(date, "5 activities uploaded", "Activity count");
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
