package seng202.team5.Model;

import java.util.Date;

public class DataPoint {

	private Date dateTime;
    private int heartRate;
    private double latitude;
    private double longitude;
    private double elevation;

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

    public String toString() {
        return "Date time: "+dateTime+", Heart rate: "+heartRate+", Latitude: "+latitude+", Longitude: "+longitude+", Elevation: "+elevation;
    }






}
