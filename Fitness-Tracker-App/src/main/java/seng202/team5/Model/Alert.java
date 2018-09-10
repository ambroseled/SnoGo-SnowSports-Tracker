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


    private Date dateTime;
    private String webLink;
    private String message;
    private String name;
    private int id = -1;


    /**
     *
     * @param date
     * @param webLink
     * @param message
     * @param name
     */
    public Alert(String date, String webLink, String message, String name) {
        this.webLink = webLink;
        this.message = message;
        this.name = name;

        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
            this.dateTime = dateTimeFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }

    }


    /**
     *
     * @param date
     * @param webLink
     * @param message
     * @param id
     * @param name
     */
    public Alert(Date date, String webLink, String message, int id, String name) {
        this.dateTime = dateTime;
        this.webLink = webLink;
        this.message = message;
        this.id = id;
        this.name = name;
    }


    /**
     *
     * @param date
     * @param webLink
     * @param message
     * @param id
     * @param name
     */
    public Alert(String date, String webLink, String message, int id, String name) {
        this.webLink = webLink;
        this.message = message;
        this.name = name;

        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
            this.dateTime = dateTimeFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
        this.id = id;
    }


    /**
     *
     * @return
     */
    public Date getDateTime() {
        return dateTime;
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
}
