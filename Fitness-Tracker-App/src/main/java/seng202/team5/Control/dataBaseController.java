package seng202.team5.Control;

import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;
import seng202.team5.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


public class dataBaseController {

    Connection con = null;


    public dataBaseController() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:dataBase.sqlite");
            System.out.println("Connected to dataBase");
        } catch (Exception e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }

    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        try {
            Statement stmt = con.createStatement();
            ResultSet set = stmt.executeQuery("SELECT * FROM User");
            User newUser;

            while (set.next()) {
                int id = set.getInt("ID");
                String name = set.getString("Name");
                double weight = set.getDouble("Weight");
                double height = set.getDouble("Height");
                int age = set.getInt("Age");
                ArrayList<Activity> activities = getActivities(id);
                newUser = new User(name, age, height, weight, activities);
                users.add(newUser);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        System.out.println(users.get(0).getName());
        return users;
    }

    public ArrayList<Activity> getActivities(int UserId) {
        ArrayList<Activity> activities = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM Activity WHERE User=" + UserId;
            ResultSet set = stmt.executeQuery(query);
            Activity newAct;

            while (set.next()) {
                int actID = set.getInt("ID");
                String name = set.getString("Name");
                DataSet dataSet = getDataSet(actID);
                if (dataSet == null) {
                    newAct = new Activity(actID, name);
                } else {
                    newAct = new Activity(actID, name, dataSet);
                }
                activities.add(newAct);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return activities;
    }

    public DataSet getDataSet(int actID) {
        DataSet dataSet;
        try {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM DataSet WHERE Activity=" + actID;
            ResultSet set = stmt.executeQuery(query);

            int setId = set.getInt("ID");
            double topSpeed = set.getInt("TopSpeed");
            double totalDist = set.getDouble("TotalDist");
            int heart = set.getInt("AvgHeartRate");
            double vert = set.getInt("VerticalDist"); // NEED TO FIX IN THE DATABASE
            ArrayList<DataPoint> dataPoints = getDataPoints(setId);

            dataSet = new DataSet(setId, topSpeed, totalDist, vert, heart, dataPoints);
            return dataSet;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
            return null;
        }
    }


    public ArrayList<DataPoint> getDataPoints(int setID) {
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM DataPoint WHERE DataSet=" + setID;
            ResultSet set = stmt.executeQuery(query);
            DataPoint newPoint;

            while (set.next()) {
                int pointId = set.getInt("ID");
                Date dateTime = set.getDate("DateTime");
                int heart = set.getInt("HeartRate");
                double lat = set.getDouble("Latitude");
                double lon = set.getDouble("Longitude");
                double elev = set.getDouble("Elevation");
                double speed = set.getDouble("Speed");
                boolean active = set.getBoolean("Active");

                newPoint = new DataPoint(pointId, dateTime, heart, lat, lon, elev, speed, active);
                dataPoints.add(newPoint);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return dataPoints;
    }

    public void addNewUser(User toAdd) {
        try {
            Statement stmt = con.createStatement();
            String name = toAdd.getName();
            double height = toAdd.getHeight();
            double weight = toAdd.getWeight();
            int age = toAdd.getAge();
            Date birth = toAdd.getBirthDate();
            String query = String.format("INSERT INTO User (Name, Height, Weight, Age) VALUES ('%s', %.2f, %.2f, %d)", name, height, weight, age);
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error when adding user: " + e.getLocalizedMessage());
        }
    }


    public static void main(String[] args) {
     //   dataBaseController db = new dataBaseController();
     //   User toAdd = new User("John Jones", 25, 1.8, 75.8);
     //   db.addNewUser(toAdd);
    }

    /**
     * read: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
     */
}
