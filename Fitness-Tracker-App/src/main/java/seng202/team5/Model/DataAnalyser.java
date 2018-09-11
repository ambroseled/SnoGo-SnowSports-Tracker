package seng202.team5.Model;

import java.util.ArrayList;


/**
 * This class performs analytics on the data uploaded into the application by the user.
 * The results of these calculations are then added to the DataSet and DataPoints of the
 * passed Activity.
 */
public class DataAnalyser {




    /**
     * Performs analysis on a passed activity.
     * @param activity The activity to analyse.
     */

    // Add a user that is passed here once user loading is working in the app
    public void analyseActivity(Activity activity) {
        // Getting the dataSet out of the activity
        DataSet dataSet = activity.getDataSet();
        // Getting the data points out of the dataSet
        ArrayList<DataPoint> dataPoints = dataSet.getDataPoints();
        // Marking the activities as active or inactive
        markActive(dataPoints);
        // Setting the previous data point to the first point
        DataPoint previous = dataPoints.get(0);
        // Setting the speed and distance traveled of the first data point
        previous.setSpeed(0);
        previous.setDistance(0);
        // Looping over the remaining dataPoints
        for (int i = 1; i < dataPoints.size(); i++) {
            // Getting the current dataPoint
            DataPoint current = dataPoints.get(i);
            if (current.isActive()) {
                // The dataPoint is active so data is calculated
                appendDistance(current, previous);
                appendSpeed(current, previous);
            } else {
                // The dataPoint is inactive so no new data is calculated
                current.setSpeed(0);
                current.setDistance(previous.getDistance());
            }
            // Setting the previous point to the last current point
            previous = dataPoints.get(i);
        }
        // Getting information that was found in the above loop and
        // adding it to the dataSet
        dataSet.setVerticalDistance(calcVertical(dataSet));
        dataSet.setTotalDistance(previous.getDistance());
        dataSet.setAvgHeartRate(calcAvgHeart(dataSet));
        dataSet.setTopSpeed(topSpeed(dataSet));
        dataSet.setSlopeTime(slopeTime(dataSet));

        /**
         * Uncomment when user loading is working in the app
         */
        //  dataSet.setCaloriesBurned(calcCalBurned(dataSet, user.getWeight()));
    }


    /**
     * Looping over all DataPoints in a passed list and using checkInactive as
     * a helper function to mark each DataPoint as active or inactive.
     * @param dataPoints A list of the DataPoints to mark active or inactive.
     */
    private void markActive(ArrayList<DataPoint> dataPoints) {
        // Looping over all dataPoints
        for (int i = 0; i < dataPoints.size(); i++) {
            // Checking if the dataPoint is active
            String active = checkInactive(i, dataPoints);
            if (active.equals("Inactive")) {
                // Marking the dataPoint as inactive
                DataPoint previous = dataPoints.get(i++);
                previous.setActive(false);
            } else {
                // Marking the dataPoint as active
                dataPoints.get(i).setActive(true);
            }
        }
    }


    /**
     * Checks if a passed DataPoint is active or not. The check is done by
     * calculating the change in vertical distance over 1 minute. If this change
     * is negative then it is assumed the user is on a lift or hiking hence "Inactive"
     * is returned. If the change is less than 1 it is also assumed that the user is
     * inactive either being in a cafe or standing still so "Inactive is also returned.
     * If neither of these conditions are meet then it is assumed the user is active and
     * hence "active" is returned.
     * @param index An index pointing to the DataPoint to check the activity of.
     * @param dataPoints An arrayList of all the DataPoints in the activity.
     * @return A string holding if the passed DataPoint is active or not.
     */
    private String checkInactive(int index, ArrayList<DataPoint> dataPoints) {
        // Getting the location information out of the dataPoint to check
        double startLat = dataPoints.get(index).getLatitude();
        double startLong = dataPoints.get(index).getLongitude();
        double startAlt = dataPoints.get(index).getElevation();


        Boolean flag = false;
        int endIndex = 60;
        int len = dataPoints.size();
        if ((index + endIndex) > len) {
            endIndex = len - 1;
            flag = true;
        }
        if (endIndex == index) {
            if (dataPoints.get(index - 1).isActive()) {
                return "Active";
            } else {
                return "Inactive";
            }
        } else {
            // Getting the location information out of the dataPoint 60 seconds on
            double endLat = dataPoints.get(endIndex).getLatitude();
            double endLong = dataPoints.get(endIndex).getLongitude();
            double endAlt = dataPoints.get(endIndex).getElevation();


            // Getting the altitude change over the two points
            double movement = oneDist(startLat, startLong, endLat, endLong);
            double condition;
            if (flag) {
                condition = 0.2 * (dataPoints.size() - index);
            } else{
                condition = 1;
            }
            // Checking if the activity is active or not
            if (movement < condition) {
                return "Inactive";
            } else {
                if ((startAlt - endAlt) < 0) {
                    return "Inactive";
                }
                return "Active";
            }
        }
    }


    /**
     * Calculates the distance traveled between two passed polar points
     * @param lat1 The latitude of the first point
     * @param long1 The longitude of the first point
     * @param lat2 The latitude of the second point
     * @param long2 The longitude of the second point
     * @return A double holding the distance between the two points
     */
    private double oneDist(double lat1, double long1, double lat2, double long2) {
        // Getting the change in latitude and longitude
        double delLon = Math.toRadians(long2 - long1);
        double delLat = Math.toRadians(lat2 - lat1);
        // Setting the radius of the earth
        final double rad = 6371000;

        // Using the haversine formula to find the distance between the two points
        double temp = Math.pow(Math.sin(delLat / 2), 2) + Math.pow(Math.sin(delLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double temp2 = 2 * Math.asin(Math.sqrt(temp));

        // Returning the distance traveled
        return roundNum(temp2 * rad);
    }



    /**
     * Calculates the total distance traveled at each data point in the passed activity * and appends
     * this distance value to the data point.
     * @param current The data point to append a distance to.
     * @param previous The previous data point.
     */
    private void appendDistance(DataPoint current, DataPoint previous) {
        // Calculating the distance traveled
        double distance = oneDist(previous.getLatitude(), previous.getLongitude(), current.getLatitude(), current.getLongitude());
        distance += previous.getDistance();
        current.setDistance(roundNum(distance));
    }


    /**
     * Calculates the difference in altitude between two altitude values.
     * @param current The first altitude value in meters.
     * @param previous The second altitude value in meters.
     * @return A double the difference between the two passed values.
     */
    private double oneAlt(double current, double previous) {
        // Calculating the change in altitude
        double diff = current - previous;
        return roundNum(-1*diff);
    }


    /**
     * Takes the distance and time values of two data points and calculates the current
     * speed.
     * @param dist1 The current distance of the first data point in meters.
     * @param dist2 The current distance of the second data point in meters.
     * @param time1 The current time of the first data point in seconds.
     * @param time2 The current time of the second data point in seconds.
     * @return A double holding the current speed in m/s.
     */
    private double oneSpeed(double dist1, double dist2, long time1, long time2) {
        // Calculating the change in distance
        double distance = dist1 - dist2;
        // Calculating the change in time in seconds
        double time = (time1 - time2)/1000;

        //System.out.println("Distance: " + distance);
        if (time == 0) {
            // The time change is zero so the speed is zero
            return 0;
        } else if (distance == 0) {
            // The distance change is zero so the speed is zero
            return 0;
        } else {
            // The speed is above zero and will be calculated
            double speed = distance / time;
            return roundNum(speed);
        }
    }


    /**
     * Calculates the current speed at a passed DataPoint and appends the speed
     * to the data point.
     * @param current The DataPoint to calculate the speed of.
     * @param previous The previous DataPoint.
     */
    private void appendSpeed(DataPoint current, DataPoint previous) {
        // Getting the distance and time out of the two dataPoints
        double distance1 = current.getDistance();
        long time1 = current.getDateTime().getTime();
        double distance2 = previous.getDistance();
        long time2 = previous.getDateTime().getTime();
        // Calculating the speed
        double speed = oneSpeed(distance1, distance2, time1, time2);
        current.setSpeed(speed);
    }


    /**
     * Calculates the average heart rate from a given array of heart rates.
     * @param dataSet The DataSet of the activity being analysed.
     * @return A double holding the average heart rate found.
     */
    private int calcAvgHeart(DataSet dataSet) {
        // Getting all DataPoints out of the passed DataSet
        ArrayList<DataPoint> dataPoints = dataSet.getDataPoints();
        int avg = 0;
        // Looping through all DataPoints to find the average heart rate
        for (DataPoint point : dataPoints) {
            avg += point.getHeartRate();
        }
        // Calculating the average heart rate
        avg = avg/(dataPoints.size());
        return avg;
    }


    /**
     * Calculates the total vertical distance traveled over a given list
     * of altitude values.
     * @param dataSet The DataSet of the activity being analysed.
     * @return The vertical distance traveled.
     */
    private double calcVertical(DataSet dataSet) {
        // Getting all DataPoints out of the passed DataSet
        ArrayList<DataPoint> dataPoints = dataSet.getDataPoints();
        double vertical = 0;
        double previous = dataPoints.get(0).getElevation();
        // Looping over DataPoints to calculate the vertical distance traveled
        for(int i = 1; i < dataPoints.size(); i++) {
            if (dataPoints.get(i).isActive()) {
                // The DataPoint is marked as active so the vertical distance is recorded
                vertical += oneAlt(dataPoints.get(i).getElevation(), previous);
            }
            previous = dataPoints.get(i).getElevation();
        }
        return roundNum(vertical);
    }


    /**
     * Finds the top speed of a given DataSet.
     * @param dataSet The DataSet to find the top speed for.
     * @return A double holding the top speed.
     */
    private double topSpeed(DataSet dataSet) {
        // Getting all DataPoints out of the passed DataSet
        ArrayList<DataPoint> dataPoints = dataSet.getDataPoints();
        double top = 0;
        // Looping over DataPoints to find the top speed
        for(DataPoint point : dataPoints) {
            double speed = point.getSpeed();
            if (speed > top) {
                // A new top speed is found
                top = speed;
            }
        }
        return top;
    }


    /**
     * Calculates the bmi using the passed information about a user.
     * @param height The height of the user.
     * @param weight The weight of the user.
     * @return The BMI of the user.
     */
    public static double calcBMI(double height, double weight) {
        double bmi = (weight / height) / height;
        return roundNum(bmi);
    }


    /**
     * Rounds a passed double to 2 decimal places.
     * @param toRound The double to be rounded.
     * @return The rounded value.
     */
    public static double roundNum(double toRound) {
        double rounded = Math.round(toRound * 100.0);
        return rounded / 100.0;
    }


    /**
     * Calculates the calories burned by a user during a passed DataSet.
     * @param dataSet The DataSet to calculate calories burned on.
     * @param weight The wieght of the user.
     * @return The amount of calories burned.
     */
    private double calcCalBurned(DataSet dataSet, double weight) {
        double MET = 7.0;
        double perMin = (MET * 3.5 * weight) / 200;
        double startTime = dataSet.getDataPoints().get(0).getDateTime().getTime();
        double endTime = dataSet.getDataPoints().get(dataSet.getDataPoints().size() - 1).getDateTime().getTime();
        double timeMilli = endTime - startTime;
        double timeMin = (timeMilli / 1000.0) / 60.0;
        return roundNum(perMin * timeMin);
    }


    /**
     * Calculates the time the user spends on the slopes.
     * @param dataSet The DataSet to calculate slope time over.
     * @return The slope time calculated.
     */
    private double slopeTime(DataSet dataSet) {
        double time = 0.0;
        ArrayList<DataPoint> dataPoints = dataSet.getDataPoints();

        for (int i = 1; i < dataSet.getDataPoints().size(); i++) {
            if (dataPoints.get(i).isActive()) {
                time += dataPoints.get(i).getDateTime().getTime() - dataPoints.get(i).getDateTime().getTime();
            }
        }
        return time;
    }
}
