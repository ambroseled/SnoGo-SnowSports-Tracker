package seng202.team5.Model;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private String name;
    private Date birthDate;
    private double height;
    private double weight;
    private double age;
    private ArrayList<Activity> activities;

    //getters
    public String getName() {return name;}
    public Date getBirthDate() {return birthDate;}
    public double getHeight() {return height;}
    public double getWeight() {return weight;}
    public ArrayList<Activity> getActivities() {return activities;}

    //setters
    public void setName(String tempName) {name = tempName;}
    public void setBirthDate(Date tempDate) {birthDate = tempDate;}
    public void setHeight(double tempHeight) {height = tempHeight;}
    public void setWeight(double tempWeight) {weight = tempWeight;}
    /*
    //Adds activities from CSV to user's list of activities
    public void addActivities(String fileName) {
        ArrayList<Activity> activitiesToAdd = InputDataParser.parseCSVToActivities(String fileName);
        activities.addAll(activitiesToAdd);
    } */
}
