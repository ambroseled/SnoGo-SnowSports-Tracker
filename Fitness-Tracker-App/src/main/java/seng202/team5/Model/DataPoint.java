package seng202.team5.Model;

import javafx.scene.control.Button;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class is used to hold one line of information out of an uploaded csv file.
 */
public class DataPoint {


    // Variables for data stored in each data point
    private Date dateTime;
    private int id = -1;
    private int heartRate;
    private double latitude;
    private double longitude;
    private double elevation;
    private double distance;
    private double speed;
    private boolean active;

    private DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss");


    /**
     * The constructor used to make a new DataPoint when the data is being parsed from a csv file.
     * @param newDateTime The data and time of the DataPoint.
     * @param newHeartRate The heart rate of the DataPoint.
     * @param newLatitude The latitude of the DataPoint.
     * @param newLongitude The longitude of the DataPoint.
     * @param newElevation The elevation of the DataPoint.
     */
    public DataPoint(Date newDateTime, int newHeartRate, double newLatitude, double newLongitude,
                     double newElevation) {
        dateTime = newDateTime;
        heartRate = newHeartRate;
        latitude = newLatitude;
        longitude = newLongitude;
        elevation = newElevation;
    }
    public DataPoint() {}

    /**
     * This constructor is used when a DataPoint is read from the database.
     * @param id The database id of the DataPoint.
     * @param date The date of the database.
     * @param newHeartRate The heart rate of the DataPoint.
     * @param newLatitude The latitude of the DataPoint.
     * @param newLongitude The longitude of the DataPoint.
     * @param newElevation The elevation of the DataPoint.
     * @param distance The distance of the DataPoint.
     * @param speed The speed of the DataPoint.
     * @param active Flag showing if the DataPoint is active.
     */
    public DataPoint(int id, String date, int newHeartRate, double newLatitude, double newLongitude,
                     double newElevation, double distance, double speed, boolean active) {
        heartRate = newHeartRate;
        latitude = newLatitude;
        longitude = newLongitude;
        elevation = newElevation;
        this.id = id;
        this.speed = speed;
        this.active = active;

        try {
            this.dateTime = dateTimeFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
        this.distance = distance;
    }


    public void setDateTime(Date newDateTime) {
        dateTime = newDateTime;
    }


    public void setHeartRate(int newHeartRate) {
        heartRate = newHeartRate;
    }


    public void setLatitude(double newLatitude) {
        latitude = newLatitude;
    }


    public void setLongitude(double newLongitude) {
        longitude = newLongitude;
    }


    public void setElevation(double newElevation) {
        elevation = newElevation;
    }


    public void setDistance(double newDistance) {
        distance = newDistance;
    }


    public void setSpeed(double newSpeed) {
        speed = newSpeed;
    }


    public void setActive(boolean newActive) {
        active = newActive;
    }


    public Date getDateTime() {
        return dateTime;
    }


    public int getHeartRate() {
        return heartRate;
    }


    public double getLatitude() {
        return latitude;
    }


    public double getLongitude() {
        return longitude;
    }


    public double getElevation() {
        return elevation;
    }


    public double getDistance() {
        return  distance;
    }


    public double getSpeed() {
        return speed;
    }


    public boolean isActive() {
        return active;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getFormattedDate() {
        return dateTimeFormat.format(dateTime);
    }



    public String toString() {
        return "Date time: "+dateTime+", Heart rate: "+heartRate+", Latitude: "+latitude+", Longitude: "+longitude+", Elevation: "+elevation+
                ", Distance: "+distance+", Speed: "+speed;
    }


    public String toLine() {
        String[] date = dateTimeFormat.format(dateTime).split(" ");
        return String.format("%s, %s, %d, %f, %f, %f, %f, %f", date[0], date[1], heartRate, latitude,
                longitude, elevation, distance, speed);
    }


    /**
     * Tests two data points for equality. Equal if each of the given data's are equal.
     * @param otherDataPoint the point which is being compared to.
     * @return true if they are equal, false if not.
     */
    public boolean equals(DataPoint otherDataPoint) {

        if (!this.dateTime.equals(otherDataPoint.getDateTime())) {
            return false;
        }
        if (!(this.heartRate == otherDataPoint.getHeartRate())) {
            return false;
        }
        if (!(this.latitude == otherDataPoint.getLatitude())) {
            return false;
        }
        if (!(this.longitude == otherDataPoint.getLongitude())) {
            return false;
        }
        if (!(this.elevation == otherDataPoint.getElevation())) {
            return false;
        }
        //TODO //print each to see the problem
        return true;
    }
}
