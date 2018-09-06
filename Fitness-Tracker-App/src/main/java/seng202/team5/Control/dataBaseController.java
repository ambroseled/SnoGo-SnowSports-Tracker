package seng202.team5.Control;

import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;
import seng202.team5.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


public class dataBaseController {

    Connection connection = null;


    /**
     * Tries to establish a connection wih the applications database.
     */
    public dataBaseController() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:dataBase.sqlite");
            System.out.println("Connected to dataBase");
        } catch (Exception e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }


    /**
     * Closes the connections to the database
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }


    /**
     * Gets all of the users out of the database and into an ArrayList.
     * @return An ArrayList holding all the user that are in the database.
     */
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        try {
            Statement stmt = connection.createStatement();
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
     * Gets all the activities out of the database that are related to a passed user id.
     * @param UserId The user id to get activities for.
     * @return An ArrayList holding all the found activities.
     */
    public ArrayList<Activity> getActivities(int UserId) {
        ArrayList<Activity> activities = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
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
     * Gets a DataSet out of the database that is related to a passed activity id.
     * @param actID The activity id to get a DataSet for.
     * @return The found DataSet.
     */
    public DataSet getDataSet(int actID) {
        DataSet dataSet;
        try {
            Statement stmt = connection.createStatement();
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
     * Gets all the DataPoints out of the database that are related to a passed DataSet id.
     * @param setID The DataSet id to find DataPoints for.
     * @return An ArrayList holding all the found DataPoints.
     */
    public ArrayList<DataPoint> getDataPoints(int setID) {
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
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
     * Stores a passed user in the dataBase.
     * @param toAdd The user to be stored in the database.
     * @return A boolean holding if the user was successfully stored in the database.
     */
    public boolean storeNewUser(User toAdd) {
        try {
            int id = toAdd.getId();
            if (checkId("User", id)) {
                return false;
            } else {
                Statement stmt = connection.createStatement();
                String name = toAdd.getName();
                double height = toAdd.getHeight();
                double weight = toAdd.getWeight();
                int age = toAdd.getAge();
                Date birth = toAdd.getBirthDate();
                String query = String.format("INSERT INTO User (Name, Height, Weight, Age) VALUES ('%s', %.2f, %.2f, " +
                        "%d)", name, height, weight, age);
                stmt.executeUpdate(query);
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error when adding user: " + e.getLocalizedMessage());
            return false;
        }
    }


    /**
     * Stores a passed activity in the database.
     * @param toAdd The activity to be stored.
     * @param userId The related user for the activity being stored.
     * @return A boolean holding if the store operation was successful.
     */
    public boolean storeActivity(Activity toAdd, int userId) {
        try {
            int id = toAdd.getId();
            if (checkId("Activity", id) || checkId("User", userId) == false) {
                return false;
            } else {
                Statement stmt = connection.createStatement();
                String query = String.format("INSERT INTO Activity (Name, User) Values ('%s', %d)",
                        toAdd.getName(), userId);
                stmt.executeUpdate(query);
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error when adding activity: " + e.getLocalizedMessage());
            return false;
        }
    }


    /**
     * Stores a passed DataSet into the database.
     * @param toAdd The DataSet to be stored.
     * @param actId The related activty for the DataSet being stored.
     * @return A boolean holding if the store operation was successful.
     */
    public boolean storeDataSet(DataSet toAdd, int actId) {
        try {
            int id = toAdd.getId();
            if (checkId("DataSet", id) || checkId("Activity", actId) == false) {
                return false;
            } else {
                Statement stmt = connection.createStatement();
                String query = String.format("INSERT INTO DataSet (TopSpeed, TotalDist, AvgHeartRate, VerticalDist, " +
                                "Activity) Values (%f, %f, %d, %f, %d)", toAdd.getTopSpeed(), toAdd.getTotalDistance(),
                        toAdd.getAvgHeartRate(), toAdd.getVerticalDistance(), actId);
                stmt.executeUpdate(query);
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error when adding DataSet: " + e.getLocalizedMessage());
            return false;
        }
    }



    /**
     * Finds the latest id in the database of a passed table.
     * @param table The table to find the id for.
     * @return The found id.
     */
    public int findId(String table) {
        int id = -1;
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT ID FROM " + table;
            ResultSet set = stmt.executeQuery(query);

            while (set.next()) {
                id = set.getInt("ID");
            }
        } catch (SQLException e) {
            System.out.println("Error finding id: " + e.getLocalizedMessage());
        }
        return id;
    }


    /**
     * Checks if a passed id is in a passed table. Used to stop double up data from being entered
     * into a table.
     * @param table The table to check.
     * @param toCheck The id to check.
     * @return A boolean holding if the id was found.
     */
    public boolean checkId(String table, int toCheck) {
        boolean inTable = false;
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT ID FROM " + table;
            ResultSet set = stmt.executeQuery(query);
            int id = -1;

            while (set.next()) {
                id = set.getInt("ID");
                if (id == toCheck) {
                    inTable = true;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding id: " + e.getLocalizedMessage());
        }
        return inTable;

    }


    public static void main(String[] args) {
        dataBaseController db = new dataBaseController();
        User toAdd = new User("John Jones", 25, 1.8, 75.8);
        boolean success = db.storeNewUser(toAdd);
        System.out.println(success);
    }

    /**
     * read: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
     */
}
