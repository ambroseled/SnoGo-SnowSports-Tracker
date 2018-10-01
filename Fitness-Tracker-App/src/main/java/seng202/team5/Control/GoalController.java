package seng202.team5.Control;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.*;
import seng202.team5.Model.Alert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;


//TODO Work out y goal button is fadded

/**
 * This class handles controlling the goal view of the application. It provides functionality to view, remove
 * and create goals.
 */
public class GoalController {

    // Java fx elements used in controller
    @FXML
    private TableView goalTable;
    @FXML
    private TableColumn<Goal, String> nameCol;
    @FXML
    private TableColumn<Goal, String> metricCol;
    @FXML
    private TableColumn<Goal, Double> valueCol;
    @FXML
    private TableColumn<Goal, String> dateCol;
    @FXML
    private TableColumn<Goal, Boolean> compCol;
    @FXML
    private Button createButton;
    @FXML
    private TextField goalName;
    @FXML
    private CheckBox nameCheck;
    @FXML
    private CheckBox metricCheck;
    @FXML
    private ComboBox<String> metricCombo;
    @FXML
    private DatePicker datePicker;
    @FXML
    private CheckBox dateCheck;
    @FXML
    private ComboBox<Double> valueCombo;
    @FXML
    private CheckBox valueCheck;
    @FXML
    private CheckBox globalCheck;



    private ObservableList<Goal> goals = FXCollections.observableArrayList();
    private DataBaseController db = HomeController.getDb();
    private DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");




    @FXML
    /**
     * This method fills the goal table
     * with all of the users goals, if the number of goals in teh table
     * does not match teh number of goals the user has.;
     */
    public void viewData() {
        // Checking if the data in the table is current
        if (goalTable.getItems().size() != db.getGoals(HomeController.getCurrentUser().getId()).size()) {
            goalTable.getItems().clear();
            goals.clear();
            // Getting the users goals
            goals.addAll(db.getGoals(HomeController.getCurrentUser().getId()));
            // Setting up the tables columns
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            metricCol.setCellValueFactory(new PropertyValueFactory<>("metric"));
            valueCol.setCellValueFactory(new PropertyValueFactory<>("metricGoal"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("dateString"));
            compCol.setCellValueFactory(new PropertyValueFactory<>("completed"));
            // Filling the table
            goalTable.setItems(goals);
        }
    }


    @FXML
    /**
     * This method is called on a key release in the goalName textField.
     * It performs checks to see if the name entered is valid and sets the
     * state of the nameCheck checkBox accordingly.
     */
    public void nameEntry() {
        // Getting the current name text
        String text = goalName.getText();
        // Checking the length of the name then setting the name check box accordingly
        if (text.length() > 4 && text.length() < 30) {
            nameCheck.setSelected(true);
        } else {
            nameCheck.setSelected(false);
        }
        // Filling the metric picker combo box
        if (metricCombo.getItems().size() == 0) {
            fillCombo();
        }
        // Checking if all fields are now valid
        checkChecks();
    }


    /**
     * This method is used to fill the goal metric comboBox with the possible
     * choices for a new goal.
     */
    private void fillCombo() {
        // Getting all possible metrics
        ObservableList<String> metrics = FXCollections.observableArrayList();
        metrics.addAll("Top Speed, (m/s)", "Distance Traveled, (km)", "Vertical Distance, (km)", "Average Heart Rate, (bpm)", "Calories Burned");
        // Filling the combo box
        metricCombo.getItems().addAll(metrics);
    }


    @FXML
    /**
     * This method is called on an action on the metricCombo comboBox. It checks if
     * a value has been selected and then sets the state of the metricCheck checkBox
     * accordingly.
     */
    public void checkMetricCombo() {
        // Checking if a metric has been selected in the combo and marking the metric check box accordingly
        boolean selected = metricCombo.getSelectionModel().isEmpty();
        if (!selected) {
            metricCheck.setSelected(true);
            // Filling teh value combo with the correct set of data according to the metric selected
            fillValueCombo(metricCombo.getSelectionModel().getSelectedItem());
        } else {
            metricCheck.setSelected(false);
        }
        // Checking if all fields are now valid
        checkChecks();
    }


    @FXML
    /**
     * This method is called an action on the datePicker. It checks if
     * the entered date is valid and then sets the state of the dateCheck checkBox
     * accordingly.
     */
    public void checkDate() {
        // Getting the entered date string
        Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        // Converting the string to a date, if successful then the date is valid, marking the date check box accordingly
        Date current = new Date();
        if (current.getTime() > date.getTime()) {
            dateCheck.setSelected(false);
        } else {
            dateCheck.setSelected(true);
        }
        // Checking if all fields are now valid
        checkChecks();
    }


    /**
     * This method fills the valueCombo with the possible choices according to
     * the passed metric type.
     * @param metric
     */
    private void fillValueCombo(String metric) {
        // Enabling and emptying the combo box
        valueCombo.getItems().clear();
        ObservableList<Double> values = FXCollections.observableArrayList();
        // Getting teh values to fill the combo box with according to the selected metric type
        if (metric.equals("Top Speed, (m/s)")) {
            for (double i = 10.0; i < 34.0; i += 0.5) {
                values.add(i);
            }
            valueCombo.getItems().addAll(values);
        } else if (metric.equals("Distance Traveled, (km)") || metric.equals("Vertical Distance, (km)")) {
            for (double i = 0.5; i < 25.5; i += 0.5) {
                values.add(i);
            }
            valueCombo.getItems().addAll(values);
        } else if (metric.equals("Average Heart Rate, (bpm)")) {
            for (double i = 130; i < 155; i++) {
                values.add(i);
            }
            valueCombo.getItems().addAll(values);
        } else if (metric.equals("Calories Burned")) {
            for (double i = 300; i < 2000; i += 5) {
                values.add(i);
            }
            valueCombo.getItems().addAll(values);
        }
    }



    @FXML
    /**
     * This method is called on an action on the valueCombo comboBox. It checks if
     * a value has been selected and then sets the state of the valueCheck checkBox
     * accordingly.
     */
    public void checkValueCombo() {
        // Getting if a value has been selected and then marking the value check box accordingly
        boolean selected = valueCombo.getSelectionModel().isEmpty();
        if (!selected) {
            valueCheck.setSelected(true);
        } else {
            valueCheck.setSelected(false);
        }
        // Checking if all fields are now valid
        checkChecks();
    }


    @FXML
    /**
     * This method is called by a press of the createButton. It pulls all the data
     * from the entries and creates a goal from this data. The goal is then stored in
     * the database after wwhich the goal table is updated.
     */
    public void createGoal() {
        // Getting all entered data
        String name = goalName.getText();
        String metric = metricCombo.getSelectionModel().getSelectedItem();
        double value = valueCombo.getSelectionModel().getSelectedItem();
        String dateString = dateTimeFormat.format(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        metric = getMetric(metric);
        boolean global = globalCheck.isSelected();
        // Creating the new Goal
        Goal newGoal = new Goal(name, metric, value, dateString, global);
        // Checking if any alerts need to be created for the new goal
        newGoal.setCompleted(CheckGoals.checkGoal(newGoal, HomeController.getCurrentUser().getActivities(), HomeController.getCurrentUser()));
        newGoal.setExpired(CheckGoals.checkExpired(newGoal));
        if (newGoal.isExpired()) {
            // Creating an expired goal alert
            Alert alert = AlertHandler.expiredGoalAlert(newGoal.getName());
            db.storeAlert(alert, HomeController.getCurrentUser().getId());
            HomeController.getCurrentUser().addAlert(alert);
            HomeController.addAlert(alert);
        } else if (newGoal.isCompleted()) {
            // Creating a completed goal alert
            Alert alert = AlertHandler.newGoalAlert(newGoal.getName());
            db.storeAlert(alert, HomeController.getCurrentUser().getId());
            HomeController.getCurrentUser().addAlert(alert);
            HomeController.addAlert(alert);
        }
        // Storing the goal in the database
        db.storeGoal(newGoal, HomeController.getCurrentUser().getId());
        // Adding the goal to the user
        HomeController.getCurrentUser().addGoal(newGoal);
        // Resetting all the entry fields and check boxes
        nameCheck.setSelected(false);
        metricCheck.setSelected(false);
        dateCheck.setSelected(false);
        valueCheck.setSelected(false);
        globalCheck.setSelected(false);
        goalName.clear();
        metricCombo.getItems().clear();
        datePicker.getEditor().clear();
        valueCombo.getItems().clear();
        // Refreshing the goal table
        viewData();
    }


    /**
     * This method gets the metric out of the string which is returned
     * from the metricCombo.
     * @param metric The string form the metricCombo.
     * @return The metric string.
     */
    private String getMetric(String metric) {
        int index = 0;
        // Finding the comma and getting the metric string
        for (int i = 0; i < metric.length(); i++) {
            if (metric.charAt(i) == ',') {
                index = i;
                break;
            }
        }
        return metric.substring(0, index);
    }


    /**
     * This method checks that all the checkBoxes are selected.
     * If so all the data entered is valid and the createButton is enabled.
     */
    private void checkChecks() {
        // Checking if all check boxes are selected and therefore al fields are valid
        if (valueCheck.isSelected() && metricCheck.isSelected() && dateCheck.isSelected() && nameCheck.isSelected()) {
            // Enabling the create button
            createButton.setDisable(false);
        } else {
            // Disabling the create button
            createButton.setDisable(true);
        }
    }


    @FXML
    /**
     * This method is called by a press to teh deleteButton. It gets the selected
     * goal from the goal table and removes it from teh user and the database. The
     * goal table is then updated.
     */
    public void removeGoal() {
        // Getting the selected goal
        Goal goal = (Goal) goalTable.getSelectionModel().getSelectedItem();
        // Removing the goal from the database and the user
        if (goal != null) {
            db.removeGoal(goal);
            HomeController.getCurrentUser().setGoals(db.getGoals(HomeController.getCurrentUser().getId()));
            // Refreshing the goal table
            viewData();
        }
    }



}
