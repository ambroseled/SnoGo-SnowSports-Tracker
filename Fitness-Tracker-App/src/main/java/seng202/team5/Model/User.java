package seng202.team5.Model;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private String name;
    private Date birthDate;
    private double height;
    private double weight;
    private int age;
    private int id; // Need to add a hanlder for this
    private ArrayList<Activity> activities;


    /*
    Need another constructor here
     */

    public User(String name, int age, double height, double weight, ArrayList<Activity> activities){
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.activities = activities;
    }

    /**
     * Overloaded version of constructor used when user is created from the dataBase
     * @param name
     * @param age
     * @param height
     * @param weight
     * @param activities
     * @param id
     */
    public User(String name, int age, double height, double weight, ArrayList<Activity> activities, int id){
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.id = id;
        this.activities = activities;
    }

    //getters
    public String getName() {return name;}
    public Date getBirthDate() {return birthDate;}
    public double getHeight() {return height;}
    public double getWeight() {return weight;}
    public ArrayList<Activity> getActivities() {return activities;}

    public int getAge() {
        return age;
    }

    //setters
    public void setName(String tempName) {name = tempName;}
    public void setBirthDate(Date tempDate) {birthDate = tempDate;}
    public void setHeight(double tempHeight) {height = tempHeight;}
    public void setWeight(double tempWeight) {weight = tempWeight;}

    public void setAge(int age) {
        this.age = age;
    }
    /*
    //Adds activities from CSV to user's list of activities
    public void addActivities(String fileName) {
        ArrayList<Activity> activitiesToAdd = InputDataParser.parseCSVToActivities(String fileName);
        activities.addAll(activitiesToAdd);
    } */
}
