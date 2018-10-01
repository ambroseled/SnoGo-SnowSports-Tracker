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
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.DataManipulation.DataExporter;
import seng202.team5.DataManipulation.DataUpload;
import seng202.team5.Model.*;
import seng202.team5.Model.Alert;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;


//TODO If you delete an activity and then upload it again straight away it is caught as a double up activity - need to fix


public class DataController {

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
    private TableView manualEntrytable;
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


    private ObservableList<Activity> activityNames = FXCollections.observableArrayList();


    private void viewData(String filePath) {

        DataUpload uploader = new DataUpload();
        uploader.uploadData(filePath);


        activities = db.getActivities(HomeController.getCurrentUser().getId());

        CheckGoals.markGoals(HomeController.getCurrentUser(), HomeController.getDb(), uploader.getNewActvities());
        Alert countAlert = AlertHandler.activityAlert(HomeController.getCurrentUser());
        if (countAlert != null) {
            db.storeAlert(countAlert, HomeController.getCurrentUser().getId());
            HomeController.getCurrentUser().addAlert(countAlert);
        }

        fillTable();
        //TODO Implement proper

        //  homeController.updateTabs();
        // statsController.resetData();
        statsController.setOverallStats();


    }


    private void createTable(Activity activity) {

        rawDataTable.getItems().clear();
        if (rawDataTable.getItems().size() != HomeController.getCurrentUser().getActivities().size()) {
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
    }

    private ObservableList<DataPoint> getDataPointsList(Activity activity) {
        ObservableList<DataPoint> dataPointsList = FXCollections.observableArrayList();

        DataSet dataSet = activity.getDataSet();
        dataPointsList.addAll(dataSet.getDataPoints());
        return dataPointsList;
    }

    @FXML
    /**
     * Called by a mouse click on the activity table.
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
     *Fills the table with all of the HomeController.getCurrentUser()s activities if the number of
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
//        rawDataTable.setEditable(true);

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

    public void deleteActivity() {
        //System.out.println("Deleted, Right?");
        Activity selectedAct =  (Activity) actTable.getSelectionModel().getSelectedItem();
        if (selectedAct != null) {
            db.removeActivity(selectedAct);
            fillTable();
        }
        else {
            ErrorController.displayError("No Activity Selected");
        }
    }

    public void renameActivity(TableColumn.CellEditEvent<Activity, String> activityStringCellEditEvent) {
        Activity selectedAct =  (Activity) actTable.getSelectionModel().getSelectedItem();
        selectedAct.setName(activityStringCellEditEvent.getNewValue());
        db.updateActivityName(selectedAct);
    }

//    public void renameActivity() {
//    }


    /**
     * Called by a press of the export file button. This method exports the selected
     * activity to a csv file in the users home directory.
     */
    public void exportActivity() {
        Activity selectedAct =  (Activity) actTable.getSelectionModel().getSelectedItem();
//        String title = activity.getName();
//        Activity selectedAct = null;
        /*for (Activity activity : db.getActivities(HomeController.getCurrentUser().getId())) {
            String name = activity.getName();
            Date startDateTime = activity.getDataSet().getDateTime(0);
            Date endDateTime = activity.getDataSet().getDateTime(activity.getDataSet().getDataPoints().size() - 1);
            String dropdownText = (name + ", " + startDateTime + " - " + endDateTime);
            if (title.equals(dropdownText)) {
                selectedAct = activity;
                break;
            }
        }*/
        if (selectedAct != null) {
            ArrayList<Activity> activities = new ArrayList<Activity>();
            activities.add(selectedAct);
            String filename = makeFilename(selectedAct.getName());
            boolean status = DataExporter.exportData(activities, filename);
            if (status) {
                ErrorController.displaymessage("File exported as " + filename + ".csv");
            } else {
                ErrorController.displayError("File export failed");
            }
        }
        else {
            ErrorController.displayError("No Activity Selected");
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


    @FXML
    public void showManual() {
        gridPane.setDisable(true);
        manualEntrytable.setVisible(true);
        manualEntrytable.setDisable(false);
        timeEntry.setVisible(true);
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
        enterLineButton.setDisable(false);
        confirmButton.setDisable(false);
        timeEntry.setDisable(false);
    }



}