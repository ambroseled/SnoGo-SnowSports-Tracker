package seng202.team5.Model;

import java.util.*;


/**
 * This class is used to hold the information about one uploaded activity.
 */
public class DataSet {

    private ArrayList<DataPoint> dataPoints;

    //Where the thing calculated by analysis are stored
    private double topSpeed;
    private double totalDistance;
    private double verticalDistance;
    private int avgHeartRate;
    private int id = -1;
    private double caloriesBurned = 0;
    private double slopeTime;
    private double avgSpeed;

    /**
     *
     */
    public DataSet() {
        dataPoints = new ArrayList<>();
    }


    /**
     *
     * @param id
     * @param topSpeed
     * @param totalDistance
     * @param verticalDistance
     * @param avgHeartRate
     * @param dataPoints
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
     *
     * @param dataPoint
     */
    public void addDataPoint(DataPoint dataPoint) {
        dataPoints.add(dataPoint);
    }


    /**
     *
     * @return
     */
    public String toString() {
        String dataPointsString = "";
        for (DataPoint dataPoint : dataPoints) {
            dataPointsString += dataPoint + "\n";
        }
        return dataPointsString;
    }


    /**
     *
     * @return
     */
    public ArrayList<DataPoint> getDataPoints() {
        return dataPoints;
    }


    /**
     *
     * @return
     */
    public double getTopSpeed() {
        return topSpeed;
    }


    /**
     *
     * @return
     */
    public double getTotalDistance() {
        return totalDistance;
    }


    /**
     *
     * @return
     */
    public int getAvgHeartRate() {
        return avgHeartRate;
    }


    /**
     *
     * @return
     */
    public double getVerticalDistance() {
        return verticalDistance;
    }


    /**
     *
     * @return
     */
    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    /**
     *
     * @param index
     * @return
     */
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


    public void setDataPoints(ArrayList<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
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
    public double getSlopeTime() {
        return slopeTime;
    }

    /**
     *
     * @param distance
     */
    public void setTotalDistance(double distance) {
        totalDistance = distance;
    }


    /**
     *
     * @param vetical
     */
    public void setVerticalDistance(double vetical) {
        verticalDistance = vetical;
    }


    /**
     *
     * @param rate
     */
    public void setAvgHeartRate(int rate) {
        avgHeartRate = rate;
    }


    /**
     *
     * @param speed
     */
    public void setTopSpeed(double speed) {
        topSpeed = speed;
    }


    /**
     *
     * @param caloriesBurned
     */
    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }


    /**
     *
     * @param slopeTime
     */
    public void setSlopeTime(double slopeTime) {
        this.slopeTime = slopeTime;
    }


    public void setId(int id) {
        this.id = id;
    }
}
