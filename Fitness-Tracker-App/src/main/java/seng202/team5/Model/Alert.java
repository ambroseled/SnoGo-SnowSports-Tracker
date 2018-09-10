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
     *
     * @param dateString
     * @param webLink
     * @param message
     * @param name
     */
    public Alert(String dateString, String webLink, String message, String name) {
        this.webLink = webLink;
        this.message = message;
        this.name = name;

        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/mm/yyyy");
            this.date = dateTimeFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
        this.dateString = dateString;
    }


    /**
     *
     * @param dateString
     * @param webLink
     * @param message
     * @param id
     * @param name
     */
    public Alert(String dateString, String webLink, String message, int id, String name) {
        this.webLink = webLink;
        this.message = message;
        this.name = name;

        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/mm/yyyy");
            this.date = dateTimeFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
        this.id = id;
        this.dateString = dateString;
    }


    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }


    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }


    /**
     *
     * @return
     */
    public String getWebLink() {
        return webLink;
    }


    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }


    /**
     *
     * @return
     */
    public String getDateString() {
        return dateString;
    }


    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }
}
