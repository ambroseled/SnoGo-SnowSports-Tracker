package seng202.team5.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class InputDataParser {

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
    			int heartRate = Integer.parseInt(lineValues[2]);
    			double latitude = Double.parseDouble(lineValues[3]);
    			double longitude = Double.parseDouble(lineValues[4]);
    			double elevation = Double.parseDouble(lineValues[5]);
    			
    			DataPoint dataPoint = new DataPoint(heartRate, latitude, longitude, elevation);
    			
    			activities.get(activities.size() -1).getDataSet().addDataPoint(dataPoint);
    		}
    		
    	}
    	//System.out.println(activities);
    	return activities;
    }
	
	public ArrayList<Activity> parseCSVToActivities(String fileName) {
		ArrayList<String> lines = readFile(fileName);
		ArrayList<Activity> activities = createActivitiesFromLines(lines);
		
		return activities;
	}
	
	public static void main(String[] args) {
        InputDataParser test = new InputDataParser();
        
        ArrayList<String> lines = test.readFile("testData.csv");
        test.createActivitiesFromLines(lines);
        
    }

		
	






}