package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import seng202.team5.DataManipulation.*;
import seng202.team5.Model.*;
import seng202.team5.Model.Alert;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import static java.lang.Math.abs;


/**
 * This class is the controller for the dataRawView.fxml. It provides the functionality for
 * the user to load csv files, manually enter data, edit activities, delete activities, export
 * activities and view the raw data of activities.
 */
public class DataController {

    private ArrayList<Activity> activities;
    // Getting database controller and current user
    private DataBaseController db = HomeController.getDb();
    @FXML
    private TableView actTable;
    @FXML
    private TableColumn<Activity, String> actCol;
    @FXML
    private TableView rawDataTable;
    @FXML
    private TableColumn<DataPoint, String> dateTimeCol;
    @FXML
    private TableColumn<DataPoint, Integer> heartRateCol;
    @FXML
    private TableColumn<DataPoint, Double> latitudeCol;
    @FXML
    private TableColumn<DataPoint, Double> longitudeCol;
    @FXML
    private TableColumn<DataPoint, Double> elevationCol;
    @FXML
    private TableColumn<DataPoint, Double> speedCol;
    @FXML
    private TableColumn<DataPoint, Double> distanceCol;
    @FXML
    private TableView manualEntryTable;
    @FXML
    private CheckBox appendCheck;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button enterLineButton;
    @FXML
    private Button confirmButton;
    @FXML
    private TextField nameEntry;
    @FXML
    private TextField heartEntry;
    @FXML
    private TextField latEntry;
    @FXML
    private TextField longEntry;
    @FXML
    private TextField eleEntry;
    @FXML
    private TextField timeEntry;
    @FXML
    private DatePicker datePicker;
    @FXML
    private CheckBox nameCheck;
    @FXML
    private CheckBox dateCheck;
    @FXML
    private CheckBox timeCheck;
    @FXML
    private CheckBox heartCheck;
    @FXML
    private CheckBox latCheck;
    @FXML
    private CheckBox longCheck;
    @FXML
    private CheckBox eleCheck;
    @FXML
    private TableColumn<DataPoint, String> dateTimeColMan;
    @FXML
    private TableColumn<DataPoint, Integer> heartRateColMan;
    @FXML
    private TableColumn<DataPoint, Double> latitudeColMan;
    @FXML
    private TableColumn<DataPoint, Double> longitudeColMan;
    @FXML
    private TableColumn<DataPoint, Double> elevationColMan;
    @FXML
    private Button abortButton;

    private ObservableList<Activity> activityNames = FXCollections.observableArrayList();
    private ObservableList<DataPoint> dataPoints = FXCollections.observableArrayList();

    private DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
    private DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss");

    private DataAnalyser dataAnalyser = new DataAnalyser();


    
    /**
     * This method is called when the user presses the 'Load File'
     * button, once they have selected a file. The method the uses the
     * DataUpload class to parse the data. Then the data is displayed.
     * @param filePath the selected file path from the user
     */
    private void viewData(String filePath) {

        DataUpload uploader = new DataUpload();
        dataAnalyser.setCurrentUser(HomeController.getCurrentUser());

        if (appendCheck.isSelected()) {
            Activity selectedAct = (Activity) actTable.getSelectionModel().getSelectedItem();
            if (selectedAct != null) {
                uploader.appendNewData(filePath, selectedAct);

                dataAnalyser.setCurrentUser(HomeController.getCurrentUser());
                dataAnalyser.analyseActivity(selectedAct);

                checkHearthealth(dataAnalyser, selectedAct);

                db.updateDataSet(selectedAct);

                appendCheck.setSelected(false);
                ArrayList<Activity> acts = new ArrayList<>();
                acts.add(selectedAct);
                CheckGoals.markGoals(HomeController.getCurrentUser(), HomeController.getDb(), acts);
                fillTable();
            }
            else {
                DialogController.displayError("No Activity Selected");
            }
        }
        else {
            uploader.uploadData(filePath);
            ArrayList<Activity> activities = uploader.getNewActvities();
            CheckGoals.markGoals(HomeController.getCurrentUser(), HomeController.getDb(), activities);
            Alert countAlert = CheckAlerts.activityAlert(HomeController.getCurrentUser());
            if (countAlert != null) {
                db.storeAlert(countAlert, HomeController.getCurrentUser().getId());
                HomeController.getCurrentUser().addAlert(countAlert);
                HomeController.addAlert(countAlert);
            }
            fillTable();

            for (Activity activity : activities) {
                System.out.println(activity.getName());
                checkHearthealth(dataAnalyser, activity);
            }
        }

        activities = db.getActivities(HomeController.getCurrentUser().getId());

    }


    /**
     *
     * @param analyser
     * @param selectedAct
     */
    private void checkHearthealth(DataAnalyser analyser, Activity selectedAct) {


        Alert tachycardiaAlert = analyser.checkTachycardia(selectedAct);

        System.out.println(tachycardiaAlert.getMessage());

        if (tachycardiaAlert != null) {
            System.out.println("check heart health alert not null");
            db.storeAlert(tachycardiaAlert, HomeController.getCurrentUser().getId());
            HomeController.getCurrentUser().addAlert(tachycardiaAlert);
            HomeController.addAlert(tachycardiaAlert);
        }

        Alert bradycardiaAlert = analyser.checkBradycardia(selectedAct);
        System.out.println(bradycardiaAlert.getMessage());

        if (bradycardiaAlert != null) {
            System.out.println("check heart health alert not null");
            db.storeAlert(bradycardiaAlert, HomeController.getCurrentUser().getId());
            HomeController.getCurrentUser().addAlert(bradycardiaAlert);
            HomeController.addAlert(bradycardiaAlert);
        }

        Alert cardioMortalityAlert = analyser.checkCardiovascularMortality(selectedAct);

        if (cardioMortalityAlert != null) {
            System.out.println("check heart health alert not null");
            db.storeAlert(cardioMortalityAlert, HomeController.getCurrentUser().getId());
            HomeController.getCurrentUser().addAlert(cardioMortalityAlert);
            HomeController.addAlert(cardioMortalityAlert);
        }

    }


    /**
     * This method creates and then fills the activity with a passed activity.
     * @param activity The activity to fill the table
     */
    private void createTable(Activity activity) {

        rawDataTable.getItems().clear();
        activities = db.getActivities(HomeController.getCurrentUser().getId());
        // date and time column
        dateTimeCol.setCellValueFactory(new PropertyValueFactory("formattedDate"));

        //heart rate column
        heartRateCol.setCellValueFactory(new PropertyValueFactory("heartRate"));

        //latitude column
        latitudeCol.setCellValueFactory(new PropertyValueFactory("latitude"));

        //longitude column
        longitudeCol.setCellValueFactory(new PropertyValueFactory("longitude"));

        //elevation column
        elevationCol.setCellValueFactory(new PropertyValueFactory("elevation"));

        //distance column
        distanceCol.setCellValueFactory(new PropertyValueFactory("distance"));

        //speed column
        speedCol.setCellValueFactory(new PropertyValueFactory("speed"));


        //rawDataTable.getColumns().addAll(dateTimeCol, heartRateCol, latitudeCol, longitudeCol, elevationCol, distanceCol, speedCol, removeCol);
        rawDataTable.setItems(getDataPointsList(activity));
        rawDataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }


    /**
     * This method takes an activity and creates am observable list of the data points for the passed activity
     * @param activity
     * @return
     */
    private ObservableList<DataPoint> getDataPointsList(Activity activity) {
        ObservableList<DataPoint> dataPointsList = FXCollections.observableArrayList();

        DataSet dataSet = activity.getDataSet();
        dataPointsList.addAll(dataSet.getDataPoints());
        return dataPointsList;
    }


    @FXML
    /**
     * Called by a mouse click on the activity table. it grabs the selected activity from the table and uses the
     * createTable() method to fill the data points table
     */
    public void showActivityData(MouseEvent mouseEvent) {
        if (HomeController.getCurrentUser() != null) {
            Activity activity =  (Activity) actTable.getSelectionModel().getSelectedItem();
            if (activity != null) {
                createTable(activity);
            }
        }
    }


    @FXML
    /**
     * Fills the table with all of the HomeController.getCurrentUser()s activities if the number of
     * activities in the table is not equal to the number of activities the HomeController.getCurrentUser() has.
     */
    public void fillTable() {

        rawDataTable.getItems().clear();
        actTable.getItems().clear();
        if (actTable.getItems().size() != HomeController.getCurrentUser().getActivities().size()) {
            activities = db.getActivities(HomeController.getCurrentUser().getId());
            actCol.setCellValueFactory(new PropertyValueFactory("name"));
            activityNames.addAll(activities);
            actTable.setItems(activityNames);
            actTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
        actTable.setEditable(true);
        actCol.setCellFactory(TextFieldTableCell.forTableColumn());

        rawDataTable.setEditable(true);
        dateTimeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        heartRateCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() {
            @Override
            public Integer fromString(String value) {
                try {
                    return super.fromString(value);
                }
                catch(NumberFormatException e) {
                    return null;
                }
            }
        }));
        latitudeCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() {
            @Override
            public Double fromString(String value) {
                try {
                    return super.fromString(value);
                }
                catch(NumberFormatException e) {
                    return null;
                }
            }
        }));
        longitudeCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() {
            @Override
            public Double fromString(String value) {
                try {
                    return super.fromString(value);
                }
                catch(NumberFormatException e) {
                    return null;
                }
            }
        }));
        elevationCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() {
            @Override
            public Double fromString(String value) {
                try {
                    return super.fromString(value);
                }
                catch(NumberFormatException e) {
                    return null;
                }
            }
        }));

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
            }
        }
    }


    @FXML
    /**
     * This method is called when the user presses the 'Delete' button.
     * The activity is deleted and the database is updated
     */
    public void deleteActivity() {
        Activity selectedAct =  (Activity) actTable.getSelectionModel().getSelectedItem();
        if (selectedAct != null) {
            db.removeActivity(selectedAct);
            HomeController.getCurrentUser().removeActivity(selectedAct);
            fillTable();
        }
        else {
            DialogController.displayError("No Activity Selected");
        }
    }


    @FXML
    /**
     * This method is called when the user confirms an edit on the name of an activity
     * The name is changed and the database is updated
     * @param activityStringCellEditEvent
     */
    public void renameActivity(TableColumn.CellEditEvent<Activity, String> activityStringCellEditEvent) {
        Activity selectedAct =  (Activity) actTable.getSelectionModel().getSelectedItem();
        selectedAct.setName(activityStringCellEditEvent.getNewValue());
        db.updateActivityName(selectedAct);
    }


    @FXML
    /**
     * This method is called when the user confirms an edit on the date time of
     * and activity, the new date time must be between the date times of the
     * preceding and succeeding date times
     * @param dataPointStringCellEditEvent
     */
    public void changeDateTime(TableColumn.CellEditEvent<DataPoint, String> dataPointStringCellEditEvent) {
        Activity activity = (Activity) actTable.getSelectionModel().getSelectedItem();

        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
            Date newDateTime = dateTimeFormat.parse(dataPointStringCellEditEvent.getNewValue());

            DataPoint selectedPoint = (DataPoint) rawDataTable.getSelectionModel().getSelectedItem();

            int i = activity.getDataSet().getDataPoints().indexOf(selectedPoint);
            if (i == 0) {
                if (newDateTime.getTime() < activity.getDataSet().getDataPoints().get(i+1).getDateTime().getTime()) {
                    selectedPoint.setDateTime(newDateTime);
                }
                else {
                    DialogController.displayError("Invalid date. Out of order");
                }
            }
            else if (i == activity.getDataSet().getDataPoints().size() - 1) {
                if (newDateTime.getTime() > activity.getDataSet().getDataPoints().get(i - 1).getDateTime().getTime()) {
                    selectedPoint.setDateTime(newDateTime);
                } else {
                    DialogController.displayError("Invalid date. Out of order");
                }
            }
            else {
                if (newDateTime.getTime() > activity.getDataSet().getDataPoints().get(i-1).getDateTime().getTime() &&
                    newDateTime.getTime() < activity.getDataSet().getDataPoints().get(i+1).getDateTime().getTime()) {
                    selectedPoint.setDateTime(newDateTime);
                }
                else {
                    DialogController.displayError("Invalid date. Out of order");
                }
            }

            DataAnalyser analyser = new DataAnalyser();
            analyser.setCurrentUser(HomeController.getCurrentUser());
            analyser.analyseActivity(activity);

            db.updateDataSet(activity);

        } catch (ParseException e) {
            DialogController.displayError("Wrong format for date");
        }
        createTable(activity);
    }


    @FXML
    /**
     * This method is called when the user confirms an edit on the heart rate
     * of the data point. It updates the activity in the data base.
     * @param dataPointIntegerCellEditEvent
     */
    public void changeHeartRate(TableColumn.CellEditEvent<DataPoint, Integer> dataPointIntegerCellEditEvent) {
        Activity activity = (Activity) actTable.getSelectionModel().getSelectedItem();
        try {
            int newHeartRate = dataPointIntegerCellEditEvent.getNewValue();
            if (newHeartRate > 26 && newHeartRate < 480) {
                DataPoint selectedPoint = (DataPoint) rawDataTable.getSelectionModel().getSelectedItem();
                selectedPoint.setHeartRate(newHeartRate);

                DataAnalyser analyser = new DataAnalyser();
                analyser.setCurrentUser(HomeController.getCurrentUser());
                analyser.analyseActivity(activity);

                db.updateDataSet(activity);

            } else {
                DialogController.displayError("Value must be between 26 and 480");
            }
        }
        catch (NullPointerException e) {
            DialogController.displayError("New value must be a number");
        }
        createTable(activity);

    }


    @FXML
    /**
     * This method is called when the user confirms an edit on the latitude
     * of the data point. It updates the activity in the data base.
     * @param dataPointDoubleCellEditEvent
     */
    public void changeLatitude(TableColumn.CellEditEvent<DataPoint, Double> dataPointDoubleCellEditEvent) {
        Activity activity = (Activity) actTable.getSelectionModel().getSelectedItem();
        try {
            double newLatitude = dataPointDoubleCellEditEvent.getNewValue();
            if (newLatitude > -90 && newLatitude < 90) {
                DataPoint selectedPoint = (DataPoint) rawDataTable.getSelectionModel().getSelectedItem();
                selectedPoint.setLatitude(newLatitude);

                DataAnalyser analyser = new DataAnalyser();
                analyser.setCurrentUser(HomeController.getCurrentUser());
                analyser.analyseActivity(activity);

                db.updateDataSet(activity);

            } else {
                DialogController.displayError("Value must be between -90.0 and 90.0");
            }
        }
        catch (NullPointerException e) {
            DialogController.displayError("New value must be a decimal");
        }
        createTable(activity);
    }


    @FXML
    /**
     * This method is called when the user confirms an edit on the longitude
     * of the data point. It updates the activity in the data base.
     * @param dataPointDoubleCellEditEvent
     */
    public void changeLongitude(TableColumn.CellEditEvent<DataPoint, Double> dataPointDoubleCellEditEvent) {
        Activity activity = (Activity) actTable.getSelectionModel().getSelectedItem();
        try {
            double newLongitude = dataPointDoubleCellEditEvent.getNewValue();
            if (newLongitude > -180 && newLongitude < 180) {
                DataPoint selectedPoint = (DataPoint) rawDataTable.getSelectionModel().getSelectedItem();
                selectedPoint.setLongitude(newLongitude);

                DataAnalyser analyser = new DataAnalyser();
                analyser.setCurrentUser(HomeController.getCurrentUser());
                analyser.analyseActivity(activity);

                db.updateDataSet(activity);

            } else {
                DialogController.displayError("Value must be between -180.0 and 180.0");
            }
        }
        catch (NullPointerException e) {
            DialogController.displayError("New value must be a decimal");
        }
        createTable(activity);
    }


    @FXML
    /**
     * This method is called when the user confirms an edit on the elevation
     * of the data point. It updates the activity in the data base.
     * @param dataPointDoubleCellEditEvent
     */
    public void changeElevation(TableColumn.CellEditEvent<DataPoint, Double> dataPointDoubleCellEditEvent) {
        Activity activity = (Activity) actTable.getSelectionModel().getSelectedItem();
        try {
            double newElevation = dataPointDoubleCellEditEvent.getNewValue();
            if (newElevation > -213 && newElevation < 8850) {
                DataPoint selectedPoint = (DataPoint) rawDataTable.getSelectionModel().getSelectedItem();
                selectedPoint.setElevation(newElevation);

                DataAnalyser analyser = new DataAnalyser();
                analyser.setCurrentUser(HomeController.getCurrentUser());
                analyser.analyseActivity(activity);

                db.updateDataSet(activity);

            } else {
                DialogController.displayError("Value must be between -213.0 and 8850.0");
            }
        }
        catch (NullPointerException e) {
            DialogController.displayError("New value must be a decimal");
        }
        createTable(activity);
    }


    @FXML
    /**
     * This method is called when the user presses the 'Delete Selected Data Point' button
     * It removes the data point from the activity and updates the database
     */
    public void deleteDataPoint() {
        Activity activity = (Activity) actTable.getSelectionModel().getSelectedItem();
        DataPoint dataPoint = (DataPoint) rawDataTable.getSelectionModel().getSelectedItem();

        if (dataPoint != null) {
            activity.getDataSet().removeDataPoint(dataPoint);

            DataAnalyser analyser = new DataAnalyser();
            analyser.setCurrentUser(HomeController.getCurrentUser());
            analyser.analyseActivity(activity);

            db.updateDataSet(activity);

            createTable(activity);
        }
        else {
            DialogController.displayError("No Data Point Selected");
        }
    }


    @FXML
    /**
     * Called by a press of the export file button. This method exports the selected
     * activity to a csv file in the users home directory.
     */
    public void exportActivity() {
        // Getting the selected activity
        Activity selectedAct =  (Activity) actTable.getSelectionModel().getSelectedItem();
        if (selectedAct != null) {
            ArrayList<Activity> activities = new ArrayList<Activity>();
            activities.add(selectedAct);
            // Making the filename for the export from the activities name
            String filename = makeFilename(selectedAct.getName());
            // Exporting the file
            boolean status = DataExporter.exportData(activities, filename);
            // Displaying the result of the file export to the user
            if (status) {
                DialogController.displayExportMessage("File exported as " + filename + ".csv");
            } else {
                DialogController.displayError("File export failed");
            }
        }
        else {
            DialogController.displayError("No Activity Selected");
        }
    }


    /**
     * Used by the exportActivity method. This method turns an activity name into
     * a filename.
     * @param actName The activity name.
     * @return The corresponding filename.
     */
    private String makeFilename(String actName) {
        // Splitting the name by spaces
        String[] words = actName.split(" ");
        String filename = "";
        // Concatenating the words together
        for (int i = 0; i < words.length; i++) {
            filename += words[i];
        }
        // returning a default filename if one is not created
        if (filename.equals("")) {
            return "snoGoExportedData.csv";
        } else {
            return filename;
        }
    }


    @FXML
    /**
     * This method enables the mechanisms for manual entry and disables the other mechanisms of the tab
     */
    public void showManual() {
        gridPane.setDisable(true);
        manualEntryTable.setVisible(true);
        manualEntryTable.setDisable(false);
        timeEntry.setVisible(true);
        abortButton.setVisible(true);
        confirmButton.setVisible(true);
        enterLineButton.setVisible(true);
        nameEntry.setVisible(true);
        datePicker.setVisible(true);
        heartEntry.setVisible(true);
        latEntry.setVisible(true);
        longEntry.setVisible(true);
        eleEntry.setVisible(true);
        eleEntry.setDisable(false);
        longEntry.setDisable(false);
        latEntry.setDisable(false);
        heartEntry.setDisable(false);
        datePicker.setDisable(false);
        nameEntry.setDisable(false);
        timeEntry.setDisable(false);
        abortButton.setDisable(false);
        nameCheck.setVisible(true);
        dateCheck.setVisible(true);
        timeCheck.setVisible(true);
        heartCheck.setVisible(true);
        latCheck.setVisible(true);
        longCheck.setVisible(true);
        eleCheck.setVisible(true);
    }


    @FXML
    /**
     * This method disables the mechanisms for manual entry and enables the other mechanisms of the tab
     */
    public void hideManual() {
        gridPane.setDisable(false);
        gridPane.setVisible(true);
        manualEntryTable.setVisible(false);
        manualEntryTable.setDisable(true);
        timeEntry.setVisible(false);
        abortButton.setVisible(false);
        confirmButton.setVisible(false);
        enterLineButton.setVisible(false);
        nameEntry.setVisible(false);
        datePicker.setVisible(false);
        heartEntry.setVisible(false);
        latEntry.setVisible(false);
        longEntry.setVisible(false);
        eleEntry.setVisible(false);
        eleEntry.setDisable(true);
        longEntry.setDisable(true);
        latEntry.setDisable(true);
        heartEntry.setDisable(true);
        datePicker.setDisable(true);
        nameEntry.setDisable(true);
        timeEntry.setDisable(true);
        abortButton.setDisable(true);
        nameCheck.setVisible(false);
        dateCheck.setVisible(false);
        timeCheck.setVisible(false);
        heartCheck.setVisible(false);
        latCheck.setVisible(false);
        longCheck.setVisible(false);
        eleCheck.setVisible(false);
    }


    @FXML
    /**
     * This method is called by a key release on the activity name entry text field. It then checks the current text
     * for validity
     */
    public void checkName() {
        // Getting the name out of the text field
        String name = nameEntry.getText();
        // Checking if name is a valid length
        if (name.length() > 5 && name.length() < 45) {
            nameCheck.setSelected(true);
            // Checking if the currently entered data is enough to be a valid activity
            if (dataPoints.size() >= 2) {
                confirmButton.setDisable(false);
            }
        } else {
            nameCheck.setSelected(false);
        }
    }


    @FXML
    /**
     * This method is called by an action on the datePicker. It checks that the date is with in a valid range
     */
    public void checkDate() {
        if (manualEntryTable.getItems().isEmpty()) {
            // Checking if there is a value entered
            if (datePicker.getValue() != null) {
                Date date = new Date();
                Date picked = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                // Checking that the date is not in the future
                if (picked.getTime() >= date.getTime()) {
                    dateCheck.setSelected(false);
                } else {
                    dateCheck.setSelected(true);
                }
            } else {
                dateCheck.setSelected(false);
            }
        } else {
            // Checking the date with the currently entered time against previously entered date time values
            checkDateTime();
        }
        checkChecks();
    }


    @FXML
    /**
     * This method is called by a key realease on the tiem entry test field. It checks the time is valid by passing it
     * through a date time format and then comparing it with other entered tme values
     */
    public void checkTime() {
        String time = timeEntry.getText();
        try {
            // Getting the entered time
            Date date = timeFormat.parse(time);
            if (manualEntryTable.getItems().isEmpty()) {
                // table is entered so a time that can be parsed is valid
                timeCheck.setSelected(true);
            } else {
                // Checking the time with the currently entered date against previously entered date time values
                checkDateTime();
            }
        } catch (ParseException e) {
            timeCheck.setSelected(false);
        }
        checkChecks();
    }


    @FXML
    /**
     * This method is called by a ket release on the heart rate entry text field. It checks that the entered heart rate
     * is within a valid range
     */
    public void checkHeart() {
        // Getting the heart rate entered
        String rate = heartEntry.getText();
        try {
            // parsing the text to an int
            int heartRate = Integer.parseInt(rate);
            if (manualEntryTable.getItems().isEmpty()) {
                heartCheck.setSelected(true);
            } else {
                // Checking the heart rate is within a valid range
                if (abs(heartRate - dataPoints.get(dataPoints.size() - 1).getHeartRate()) > 5 || heartRate < 26
                        || heartRate > 480) {
                    heartCheck.setSelected(false);
                } else {
                    heartCheck.setSelected(true);
                }
            }
        } catch (NumberFormatException e) {
            heartCheck.setSelected(false);
        }
        checkChecks();
    }


    @FXML
    /**
     * This method is called by a key release on the latitude entry text field. It checks that the value is with in
     * a valid range and then compares it with previously entered longitude and latitude points
     */
    public void checkLat() {
        // Getting the entered value
        String lat = latEntry.getText();
        try {
            // parsing the value to a double
            double latitude = Double.parseDouble(lat);
            if (manualEntryTable.getItems().isEmpty()) {
                latCheck.setSelected(true);
            } else { // Check the value is in a valid range
                if (latitude < -90 || latitude > 90) {
                    latCheck.setSelected(false);
                } else {
                    // Checking with other entered values
                    checkLatLong();
                }
            }
        } catch (NumberFormatException e) {
            latCheck.setSelected(false);
        }
        checkChecks();
    }


    /**
     * This method checks that the currently entered latitude and longitude points are valid relatively to the
     * previously entered points
     */
    private void checkLatLong() {
        // Getting the latitude and longitude values
        String lat = latEntry.getText();
        String lng = longEntry.getText();
        if (lat != null && lng != null) {
            // Parsing the values to double
            double latitude = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lng);
            // Getting the last point entered
            DataPoint lastPoint = dataPoints.get(dataPoints.size() - 1);
            // Calculating the distance change between the last point and the entered values
            double change = dataAnalyser.oneDist(latitude, longitude, lastPoint.getLatitude(), lastPoint.getLongitude());
            Date dateTime = getDateTime();
            if (dateTime != null) {
                // Getting the time change between the current and last point
                double timeChange = (dateTime.getTime() - lastPoint.getDateTime().getTime()) / 1000;
                // Checking that the speed is valid between the two points
                if (change / timeChange > 35) {
                    latCheck.setSelected(false);
                    longCheck.setSelected(false);
                } else {
                    latCheck.setSelected(true);
                    longCheck.setSelected(true);
                }
            } else {
                latCheck.setSelected(false);
                longCheck.setSelected(false);
            }
        } else {
            latCheck.setSelected(false);
            longCheck.setSelected(false);
        }
    }


    @FXML
    /**
     * This method is called by a key release on the latitude entry text field. It checks that the value is with in
     * a valid range and then compares it with previously entered longitude and latitude points
     */
    public void checkLong() {
        // Getting the longitude value
        String lng = longEntry.getText();
        try {
            // Parsing the value to a double
            double longitude = Double.parseDouble(lng);;
            if (manualEntryTable.getItems().isEmpty()) {
                longCheck.setSelected(true);
            } else {
                // Checking the value is within a valid range
                if (longitude < -180 || longitude > 180) {
                    longCheck.setSelected(false);
                } else {
                    // Checking with other entered values
                    checkLatLong();
                }
            }
        } catch (NumberFormatException e) {
            longCheck.setSelected(false);
        }
        checkChecks();
    }


    @FXML
    /**
     * This method is called by a key release on the elevation entry text field. It checks that the value is within a
     * valid range compared to previously entered elevations
     */
    public void checkEle() {
        // Getting the value from the text field
        String ele = eleEntry.getText();
        try {
            // Parsing the value
            double elevation = Double.parseDouble(ele);
            if (manualEntryTable.getItems().isEmpty()) {
                eleCheck.setSelected(true);
            } else {
                // Checking the value is within a valid range
                if (abs(elevation - dataPoints.get(dataPoints.size() - 1).getElevation()) > 5 || elevation < -213 ||
                        elevation > 8850) {
                    eleCheck.setSelected(false);
                } else {
                    eleCheck.setSelected(true);
                }
            }
        } catch (NumberFormatException e) {
            eleCheck.setSelected(false);
        }
        checkChecks();
    }


    /**
     * This method checks if all entry fields are valid by checking that all check boxes are ticked, if so the
     * enterLineButton is enabled
     */
    private void checkChecks() {
        if (dateCheck.isSelected() && timeCheck.isSelected() && heartCheck.isSelected() && latCheck.isSelected() &&
                longCheck.isSelected() && eleCheck.isSelected()) {
            enterLineButton.setDisable(false);
        } else {
            enterLineButton.setDisable(true);
        }
    }


    /**
     * This method checks that the date entered along with the time entered is valid
     */
    private void checkDateTime() {
        Date dateTime = getDateTime();
        Date lastDate = dataPoints.get(dataPoints.size() - 1).getDateTime();
        // checking the change in time is less than 60 seconds
        if (abs(dateTime.getTime() - lastDate.getTime())/1000 > 60) {
            dateCheck.setSelected(false);
            timeCheck.setSelected(false);
        } else {
            dateCheck.setSelected(true);
            timeCheck.setSelected(true);
        }
    }


    @FXML
    /**
     * This method is called by a press of the enterLineButton. It grabs all currently entered data and forms
     * a data point which is then added to the table
     */
    public void enterLine() {
        Date dateTime = getDateTime();
        if (dateTime != null) {
            int heart = Integer.parseInt(heartEntry.getText());
            double lat = Double.parseDouble(latEntry.getText());
            double lng = Double.parseDouble(longEntry.getText());
            double ele = Double.parseDouble(eleEntry.getText());
            DataPoint point = new DataPoint(dateTime, heart, lat, lng, ele);
            addDataPoint(point);
            clearEntries();
        }
    }


    /**
     * This method is used after a new line is entered. It clears all the entry mechanisms ready for the next
     * line to be entered
     */
    private void clearEntries() {
        timeCheck.setSelected(false);
        timeEntry.setText("");
        heartCheck.setSelected(false);
        heartEntry.setText("");
        latCheck.setSelected(false);
        latEntry.setText("");
        longCheck.setSelected(false);
        longEntry.setText("");
        eleCheck.setSelected(false);
        eleEntry.setText("");
        enterLineButton.setDisable(true);
    }


    /**
     * This method gets the currently entered date time
     * @return A Date holding the currently entered data time
     */
    private Date getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        // Getting the entered date and time
        String date = dateFormat.format(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        String time = timeEntry.getText();
        String dateTime = date + " " + time;
        try {
            // Parsing the date and time together to be returned
            return dateTimeFormat.parse(dateTime);
        } catch (ParseException e) {
            // Returning null as the parsing of the date time failed
            return null;
        }
    }


    /**
     * This method adds a passed data point to the table
     * @param point The data point to be added
     */
    private void addDataPoint(DataPoint point) {
        // Defining columns of the table as the table is empty
        if (manualEntryTable.getItems().isEmpty()) {
            dateTimeColMan.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));
            heartRateColMan.setCellValueFactory(new PropertyValueFactory<>("heartRate"));
            latitudeColMan.setCellValueFactory(new PropertyValueFactory<>("latitude"));
            longitudeColMan.setCellValueFactory(new PropertyValueFactory<>("longitude"));
            elevationColMan.setCellValueFactory(new PropertyValueFactory<>("elevation"));
        }
        // adding the point
        dataPoints.add(point);
        manualEntryTable.setItems(dataPoints);
        if (dataPoints.size() >= 2 && nameCheck.isSelected()) {
            confirmButton.setDisable(false);
        }
    }


    @FXML
    /**
     * This method is called by a press of the confirm button. It creates and activity from the data points in the table
     */
    public void makeActivity() {
        // Setting the current user of the dataAnalyser
        dataAnalyser.setCurrentUser(HomeController.getCurrentUser());
        // Getting the data points
        ArrayList<DataPoint> points = new ArrayList<>();
        points.addAll(dataPoints);
        // Making the data set
        DataSet dataSet = new DataSet();
        dataSet.setDataPoints(points);
        // Making the activity
        Activity activity = new Activity(nameEntry.getText(), dataSet);
        // Analysing the activity
        dataAnalyser.analyseActivity(activity);
        // Adding teh activity to the user and storing it in the database
        HomeController.getCurrentUser().addActivity(activity);
        db.storeActivity(activity, HomeController.getCurrentUser().getId());
        // Hiding the manual entry mechanisms
        hideManual();
        // Refreshing the activity table
        fillTable();
    }

}