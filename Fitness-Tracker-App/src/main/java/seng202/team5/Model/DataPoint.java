package seng202.team5.Model;

import java.util.Date;


/**
 *
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
     *
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


    /**
     *
     * @param id
     * @param newDateTime
     * @param newHeartRate
     * @param newLatitude
     * @param newLongitude
     * @param newElevation
     * @param speed
     * @param active
     */
    public DataPoint(int id, Date newDateTime, int newHeartRate, double newLatitude, double newLongitude, double newElevation, double speed, boolean active) {
        dateTime = newDateTime;
        heartRate = newHeartRate;
        latitude = newLatitude;
        longitude = newLongitude;
        elevation = newElevation;
        this.id = id;
        this.speed = speed;
        this.active = active;
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
     *
     * @param newLatitude
     */
    public void setLatitude(double newLatitude) {
        latitude = newLatitude;
    }

    /**
     *
     * @param newLongitude
     */
    public void setLongitude(double newLongitude) {
        longitude = newLongitude;
    }

    /**
     *
     * @param newElevation
     */
    public void setElevation(double newElevation) {
        elevation = newElevation;
    }


    /**
     *
     * @param newDistance
     */
    public void setDistance(double newDistance) {
        distance = newDistance;
    }


    /**
     *
     * @param newSpeed
     */
    public void setSpeed(double newSpeed) {
        speed = newSpeed;
    }


    /**
     *
     * @param newActive
     */
    public void setActive(boolean newActive) {
        active = newActive;
    }


    /**
     * \
     * @return
     */
    public Date getDateTime() {
        return dateTime;
    }


    /**
     *
     * @return
     */
    public double getHeartRate() {
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
