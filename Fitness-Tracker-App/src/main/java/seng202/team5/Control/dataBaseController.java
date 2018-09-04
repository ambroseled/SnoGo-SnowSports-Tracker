package seng202.team5.Control;

import seng202.team5.Model.User;

import java.sql.*;
import java.util.ArrayList;


public class dataBaseController {

    Connection con = null;
    Statement stmt = null;


    public dataBaseController() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:dataBase.sqlite");
            System.out.println("Connected to dataBase");
            getUsers();
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

    public void getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        try {
            Statement stmt = con.createStatement();
            ResultSet set = stmt.executeQuery("SELECT * FROM User");
            User newUser;

            while (set.next()) {
                int id = set.getInt("ID");
                String first = set.getString("FirstName");
                String last = set.getString("LastName");
                double weight = set.getDouble("Weight");
                double height = set.getDouble("Height");
                int age = set.getInt("Age");
                newUser = new User(first, last, age, height, weight);
                users.add(newUser);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        System.out.println(users.get(0).getFirstName());
    }
}
