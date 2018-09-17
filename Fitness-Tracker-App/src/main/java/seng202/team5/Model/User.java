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


    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }


    /**
     *
     * @return
     */
    public Date getBirthDate() {
        return birthDate;
    }


    /**
     *
     * @return
     */
    public double getHeight() {
        return height;
    }


    /**
     *
     * @return
     */
    public double getWeight() {
        return weight;
    }


    /**
     *
     * @return
     */
    public ArrayList<Activity> getActivities() {
        return activities;
    }


    /**
     *
     * @return
     */
    public double getBmi() {
        return bmi;
    }


    /**
     *
     * @return
     */
    public int getAge() {
        return age;
    }


    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }


    /**
     *
     * @return
     */
    public ArrayList<Goal> getGoals() {
        return goals;
    }

    /**
     *
     * @param tempName
     */
    public void setName(String tempName) {
        name = tempName;
    }


    /**
     *
     * @param tempDate
     */
    public void setBirthDate(Date tempDate) {
        birthDate = tempDate;
    }


    /**
     *
     * @param tempHeight
     */
    public void setHeight(double tempHeight) {
        height = tempHeight;
    }


    /**
     *
     * @param tempWeight
     */
    public void setWeight(double tempWeight) {
        weight = tempWeight;
    }


    /**
     *
     * @param goals
     */
    public void setGoals(ArrayList<Goal> goals) {
        this.goals = goals;
    }


    /**
     *
     * @param alerts
     */
    public void setAlerts(ArrayList<Alert> alerts) {
        this.alerts = alerts;
    }


    /**
     *
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }


    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     *
     * @param bmi
     */
    public void setBmi(double bmi) {
        this.bmi = bmi;
    }


    /**
     *
     * @param toAdd
     */
    public void addGoal(Goal toAdd) {
        goals.add(toAdd);
    }


    /**
     *
     * @return
     */
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

    /*
    //Adds activities from CSV to user's list of activities
    public void addActivities(String fileName) {
        ArrayList<Activity> activitiesToAdd = InputDataParser.parseCSVToActivities(String fileName);
        activities.addAll(activitiesToAdd);
    } */
}
