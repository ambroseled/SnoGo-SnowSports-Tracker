package seng202.team5.Data;


import seng202.team5.Data.DataAnalyser;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

/**
 *
 */
public class InputDataParser {

    private DataAnalyser analyser = new DataAnalyser();
    private DataValidator validator = new DataValidator();


    //As user is used in the calculations
    private static User currentUser;


    /**
     *
     * @param filePath
     * @return
     */
    private ArrayList<String> readFile(String filePath) {
        ArrayList<String> lines = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;
            while((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    /**
     * The data read in readFile() is further processed into separate activities
     * creating data points from each line and adding them to the activity they are under
     * for each activity.
     * @param lines a list of lines returned from the readFile method
     * @return An array list activities
     */
    private ArrayList<Activity> createActivitiesFromLines(ArrayList<String> lines) {
    	ArrayList<Activity> activities = new ArrayList<Activity>();
    	
    	boolean activityFound = false;
    	
    	for (String line : lines) {
    		String[] lineValues = line.split(",");
    		
    		
    		if (lineValues[0].equals("#start")) {
    			activityFound = true;
    			Activity activity = new Activity(lineValues[1]);
    			activities.add(activity);
    		}
    		else {
    			if (activityFound) {
	    			DataPoint dataPoint = getDataPointFromLine(lineValues);
	    			activities.get(activities.size() -1).getDataSet().addDataPoint(dataPoint);
    			}
    		}
    		
    	}
    	return activities;
    }
    
    private DataPoint getDataPointFromLine(String[] lineValues) {
    	DataPoint dataPoint = new DataPoint();
    	
		try {//DATE
			DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			Date dateTime = dateTimeFormat.parse(lineValues[0] + " " + lineValues[1]);
			dataPoint.setDateTime(dateTime);
		}
		catch (Exception e) {
			Date dateTime = null;
			dataPoint.setDateTime(dateTime);
		}
		
		try {//HEART RATE
			int heartRate = Integer.parseInt(lineValues[2]);
			dataPoint.setHeartRate(heartRate);
		}
		catch (Exception e) {
			int heartRate = 0;
			dataPoint.setHeartRate(heartRate);
		}
		
		try {//LATITIDE
			double latitude = Double.parseDouble(lineValues[3]);
			dataPoint.setLatitude(latitude);
		}
		catch (Exception e) {
			double latitude = 91;
			dataPoint.setLatitude(latitude);
		}
		
		try {//LONGITUDE
			double longitude = Double.parseDouble(lineValues[4]);
			dataPoint.setLongitude(longitude);
		}
		catch (Exception e) {
			double longitude = 181;
			dataPoint.setLongitude(longitude);
		}
		
		try {//ELEVATION
			double elevation = Double.parseDouble(lineValues[5]);
			dataPoint.setElevation(elevation);
		}
		catch (Exception e) {
			double elevation = 8851;
			dataPoint.setElevation(elevation);
		}
			
    	return dataPoint;
    }


    /**
     *
     * @param fileName
     * @return
     */
	public ArrayList<Activity> parseCSVToActivities(String fileName) {
		ArrayList<String> lines = readFile(fileName);
		ArrayList<Activity> activities = createActivitiesFromLines(lines);
		for (Activity activity : activities) {
			validator.validateActivity(activity);
            analyser.analyseActivity(activity);
		}
		return activities;
	}
	
//	public static void main(String[] args) {
//		InputDataParser inputDataParser = new InputDataParser();
//		ArrayList<Activity> activities = inputDataParser.parseCSVToActivities("testData2.csv");
//	
//		System.out.println(activities);
//	}


	public static void setCurrentUser(User user) {
        currentUser = user;
    }

}