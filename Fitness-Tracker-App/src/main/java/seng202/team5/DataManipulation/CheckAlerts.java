package seng202.team5.DataManipulation;

import seng202.team5.Model.Alert;
import seng202.team5.Model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class handles the creation of alerts in the application
 */
public class CheckAlerts {

    // Date format used to create new alerts
    private static DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");


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
        Alert actAlert = null;
        if (actCount >= 50) {
            actAlert = new Alert(date, "50+ activities uploaded", "Activity count");
        } else if (actCount >= 45) {
            actAlert = new Alert(date, "45+ activities uploaded", "Activity count");
        } else if (actCount >= 40) {
            actAlert = new Alert(date, "40+ activities uploaded", "Activity count");
        } else if (actCount >= 35) {
            actAlert = new Alert(date, "35+ activities uploaded", "Activity count");
        } else if (actCount >= 30) {
            actAlert = new Alert(date, "30+ activities uploaded", "Activity count");
        } else if (actCount >= 25) {
            actAlert = new Alert(date, "25+ activities uploaded", "Activity count");
        } else if (actCount >= 20) {
            actAlert = new Alert(date, "20+ activities uploaded", "Activity count");
        } else if (actCount >= 15) {
            actAlert = new Alert(date, "15+ activities uploaded", "Activity count");
        } else if (actCount >= 10) {
            actAlert = new Alert(date, "10+ activities uploaded", "Activity count");
        } else if (actCount >= 5) {
            actAlert = new Alert(date, "5+ activities uploaded", "Activity count");
        }
        if (actAlert != null) {
            if (alertExists(user, actAlert.getMessage())) {
                return null;
            }
        }
        return actAlert;
    }


    /**
     * This method checks if the newly created alert has been created already. It is used by the activityAlert
     * method to stop double up alerts being created
     * @param user The current user
     * @param message The message of the alert to check for
     * @return True if matching alert is found false otherwise
     */
    private static boolean alertExists(User user, String message) {
        for (Alert alert : user.getAlerts()) {
            if (alert.getMessage().equals(message)) {
                return true;
            }
        }
        return false;
    }


    /**
     * This method creates a new alert for a goal that is expired.
     * @param goalName The name of the goal
     * @return The alert for the expired goal
     */
    public static Alert expiredGoalAlert(String goalName) {
        String date = dateTimeFormat.format(new Date());
        String message = "Goal: " + goalName + " expired";
        Alert alert = new Alert(date, message, "Goal expired");
        return alert;
    }


    /**
     * This method checks a passed users bmi against 4 unsafe categories: obese, overweight, underweight,
     * severely underweight
     * @param user The users whose bmi is to be checked
     * @return An alert if an unsafe bmi is found otherwise null
     */
    public static Alert bmiAlert(User user) {
        double bmi = user.getBmi();
        System.out.println(bmi);
        String message = null;
        if (bmi >= 30.0) {
            message = "BMI Category: Obese, seek info at: https://www.kiwicover.co.nz/your-health/bmi";
        } else if (bmi >= 24.0) {
            message = "BMI Category: Overweight, seek info at: https://www.kiwicover.co.nz/your-health/bmi";
        } else if (bmi >= 18.5) {
            message = null;
        } else if (bmi >= 16.0) {
            message = "BMI Category: Under weight, seek info at: https://www.kiwicover.co.nz/your-health/bmi";
        } else {
            message = "BMI Category: Severely Underweight, seek info at: https://www.kiwicover.co.nz/your-health/bmi";
        }
        if (message != null) {
            String date = dateTimeFormat.format(new Date());
            Alert bmiAlert = new Alert(date, message, "Unsafe BMI");
            return bmiAlert;
        }
        return null;
    }


}
