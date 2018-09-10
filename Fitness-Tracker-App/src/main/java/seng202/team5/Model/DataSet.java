package seng202.team5.Model;

import java.util.*;


/**
 *
 */
public class DataSet {

    private ArrayList<DataPoint> dataPoints;

    //Where the thing calculated by analysis are stored
    private double topSpeed;
    private double totalDistance;
    private double verticalDistance;
    private int avgHeartRate;
    private int id;
    // Need to add a slopeTime variable

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
    public DataSet(int id, double topSpeed, double totalDistance, double verticalDistance, int avgHeartRate, ArrayList<DataPoint> dataPoints) {
        this.id = id;
        this.topSpeed = topSpeed;
        this.totalDistance = totalDistance;
        this.verticalDistance = verticalDistance;
        this.avgHeartRate = avgHeartRate;
        this.dataPoints = dataPoints;
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
     * @param index
     * @return
     */
    public Date getDateTime(int index) {
        DataPoint point = dataPoints.get(index);
        return point.getDateTime();
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
        verticalDistance = verticalDistance;
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

}
