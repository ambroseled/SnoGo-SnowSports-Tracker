package seng202.team5.Model;

import java.util.ArrayList;

public class DataSet {

    private ArrayList<DataPoint> dataPoints = new ArrayList<>();
    
    //Where the thing calculated by analysis are stored
    private double topSpeed;
    private double totalDistance;

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

}
