package seng202.team5.DataManipulation;

import seng202.team5.Control.App;
import seng202.team5.Control.ErrorController;
import seng202.team5.Model.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


//TODO: Duplicate data handling



/**
 * This class is used to interact with the applications database
 */
public class DataBaseController {

    private Connection connection = null;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private static String dbString = "jdbc:sqlite:" + System.getProperty("user.home") + "/SnoGoDB.db";


    /**
     * Tries to establish a connection wih the applications database.
     */
    public DataBaseController() {
        // Try-catch is used to catch any exceptions that throw while creating connection to the database
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbString);
        } catch (Exception e) {
            createDB();
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(dbString);
            } catch (Exception e2) {
                //TODO: error dialogue
            }
        }
    }



    public void createDB() {

    }


    /**
     * Closes the connections to the database
     */
    public void closeConnection() {
        // Try-catch used to catch any exceptions trow while closing connection to database.
        try {
            connection.close();
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error closing connection to database");
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
                String birth = set.getString("BirthDate");
                ArrayList<Activity> activities = getActivities(id);
                // Creating the user
                newUser = new User(name, age, height, weight, activities, id, birth);
                newUser.setAlerts(getAlerts(id));
                newUser.setGoals(getGoals(id));
                // Adding the user to the ArrayList
                users.add(newUser);
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error populating the database");
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
            if (checkId("User", UserId)) {
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
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error querying the database");
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
            if (checkId("Activity", actID)) {
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
                double calories = set.getDouble("Calories");
                double slopeTime = set.getDouble("SlopeTime");
                double avgSpeed = set.getDouble("AvgSpeed");

                // Creating the DataSet
                dataSet = new DataSet(setId, topSpeed, totalDist, vert, heart, dataPoints, calories, slopeTime, avgSpeed);
                // Returning the DataSet
                return dataSet;
            }
            return null;
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error querying the database");
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
            if (checkId("DataSet", setID)) {
                // Creating a statement and executing a query
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM DataPoint WHERE DataSet=" + setID;
                ResultSet set = stmt.executeQuery(query);
                DataPoint newPoint;

                // Looping over the results of the query
                while (set.next()) {
                    // Getting all the information on the DataPoint
                    int pointId = set.getInt("ID");
                    String date = set.getString("DateTime");
                    int heart = set.getInt("HeartRate");
                    double lat = set.getDouble("Latitude");
                    double lon = set.getDouble("Longitude");
                    double elev = set.getDouble("Elevation");
                    double speed = set.getDouble("Speed");
                    boolean active = set.getBoolean("Active");
                    double distance = set.getDouble("Distance");
                    // Creating the DataPoint
                    newPoint = new DataPoint(pointId, date, heart, lat, lon, elev, distance, speed, active);
                    // Adding the DataPoint to the ArrayList
                    dataPoints.add(newPoint);
                }
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error querying the database");
        }
        // Returning the ArrayList of DataPoints
        return dataPoints;
    }


    /**
     * Stores a passed user in the dataBase.
     * @param toAdd The user to be stored in the database.
     */
    public void storeNewUser(User toAdd) {
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking if the user is already in the database
            int id = toAdd.getId();
            if (!checkId("User", id)) {
                // The user is not in the database and will now be stored
                // Creating a statement
                Statement stmt = connection.createStatement();
                // Getting the values to store with the user
                String name = toAdd.getName();
                double height = toAdd.getHeight();
                double weight = toAdd.getWeight();
                int age = toAdd.getAge();
                String birth = formatter.format(toAdd.getBirthDate());
                // Creating and executing the update to store the user
                String query = String.format("INSERT INTO User (Name, Height, Weight, Age, BirthDate) VALUES ('%s', %.2f, %.2f, " +
                        "%d, '%s')", name, height, weight, age, birth);
                stmt.executeUpdate(query);
                toAdd.setId(findId("User"));
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error storing new user in database");
        }
    }


    /**
     * Updates a row in teh User table to the passed User. If the passed User is not in
     * the database then it will be stored.
     * @param user The User to be updated.
     */
    public void updateUser(User user) {
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking if the user is already in the database
            if (checkId("User", user.getId())) {
                // The user is in the database so can be updated
                // Creating a statement
                Statement stmt = connection.createStatement();
                // Getting the values to update with the user
                String name = user.getName();
                double height = user.getHeight();
                double weight = user.getWeight();
                int age = user.getAge();
                String birth = formatter.format(user.getBirthDate());
                // Creating and executing the update to update the user
                String query = String.format("UPDATE User Set Name = '%s', Height = %.2f,  Weight = %.2f, " +
                        "Age = %d, BirthDate = '%s' WHERE ID = %d", name, height, weight, age, birth, user.getId());
                stmt.executeUpdate(query);
            } else {
                storeNewUser(user);
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error updating user details in database");
        }
    }


    /**
     * Stores a passed activity in the database.
     * @param toAdd The activity to be stored.
     * @param userId The related user for the activity being stored.
     */
    public void storeActivity(Activity toAdd, int userId) {
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking if activity is already in teh database or the related us is not in the database
            int id = toAdd.getId();
            if (!checkId("Activity", id) && checkId("User", userId)) {
                // Creating a statement and executing an update to store the activity
                Statement stmt = connection.createStatement();
                String query = String.format("INSERT INTO Activity (Name, User) Values ('%s', %d)",
                        toAdd.getName(), userId);
                stmt.executeUpdate(query);
                storeDataSet(toAdd.getDataSet(), findId("Activity"));
                int actId = findId("Activity");
                storeDataSet(toAdd.getDataSet(), actId);
                toAdd.setId(findId("Activity"));
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error storing activity database");
        }
    }


    /**
     * Stores a passed DataSet into the database.
     * @param toAdd The DataSet to be stored.
     * @param actId The related activty for the DataSet being stored.
     */
    private void storeDataSet(DataSet toAdd, int actId) {
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking that the DataSet is not already in the database and that the activity passed is in the database
            int id = toAdd.getId();
            if (!checkId("DataSet", id) && checkId("Activity", actId)) {
                // Creating a statement and executing an update to store the DataSet
                Statement stmt = connection.createStatement();
                String query = String.format("INSERT INTO DataSet (TopSpeed, TotalDist, AvgHeartRate, VerticalDist, " +
                                "Activity, Calories, SlopeTime, AvgSpeed) Values (%f, %f, %d, %f, %d, %f, %f, %f)",
                        toAdd.getTopSpeed(), toAdd.getTotalDistance(), toAdd.getAvgHeartRate(),
                        toAdd.getVerticalDistance(), actId, toAdd.getCaloriesBurned(), toAdd.getSlopeTime(), toAdd.getAvgSpeed());
                stmt.executeUpdate(query);
                int setId = findId("DataSet");
                for (DataPoint x : toAdd.getDataPoints()) {
                    storeDatePoint(x, setId);
                }
                toAdd.setId(findId("DataSet"));
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error storing data set in database");
        }
    }



    /**
     * Stores a passed DataPoint in the database.
     * @param toAdd The DataPoint to be stored
     * @param setId The DataSet that the passed DataPoint belongs to.
     */
    private void storeDatePoint(DataPoint toAdd, int setId) {
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking that the DataPoint is not already in the database and the the DataSet id passed is in the database
            int id = toAdd.getId();
            if (!checkId("DataPoint", id) && checkId("DataSet", setId)) {
                // Creating a statement and executing an update to store the DataSet
                DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String dateString = dateTimeFormat.format(toAdd.getDateTime());
                Statement stmt = connection.createStatement();
                String query = String.format("INSERT INTO DataPoint (DateTime, HeartRate, Latitude, Longitude, " +
                                "Elevation, Speed, Active, DataSet, Distance) Values ('%s', %d, %f, %f, %f, %f, %b," +
                                "%d, %f)", dateString, toAdd.getHeartRate(), toAdd.getLatitude(), toAdd.getLongitude(),
                        toAdd.getElevation(), toAdd.getSpeed(), toAdd.isActive(), setId, toAdd.getDistance());
                stmt.executeUpdate(query);
                toAdd.setId(findId("DataPoint"));
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error storing data point database");
        }
    }


    /**
     * Stores a passed Goal into the database.
     * @param toAdd The Goal to be stored in the database.
     * @param userId The is of the User related to the Goal.
     */
    public void storeGoal(Goal toAdd, int userId) {
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking that the DataPoint is not already in the database and tha the activity passed is in the database
            int id = toAdd.getId();
            if (!checkId("Goal", id) && checkId("User", userId)) {
                // Creating a statement and executing an update to store the DataSet
                String query = String.format("INSERT INTO Goal (Metric, MetricGoal, Name, Completed, CompletionDate," +
                        "User, Global, Expired) Values (?, ?, ?, ?, ?, ?, ?, ?)");
                PreparedStatement pStmt = connection.prepareStatement(query);
                pStmt.setString(1, toAdd.getMetric());
                pStmt.setDouble(2, toAdd.getMetricGoal());
                pStmt.setString(3, toAdd.getName());
                pStmt.setBoolean(4, toAdd.isCompleted());
                pStmt.setString(5, toAdd.getDateString());
                pStmt.setInt(6, userId);
                pStmt.setBoolean(7, toAdd.isGlobal());
                pStmt.setBoolean(8, toAdd.isExpired());
                pStmt.executeUpdate();
                toAdd.setId(findId("Goal"));
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error storing goal in database");
        }
    }


    /**
     * Updates a passed goal in the database. If the passed goal is not in
     * the database it will be stored.
     * @param goal The goal to update.
     */
    public void updateGoal(Goal goal) {
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking if the goal is already in the database
            if (checkId("Goal", goal.getId())) {
                // The goal is in the database so can be updated
                // Creating a statement
                Statement stmt = connection.createStatement();
                // Getting the values to update with the goal
                String metric = goal.getMetric();
                double metricGoal = goal.getMetricGoal();
                String name = goal.getName();
                boolean comp = goal.isCompleted();
                String compDate = goal.getDateString();
                boolean global = goal.isGlobal();
                boolean expired = goal.isExpired();
                // Creating and executing the update to update the user
                String query = String.format("UPDATE Goal Set Metric = '%s', MetricGoal = %.2f,  Name = '%s', " +
                        "Completed = %b, CompletionDate = '%s', Global = %b, Expired = %b WHERE ID = %d", metric,
                        metricGoal, name, comp, compDate, global, expired, goal.getId());
                stmt.executeUpdate(query);
            } else {
                storeGoal(goal, App.getCurrentUser().getId());
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error updating goal within database");
        }
    }


    /**
     * Removes a passed goal form the database.
     * @param goal The goal to be removed.
     */
    public void removeGoal(Goal goal) {
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking that the goal is in the database
            if (checkId("Goal", goal.getId())) {
                // The goal is in the database so can be removed
                // Creating a statement
                Statement stmt = connection.createStatement();
                // Creating and executing the update to update the user
                String query = String.format("DELETE FROM Goal WHERE ID = %d", goal.getId());
                stmt.executeUpdate(query);
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error removing goal from database");
        }
    }


    /**
     * Stores a passed Alert into the database.
     * @param toAdd The Alert to store.
     * @param userId The id of the User that the Alert is related to.
     */
    public void storeAlert(Alert toAdd, int userId) {
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking that the DataPoint is not already in the database and tha the activity passed is in the database
            int id = toAdd.getId();
            if (!checkId("Goal", id) && checkId("User", userId)) {
                // Creating a statement and executing an update to store the DataSet
                String query = String.format("INSERT INTO Alert (Type, Message, Date, User) " +
                        "Values (?, ?, ?, ?)");
                PreparedStatement pStmt = connection.prepareStatement(query);
                pStmt.setString(1, toAdd.getType());
                pStmt.setString(2, toAdd.getMessage());
                pStmt.setString(3, toAdd.getDateString());
                pStmt.setInt(4, userId);
                pStmt.executeUpdate();
                toAdd.setId(findId("Alert"));
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error storing alert in database");
        }
    }


    /**
     * Removes a passed goal form the database.
     * @param alert The goal to be removed.
     */
    public void removeAlert(Alert alert) {
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking that the alert is in the database
            if (checkId("Alert", alert.getId())) {
                // The alert is in the database so can be removed
                // Creating a statement
                Statement stmt = connection.createStatement();
                // Creating and executing the update to update the user
                String query = String.format("DELETE FROM Alert WHERE ID = %d", alert.getId());
                stmt.executeUpdate(query);
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error removing alert from database");
        }
    }


    /**
     * Gets an ArrayList of Goals from the database that are related to a passed
     * user id.
     * @param userId The user id to find goals for.
     * @return An ArrayList holding all the found Goals.
     */
    public ArrayList<Goal> getGoals(int userId) {
        ArrayList<Goal> goals = new ArrayList<>();
        try {
            if (checkId("User", userId)) {
                // Creating a statement to execute the query
                Statement stmt = connection.createStatement();
                // Executing the query to get the goals
                String query = "SELECT * FROM Goal WHERE User=" + userId;
                ResultSet set = stmt.executeQuery(query);
                while (set.next()) {
                    String name = set.getString("Name");
                    String metric = set.getString("Metric");
                    double metricGoal = set.getDouble("MetricGoal");
                    boolean completed = set.getBoolean("Completed");
                    int id = set.getInt("ID");
                    String dateString = set.getString("CompletionDate");
                    boolean global = set.getBoolean("Global");
                    boolean expired = set.getBoolean("Expired");
                    Goal newGoal = new Goal(name, metric, metricGoal, dateString, completed, id, global, expired);
                    goals.add(newGoal);
                }
            }

        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error retrieving goal from database");
        }
        return goals;
    }


    /**
     * Gets an ArrayList of Alerts from the database that are related to a passed
     * user id.
     * @param userId The user id to find alerts for.
     * @return An ArrayList holding all the found Alerts.
     */
    public ArrayList<Alert> getAlerts(int userId) {
        ArrayList<Alert> alerts = new ArrayList<>();
        try {
            if (checkId("User", userId)) {
                // Creating a statement to execute the query
                Statement stmt = connection.createStatement();
                // Executing the query to get the alerts
                String query = "SELECT * FROM Alert WHERE User=" + userId;
                ResultSet set = stmt.executeQuery(query);
                while (set.next()) {
                    String type = set.getString("Type");
                    String message = set.getString("Message");
                    int id = set.getInt("ID");
                    String dateString = set.getString("Date");
                    Alert alert = new Alert(dateString , message, id, type);
                    alerts.add(alert);
                }
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error getting alerts from database");
        }
        return alerts;
    }



    /**
     * Finds the latest id in the database of a passed table.
     * @param table The table to find the id for.
     * @return The found id.
     */
    private int findId(String table) {
        int id = -1;
        // Try-catch is used to catch any exception that are throw wile executing the query
        try {
            // Creating a statement and executing a query
            Statement stmt = connection.createStatement();
            String query = "SELECT ID FROM " + table;
            ResultSet set = stmt.executeQuery(query);
            ArrayList<Integer> ids = new ArrayList<>();
            // Looping over the results to find the last id
            while (set.next()) {
                id = set.getInt("ID");
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error finding id within database");
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
    private boolean checkId(String table, int toCheck) {
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
            // Showing error dialogue to user
            ErrorController.displayError("Error checking id within database");
        }
        // Returning the result of the search
        return inTable;
    }


}
