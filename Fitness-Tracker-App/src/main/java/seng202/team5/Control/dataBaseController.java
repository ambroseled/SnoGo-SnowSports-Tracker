package seng202.team5.Control;

import seng202.team5.Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


public class dataBaseController {

    Connection con = null;

    /**
     * Tries to get a connection to the applications database. If this fails and error
     * message is output.
     */
    public dataBaseController() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:dataBase.sqlite");
            System.out.println("Connected to dataBase");
        } catch (Exception e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }


    /**
     * Closes the connection to the database.
     */
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }


    /**
     * Gets a list of all the users in the database.
     * @return
     */
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


    /**
     * Gets a list of all the activities in the database that are for a passed user.
     * @param UserId
     * @return
     */
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


    /**
     * Gets the DataSet from the database that is for a passed activity.
     * @param actID
     * @return
     */
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


    /**
     * Gets a list of dataPoints from the database that are for a passed dataSet.
     * @param setID
     * @return
     */
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


    /**
     * Stores a passed user into the database.
     * @param toAdd
     */
    public void addNewUser(User toAdd) {
        try {
            Statement stmt = con.createStatement();
            String query = String.format("INSERT INTO User (Name, Height, Weight, Age, BirthDate) VALUES ('%s', %.2f, " +
                    "%.2f, %d)", toAdd.getName(), toAdd.getHeight(), toAdd.getWeight(), toAdd.getAge(), toAdd.getBirthDate());
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error when adding user: " + e.getLocalizedMessage());
        }
    }


    /**
     * It is assumed that all users passed to this have an id value as this will only be used once a user has
     * been grabbed from the database
     * @param toAdd
     * @param toAddto
     */
    public void addActivity(Activity toAdd, User toAddto) {
        try {
            Statement stmt = con.createStatement();
            String query = String.format("INSERT INTO Activity (Name, User) VALUES ('%s', %d)", toAdd.getName(), toAddto.getId());
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error when adding user: " + e.getLocalizedMessage());
        }
    }

    /**
     * Stores a passed dataSet in the dataBase.
     * @param toAdd
     * @param actId
     */
    public void addDataSet(DataSet toAdd, int actId) {
        try {
            Statement stmt = con.createStatement();
            String query = String.format("INSERT INTO DataSet (TopSpeed, TotalDist, AvgHeartRate, VerticalDist, " +
                    "Activity) VALUES (%f, %f, %d, %f, %d)", toAdd.getTopSpeed(), toAdd.getTotalDistance(),
                    toAdd.getAvgHeartRate(), toAdd.getVerticalDistance(), actId);
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error when adding user: " + e.getLocalizedMessage());
        }
    }


    public void addDataPoint(DataPoint toAdd, int setId) {
        try {
            Statement stmt = con.createStatement();
            String query = String.format("INSERT INTO DataPoint (DataTime, HeartRate, Latitude, Longitude, " +
                            "Elevation, Distance, Speed, Active, DataSet) VALUES ('%s', %d, %f, %f, %f, %f, %f,  %b, " +
                            "%d)", toAdd.getDateTime(), toAdd.getHeartRate(), toAdd.getLatitude(), toAdd.getLongitude(),
                    toAdd.getElevation(), toAdd.getSpeed(), setId);
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error when adding user: " + e.getLocalizedMessage());
        }
    }


    public static void main(String[] args) {
        InputDataParser parser = new InputDataParser();
        ArrayList<Activity> activities = parser.parseCSVToActivities("dataBaseTest.csv");
        System.out.println(activities.get(0).getName());
     //   dataBaseController db = new dataBaseController();
     //   User toAdd = new User("John Jones", 25, 1.8, 75.8);
     //   db.addNewUser(toAdd);
    }

    /**
     * read: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
     */
}
