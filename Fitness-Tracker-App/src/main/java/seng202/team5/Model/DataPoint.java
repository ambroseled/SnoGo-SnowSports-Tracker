package seng202.team5.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * THis class is used to hold one line of information out of an uploaded csv file.
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
     * @param newDateTime
     * @param newHeartRate
     * @param newLatitude
     * @param newLongitude
     * @param newElevation
     */
    public DataPoint(Date newDateTime, int newHeartRate, double newLatitude, double newLongitude, double newElevation) {
        dateTime = newDateTime;
        heartRate = newHeartRate;
        latitude = newLatitude;
        longitude = newLongitude;
        elevation = newElevation;
    }
    public DataPoint() {}

    /**
     * This constructor is used when a DataPoint is read from the database.
     * @param id
     * @param date
     * @param newHeartRate
     * @param newLatitude
     * @param newLongitude
     * @param newElevation
     * @param speed
     * @param active
     */
    public DataPoint(int id, String date, int newHeartRate, double newLatitude, double newLongitude, double newElevation, double speed, boolean active) {
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
     * @return The Data of the DataPoint.
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
     *
     * @return
     */
    public double getLatitude() {
        return latitude;
    }


    /**
     *
     * @return
     */
    public double getLongitude() {
        return longitude;
    }


    /**
     *
     * @return
     */
    public double getElevation() {
        return elevation;
    }


    /**
     *
     * @return
     */
    public double getDistance() {
        return  distance;
    }


    /**
     *
     * @return
     */
    public double getSpeed() {
        return speed;
    }


    /**
     *
     * @return
     */
    public boolean isActive() {
        return active;
    }


    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }


    /**
     *
     * @return
     */
    public String toString() {
        return "Date time: "+dateTime+", Heart rate: "+heartRate+", Latitude: "+latitude+", Longitude: "+longitude+", Elevation: "+elevation+
                ", Distance: "+distance+", Speed: "+speed;
    }
}
