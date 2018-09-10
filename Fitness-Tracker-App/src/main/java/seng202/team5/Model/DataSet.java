package seng202.team5.Model;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.*;

public class DataSet {

    private ArrayList<DataPoint> dataPoints = new ArrayList<>();
    
    //Where the thing calculated by analysis are stored
    private double topSpeed;
    private double totalDistance;
    private double verticalDistance;
    private double avgHeartRate;
    // Need to add a slopeTime variable

    public void addDataPoint(DataPoint dataPoint) {
        dataPoints.add(dataPoint);
    }


    public String toString() {
    	String dataPointsString = "";
    	for (DataPoint dataPoint : dataPoints) {
    		dataPointsString += dataPoint + "\n";
    	}
    	return dataPointsString;
    }

    // Getters
    public ArrayList<DataPoint> getDataPoints() {
        return dataPoints;
    }
    public double getTopSpeed() {
        return topSpeed;
    }
    public double getTotalDistance() {
        return totalDistance;
    }
    public double getAvgHeartRate() {
        return avgHeartRate;
    }
    public double getVerticalDistance() {
        return verticalDistance;
    }
    public Date getDateTime(int index) {
        return dataPoints.get(index).getDateTime();
    }


    // Setters

    public void setTotalDistance(double distance) {
        totalDistance = distance;
    }
    public void setVerticalDistance(double vertical) {
        verticalDistance = vertical;
    }
    public void setAvgHeartRate(double rate) {
        avgHeartRate = rate;
    }
    public void setTopSpeed(double speed) {
        topSpeed = speed;
    }




}
