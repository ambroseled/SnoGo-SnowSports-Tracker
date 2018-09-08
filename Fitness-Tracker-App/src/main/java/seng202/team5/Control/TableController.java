package seng202.team5.Control;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;
import seng202.team5.Model.InputDataParser;


import java.util.ArrayList;
import java.util.Date;


public class TableController {

    @FXML
    private Accordion accordion;
    @FXML
    private Button viewButton;
    @FXML
    private Button resetButton;

    private ArrayList<Activity> activities;


    private void initialise() {
        int numActivities = activities.size();
        for (int i = 0; i < (numActivities - 1); i += 1) {
            addActivityPanels(i);
        }

    }

    @FXML
    public void viewData() {
        viewButton.setDisable(true);
        resetButton.setDisable(false);
        InputDataParser inputDataParser = new InputDataParser();
        ArrayList<Activity> inputActivities = inputDataParser.parseCSVToActivities("testData.csv");
        setActivities(inputActivities);

        initialise();
    }

    @FXML
    public void resetData() {
        accordion.getPanes().clear();

        InputDataParser inputDataParser = new InputDataParser();
        ArrayList<Activity> inputActivities = inputDataParser.parseCSVToActivities("testData.csv");
        setActivities(inputActivities);

        initialise();
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


    private void setActivities(ArrayList<Activity> inputActivities) {
        activities = inputActivities;
    }
}
