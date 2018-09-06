package seng202.team5.Control;

import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;
import seng202.team5.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class is used to interact with the applications database
 */
public class dataBaseController {

    Connection connection = null;


    /**
     * Tries to establish a connection wih the applications database.
     */
    public dataBaseController() {
        // Try-catch is used to catch any exceptions that throw while creating connection to the database
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:dataBase.sqlite");
            System.out.println("Connected to dataBase");
        } catch (Exception e) {
            // Printing out an error message
            System.out.println("Error opening connection to database: " + e.getLocalizedMessage());
        }
    }


    /**
     * Closes the connections to the database
     */
    public void closeConnection() {
        // Try-catch used to catch any exceptions trow while closing connection to database.
        try {
            connection.close();
        } catch (SQLException e) {
            // Printing out an error message
            System.out.println("Error closing connection: " + e.getLocalizedMessage());
        }
    }


    /**
     * Gets all of the users out of the database and into an ArrayList.
     * @return An ArrayList holding all the user that are in the database.
     */
    public ArrayList<User> getUsers() {
        // Creating an ArrayList to hold all the users.
        ArrayList<User> users = new ArrayList<User>();
        // Try-catch is used to catch any exception that are throw wile executing the query
        try {
            // Creating a statement and then executing a query to get all users
            Statement stmt = connection.createStatement();
            ResultSet set = stmt.executeQuery("SELECT * FROM User");
            User newUser;

            // Looping over the result of the query and creating users from the data
            while (set.next()) {
                // Getting all the information about the current user
                int id = set.getInt("ID");
                String name = set.getString("Name");
                double weight = set.getDouble("Weight");
                double height = set.getDouble("Height");
                int age = set.getInt("Age");
                ArrayList<Activity> activities = getActivities(id);
                // Creating the user
                newUser = new User(name, age, height, weight, activities, id);
                // Adding the user to the ArrayList
                users.add(newUser);
            }
        } catch (SQLException e) {
            // Printing out an error message
            System.out.println("Error getting users: " + e.getLocalizedMessage());
        }
        // Returning the ArrayList of user
        return users;
    }


    /**
     * Gets all the activities out of the database that are related to a passed user id.
     * @param UserId The user id to get activities for.
     * @return An ArrayList holding all the found activities.
     */
    public ArrayList<Activity> getActivities(int UserId) {
        // Creating an ArrayList to hold the activities
        ArrayList<Activity> activities = new ArrayList<>();
        // Try-catch is used to catch any exception that are throw wile executing the query
        try {
            // Creating a statement to execute the query
            Statement stmt = connection.createStatement();
            // Executing the query to get the activities
            String query = "SELECT * FROM Activity WHERE User=" + UserId;
            ResultSet set = stmt.executeQuery(query);
            Activity newAct;

            // Looping over the results of the query
            while (set.next()) {
                // Getting all the information on the current query
                int actID = set.getInt("ID");
                String name = set.getString("Name");
                DataSet dataSet = getDataSet(actID);
                // Creating the activity
                if (dataSet == null) {
                    newAct = new Activity(actID, name);
                } else {
                    newAct = new Activity(actID, name, dataSet);
                }
                // Adding the activity to the ArrayList
                activities.add(newAct);
            }

        } catch (SQLException e) {
            // Printing out an error message
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        // Returning the ArrayList of activities
        return activities;
    }


    /**
     * Gets a DataSet out of the database that is related to a passed activity id.
     * @param actID The activity id to get a DataSet for.
     * @return The found DataSet.
     */
    public DataSet getDataSet(int actID) {
        DataSet dataSet;
        // Try-catch is used to catch any exception that are throw wile executing the query.
        try {
            // Creating a statement and executing a query
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM DataSet WHERE Activity=" + actID;
            ResultSet set = stmt.executeQuery(query);

            // Getting the information about the DataSet
            int setId = set.getInt("ID");
            double topSpeed = set.getInt("TopSpeed");
            double totalDist = set.getDouble("TotalDist");
            int heart = set.getInt("AvgHeartRate");
            double vert = set.getInt("VerticalDist"); // NEED TO FIX IN THE DATABASE
            ArrayList<DataPoint> dataPoints = getDataPoints(setId);

            // Creating the DataSet
            dataSet = new DataSet(setId, topSpeed, totalDist, vert, heart, dataPoints);
            // Returning the DataSet
            return dataSet;
        } catch (SQLException e) {
            // Printing an error message
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
        // Creating an ArrayList to hold the DataPoints
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        // Try-catch is used to catch any exception that are throw wile executing the query
        try {
            // Creating a statement and executing a query
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM DataPoint WHERE DataSet=" + setID;
            ResultSet set = stmt.executeQuery(query);
            DataPoint newPoint;

            // Looping over the results of the query
            while (set.next()) {
                // Getting all the information on the DataPoint
                int pointId = set.getInt("ID");
                Date dateTime = set.getDate("DateTime");
                int heart = set.getInt("HeartRate");
                double lat = set.getDouble("Latitude");
                double lon = set.getDouble("Longitude");
                double elev = set.getDouble("Elevation");
                double speed = set.getDouble("Speed");
                boolean active = set.getBoolean("Active");

                // Creating the Datapoint
                newPoint = new DataPoint(pointId, dateTime, heart, lat, lon, elev, speed, active);
                // Adding the DataPoint to the ArrayList
                dataPoints.add(newPoint);
            }
        } catch (SQLException e) {
            // Printing an error message
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        // Returning the ArrayList of DataPoints
        return dataPoints;
    }


    /**
     * Stores a passed user in the dataBase.
     * @param toAdd The user to be stored in the database.
     * @return A boolean holding if the user was successfully stored in the database.
     */
    public boolean storeNewUser(User toAdd) {
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking if the user is already in the database
            int id = toAdd.getId();
            if (checkId("User", id)) {
                // Returning false as the use was not stored in the database
                return false;
            } else {
                // The user is not in the database and will now be stored
                // Creating a statement
                Statement stmt = connection.createStatement();
                // Getting the values to store with the user
                String name = toAdd.getName();
                double height = toAdd.getHeight();
                double weight = toAdd.getWeight();
                int age = toAdd.getAge();
                Date birth = toAdd.getBirthDate();
                // Creating and executing the update to store the user
                String query = String.format("INSERT INTO User (Name, Height, Weight, Age) VALUES ('%s', %.2f, %.2f, " +
                        "%d)", name, height, weight, age);
                stmt.executeUpdate(query);
                // Returning true as the user was stored in the database
                return true;
            }

        } catch (SQLException e) {
            // Printing an error message
            System.out.println("Error when adding user: " + e.getLocalizedMessage());
            // Returning false as the user was not stored in the database
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
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking if activity is already in teh database or the related us is not in the database
            int id = toAdd.getId();
            if (checkId("Activity", id)) {
                // Return false as user was not stored in the database
                return false;
            } else if (!checkId("User", userId)) {
                // Return false as user was not stored in the database
                return false;
            } else {
                // Creating a statement and executing an update to store the activity
                Statement stmt = connection.createStatement();
                String query = String.format("INSERT INTO Activity (Name, User) Values ('%s', %d)",
                        toAdd.getName(), userId);
                stmt.executeUpdate(query);
                // Returning true as the activity was stored in the database
                return true;
            }

        } catch (SQLException e) {
            // Printing an error message
            System.out.println("Error when adding activity: " + e.getLocalizedMessage());
            // Returning false as the activity was not stored in the database
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
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking that the DataSet is not already in the database and tha the activity passed is in the database
            int id = toAdd.getId();
            if (checkId("DataSet", id)) {
                // Return false as user was not stored in the database
                return false;
            } else if(!checkId("Activity", actId)) {
                // Return false as user was not stored in the database
                return false;
            } else {
                // Creating a statement and executing an update to store the DataSet
                Statement stmt = connection.createStatement();
                String query = String.format("INSERT INTO DataSet (TopSpeed, TotalDist, AvgHeartRate, VerticalDist, " +
                                "Activity) Values (%f, %f, %d, %f, %d)", toAdd.getTopSpeed(), toAdd.getTotalDistance(),
                        toAdd.getAvgHeartRate(), toAdd.getVerticalDistance(), actId);
                stmt.executeUpdate(query);
                // Returning true as the
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error when adding DataSet: " + e.getLocalizedMessage());
            // Return false as user was not stored in the database
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
        // Try-catch is used to catch any exception that are throw wile executing the query
        try {
            // Creating a statement and executing a query
            Statement stmt = connection.createStatement();
            String query = "SELECT ID FROM " + table;
            ResultSet set = stmt.executeQuery(query);

            // Looping over the results to find the last id
            while (set.next()) {
                id = set.getInt("ID");
            }
        } catch (SQLException e) {
            // Printing an error message
            System.out.println("Error finding id: " + e.getLocalizedMessage());
        }
        // Returning th found id
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
        // Try-catch is used to catch any exception that are throw wile executing the query
        try {
            // Creating a statement and executing a query
            Statement stmt = connection.createStatement();
            String query = "SELECT ID FROM " + table;
            ResultSet set = stmt.executeQuery(query);
            int id = -1;

            // Searching for the passed id
            while (set.next()) {
                id = set.getInt("ID");
                if (id == toCheck) {
                    // Id is found
                    inTable = true;
                    break;
                }
            }
        } catch (SQLException e) {
            // Printing an error message
            System.out.println("Error finding id: " + e.getLocalizedMessage());
        }
        // Returning the result of the search
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
