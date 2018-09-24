package seng202.team5.Control;


import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import seng202.team5.DataManipulation.DataAnalyser;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.DataManipulation.DataValidator;
import seng202.team5.DataManipulation.InputDataParser;
import seng202.team5.Model.*;
import seng202.team5.Model.Alert;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.image.ImageView;

/**
 * This class handles the controls for the data view tab of the application.
 * It handles the display of raw data as well as the loading of files.
 */
public class TableController {

    @FXML
    private Accordion accordion;
    private ArrayList<Activity> activities;
    // Getting database controller and current user
    private DataBaseController db = App.getDb();


    /**
     *
     */
    private void initialise() {
        accordion.getPanes().clear();
        int numActivities = activities.size();
        for (int i = 0; i < (numActivities); i += 1) {
            addActivityPanels(i);
        }

    }


    @FXML
    /**
     * Called by mouse movement on the anchor pane, this method displays the users current
     * activities in the application.
     */
    public void viewData() {

        if (accordion.getPanes().size() != App.getCurrentUser().getActivities().size()) {
            ArrayList<Activity> inputActivities = App.getCurrentUser().getActivities();
            setActivities(inputActivities);
            initialise();
        }
    }


    @FXML
    /**
     * Called by teh loadfile button, this method displays the users current
     * activities in the application.
     */
    public void viewData(String filePath) {
        InputDataParser inputDataParser = new InputDataParser();
        ArrayList<Activity> inputActivities = inputDataParser.parseCSVToActivities(filePath);

        if (inputActivities.size() == 0) {
            ErrorController.displayError("File has no activities or is missing '#start' tag.\n" +
                    "Please check file");
        }

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

        DataAnalyser analyser = new DataAnalyser();
        analyser.setCurrentUser(App.getCurrentUser());
        for (Activity activity : inputActivities) {
           if (activity.getDataSet().getDataPoints().size() > 0) {
                analyser.analyseActivity(activity);
           }
        }

        // Tests if activity is equal to any others
        for (int i = 0; i < inputActivities.size(); i++) {
            boolean addActivity = true;
            for (Activity activity : App.getCurrentUser().getActivities()) {
                if (inputActivities.get(i).getDataSet().equals(activity.getDataSet())) {
                    String message = "Activity '" + inputActivities.get(i).getName()+"'";
                    message += " is a duplicate of existing activity\n";
                    message += "It will not be added";
                    ErrorController.displayError(message);
                    addActivity = false;
                }
            }
            if (inputActivities.get(i).getDataSet().getDataPoints().size() == 0) {
                String message = "Activity '" + inputActivities.get(i).getName()+"'";
                message += " is empty.\n";
                message += "It will not be added";
                ErrorController.displayError(message);
                addActivity = false;
            }
            if (addActivity) {
                db.storeActivity(inputActivities.get(i), App.getCurrentUser().getId());
                App.getCurrentUser().addActivity(inputActivities.get(i));
            }
        }

          setActivities(db.getActivities(App.getCurrentUser().getId()));

          CheckGoals.markGoals(App.getCurrentUser(), App.getDb(), inputActivities);
          Alert countAlert = AlertHandler.activityAlert(App.getCurrentUser());
          if (countAlert != null) {
            db.storeAlert(countAlert, App.getCurrentUser().getId());
            App.getCurrentUser().addAlert(countAlert);
          }

          initialise();
    }


    @FXML
    /**
     * Called by a press of the fileLoad button. This method enables the user
     * to select and upload a csv file into the application.
     */
    public void loadFile() {
        if (App.getCurrentUser() != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load CSV File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV", "*.csv")
            );
            File f = fileChooser.showOpenDialog(null);
            try {
                viewData(f.getAbsolutePath());
            } catch (Exception e) {
                ErrorController.displayError("File loading error");
            }
        }
    }


    /**
     * Populates the given table with the data from a specific activity
     * @param table an empty TableView object
     * @param index refers to an activity in the list "activities"
     */
    private void createTable(TableView table, int index) {


        table.getItems().clear();
        if (App.getCurrentUser() != null) {
            if (table.getItems().size() != App.getCurrentUser().getActivities().size()) {
                activities = db.getActivities(App.getCurrentUser().getId());
                // date and time column
                TableColumn<DataPoint, Date> dateTimeCol = new TableColumn("Date and Time");
                dateTimeCol.setCellValueFactory(new PropertyValueFactory("dateTime"));

                //heart rate column
                TableColumn<DataPoint, Integer> heartRateCol = new TableColumn("Heart Rate");
                heartRateCol.setCellValueFactory(new PropertyValueFactory("heartRate"));

                //latitude column
                TableColumn<DataPoint, Double> latitudeCol = new TableColumn("Latitude");
                latitudeCol.setCellValueFactory(new PropertyValueFactory("latitude"));

                //longitude column
                TableColumn<DataPoint, Double> longitudeCol = new TableColumn("Longitude");
                longitudeCol.setCellValueFactory(new PropertyValueFactory("longitude"));

                //elevation column
                TableColumn<DataPoint, Double> elevationCol = new TableColumn("Elevation");
                elevationCol.setCellValueFactory(new PropertyValueFactory("elevation"));

                //distance column
                TableColumn<DataPoint, Double> distanceCol = new TableColumn("Distance");
                distanceCol.setCellValueFactory(new PropertyValueFactory("distance"));

                //speed column
                TableColumn<DataPoint, Double> speedCol = new TableColumn("Speed");
                speedCol.setCellValueFactory(new PropertyValueFactory("speed"));
                table.getColumns().addAll(dateTimeCol, heartRateCol, latitudeCol, longitudeCol, elevationCol, distanceCol, speedCol);
                table.setItems(getDataPointsList(index));
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            }
        }
    }


    /**
     * Retrieves all dataPoint objects from a specific activity
     * @param index refers to an activity in the list "activities"
     * @return an ObservableList containing all dataPoints retrieved
     */
    public ObservableList<DataPoint> getDataPointsList(int index) {
        ObservableList<DataPoint> dataPointsList = FXCollections.observableArrayList();

        DataSet dataSet = activities.get(index).getDataSet();
        dataPointsList.addAll(dataSet.getDataPoints());
        return dataPointsList;
    }


    /**
     * Sets the text in the TitledPane object given, in the format:
     * [activity name], [date time of first dataPoint] - [date time of last dataPoint]
     * @param titledPane an empty TitledPane object
     * @param index refers to an activity in the list "activities"
     */
    public void setDropdown(TitledPane titledPane, int index) {
        String dropdownText;
        Activity activity = activities.get(index);
        String name = activity.getName();
        Date startDateTime = activity.getDataSet().getDateTime(0);
        Date endDateTime = activity.getDataSet().getDateTime(activity.getDataSet().getDataPoints().size() - 1);
        dropdownText = (name + ", " + startDateTime + " - " + endDateTime);
        titledPane.setText(dropdownText);
        titledPane.setMinHeight(320);

    }


    /**
     * Creates a new TitledPane for the activity referred to by "index"
     * TitledPane will house a TableView object
     * Adds new TitledPane object to accordion
     * @param index refers to an activity in the list "activities"
     */
    public void addActivityPanels(int index) {
        TitledPane titledPane = new TitledPane();
        setDropdown(titledPane, index);
        titledPane.setAnimated(true);
        titledPane.setCollapsible(true);

        //Add table, remove as fxml
        TableView<DataPoint> table = new TableView();
        createTable(table, index);

        titledPane.setContent(table);
        accordion.getPanes().add(titledPane);
    }


    /**
     * Sets the activities ArrayList to the passed ArrayList of activities.
     * @param inputActivities The new ArrayList of activities.
     */
    private void setActivities(ArrayList<Activity> inputActivities) {
        activities = inputActivities;
    }
}
