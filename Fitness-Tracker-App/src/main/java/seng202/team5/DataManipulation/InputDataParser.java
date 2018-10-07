package seng202.team5.DataManipulation;


import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;


/**
 * The class reads a csv file into a set of Activity objects.
 */
public class InputDataParser {

	/**
	 * This method reads the lines of a csv file.
	 * @param filePath the path of the chosen file to be read
	 * @return An list of lines read from teh given file
	 */
	private ArrayList<String> readFile(String filePath) {
		ArrayList<String> lines = new ArrayList<String>();

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

			try {
				if (lineValues[0].equals("#start")) {
					activityFound = true;
					try {
						Activity activity = new Activity(lineValues[1]);
						activities.add(activity);
					}
					catch (Exception e) {
						Activity activity = new Activity("");
						activities.add(activity);
					}
				} else {
					if (activityFound) {
						DataPoint dataPoint = getDataPointFromLine(lineValues);
						activities.get(activities.size() - 1).getDataSet().addDataPoint(dataPoint);
					}
				}
			}
			catch (Exception e) {
				//Just in case an empty line of the csv file is ',,,,,' instead of just empty
				//Only happens when you manually delete lines in e.g. excel.
			}
		}
		return activities;
	}


	/**
	 * This is a helper method to the createActivitiesFromLines method. It deals with parsing one
	 * line of the csv into a dataPoint object.
	 * @param lineValues an array of values parsed from one line of the csv file
	 * @return An array list activities
	 */
	private DataPoint getDataPointFromLine(String[] lineValues) {
		DataPoint dataPoint = new DataPoint();

		try {//DATE
			DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
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
	 * This is the method which can be called from other classes. It puts the method which reads the csv and the
	 * method which parses this into DataPoints and Activities. This method also calls the validate and analysis
	 * procedures on each activity before returning the list of activities.
	 * @param filePath A String of the filepath.
	 * @return An ArrayList of activities parsed from the
	 */
	public ArrayList<Activity> parseCSVToActivities(String filePath) {
		ArrayList<String> lines = readFile(filePath);
		ArrayList<Activity> activities = createActivitiesFromLines(lines);

		return activities;
	}


}