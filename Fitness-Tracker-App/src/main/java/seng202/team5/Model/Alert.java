package seng202.team5.Model;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class holds an alert that is displayed in the application
 */
public class Alert {


    private Date date;
    private String webLink;
    private String message;
    private String name;
    private int id = -1;
    private String dateString;


    /**
     * The constructor used for creating a new Alert.
     * @param dateString A string holding the date of the new Alert.
     * @param webLink The web link for the new Alert.
     * @param message The message for the new Alert.
     * @param name The name for the new Alert.
     */
    public Alert(String dateString, String webLink, String message, String name) {
        this.webLink = webLink;
        this.message = message;
        this.name = name;

        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            this.date = dateTimeFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
        this.dateString = dateString;
    }


    /**
     * The constructor used to create an Alert object after reading the information out
     * of the database.
     * @param dateString A string holding the date of the new Alert.
     * @param webLink The web link of the Alert.
     * @param message The message of the Alert.
     * @param id The id from the database of the Alert.
     * @param name The name of the Alert.
     */
    public Alert(String dateString, String webLink, String message, int id, String name) {
        this.webLink = webLink;
        this.message = message;
        this.name = name;

        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            this.date = dateTimeFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
        this.id = id;
        this.dateString = dateString;
    }


    /**
     * Gets the Date from the Alert.
     * @return The Date of the Alert.
     */
    public Date getDate() {
        return date;
    }


    /**
     * Gets the message of the Alert.
     * @return The message of the Alert.
     */
    public String getMessage() {
        return message;
    }


    /**
     * Gets the web link of the Alert.
     * @return The web link of the Alert.
     */
    public String getWebLink() {
        return webLink;
    }


    /**
     * Gets the name of the Alert.
     * @return The name of the Alert.
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the dateString of the Alert.
     * @return A string holding the value of the date of the Alert.
     */
    public String getDateString() {
        return dateString;
    }


    /**
     * Gets the id of the Alert.
     * @return The id of the Alert.
     */
    public int getId() {
        return id;
    }
}
