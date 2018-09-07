package seng202.team5.Model;

import java.util.Date;

public class Alert {


    private Date dateTime;
    private String webLink;
    private String message;
    private int id = -1;

    public Alert(Date dateTime, String webLink, String message) {
        this.dateTime = dateTime;
        this.webLink = webLink;
        this.message = message;
    }


    public Alert(Date dateTime, String webLink, String message, int id) {
        this.dateTime = dateTime;
        this.webLink = webLink;
        this.message = message;
        this.id = id;
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
}
