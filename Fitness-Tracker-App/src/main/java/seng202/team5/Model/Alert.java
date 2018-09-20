package seng202.team5.Model;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class holds the alerts that are displayed in the application.
 */
public class
Alert {


    private Date date;
    private String message;
    private String type;
    private int id = -1;
    private String dateString;


    /**
     * The constructor used for creating a new Alert.
     * @param dateString A string holding the date of the new Alert.
     * @param message The message for the new Alert.
     * @param type The name for the new Alert.
     */
    public Alert(String dateString, String message, String type) {
        this.message = message;
        this.type = type;

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
     * @param message The message of the Alert.
     * @param id The id from the database of the Alert.
     * @param type The name of the Alert.
     */
    public Alert(String dateString, String message, int id, String type) {
        this.message = message;
        this.type = type;

        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            this.date = dateTimeFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
        this.id = id;
        this.dateString = dateString;
    }


    public Date getDate() {
        return date;
    }


    public String getMessage() {
        return message;
    }


    public String getType() {
        return type;
    }


    public String getDateString() {
        return dateString;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }
}
