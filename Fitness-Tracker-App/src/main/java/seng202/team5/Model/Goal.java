package seng202.team5.Model;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 */
public class Goal {

    private Date completionDate;
    private String metric;
    private double metricGoal;
    private String name;
    private boolean completed;
    private int id = -1;
    private String dateString;


    /**
     *
     * @param name
     * @param metric
     * @param metricGoal
     * @param dateString
     * @param completed
     */
    public Goal(String name, String metric, double metricGoal, String dateString, boolean completed) {
        this.name = name;
        this.metric = metric;
        this.metricGoal = metricGoal;
        this.completed = completed;
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/mm/yyyy");
            this.completionDate = dateTimeFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
        this.dateString = dateString;

    }


    /**
     *
     * @param name
     * @param metric
     * @param metricGoal
     * @param dateString
     * @param completed
     * @param id
     */
    public Goal(String name, String metric, double metricGoal, String dateString, boolean completed, int id) {
        this.name = name;
        this.metric = metric;
        this.metricGoal = metricGoal;
        this.completed = completed;
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/mm/yyyy");
            this.completionDate = dateTimeFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
        this.id = id;
        this.dateString = dateString;
    }


    /**
     *
     * @param name
     * @param metric
     * @param metricGoal
     * @param date
     * @param completed
     * @param id
     */
    public Goal(String name, String metric, double metricGoal, Date date, boolean completed, int id) {
        this.name = name;
        this.metric = metric;
        this.metricGoal = metricGoal;
        this.completed = completed;
        completionDate = date;
        this.id = id;

    }


    /**
     *
     * @return
     */
    public Date getCompletionDate() {
        return completionDate;
    }


    /**
     *
     * @return
     */
    public double getMetricGoal() {
        return metricGoal;
    }


    /**
     *
     * @return
     */
    public String getMetric() {
        return metric;
    }


    /**
     *
     * @return
     */
    public boolean isCompleted() {
        return completed;
    }


    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }


    public int getId() {
        return id;
    }


    public String getDateString() {
        return dateString;
    }

    /**
     *
     * @param completionData
     */
    public void setCompletionData(Date completionData) {
        this.completionDate = completionData;
    }


    /**
     *
     * @param metric
     */
    public void setMetric(String metric) {
        this.metric = metric;
    }


    /**
     *
     * @param metricGoal
     */
    public void setMetricGoal(double metricGoal) {
        this.metricGoal = metricGoal;
    }
}
