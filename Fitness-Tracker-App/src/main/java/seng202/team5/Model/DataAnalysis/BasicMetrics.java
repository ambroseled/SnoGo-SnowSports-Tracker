package seng202.team5.Model.DataAnalysis;

import seng202.team5.Model.Activity;
import java.lang.Math;

/**
 * This class performs calculations on the raw data uploaded into the application.
 * The metrics calculated are: Speed, Distance.......
 */
public class BasicMetrics {


    /**
     * Calculating the cartesian product of a passed location point.
     * @param alt The altitude
     * @param longitude The longitude value
     * @param lat The latitude value.
     * @return A double array holding the cartesian product.
     */
    public static double[] cartesian(double alt, double longitude, double lat) {
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
    public static double oneDist(double[] point1, double[] point2) {
        double[] cart1 = cartesian(point1[2], point1[0], point1[1]);
        double[] cart2 = cartesian(point2[2], point2[0], point2[1]);
        double arg1 = Math.pow((cart2[0] - cart1[0]), 2);
        double arg2 = Math.pow((cart2[1] - cart1[1]), 2);
        double arg3 = Math.pow((cart2[2] - cart1[2]), 2);
        double distance = Math.sqrt(arg1 + arg2 + arg3);
        return distance;
    }

    // Will need to implement after activity is implemented

    /**
     * Calculates the total distance traveled at each data point in the passed activity
     * and appends this distance value to the data point.
     * @param activity The activity to perform calculations on
     * @return **Add later**
     */
    public static void appendDistance(Activity activity) {

    }


    /**
     * Takes the distance and time values of two data points and calculates the current
     * speed.
     * @param dist1 The current distance of the first data point.
     * @param dist2 The current distance of the second data point.
     * @param time1 The current time of the first data point.
     * @param time2 The current time of the second data point.
     * @return Double holding the current speed.
     */
    public static double oneSpeed(double dist1, double dist2, double time1, double time2) {
        double distance = dist2 - dist1;
        double time = time2 - time1;
        double speed = distance / time;
        return speed;
    }


    /**
     * Calculates the average heart rate from a given array of heart rates.
     * @param heartRates An array holding a range of heart rates.
     * @return The average heart rate found.
     */
    public static double calcAvgHeart(double[] heartRates) {
        double resting = 0;
        for (double rate : heartRates) {
            resting += rate;
        }
        resting = resting/(heartRates.length);
        return resting;
    }
}
