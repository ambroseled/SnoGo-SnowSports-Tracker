package seng202.team5.DataManipulation;

import seng202.team5.Control.App;
import seng202.team5.Control.ErrorController;
import seng202.team5.Model.Activity;

import java.util.ArrayList;

public class DataUpload {

    private DataBaseController db = App.getDb();

    private ArrayList<Activity> newActivities = new ArrayList<Activity>();

    public ArrayList<Activity> getNewActvities() { return newActivities; }


    public void uploadData(String filePath) {
        InputDataParser inputDataParser = new InputDataParser();
        ArrayList<Activity> inputActivities = inputDataParser.parseCSVToActivities(filePath);

        checkEmptyFile(inputActivities);

        validateActivities(inputActivities);

        analyseActivities(inputActivities);

        checkDuplicates(inputActivities);

        for (Activity activity : newActivities) {
            db.storeActivity(activity, App.getCurrentUser().getId());
            App.getCurrentUser().addActivity(activity);
        }

    }


    private void checkEmptyFile(ArrayList<Activity> inputActivities) {
        if (inputActivities.size() == 0) {
            ErrorController.displayError("File has no activities or is missing '#start' tag(s).\n" +
                    "Please check file");
        }
    }

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
                ErrorController.displayError(message);
            }
        }
    }

    private void analyseActivities(ArrayList<Activity> inputActivities) {

        DataAnalyser analyser = new DataAnalyser();
        analyser.setCurrentUser(App.getCurrentUser());
        for (Activity activity : inputActivities) {
            if (activity.getDataSet().getDataPoints().size() > 0) {
                analyser.analyseActivity(activity);
            }
        }
    }

    private void checkDuplicates(ArrayList<Activity> inputActivities) {
        // Tests if activity is equal to any others
        for (int i = 0; i < inputActivities.size(); i++) {
            boolean notDuplicate = true;
            for (Activity activity : App.getCurrentUser().getActivities()) {
                if (inputActivities.get(i).getDataSet().equals(activity.getDataSet())) {
                    String message = "Activity '" + inputActivities.get(i).getName()+"'";
                    message += " is a duplicate of existing activity\n";
                    message += "It will not be added";
                    ErrorController.displayError(message);
                    notDuplicate = false;
                }
            }
            if (notDuplicate) {
                addActivity(inputActivities.get(i));
            }
        }
    }

    private void addActivity(Activity activity) {

        if (activity.getDataSet().getDataPoints().size() == 0) {
            String message = "Activity '" + activity.getName()+"'";
            message += " is empty.\n";
            message += "It will not be added";
            ErrorController.displayError(message);
        }
        else {
            newActivities.add(activity);
        }
    }

}
