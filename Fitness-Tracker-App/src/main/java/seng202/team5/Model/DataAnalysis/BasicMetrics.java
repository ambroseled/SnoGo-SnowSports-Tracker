package seng202.team5.Model.DataAnalysis;

import seng202.team5.Model.Activity;
import java.lang.Math;

/**
 * This is a class of static methods that are used to
 * assist the DataAnalyser class. In the analysis of raw data.x
 */
public class BasicMetrics {


    /**
     * Calculating the cartesian product of a passed location point.
     * @param alt The altitude
     * @param longitude The longitude value
     * @param lat The latitude value.
     * @return A double array holding the cartesian product.
     */
    private static double[] cartesian(double alt, double longitude, double lat) {
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
     * @return A double holding the distance bewtwwen teh two points.
     */
    private static double oneDist(double[] point1, double[] point2) {
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
     * @return A double holding the current speed.
     */
    private static double oneSpeed(double dist1, double dist2, double time1, double time2) {
        double distance = dist2 - dist1;
        double time = time2 - time1;
        double speed = distance / time;
        return speed;
    }


    /**
     * Calculates the average heart rate from a given array of heart rates.
     * @param heartRates An array holding a range of heart rates.
     * @return A double holding the average heart rate found.
     */
    public static double calcAvgHeart(double[] heartRates) {
        double avg = 0;
        for (double rate : heartRates) {
            avg += rate;
        }
        avg = avg/(heartRates.length);
        return avg;
    }


    /**
     * Calculates the difference in altitude between two altitude values.
     * @param alt1 The first altitude value
     * @param alt2 The second altitude value
     * @return A double the difference between the two passed values.
     */
    private static double oneAlt(double alt1, double alt2) {
        double diff = alt2 - alt1;
        if (diff < 0) {
            return diff * -1;
        } else {
            return diff;
        }
    }


    /**
     * Calculates the total vertical distance traveled over a given list
     * of altitude values.
     * @param altitudes A list holding all the altitude values to be considered
     * @return The vertical distance traveled.
     */
    public static double calcVertical(double[] altitudes) {
        double vertical = 0;
        double previous = altitudes[0];
        for(int i = 1; i < altitudes.length; i ++) {
            vertical += oneAlt(previous, altitudes[i]);
            previous = altitudes[i];
        }
        return vertical;
    }
}
