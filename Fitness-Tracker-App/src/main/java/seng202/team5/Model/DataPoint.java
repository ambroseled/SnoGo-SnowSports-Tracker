package seng202.team5.Model;

import java.util.Date;

public class DataPoint {

	private Date dateTime;
    private int heartRate;
    private double latitude;
    private double longitude;
    private double elevation;
    private double distance;
    private double speed;
    private boolean active;

    public DataPoint(Date newDateTime, int newHeartRate, double newLatitude, double newLongitude, double newElevation) {
    	dateTime = newDateTime;
        heartRate = newHeartRate;
        latitude = newLatitude;
        longitude = newLongitude;
        elevation = newElevation;
    }

    //These setter methods will come in handy when we have to implement editing point manually
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


    // Getters for the parameters of a DataPoint
    public Date getDateTime() {
        return dateTime; }
    public int getHeartRate() {
        return heartRate; }
    public double getLatitude() {
        return latitude; }
    public double getLongitude() {
        return longitude; }
    public double getElevation() {
        return elevation; }
    public double getDistance() {
        return  distance; }
    public double getSpeed() {
        return speed; }
    public boolean isActive() {
        return active;
    }

    public String toString() {
        return "Date time: "+dateTime+", Heart rate: "+heartRate+", Latitude: "+latitude+", Longitude: "+longitude+", Elevation: "+elevation+
                ", Distance: "+distance+", Speed: "+speed;
    }
}
