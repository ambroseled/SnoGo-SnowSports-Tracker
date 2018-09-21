package seng202.team5.DataManipulation;

import org.sqlite.SQLiteConfig;
import seng202.team5.Control.ErrorController;

import java.sql.*;

public class dataBase2 {


    private static String dbString = "jdbc:sqlite:" + System.getProperty("user.home") + "/SnoGo.db";
    private static final String driver = "org.sqlite.JDBC";
    private static Connection connection = null;

    private static SQLiteConfig configureSQl() {
        SQLiteConfig sqlConfig = new SQLiteConfig();
        sqlConfig.enforceForeignKeys(true);
        return sqlConfig;
    }


    public static void connectDB(){
        try {
            System.out.println("bean");
            Class.forName(driver);
            SQLiteConfig sqlConfig = configureSQl();
            connection = DriverManager.getConnection(dbString, sqlConfig.toProperties());
            createDatabase();
        } catch (Exception e) {
            System.out.println("beaned");
            createDatabase();
        }

    }




    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error closing connection to database");
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
                    "User INTEGER REFERENCES Activity (ID) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL" +
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
        closeConnection();
    }

}
