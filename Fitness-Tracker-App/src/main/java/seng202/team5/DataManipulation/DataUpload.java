package seng202.team5.DataManipulation;

import seng202.team5.Control.HomeController;
import seng202.team5.Control.DialogController;
import seng202.team5.Model.Activity;
import seng202.team5.Model.Alert;
import java.util.ArrayList;


/**
 * This class controls the process of uploading data, it is used by the DataController
 * class and uses the InputDataParser, DataValidation, and DataAnalyser.
 */
public class DataUpload {

    private DataBaseController db = HomeController.getDb();

    private ArrayList<Activity> newActivities = new ArrayList<Activity>();

    public ArrayList<Activity> getNewActvities() { return newActivities; }


    /**
     * This method is called when the user presses the 'Load File' button.
     * It controls the process of parsing, validating, and analysing the new data
     * @param filePath the file path of the file that the user wishes to upload data from
     */
    public void uploadData(String filePath) {
        InputDataParser inputDataParser = new InputDataParser();
        ArrayList<Activity> inputActivities = inputDataParser.parseCSVToActivities(filePath);

        checkEmptyFile(inputActivities);

        validateActivities(inputActivities);

        analyseActivities(inputActivities);

        checkDuplicates(inputActivities);

        checkGoalsUpdateAlerts(newActivities);
    }


    /**
     * This method is called when the user presses the 'Load File' button
     * and they have the 'Add data to existing activity' checkbox checked.
     * It controls the process of appending data to and existing activity.
     * @param filePath the file path of the data file
     * @param targetActivity the activity which to append the data to
     */
    public void appendNewData(String filePath, Activity targetActivity) {
        InputDataParser inputDataParser = new InputDataParser();
        ArrayList<Activity> inputActivities = inputDataParser.parseCSVToActivities(filePath);

        checkEmptyFile(inputActivities);
        validateActivities(inputActivities);

        appendDataSets(targetActivity, inputActivities);

        ArrayList<Activity> activity = new ArrayList<Activity>();
        activity.add(targetActivity);

        analyseActivities(activity);
    }


    /**
     * This method is used by the appendNewData method it loops through the target activity
     * checking that the data was not a subset of the data already there, if not is will
     * add the data
     * @param targetActivity
     * @param inputActivities activities parsed from file
     */
    private void appendDataSets(Activity targetActivity, ArrayList<Activity> inputActivities) {

        for (Activity inputActivity : inputActivities) {
            if (!targetActivity.getDataSet().contains(inputActivity.getDataSet())) {
                targetActivity.getDataSet().addDataPoints(inputActivity.getDataSet());
            }
            else {
                DialogController.displayError("The data from '"+inputActivity.getName()+"' " +
                        "is already contained in activity. \n It will not be appended.");
            }
        }
    }


    /**
     * This method tells the user if no activities were parsed from the activity
     * @param inputActivities activities parsed from file
     */
    private void checkEmptyFile(ArrayList<Activity> inputActivities) {
        if (inputActivities.size() == 0) {
            DialogController.displayError("File has no activities or is missing '#start' tag(s).\n" +
                    "Please check file");
        }
    }


    /**
     * This method controls the data validation process using the DataValidation class
     * It displays to the user the changes made
     * @param inputActivities
     */
    private void validateActivities(ArrayList<Activity> inputActivities) {

        for (Activity activity : inputActivities) {
            DataValidator validator = new DataValidator();
            validator.validateActivity(activity);

            if (validator.getPointsDeleted() > 0 || validator.getDataValidated() > 0) {
                String message = "Activity: " + activity.getName() + "\n";
                message +=
                        "Points deleted: "
                                + validator.getPointsDeleted()
                                + "/"
                                + validator.getInitialDataSetSize()
                                + "\n";
                message += "Values fixed: " + validator.getDataValidated();
                DialogController.displayError(message);
            }
        }
    }


    /**
     * This method controls the data analysis process using the DataAnalyser class
     */
    private void analyseActivities(ArrayList<Activity> inputActivities) {

        DataAnalyser analyser = new DataAnalyser();
        analyser.setCurrentUser(HomeController.getCurrentUser());
        for (Activity activity : inputActivities) {
            if (activity.getDataSet().getDataPoints().size() > 0) {
                analyser.analyseActivity(activity);
            }
        }
    }


    /**
     * This method controls checking if each activity already exists for the user
     * @param inputActivities
     */
    private void checkDuplicates(ArrayList<Activity> inputActivities) {
        // Tests if activity is equal to any others
        for (int i = 0; i < inputActivities.size(); i++) {
            boolean notDuplicate = true;
            for (Activity activity : db.getActivities(HomeController.getCurrentUser().getId())) {
                if (inputActivities.get(i).getDataSet().equals(activity.getDataSet())) {
                    String message = "Activity '" + inputActivities.get(i).getName()+"'";
                    message += " is a duplicate of existing activity\n";
                    message += "It will not be added";
                    DialogController.displayError(message);
                    notDuplicate = false;
                }
            }
            if (notDuplicate) {
                addActivity(inputActivities.get(i));
            }
        }
    }


    /**
     * This method add the activity to the user.
     *  It stops empty activities from being added
     * @param activity
     */
    private void addActivity(Activity activity) {

        if (activity.getDataSet().getDataPoints().size() == 0) {
            String message = "Activity '" + activity.getName()+"'";
            message += " is empty.\n";
            message += "It will not be added";
            DialogController.displayError(message);
        }
        else {
            newActivities.add(activity);
            db.storeActivity(activity, HomeController.getCurrentUser().getId());
            HomeController.getCurrentUser().addActivity(activity);
        }
    }


    /**
     * This method uses the CheckGoals class to check if the new activites
     * cause any goals to be achieved
     * @param activties
     */
    private void checkGoalsUpdateAlerts(ArrayList<Activity> activties) {
        CheckGoals.markGoals(HomeController.getCurrentUser(), HomeController.getDb(), activties);
        Alert countAlert = CheckAlerts.activityAlert(HomeController.getCurrentUser());
        if (countAlert != null) {
            db.storeAlert(countAlert, HomeController.getCurrentUser().getId());
            HomeController.getCurrentUser().addAlert(countAlert);
        }
    }


}
