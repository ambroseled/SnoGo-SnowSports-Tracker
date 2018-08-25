package seng202.team5.Model.DataAnalysis;

import seng202.team5.Model.Activity;
import java.lang.Math;

/**
 * This class performs calculations on the raw data uploaded into the application.
 * The metrics calculated are: Speed, Distance.......
 */
public class BasicMetrics {

    private double topSpeed;
    private double distanceTraveled;
    private double[] speeds;
    private double avgHeartRate;



    private double[] cartesian(double alt, double longitude, double lat) {
        double x = alt * Math.cos(Math.toRadians(lat)) * Math.sin(Math.toRadians(longitude));
        double y = alt * Math.sin(Math.toRadians(lat));
        double z = alt * Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(longitude));
        double[] cart = {x, y, z};
        return cart;
    }

    /**
     * Calculates the distance traveled between two data points,
     * in the format (longitude, latitude, altitude).
     * @param point1 First location point.
     * @param point2 Second location point.
     * @return Double holding the distance bewtwwen teh two points.
     */
    private double oneDist(double[] point1, double[] point2) {
        double[] cart1 = cartesian(point1[2], point1[0], point1[1]);
        double[] cart2 = cartesian(point2[2], point2[0], point2[1]);
        double arg1 = Math.pow((cart2[0] - cart1[0]), 2);
        double arg2 = Math.pow((cart2[1] - cart1[1]), 2);
        double arg3 = Math.pow((cart2[2] - cart1[2]), 2);
        double distance = Math.sqrt(arg1 + arg2 + arg3);
        return distance;
    }


    /**
     * Calculates the average heart rate from a given array of heart rates.
     * @param heartRates An array holding a range of heart rates.
     * @return The average heart rate found.
     */
    private double calcAvgHeart(double[] heartRates) {
        double resting = 0;
        for (double rate : heartRates) {
            resting += rate;
        }
        resting = resting/(heartRates.length);
        return resting;
    }
}
