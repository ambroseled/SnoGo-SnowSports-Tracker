package seng202.team5.Control;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import seng202.team5.Data.DataBaseController;
import seng202.team5.Data.InputDataParser;
import seng202.team5.Model.*;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;



//TODO: Fix scroll pane, Needs to scroll for expanded activity





/**
 * This class hanldes the controls for the data view tab of the application. It
 * handles the display of raw data as well as the loading of files.
 */
public class TableController {

    @FXML
    private Accordion accordion;
    @FXML
    private Button viewButton;
    @FXML
    private Button resetButton;

    private ArrayList<Activity> activities;

    private DataBaseController db = AppController.getDb();
    private User currentUser = AppController.getCurrentUser();


    /**
     *
     */
    private void initialise() {
        int numActivities = activities.size();
        for (int i = 0; i < (numActivities); i += 1) {
            addActivityPanels(i);
        }
    }

    @FXML
    /**
     * Called by a press of the viewButton, this method displays the users current
     * activities in the application.
     */
    public void viewData() {
        resetButton.setVisible(true);
        resetButton.setDisable(false);
        viewButton.setVisible(false);
        viewButton.setDisable(true);
        ArrayList<Activity> inputActivities = db.getActivities(currentUser.getId());
        setActivities(inputActivities);

        initialise();
    }

    @FXML
    /**
     * Called by a press of the viewButton, this method displays the users current
     * activities in the application.
     */
    public void viewData(String filePath) {
        resetButton.setVisible(true);
        resetButton.setDisable(false);
        viewButton.setVisible(false);
        viewButton.setDisable(true);
        InputDataParser inputDataParser = new InputDataParser();
        ArrayList<Activity> inputActivities = inputDataParser.parseCSVToActivities(filePath);
        setActivities(inputActivities);

        initialise();
    }

    @FXML
    /**
     * Called by a press of the resetButton, this method clears and then refills the display
     * of the users activities.
     */
    public void resetData() {
        accordion.getPanes().clear();

        ArrayList<Activity> inputActivities = db.getActivities(currentUser.getId());
        setActivities(inputActivities);

        initialise();
    }

    @FXML

    public void loadFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load CSV File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
        File f = fileChooser.showOpenDialog(null);
        viewData(f.getAbsolutePath());

    }
    /**
     * Populates the given table with the data from a specific activity
     * @param table an empty TableView object
     * @param index refers to an activity in the list "activities"
     */
    private void createTable(TableView table, int index) {
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