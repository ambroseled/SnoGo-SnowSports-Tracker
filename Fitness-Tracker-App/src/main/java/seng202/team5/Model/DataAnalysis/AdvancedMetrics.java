package seng202.team5.Model.DataAnalysis;

import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;

import java.util.ArrayList;

public class AdvancedMetrics {

    /**
     *
     * @param activity
     */
    public void analyseActivity(Activity activity) {
        DataSet dataSet = activity.getDataSet();
        ArrayList<DataPoint> dataPoints = dataSet.getDataPoints();
        for (int i = 0; i < dataPoints.size(); i++) {
            String active = checkInactive(i, dataPoints);
            if (active == "Chair") {
                // Loop until no alt increase
                DataPoint previous = dataPoints.get(i++);
                double altChange;
                for (; i < dataPoints.size(); i++) {
                    altChange = oneAlt(previous.getElevation(), dataPoints.get(i).getElevation());

                }

            } else if (active == "Inactive") {
                // Loop until Movement

            }
            // Calculate stuff

        }
    }


    /**
     * Calculating the cartesian product of a passed location point.
     * @param alt The altitude
     * @param longitude The longitude value
     * @param lat The latitude value.
     * @return A double array holding the cartesian product.
     */
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
     * @return A double holding the distance bewtwwen teh two points.
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
     * Calculates the difference in altitude between two altitude values.
     * @param alt1 The first altitude value in meters.
     * @param alt2 The second altitude value in meters.
     * @return A double the difference between the two passed values.
     */
    private double oneAlt(double alt1, double alt2) {
        double diff = alt2 - alt1;
        return diff;
    }

    /**
     *
     * @param index
     * @param dataPoints
     * @return
     */
    private String checkInactive(int index, ArrayList<DataPoint> dataPoints) {
        double startLat = dataPoints.get(index).getLatitude();
        double startLong = dataPoints.get(index).getLongitude();
        double startAlt = dataPoints.get(index).getElevation();
        double[] start = {startLong, startLat, startAlt};

        double endLat = dataPoints.get(index + 60).getLatitude();
        double endLong = dataPoints.get(index + 60).getLongitude();
        double endAlt = dataPoints.get(index + 60).getElevation();
        double[] end = {endLong, endLat, endAlt};


        double vertChange = oneAlt(startAlt, endAlt);
        if (vertChange < 0) {
            // Moving upwards i.e. on a lift
            return "Chair";
        } else if (vertChange < 1) {
            // Inactive i.e. in cafe
            return "Inactive";
        } else {
            return "Active";
        }
    }
}
