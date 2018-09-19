package seng202.team5.Model;

import seng202.team5.DataManipulation.DataAnalyser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 */
public class User {
    private String name;
    private Date birthDate;
    private double height;
    private double weight;
    private int age;
    private int id = -1; // Need to add a handler for this
    private double bmi;
    private ArrayList<Activity> activities;
    private ArrayList<Alert> alerts = new ArrayList<>();
    private ArrayList<Goal> goals = new ArrayList<>();
    DateFormat dateTimeFormat = new SimpleDateFormat("dd/mm/yyyy");



    /**
     *
     * @param name
     * @param age
     * @param height
     * @param weight
     * @param activities
     */
    public User(String name, int age, double height, double weight, ArrayList<Activity> activities, Date birthDate){
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.activities = activities;
        bmi = DataAnalyser.calcBMI(height, weight);
        this.birthDate = birthDate;
    }


    /**
     *
     * @param id
     * @param name
     * @param age
     * @param height
     * @param weight
     * @param activities
     */
    public User(String name, int age, double height, double weight, ArrayList<Activity> activities, int id, String dateString) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.activities = activities;
        bmi = DataAnalyser.calcBMI(height, weight);
        try {
            birthDate = dateTimeFormat.parse(dateString);
        } catch (ParseException e) {
            // Open error dialog
        }
    }

    /**
     *
     * @param name
     * @param age
     * @param height
     * @param weight
     */
    public User(String name, int age, double height, double weight, Date birthDate) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        bmi = DataAnalyser.calcBMI(height, weight);
        this.birthDate = birthDate;
    }


    public String getName() {
        return name;
    }


    public Date getBirthDate() {
        return birthDate;
    }


    public double getHeight() {
        return height;
    }


    public double getWeight() {
        return weight;
    }


    public ArrayList<Activity> getActivities() {
        return activities;
    }


    public double getBmi() {
        return bmi;
    }


    public int getAge() {
        return age;
    }


    public int getId() {
        return id;
    }


    public ArrayList<Goal> getGoals() {
        return goals;
    }


    public void setName(String tempName) {
        name = tempName;
    }


    public void setBirthDate(Date tempDate) {
        birthDate = tempDate;
    }


    public void setHeight(double tempHeight) {
        height = tempHeight;
    }


    public void setWeight(double tempWeight) {
        weight = tempWeight;
    }


    public void setGoals(ArrayList<Goal> goals) {
        this.goals = goals;
    }


    public void setAlerts(ArrayList<Alert> alerts) {
        this.alerts = alerts;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void addGoal(Goal toAdd) {
        goals.add(toAdd);
    }


    public ArrayList<Alert> getAlerts() {
        return alerts;
    }


    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public void removeGoal(Goal goal) {
        goals.remove(goal);
    }


    public void addAlert(Alert alert) {
        alerts.add(alert);
    }


    public void removeAlert(Alert alert) {
        alerts.remove(alert);
    }


    public void addActivity(Activity activity) {
        activities.add(activity);
    }
}
