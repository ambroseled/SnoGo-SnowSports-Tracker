package seng202.team5.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class parsers activity data from a csv file. .......
 * This class parses activity data from a .csv file. It first reads
 * the data and creates an array list of string arrays where each string array
 * corresponds to a line of the file. The data is then further processed into an
 * array list of array lists where each array list holds data for a different activity.
 */
public class CSVParser {

    /**
     *
     * @param filePath
     * @return
     */
    private ArrayList<String[]> readFile(String filePath) {
        ArrayList<String[]> initialData = new ArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;
            while((line = br.readLine()) != null) {
                initialData.add(line.split(","));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return initialData;
    }


    /**
     * The data read in readFile() is further processed into separate array lists
     * for each activity.
     * @param initialData All activity data from file that has been read
     * @return An array list of array lists where each array list hold the activity data
     * for a different activity logged.
     */
    private ArrayList<ArrayList<String[]>> splitActivities(ArrayList<String[]> initialData) {
        ArrayList<ArrayList<String[]>> activities = new ArrayList<>();
        ArrayList<String[]> activity = new ArrayList<>();
        for (String[] line : initialData)
            if (line[0].equals("#start")) {
                if (!activity.isEmpty()) {
                    ArrayList<String[]> temp = new ArrayList<>();
                    for (String[] i : activity) temp.add(i);
                    activities.add(temp);
                }
                activity.removeAll(activity);
                activity.add(line);
            } else {
                activity.add(line);
            }
        return activities;
    }


    public static void main(String[] args) {
        CSVParser test = new CSVParser();
        ArrayList<String[]> testArray = test.readFile("testData.csv");
        ArrayList<ArrayList<String[]>> test2Array = test.splitActivities(testArray);
        for (ArrayList<String[]> i : test2Array) {
            System.out.println(i.get(0)[0] + " " + i.get(0)[1]);
        }
    }
        }