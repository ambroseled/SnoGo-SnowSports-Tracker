package seng202.team5.Model;

import java.util.*;


/**
 * This class is used to hold the information about one uploaded activity.
 */
public class DataSet {

    // The list of data points the data set is for
    private ArrayList<DataPoint> dataPoints;

    // The variables for the extra data stored with each data set
    private double topSpeed;
    private double totalDistance;
    private double verticalDistance;
    private int avgHeartRate;
    private int id = -1;
    private double caloriesBurned = 0;
    private double slopeTime;
    private double avgSpeed;

    /**
     * Constructor used while parsing data.
     */
    public DataSet() {
        dataPoints = new ArrayList<>();
    }


    /**
     * Constructor used when reading a data set from teh database
     * @param id The database id of the data set
     * @param topSpeed The top speed of the data set
     * @param totalDistance The total distance of the data set
     * @param verticalDistance The vertical distance of the data set
     * @param avgHeartRate The average heart rate of the data set
     * @param dataPoints The DataPoints of the data set
     */
    public DataSet(int id, double topSpeed, double totalDistance, double verticalDistance, int avgHeartRate,
                   ArrayList<DataPoint> dataPoints, double caloriesBurned, double slopeTime, double avgSpeed) {
        this.id = id;
        this.topSpeed = topSpeed;
        this.totalDistance = totalDistance;
        this.verticalDistance = verticalDistance;
        this.avgHeartRate = avgHeartRate;
        this.dataPoints = dataPoints;
        this.caloriesBurned = caloriesBurned;
        this.slopeTime = slopeTime;
        this.avgSpeed = avgSpeed;
    }


    /**
     * Adds a passed DataPoint to the DataSet
     * @param dataPoint The DataPoint to add
     */
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


    public ArrayList<DataPoint> getDataPoints() {
        return dataPoints;
    }


    public double getTopSpeed() {
        return topSpeed;
    }


    public double getTotalDistance() {
        return totalDistance;
    }


    public int getAvgHeartRate() {
        return avgHeartRate;
    }


    public double getVerticalDistance() {
        return verticalDistance;
    }


    public double getCaloriesBurned() {
        return caloriesBurned;
    }


    public Date getDateTime(int index) {
        DataPoint point = dataPoints.get(index);
        return point.getDateTime();
    }


    public double getAvgSpeed() {
        return avgSpeed;
    }


    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }


    public int getId() {
        return id;
    }


    public double getSlopeTime() {
        return slopeTime;
    }


    public void setTotalDistance(double distance) {
        totalDistance = distance;
    }


    public void setVerticalDistance(double vetical) {
        verticalDistance = vetical;
    }


    public void setAvgHeartRate(int rate) {
        avgHeartRate = rate;
    }


    public void setTopSpeed(double speed) {
        topSpeed = speed;
    }


    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }


    public void setSlopeTime(double slopeTime) {
        this.slopeTime = slopeTime;
    }


    public void setId(int id) {
        this.id = id;
    }



    public String toLine() {
        return String.format("%f, %f, %f, %d, %f, %f", topSpeed, totalDistance, verticalDistance, avgHeartRate,
                caloriesBurned, avgSpeed);
    }



    /**
     * This method is used for comparing two data sets to see if they have the exact same, set of data points
     * The equals method compares every data point in the data set to see if they are equal.
     * The method returns false as soon as two points are not equal, so most of the time it take O(1), only when
     * the two sets are equal will it take O(n).
     * @param otherDataSet the data set which is being compared
     * @return true, if the sets are equal, false if not.
     */
    public boolean equals(DataSet otherDataSet) {

        if (this.dataPoints.size() == otherDataSet.getDataPoints().size()) {
            for (int i = 0; i < this.dataPoints.size(); i++) {
                if (!(this.dataPoints.get(i).equals(otherDataSet.getDataPoints().get(i)))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
