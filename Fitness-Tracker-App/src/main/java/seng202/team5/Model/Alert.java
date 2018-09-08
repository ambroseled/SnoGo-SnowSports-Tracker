package seng202.team5.Model;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alert {


    private Date dateTime;
    private String webLink;
    private String message;
    private String name;
    private int id = -1;

    public Alert(String date, String webLink, String message, String name) {
        this.webLink = webLink;
        this.message = message;
        this.name = name;

        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/mm/yyyy");
            this.dateTime = dateTimeFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }

    }


    public Alert(Date dateTime, String webLink, String message, int id, String name) {
        this.dateTime = dateTime;
        this.webLink = webLink;
        this.message = message;
        this.id = id;
        this.name = name;
    }


    public Date getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }

    public String getWebLink() {
        return webLink;
    }

    public String getName() {
        return name;
    }
}
