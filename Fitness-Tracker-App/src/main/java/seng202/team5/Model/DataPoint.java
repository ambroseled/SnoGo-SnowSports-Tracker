package seng202.team5.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class is used to hold one line of information out of an uploaded csv file.
 */
public class DataPoint {

    private Date dateTime;
    private int id = -1;
    private int heartRate;
    private double latitude;
    private double longitude;
    private double elevation;
    private double distance;
    private double speed;
    private boolean active;


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
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
            this.dateTime = dateTimeFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getLocalizedMessage());
        }
        this.distance = distance;
    }


    /**
     * Set the value of the dateTime for the DataPoint.
     * @param newDateTime The new Date object value.
     */
    public void setDateTime(Date newDateTime) {
        dateTime = newDateTime;
    }


    /**
     * Sets the value for the heart rate of the DataPoint.
     * @param newHeartRate The new heart rate value.
     */
    public void setHeartRate(int newHeartRate) {
        heartRate = newHeartRate;
    }

    /**
     * Sets the value of the latitude of the DataPoint.
     * @param newLatitude THe new latitude value.
     */
    public void setLatitude(double newLatitude) {
        latitude = newLatitude;
    }

    /**
     * Sets the value of the longitude of the DataPoint.
     * @param newLongitude The new longitude value.
     */
    public void setLongitude(double newLongitude) {
        longitude = newLongitude;
    }

    /**
     * Sets the value of the elevation of the DatPoint.
     * @param newElevation THe new elevation value.
     */
    public void setElevation(double newElevation) {
        elevation = newElevation;
    }


    /**
     * Sets the value of the distance of the DataPoint.
     * @param newDistance The new distance value.
     */
    public void setDistance(double newDistance) {
        distance = newDistance;
    }


    /**
     * Sets the value of the speed of the DataPoint.
     * @param newSpeed The new speed value.
     */
    public void setSpeed(double newSpeed) {
        speed = newSpeed;
    }


    /**
     * Sets if the DataPoint is active or not.
     * @param newActive Flag holding if the DataPoint is active or not.
     */
    public void setActive(boolean newActive) {
        active = newActive;
    }


    /**
     * Gets the Date of the DataPoint
     * @return The DataManipulation of the DataPoint.
     */
    public Date getDateTime() {
        return dateTime;
    }


    /**
     * Gets the value of the heart rate of the DataPoint.
     * @return The heart rate of the DataPoint.
     */
    public int getHeartRate() {
        return heartRate;
    }


    /**
     * Gets the latitude value of the DataPoint.
     * @return The latitude value.
     */
    public double getLatitude() {
        return latitude;
    }


    /**
     * Gets the longitude value of the DataPoint.
     * @return The longitude value.
     */
    public double getLongitude() {
        return longitude;
    }


    /**
     * Gets the elevation value of the DataPoint.
     * @return The elevation value.
     */
    public double getElevation() {
        return elevation;
    }


    /**
     * Gets the distance of the DataPoint.
     * @return The distance value.
     */
    public double getDistance() {
        return  distance;
    }


    /**
     * Gets the speed of the DataPoint.
     * @return The speed value.
     */
    public double getSpeed() {
        return speed;
    }


    /**
     * Gets if the DataPoint is active.
     * @return Flag if the point is active.
     */
    public boolean isActive() {
        return active;
    }


    /**
     * Gets the database id of the DataPoint.
     * @return The database id.
     */
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets a string representation of the DataPoint.
     * @return The string of the DataPoint.
     */
    public String toString() {
        return "Date time: "+dateTime+", Heart rate: "+heartRate+", Latitude: "+latitude+", Longitude: "+longitude+", Elevation: "+elevation+
                ", Distance: "+distance+", Speed: "+speed;
    }
}
