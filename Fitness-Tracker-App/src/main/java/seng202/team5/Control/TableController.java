package seng202.team5.Control;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import seng202.team5.DataManipulation.*;
import seng202.team5.Model.*;
import seng202.team5.Model.Alert;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class handles the controls for the data view tab of the application.
 * It handles the display of raw data as well as the loading of files.
 */
public class TableController {

    @FXML
    private Accordion accordion;
    private ArrayList<Activity> activities;
    // Getting database controller and current user
    private DataBaseController db = HomeController.getDb();
    @FXML
    private GraphsController statsController;
    @FXML
    private AlertController alertsController;
    @FXML
    private MapController mapsController;
    @FXML
    private HomeController homeController;


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
     * This method displays the users current activities in the application.
     */
    public void viewData() {
        if (accordion.getPanes().size() != HomeController.getCurrentUser().getActivities().size()) {
            ArrayList<Activity> inputActivities = HomeController.getCurrentUser().getActivities();
            setActivities(inputActivities);
            initialise();
        }
    }



    @FXML
    /**
     * Called by the loadfile button, this method displays the users current
     * activities in the application.
     */
    public void viewData(String filePath) {

        DataUpload uploader = new DataUpload();
        uploader.uploadData(filePath);


        setActivities(db.getActivities(HomeController.getCurrentUser().getId()));

        CheckGoals.markGoals(HomeController.getCurrentUser(), HomeController.getDb(), uploader.getNewActvities());
        Alert countAlert = AlertHandler.activityAlert(HomeController.getCurrentUser());
        if (countAlert != null) {
        db.storeAlert(countAlert, HomeController.getCurrentUser().getId());
        HomeController.getCurrentUser().addAlert(countAlert);
        }

        initialise();
        //TODO Implement proper

      //  homeController.updateTabs();
       // statsController.resetData();
        statsController.setOverallStats();


    }


    @FXML
    /**
     * Called by a press of the fileLoad button. This method enables the user
     * to select and upload a csv file into the application.
     */
    public void loadFile() {
        if (HomeController.getCurrentUser() != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load CSV File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV", "*.csv")
            );
            File f = fileChooser.showOpenDialog(null);
            try {
                viewData(f.getAbsolutePath());
            } catch (Exception e) {
                //ErrorController.displayError("File loading error");
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
        if (HomeController.getCurrentUser() != null) {
            if (table.getItems().size() != HomeController.getCurrentUser().getActivities().size()) {
                activities = db.getActivities(HomeController.getCurrentUser().getId());
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
                table.setEditable(true);
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


    /**
     * Called by a press of the export file button. This method exports the selected
     * activity to a csv file in the users home directory.
     */
    public void exportActivity() {
        String title = accordion.getExpandedPane().getText();
        Activity selectedAct = null;
        for (Activity activity : db.getActivities(HomeController.getCurrentUser().getId())) {
            String name = activity.getName();
            Date startDateTime = activity.getDataSet().getDateTime(0);
            Date endDateTime = activity.getDataSet().getDateTime(activity.getDataSet().getDataPoints().size() - 1);
            String dropdownText = (name + ", " + startDateTime + " - " + endDateTime);
            if (title.equals(dropdownText)) {
                selectedAct = activity;
                break;
            }
        }
        if (selectedAct != null) {
            ArrayList<Activity> activities = new ArrayList<>();
            activities.add(selectedAct);
            String filename = makeFilename(selectedAct.getName());
            boolean status = DataExporter.exportData(activities, filename);
            if (status) {
                ErrorController.displaymessage("File exported as " + filename + ".csv");
            } else {
                ErrorController.displayError("File export failed");
            }
        }
    }


    /**
     * Used by the exportActivity method. This method turns an activity name into
     * a filename.
     * @param actName The activity name.
     * @return The corresponding filename.
     */
    private String makeFilename(String actName) {
        String[] words = actName.split(" ");
        String filename = "";
        for (int i = 0; i < words.length; i++) {
            filename += words[i];
        }
        if (filename.equals("")) {
            return "snoGoExportedData.csv";
        } else {
            return filename;
        }
    }
}
