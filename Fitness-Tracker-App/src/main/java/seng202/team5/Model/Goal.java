package seng202.team5.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Goal {

    private Date completionData;
    private String metric;
    private double metricGoal;
    private String name;
    private boolean completed;


    public Goal(String name, String metric, double metricGoal, String date, boolean completed) {
        this.name = name;
        this.metric = metric;
        this.metricGoal = metricGoal;
        this.completed = completed;
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/mm/yyyy");
            this.completionData = dateTimeFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
    }


    public Date getCompletionData() {
        return completionData;
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
        this.completionData = completionData;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public void setMetricGoal(double metricGoal) {
        this.metricGoal = metricGoal;
    }
}
