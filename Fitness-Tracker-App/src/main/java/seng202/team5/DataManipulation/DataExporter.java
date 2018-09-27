package seng202.team5.DataManipulation;



import com.opencsv.CSVWriter;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class is used to export data from the application to csv files.
 */
public class DataExporter {


    /**
     * Writes a given list of activities to a new csv file to the users home
     * directory.
     * @param activities The ArrayList of activities to be written.
     * @param filename The name of the file to be made.
     */
    public static boolean exportData(ArrayList<Activity> activities, String filename) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(System.getProperty("user.home") + "/" + filename
                    + ".csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
            for (Activity activity : activities) {
                exportActivity(activity, writer);
            }
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    /**
     * Writes a passed activity to a file using a passed CSVWriter.
     * @param activity The activity to be written.
     * @param writer The CSVWriter to write the activity with.
     */
    private static void exportActivity(Activity activity, CSVWriter writer) {
        writer.writeNext(String.format("#start, %s,,,,", activity.getName()).split(","));
        writer.writeNext(activity.getDataSet().toLine().split(","));
        for (DataPoint x : activity.getDataSet().getDataPoints()) {
            writer.writeNext(x.toLine().split(","));
        }
    }


}
