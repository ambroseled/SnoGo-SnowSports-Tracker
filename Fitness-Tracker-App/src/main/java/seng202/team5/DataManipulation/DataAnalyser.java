package seng202.team5.DataManipulation;



import seng202.team5.Model.*;
import java.util.ArrayList;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;


/**
 * This class performs analytics on the data uploaded into the application by the user.
 * The results of these calculations are then added to the DataSet and DataPoints of the
 * passed Activity. The analysis and calculations that are performed will not produced
 * expected results for a lot of data as they have been designed to specifically handle
 * skiing or snowboarding data.
 */
public class DataAnalyser {


    private User currentUser;

    int HEART_RATE_STEADY = 5;

    Double STATIONARY_AVERAGE_SPEED = 0.8; //estimating 0.8 ms^-1 qualifies as resting.

    /**
     * This method sets the current user that is used for the analysis.
     * @param user The user
     */
    public void setUser(User user) {
        currentUser = user;
    }


    /**
     * Performs analysis on a passed activity.
     * @param activity The activity to analyse.
     */
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
        dataSet.setAvgSpeed(calcAvgSpeed(dataSet));
        dataSet.setCaloriesBurned(calcCalBurned(dataSet, currentUser.getWeight()));
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
     * calculating the change in vertical distance over 5 dataPoints. If this change
     * is negative then it is assumed the user is on a lift or hiking hence "Inactive"
     * is returned. If the distance change is less than 1 it is also assumed that the user is
     * inactive either being in a cafe or standing still so "Inactive is also returned.
     * If neither of these conditions are meet then it is assumed the user is active and
     * hence "active" is returned.
     * @param index An index pointing to the DataPoint to check the activity of.
     * @param dataPoints An arrayList of all the DataPoints in the activity.
     * @return A string holding if the passed DataPoint is active or not.
     */
    private String checkInactive(int index, ArrayList<DataPoint> dataPoints) {
        // Getting the location information out of the dataPoint to check
        double startAlt = dataPoints.get(index).getElevation();

        int len = dataPoints.size();
        int endIndex = getEndIndex(index, dataPoints);
        System.out.print("index = ");
        System.out.println(index);
        System.out.print("end index = ");
        System.out.println(endIndex);

        double endAlt = dataPoints.get(endIndex).getElevation();

        double timeDifferece = getTimeDifference(index, endIndex, dataPoints);

        double movement = calculateMovement(index, endIndex, dataPoints);

        double distance = oneDist(dataPoints.get(index).getLatitude(),
                                    dataPoints.get(index).getLongitude(),
                                    dataPoints.get(endIndex).getLatitude(),
                                    dataPoints.get(endIndex).getLongitude());

        /* double averageSpeed = oneSpeed(dataPoints.get(index).getDistance(),
                                dataPoints.get(endIndex).getDistance(),
                                dataPoints.get(index).getDateTime().getTime(),
                                dataPoints.get(endIndex).getDateTime().getTime()); */

        double averageSpeed = distance/timeDifferece;

        System.out.println(dataPoints.get(index).getDistance());
        System.out.println(dataPoints.get(endIndex).getDistance());

        if (endIndex == index) {
            if (dataPoints.get(index - 1).isActive()) {
                return "Active";
            } else {
                return "Inactive";
            }
        }

        // Checking if the activity is active or not
        System.out.println(averageSpeed);
        if (averageSpeed < STATIONARY_AVERAGE_SPEED) {
            return "Inactive";
        } else {
            if ((startAlt - endAlt) < 0) {
                return "Inactive";
            }
            return "Active";
        }
/*

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
            double[] end = new double[] {endLong, endLat, endAlt};


            // Getting the altitude change over the two points
            double movement = oneDist(startLat, startLong, endLat, endLong);
            double condition;
            if (flag) {
                condition = 0.2 * (dataPoints.size() - index);
            } else{
                condition = 1;
            }*/
    }


    /**
     * Calculates the distance traveled between two passed polar points
     * @param lat1 The latitude of the first point
     * @param long1 The longitude of the first point
     * @param lat2 The latitude of the second point
     * @param long2 The longitude of the second point
     * @return A double holding the distance between the two points
     */
    public double oneDist(double lat1, double long1, double lat2, double long2) {
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
        if (time == 0) {
            // The time change is zero so the speed is zero
            System.out.println("time is zero");
            return 0;
        } else if (distance == 0) {
            // The distance change is zero so the speed is zero
            System.out.println("distance is zero");
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
                vertical += abs(oneAlt(dataPoints.get(i).getElevation(), previous));
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
     * @param weight The weight of the user.
     * @return The amount of calories burned.
     */
    private double calcCalBurned(DataSet dataSet, double weight) {
        // Defining calorie constant for skiing
        double MET = 7.0;
        // Getting the calories burned per minute
        double perMin = (MET * 3.5 * weight) / 200;
        // Getting the total time of the dataSet in minutes
        double startTime = dataSet.getDataPoints().get(0).getDateTime().getTime();
        double endTime = dataSet.getDataPoints().get(dataSet.getDataPoints().size() - 1).getDateTime().getTime();
        double timeMilli = endTime - startTime;
        double timeMin = (timeMilli / 1000.0) / 60.0;
        // Returning the result
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
                time += dataPoints.get(i).getDateTime().getTime() - dataPoints.get(i - 1).getDateTime().getTime();
            }
        }
        return roundNum(time);
    }


    /**
     * Calculates the average speed of the user over their active DataPoints
     * @param dataSet The DaraSet to find the average speed for.
     * @return The found average speed.
     */
    private double calcAvgSpeed(DataSet dataSet) {
        double avg = 0.0;
        int count = 0;
        // Looping over all dataPoints to find average
        for (DataPoint x : dataSet.getDataPoints()) {
            if (x.isActive()) {
                count++;
                avg += x.getSpeed();
            }
        }
        return roundNum(avg / count);
    }




    private int getEndIndex(int startIndex, ArrayList<DataPoint> dataPoints) {
        for (int i = startIndex; i < dataPoints.size(); i++) {
            if ((dataPoints.get(i).getDateTime().getTime() - dataPoints.get(startIndex).getDateTime().getTime()) >= 1000 * 30) {
                return i;
            }
        }
        return dataPoints.size()-1;
    }

    /**
     * Sets the current user to passed user.
     * @param user The new current user.
     */
    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public Alert checkCardiovascularMortality(Activity activity) {

        int index = 0;
        ArrayList<DataPoint> dataPoints = activity.getDataSet().getDataPoints();
        for (DataPoint dataPoint : dataPoints) {
            if (checkResting(index, dataPoints) && dataPoint.getHeartRate() > 83) {
                return new Alert(activity.getDate(), "Risk of cardiovascular mortality", "Heart risk warning");
            }
            index++;
        }
        return null;
    }


    public Alert checkBradycardia(Activity activity){
        for (DataPoint dataPoint : activity.getDataSet().getDataPoints()) {
            if (currentUser.getAge() >= 65 && dataPoint.getHeartRate() < 50) {
                return new Alert(activity.getDate(), "Risk of Bradycardia", "Heart risk warning");
            }
        }
        return null;
    }

    public Alert checkTachycardia(Activity activity) {

        int index = 0;
        Alert alert = new Alert(activity.getDate(), "Risk of Tachycardia", "Heart risk warning");

        ArrayList<DataPoint> dataPoints = activity.getDataSet().getDataPoints();
        for (DataPoint dataPoint : dataPoints) {

            int heartRate = dataPoint.getHeartRate();
            if (checkResting(index, dataPoints)) {
                if (currentUser.getAge() < 2) {
                    if (heartRate > 151) {
                        return alert;
                    }
                } else if (currentUser.getAge() < 4) {
                    if (heartRate > 137) {
                        return alert;
                    }
                } else if (currentUser.getAge() < 7) {
                    if (heartRate > 133) {
                        return alert;
                    }
                } else if (currentUser.getAge() < 11) {
                    if (heartRate > 130) {
                        return alert;
                    }
                } else if (currentUser.getAge() < 15) {
                    if (heartRate > 119) {
                        return alert;
                    }
                } else {
                    if (heartRate > 100) {
                        return alert;
                    }
                }
            }
            index++;
        }

        return null;


    }

    /*
    private void checkHeartRateDecreaseProblem(Activity activity) {
        ArrayList<DataPoint> dataPoints = activity.getDataSet().getDataPoints();
        for (int i = 0; i < dataPoints.size(); i++) {
            for (int j = i + 1; j < dataPoints.size(); j++) {
                DataPoint start = dataPoints.get(i);

                // checks that they're not moving.
                if (calculateMovement(i, j, dataPoints) < 0.2 * (j - i)) {
                    Long timeDifference = (dataPoints.get(j).getDateTime().getTime() -
                            dataPoints.get(i).getDateTime().getTime()) * 1000 * 60;

                    //checks time difference is greater than one minute.
                    if (timeDifference >= 1) {
                        int heartRateDifference = dataPoints.get(j).getHeartRate() -
                                dataPoints.get(i).getHeartRate();
                        if (heartRateDifference < 12 * timeDifference) {
                            //add alert
                        }
                    }
                } else {
                    i = j;
                    break;
                }
            }
        }
    } */


    private boolean checkResting(int index, ArrayList<DataPoint> dataPoints) {
        int endIndex = getEndIndex(index, dataPoints);
        double endAlt = dataPoints.get(endIndex).getElevation();
        double startAlt = dataPoints.get(index).getElevation();

        if (!dataPoints.get(index).isActive() && (startAlt >= endAlt) && checkHeartRateSteady(index, dataPoints)) {
                return true;
        } else {
            return false;
        }
    }

    public boolean checkHeartRateSteady(int index, ArrayList<DataPoint> dataPoints) {
        int endIndex = getEndIndex(index, dataPoints);
        if (abs(dataPoints.get(index).getHeartRate() - dataPoints.get(endIndex).getHeartRate()) < HEART_RATE_STEADY) {
            return true;
        }
        else {
            return false;
        }
    }


    private double calculateMovement(int index, int endIndex, ArrayList<DataPoint> dataPoints){
        // Getting the location information out of the dataPoint to check
        double startLat = dataPoints.get(index).getLatitude();
        double startLong = dataPoints.get(index).getLongitude();

        // Getting the location information out of the dataPoint 60 seconds on
        double endLat = dataPoints.get(endIndex).getLatitude();
        double endLong = dataPoints.get(endIndex).getLongitude();

        // Getting the distance change over the two points
        double movement = oneDist(startLat, startLong, endLat, endLong);
        return movement;

    }


    private double getTimeDifference(int index, int endIndex, ArrayList<DataPoint> dataPoints) {
        return (dataPoints.get(endIndex).getDateTime().getTime() - dataPoints.get(index).getDateTime().getTime()) / 1000;
    }

}
