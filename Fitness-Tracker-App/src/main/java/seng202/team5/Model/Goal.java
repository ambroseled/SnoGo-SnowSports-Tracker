package seng202.team5.Model;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Goal {

    private Date completionDate;
    private String metric;
    private double metricGoal;
    private String name;
    private boolean completed;
    private int id = -1;



    public Goal(String name, String metric, double metricGoal, String date, boolean completed) {
        this.name = name;
        this.metric = metric;
        this.metricGoal = metricGoal;
        this.completed = completed;
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
            this.completionDate = dateTimeFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }

    }


    public Goal(String name, String metric, double metricGoal, String date, boolean completed, int id) {
        this.name = name;
        this.metric = metric;
        this.metricGoal = metricGoal;
        this.completed = completed;
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
            this.completionDate = dateTimeFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
        this.id = id;
    }

    public Goal(String name, String metric, double metricGoal, Date date, boolean completed, int id) {
        this.name = name;
        this.metric = metric;
        this.metricGoal = metricGoal;
        this.completed = completed;
        completionDate = date;
        this.id = id;

    }


    public Date getCompletionDate() {
        return completionDate;
    }

    public double getMetricGoal() {
        return metricGoal;
    }

    public String getMetric() {
        return metric;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getName() {
        return name;
    }

    public void setCompletionData(Date completionData) {
        this.completionDate = completionData;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public void setMetricGoal(double metricGoal) {
        this.metricGoal = metricGoal;
    }
}
