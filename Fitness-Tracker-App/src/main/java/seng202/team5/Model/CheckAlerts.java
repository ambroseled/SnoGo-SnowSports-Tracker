package seng202.team5.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckAlerts {

    private static DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");


    /**
     * Checks a passed list for any health alerts related to a passed User.
     * Calculated using https://www.health24.com/Fitness/Exercise/What-should-your-target-heart-rate-be-during-exercise-20120721
     * @param toCheck The ArrayList of activities to check.
     * @param user The User.
     */
    public static ArrayList<Alert> checkAlerts(ArrayList<Activity> toCheck, User user) {
        ArrayList<Alert> alerts = new ArrayList<>();
        Date date = new Date();
        for (Activity activity : toCheck) {
            DataSet dataSet = activity.getDataSet();
            int avgRate = dataSet.getAvgHeartRate();
            int age = user.getAge();
            int lowRate = (int) 0.7 * (225 - age);
            int highRate = (int) 0.75 * (225 - age);
            if (avgRate < lowRate) {
                Alert alert = new Alert(dateTimeFormat.format(date),"https://www.health.harvard.edu/heart-health/bradycardia", "Heart rate too low", "Bradycardia");
                alerts.add(alert);
            } else if (avgRate > highRate) {
                Alert alert = new Alert(dateTimeFormat.format(date),"https://en.wikibooks.org/wiki/Exercise_as_it_relates_to_Disease/Exercise-induced_ventricular_tachycardia", "Heart rate too high", "Tachycardia");
                alerts.add(alert);
            }
        }
        return alerts;
    }
}
