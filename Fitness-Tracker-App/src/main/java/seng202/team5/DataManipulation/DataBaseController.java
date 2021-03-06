package seng202.team5.DataManipulation;


import org.sqlite.SQLiteConfig;
import seng202.team5.Control.HomeController;
import seng202.team5.Control.DialogController;
import seng202.team5.Model.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * This class is used to interact with the applications database
 */
public class DataBaseController {


    // Variables used in database processing
    private static Connection connection = null;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private static String dbString = "jdbc:sqlite:" + System.getProperty("user.home") + "/SnoGo.db";
    private static final String driver = "org.sqlite.JDBC";


    public DataBaseController() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(dbString, config().toProperties());
            createDatabase();
        } catch (Exception e) {
            DialogController.displayError("Error connecting to database");
        }
    }


    private static SQLiteConfig config() {
        SQLiteConfig sqlConfig = new SQLiteConfig();
        sqlConfig.enforceForeignKeys(true);
        return sqlConfig;
    }


    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // Showing error dialogue to user
            DialogController.displayError("Error closing connection to database");
        }

    }


    private static void executeStmt(String query) {
        try {
            Statement newStmt = connection.createStatement();
            newStmt.execute(query);
        } catch (SQLException e) {
        }
    }


    /**
     *  Creates a new data for the application if it does not already exist and creates the databases structure (tables
     *  and attributes). The data is stored in the project's directory and consists of four tables:
     *  users, activities, datapoints and targets.     *
     * @throws SQLException if an sql related problem is encountered trying to set up the data.
     */
    public static void createDatabase() {
        if (connection != null) {
            String actTable = "CREATE TABLE IF NOT EXISTS Activity (\n" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "Name VARCHAR NOT NULL,\n" +
                    "USER INTEGER REFERENCES User (ID) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL" +
                    ");";


            String alertTable = "CREATE TABLE IF NOT EXISTS Alert (\n" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "Type STRING NOT NULL,\n" +
                    "MESSAGE STRING NOT NULL,\n" +
                    "Date STRING NOT NULL,\n" +
                    "User INTEGER NOT NULL REFERENCES User (ID) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL" +
                    ");";


            String pointTable = "CREATE TABLE IF NOT EXISTS DataPoint (\n" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "DateTime STRING NOT NULL,\n" +
                    "HeartRate INTEGER NOT NULL,\n" +
                    "Latitude DOUBLE NOT NULL,\n" +
                    "Longitude DOUBLE NOT NULL,\n" +
                    "Elevation DOUBLE NOT NULL,\n" +
                    "Distance DOUBLE NOT NULL,\n" +
                    "Speed DOUBLE NOT NULL,\n" +
                    "Active BOoLEAN NOT NULL,\n" +
                    "DataSet INTEGER REFERENCES DataSet (ID) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL" +
                    ");";


            String setTable = "CREATE TABLE IF NOT EXISTS DataSet (\n" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "TopSpeed DOUBLE NOT NULL,\n" +
                    "TotalDist DOUBLE NOT NULL,\n" +
                    "AvgHeartRate INTEGER NOT NULL,\n" +
                    "VerticalDistance DOUBLE NOT NULL,\n" +
                    "Calories DOUBLE NOT NULL,\n" +
                    "SlopeTime DOUBLE NOT NULL,\n" +
                    "AvgSpeed DOUBLE NOT NULL,\n" +
                    "Activity INTEGER REFERENCES Activity (ID) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL" +
                    ");";


            String goalTable = "CREATE TABLE IF NOT EXISTS Goal (\n" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "Metric STRING NOT NULL,\n" +
                    "MetricGoal DOUBLE NOT NULL,\n" +
                    "Name STRING NOT NULL,\n" +
                    "Completed BOOLEAN NOT NULL,\n" +
                    "CompletionDate STRING NOT NULL,\n" +
                    "Global BOOLEAN NOT NULL,\n" +
                    "EXPIRED BOOLEAN NOT NULL,\n" +
                    "User INTEGER REFERENCES User (ID) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL" +
                    ");";


            String userTable = "CREATE TABLE IF NOT EXISTS User (\n" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "Name STRING NOT NULL,\n" +
                    "Height DOUBLE NOT NULL,\n" +
                    "Weight DOUBLE NOT NULL,\n" +
                    "Age INTEGER NOT NULL,\n" +
                    "BirthDate STRING NOT NULL\n" +
                    ");";


            executeStmt(actTable);
            executeStmt(alertTable);
            executeStmt(pointTable);
            executeStmt(setTable);
            executeStmt(goalTable);
            executeStmt(userTable);
        }
    }



    /**
     * Gets all of the users out of the database and into an ArrayList.
     * @return An ArrayList holding all the user that are in the database.
     */
    public ArrayList<User> getUsers() {
        // Creating an ArrayList to hold all the users.
        ArrayList<User> users = new ArrayList<>();
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
            DialogController.displayError("User error with the database");
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
            // Showing error dialogue to user
            DialogController.displayError("Error activity database");
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
        // Try-catch is used to catch any exception that are throw wile executing the query.
        try {
            if (checkId("Activity", actID)) {
                // Creating a statement and executing a query
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM DataSet WHERE Activity=" + actID;
                ResultSet set = stmt.executeQuery(query);
                if (!set.isClosed()) {
                    // Getting the information about the DataSet
                    int setId = set.getInt("ID");
                    double topSpeed = set.getDouble("TopSpeed");
                    double totalDist = set.getDouble("TotalDist");
                    int heart = set.getInt("AvgHeartRate");
                    double vert = set.getDouble("VerticalDistance"); // NEED TO FIX IN THE DATABASE
                    ArrayList<DataPoint> dataPoints = getDataPoints(setId);
                    double calories = set.getDouble("Calories");
                    double slopeTime = set.getDouble("SlopeTime");
                    double avgSpeed = set.getDouble("AvgSpeed");

                    // Creating the DataSet
                    dataSet = new DataSet(setId, topSpeed, totalDist, vert, heart, dataPoints, calories, slopeTime, avgSpeed);
                    // Returning the DataSet
                    return dataSet;
                }
            }
            return null;
        } catch (SQLException e) {
            // Showing error dialogue to user
            DialogController.displayError("Error dataSet database");
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
            // Printing an error message
            System.out.println("Error when adding user: " + e.getLocalizedMessage());
        }
    }


    /**
     * Updates a row in the User table to the passed User. If the passed User is not in
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
            // Printing an error message
            System.out.println("Error when adding user: " + e.getLocalizedMessage());
        }
    }


    /**
     * Removes a passed user from the database. Does nothing if the user is not present in the database.
     * @param user the user to be removed.
     */
    public void removeUser(User user) {
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking that the user is in the database
            if (checkId("User", user.getId())) {
                // The user is in the database so can be removed
                // Creating a statement
                Statement stmt = connection.createStatement();
                // Creating and executing the remove on the user
                String query = String.format("DELETE FROM User WHERE ID = %d", user.getId());
                stmt.executeUpdate(query);
            }
        } catch (SQLException e) {
            // Printing an error message
            System.out.println("Error when removing user: " + e.getLocalizedMessage());
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
                int actId = findId("Activity");
                stmt.close();
                storeDataSet(toAdd.getDataSet(), actId);
                toAdd.setId(findId("Activity"));
            }
        } catch (SQLException e) {
            // Printing an error message
            System.out.println("Error when adding activity: " + e.getLocalizedMessage());
        }
    }



    /**
     * Removes a passed activity from the database. Does nothing if the activity is not present in the database.
     * @param activity the user to be removed.
     */
    public void removeActivity(Activity activity) {
        // Try-catch is used to catch any exception that are throw wile executing the update
        try {
            // Checking that the activity is in the database
            if (checkId("Activity", activity.getId())) {
                // The activity is in the database so can be removed
                // Creating a statement
                Statement stmt = connection.createStatement();
                // Creating and executing the remove on the activity
                String query = String.format("DELETE FROM Activity WHERE ID = %d", activity.getId());
                stmt.executeUpdate(query);
            }
        } catch (SQLException e) {
            // Printing an error message
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
                String query = String.format("INSERT INTO DataSet (TopSpeed, TotalDist, AvgHeartRate, VerticalDistance, " +
                                "Activity, Calories, SlopeTime, AvgSpeed) Values (%f, %f, %d, %f, %d, %f, %f, %f)",
                        toAdd.getTopSpeed(), toAdd.getTotalDistance(), toAdd.getAvgHeartRate(),
                        toAdd.getVerticalDistance(), actId, toAdd.getCaloriesBurned(), toAdd.getSlopeTime(), toAdd.getAvgSpeed());
                stmt.executeUpdate(query);

                /*
                int setId = findId("DataSet");
                for (DataPoint x : toAdd.getDataPoints()) {
                    storeDatePoint(x, setId);
                }*/
                stmt.close();
                toAdd.setId(findId("DataSet"));
                storeDataPoints(toAdd.getDataPoints(), toAdd.getId());


            }
        } catch (SQLException e) {
            System.out.println("Error when adding DataSet: " + e.getLocalizedMessage());
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
            System.out.println("Error when adding DataSet: " + e.getLocalizedMessage());
        }
    }


    private void storeDataPoints(ArrayList<DataPoint> points, int setId) {
        DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
           // stmt.execute("BEGIN TRANSACTION;");
            PreparedStatement prepStmnt = connection.prepareStatement("INSERT INTO DataPoint (DateTime, HeartRate, Latitude, Longitude, " +
                    "Elevation, Speed, Active, DataSet, Distance) Values (?, ?, ?, ?, ?, ?, ?," +
                    "?, ?)");
            for (DataPoint x : points) {
                if (!checkId("DataPoint", x.getId())) {
                    prepStmnt.setString(1, dateTimeFormat.format(x.getDateTime()));
                    prepStmnt.setInt(2, x.getHeartRate());
                    prepStmnt.setDouble(3, x.getLatitude());
                    prepStmnt.setDouble(4, x.getLongitude());
                    prepStmnt.setDouble(5, x.getElevation());
                    prepStmnt.setDouble(6, x.getSpeed());
                    prepStmnt.setBoolean(7, x.isActive());
                    prepStmnt.setInt(8, setId);
                    prepStmnt.setDouble(9, x.getDistance());
                    prepStmnt.executeUpdate();
                }
            }
          //  stmt.execute("END TRANSACTION;");
            stmt.close();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            System.out.println("Error when adding DataPoint: " + e.getLocalizedMessage());
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
                pStmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Error when adding goal: " + e.getLocalizedMessage());
        }
    }


    /**
     * This method is used to update the name of an activity.
     * @param activity THe activity to be updated
     */
    public void updateActivityName(Activity activity) {
        try {
            if (checkId("Activity", activity.getId())) {
                Statement stmt = connection.createStatement();

                String query = String.format("UPDATE Activity Set Name = '%s' WHERE ID = %d", activity.getName(), activity.getId());

                stmt.executeUpdate(query);
                stmt.close();
            }
        }
        catch (SQLException e) {
            // Printing an error message
            System.out.println("Error when updating activity name: " + e.getLocalizedMessage());
        }
    }

    /**
     * This method is called when the user edits or removes a data point.
     * The entire dataset is updated as changes will propergate to other data points
     * and the overall statistics.
     * @param activity
     */
    public void updateDataSet(Activity activity) {
        DataSet dataSet = activity.getDataSet();
        try {
            if (checkId("DataSet", dataSet.getId())) {
                Statement stmt = connection.createStatement();

                String query = String.format("DELETE FROM DataSet WHERE ID = %d", dataSet.getId());
                stmt.executeUpdate(query);

                storeDataSet(dataSet, activity.getId());

//                String query = String.format("UPDATE Activity Set Name = '%s' WHERE ID = %d", name, activity.getId());
            }
        }
        catch (SQLException e) {
            // Printing an error message
            System.out.println("Error when updating data set: " + e.getLocalizedMessage());
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
                storeGoal(goal, HomeController.getCurrentUser().getId());
            }
        } catch (SQLException e) {
            // Printing an error message
            System.out.println("Error when updating goal: " + e.getLocalizedMessage());
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
            // Printing an error message
            System.out.println("Error when removing goal: " + e.getLocalizedMessage());
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
            System.out.println("Error when adding DataSet: " + e.getLocalizedMessage());
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
            // Printing an error message
            System.out.println("Error when removing goal: " + e.getLocalizedMessage());
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
        } catch (SQLException e) {
            // Showing error dialogue to user
            System.out.println(e.getLocalizedMessage());
            DialogController.displayError("Error retrieving goal from database");
        }
        return goals;
    }


    /**
     * Gets an ArrayList of Alerts from the database that are related to a passed
     * user id.stmt.executeUpdate(query);
     * @param userId The user id to find alerts for.
     * @return An ArrayList holding all the found Alerts.
     */
    public ArrayList<Alert> getAlerts(int userId) {
        ArrayList<Alert> alerts = new ArrayList<>();
        try {
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
        } catch (SQLException e) {
            System.out.println("Error getting alerts: " + e.getLocalizedMessage());
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
            // Printing an error message
            System.out.println("Error finding id: " + e.getLocalizedMessage());
        }
        // Returning the result of the search
        return inTable;
    }




}
