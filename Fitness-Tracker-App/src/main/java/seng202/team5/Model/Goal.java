package seng202.team5.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class provides the structure of a goal in the application.
 */
public class Goal {

    private Date completionDate;
    private String metric;
    private double metricGoal;
    private String name;
    private boolean completed;
    private int id = -1;
    private String dateString;
    private boolean global;


    /**
     * The normal constructor used to create a new Goal.
     * @param name The name of the goal.
     * @param metric The metric the goal is related to.
     * @param metricGoal The value to achieve to complete the goal.
     * @param dateString A string holding the completion date of the goal.
     */
    public Goal(String name, String metric, double metricGoal, String dateString, boolean global) {
        this.name = name;
        this.metric = metric;
        this.metricGoal = metricGoal;
        this.completed = false;
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            this.completionDate = dateTimeFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
        this.dateString = dateString;
        this.global = global;
    }


    /**
     * The constructor used to create a Goal which has been read form the database.
     * @param name The name of the goal.
     * @param metric The metric the goal is related to.
     * @param metricGoal The value to achieve to complete the goal.
     * @param dateString A string holding the completion date of the goal.
     * @param completed A boolean flag holding if the goal has be completed.
     * @param id The database id of the goal.
     */
    public Goal(String name, String metric, double metricGoal, String dateString, boolean completed, int id, boolean global) {
        this.name = name;
        this.metric = metric;
        this.metricGoal = metricGoal;
        this.completed = completed;
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            this.completionDate = dateTimeFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
        this.id = id;
        this.dateString = dateString;
        this.global = global;
    }


    /**
     * Gets the completion date of the goal.
     * @return The completion date of the goal.
     */
    public Date getCompletionDate() {
        return completionDate;
    }


    /**
     * Gets the goals value.
     * @return The value to meet to complete the goal.
     */
    public double getMetricGoal() {
        return metricGoal;
    }


    /**
     * Gets the metric the goal is for.
     * @return The metric of the goal.
     */
    public String getMetric() {
        return metric;
    }
// Update db with goal marked as true

    /**
     * Gets whether the goal is completed or not.
     * @return A boolean flag holding if the goal is completed.
     */
    public boolean isCompleted() {
        return completed;
    }


    /**
     * Gets the name of the goal.
     * @return The name of the goal.
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the database id of the goal.
     * @return The database id of the goal.
     */
    public int getId() {
        return id;
    }


    /**
     * Gets the string value of the completion date of the goal.
     * @return The string value of the completion date of the goal.
     */
    public String getDateString() {
        return dateString;
    }


    /**
     * Gets if the goal is for all data or just over an activity
     * @return Flag holding if the goal is global or not.
     */
    public boolean isGlobal() {
        return global;
    }


    /**
     * Sets if the goal is complete.
     * @param completed The new completion value.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


    /**
     * Sets the database id of the goal.
     * @param id The database id of the goal.
     */
    public void setId(int id) {
        this.id = id;
    }
}
