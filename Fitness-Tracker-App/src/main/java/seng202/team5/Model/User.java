package seng202.team5.Model;

import seng202.team5.DataManipulation.DataAnalyser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * This class provides the functionality for a user of the application,
 * by storing all their data as attributes, and providing getters and
 * setters.
 */
public class User {


    // The variables for the information stored with a user
    private String name;
    private Date birthDate;
    private double height;
    private double weight;
    private int age;
    private int id = -1; // Need to add a handler for this
    private double bmi;
    private ArrayList<Activity> activities = new ArrayList<>();
    private ArrayList<Alert> alerts = new ArrayList<>();
    private ArrayList<Goal> goals = new ArrayList<>();

    // The date format used to parse the birth date string
    private DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");



    /**
     * Constructor used to create a new user with new activities
     * @param name The name of the user
     * @param age The age of the user
     * @param height The height of the user
     * @param weight The weight of the user
     * @param activities The activities related to the user
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
     * Constructor used to read a user from the database
     * @param id The db id of the user
     * @param name the name of the user
     * @param age The age of the user
     * @param height The height of the user
     * @param weight The weight of the user
     * @param activities The activities related ot the user
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
     * Constructor used to create a new user without activities
     * @param name The name of the user
     * @param age The age of the user
     * @param height The height of the user
     * @param weight The weight of the user
     */
    public User(String name, int age, double height, double weight, Date birthDate) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        bmi = DataAnalyser.calcBMI(height, weight);
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return this.name;
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


    public void addAlert(Alert alert) {
        alerts.add(alert);
    }


    public void addActivity(Activity activity) {
        activities.add(activity);
    }
}
