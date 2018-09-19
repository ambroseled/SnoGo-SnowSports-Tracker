package seng202.team5.DataManipulation;



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
 * This class provides the functionality to parse csv files into activities.
 */
public class InputDataParser {

    private DataAnalyser analyser = new DataAnalyser();


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
        ArrayList<Activity> activities = new ArrayList<>();

        for (String line : lines) {
            String[] lineValues = line.split(",");

            if (lineValues[0].equals("#start")) {
                Activity activity = new Activity(lineValues[1]);
                activities.add(activity);
            }
            else {
                try {
                    DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
                    Date dateTime = dateTimeFormat.parse(lineValues[0] + " " + lineValues[1]);

                    int heartRate = Integer.parseInt(lineValues[2]);
                    double latitude = Double.parseDouble(lineValues[3]);
                    double longitude = Double.parseDouble(lineValues[4]);
                    double elevation = Double.parseDouble(lineValues[5]);

                    DataPoint dataPoint = new DataPoint(dateTime, heartRate, latitude, longitude, elevation);
                    activities.get(activities.size() -1).getDataSet().addDataPoint(dataPoint);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        //updateTable(activities);
        return activities;
    }


    /**
     * This method reads a csv file and parses the file into an ArrayList of
     * activities.
     * @param fileName The path of the file to be read.
     * @return An ArrayList holding all activities read from teh passed file.
     */
	public ArrayList<Activity> parseCSVToActivities(String fileName) {
		ArrayList<String> lines = readFile(fileName);
		ArrayList<Activity> activities = createActivitiesFromLines(lines);
		for (Activity activity : activities) {
            analyser.analyseActivity(activity);
		}
		return activities;
	}
	/*
	private void updateTable(ArrayList<Activity> activities) {
		//TableController tableController = new TableController(activities);
	} */


	public static void setCurrentUser(User user) {
        currentUser = user;
    }


}