package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.Activity;
import seng202.team5.Model.Alert;
import seng202.team5.Model.Goal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


/**
 * This class is the controller for the calView.fxml file. It provides the calendar functionality
 * for the application. Giving the user tha ability to view alerts, goals and activity over a selected
 * date range.
 */
public class CalController {

    // FXML objects used by the controller
    @FXML
    private DatePicker datePicker;
    @FXML
    private DatePicker datePicker1;
    @FXML
    private TableView actTable;
    @FXML
    private TableColumn<Activity, String> nameCol;
    @FXML
    private TableColumn<Activity, String> actDateCol;
    @FXML
    private TableColumn<Activity, Double> distCol;
    @FXML
    private TableColumn<Activity, Double> vertCol;
    @FXML
    private TableColumn<Activity, Integer> heartCol;
    @FXML
    private TableColumn<Activity, Double> calCol;
    @FXML
    private TableColumn<Activity, Double> avgSpeedCol;
    @FXML
    private TableColumn<Activity, Double> topSpeedCol;
    @FXML
    private TableColumn<Activity, String> slopeCol;
    @FXML
    private TableView goalTable;
    @FXML
    private TableColumn<Goal, String> goalNameCol;
    @FXML
    private TableColumn<Goal, String> metricCol;
    @FXML
    private TableColumn<Goal, Double> valCol;
    @FXML
    private TableColumn<Goal, String> goalDateCol;
    @FXML
    private TableColumn<Goal, Boolean> compCol;
    @FXML
    private TableView alertTable;
    @FXML
    private TableColumn<Alert, String> typeCol;
    @FXML
    private TableColumn<Alert, String> alertDateCol;
    @FXML
    private TableColumn<Alert, String> desCol;

    // Observable lists which hold the activities, alerts and goals to be shown for the current date range
    private ObservableList<Activity> activities = FXCollections.observableArrayList();
    private ObservableList<Goal> goals = FXCollections.observableArrayList();
    private ObservableList<Alert> alerts = FXCollections.observableArrayList();
    // Date format used to check for events that match the current date range
    private DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy");
    // The database connection
    private DataBaseController db = HomeController.getDb();


    @FXML
    /**
     * This method is called by action on either of the DatePickers in the view. It gets the dates selected
     * an then fills the tables with the corresponding events.
     */
    public void showData() {
        // Catching case where one datePicker has a value but the other doesn't
        if (datePicker.getValue() != null && datePicker1.getValue() != null) {
            // Getting the selected dates
            Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date date1 = Date.from(datePicker1.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            // Displaying the events for the selected date range
            fillActivities(findActivities(parseDate(date), parseDate(date1)));
            fillGoals(findGoals(parseDate(date), parseDate(date1)));
            fillAlerts(findAlerts(parseDate(date), parseDate(date1)));
        }
    }


    /**
     * This method is called by the selection of the Calendar tab. If either of the DatePickers do not currently have
     * a date selected it will put the current date into them to prevent null pointer exceptions from occuring
     * when showData is called.
     */
    public void setCurrent(boolean reset) {
        // Checking if either DatePicker holds a null value
        if (datePicker.getValue() == null || datePicker1.getValue() == null || reset) {
            // Filling both DatePickers with the current date because at least one of them is null
            Date date = new Date();
            datePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            datePicker1.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            // Then showing the events for the current date
            fillActivities(findActivities(parseDate(date), parseDate(date)));
            fillGoals(findGoals(parseDate(date), parseDate(date)));
            fillAlerts(findAlerts(parseDate(date), parseDate(date)));
        }
        // Getting the selected dates
        Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date date1 = Date.from(datePicker1.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        // Displaying the events for the selected date range
        fillActivities(findActivities(parseDate(date), parseDate(date1)));
        fillGoals(findGoals(parseDate(date), parseDate(date1)));
        fillAlerts(findAlerts(parseDate(date), parseDate(date1)));
    }


    /**
     * This method parses a Date object into a form that can be used to compare dates. It is needed as dates of events
     * are not a constant format as some are given with the format of dd/MM/yy and others are given with the format of
     * dd/MM/yyyy.
     * @param date
     * @return
     */
    private Date parseDate(Date date) {
        // Formating the date into a date string of format dd/MM/yy
        String dateString = dateTimeFormat.format(date);
        try {
            // Parsing the date string back into a date to be used for comparison
            return dateTimeFormat.parse(dateString);
        } catch(ParseException e) {
            return null;
        }
    }



    /**
     * This method finds an ArrayList of activities that are in a passed date range
     * @param date The lower bound of the date range
     * @param date1 The upper bound of the date range
     * @return An ArrayList holding all activities for the current user that are within the date range
     */
    private ArrayList<Activity> findActivities(Date date, Date date1) {
        ArrayList<Activity> acts = new ArrayList<>();
        // Lopping over the current users activities
        for (Activity activity : db.getActivities(HomeController.getCurrentUser().getId())) {
            // Parsing the activities date to a comparable format
            Date actDate = parseDate(activity.getDataSet().getDateTime(0));
            // Checking if the activity is within the date range
            if (actDate.getTime() >= date.getTime() && actDate.getTime() <= date1.getTime()) {
                acts.add(activity);
            }
        }
        // Returning the activities
        return acts;
    }


    /**
     * This method finds an ArrayList of goals that are within a passed date range
     * @param date The lower bound of the date range
     * @param date1 The upper bound of the date range
     * @return An ArrayList holding all goals for the current user that are within the date range
     */
    private ArrayList<Goal> findGoals(Date date, Date date1) {
        ArrayList<Goal> gls = new ArrayList<>();
        // Looping over all of the users goals
        for (Goal goal : db.getGoals(HomeController.getCurrentUser().getId())) {
            // Parsing the goals date to a comparable format
            Date goalDate = parseDate(goal.getCompletionDate());
            // Checking if the goal is within the date range
            if (goalDate.getTime() >= date.getTime() && goalDate.getTime() <= date1.getTime()) {
                gls.add(goal);
            }
        }
        // Returning the goals
        return gls;
    }


    /**
     * This method finds an ArrayList of alerts that are within a passed date range
     * @param date The lower bound of the date range
     * @param date1 The upper bound of the date range
     * @return An ArrayList holding all alerts for the current user that are within the date range
     */
    private ArrayList<Alert> findAlerts(Date date, Date date1) {
        ArrayList<Alert> alts = new ArrayList<>();
        // Looping over all of the users alerts
        for (Alert alert : db.getAlerts(HomeController.getCurrentUser().getId())) {
            // Parsing the alerts date to a comparable format
            Date alertDate = parseDate(alert.getDate());
            // Checking if the alert is within the date range
            if (alertDate.getTime() >= date.getTime() && alertDate.getTime() <= date1.getTime()) {
                alts.add(alert);
            }
        }
        // Returning the alerts
        return alts;
    }


    /**
     * This method fills the activity table with a passed ArrayList of activities
     * @param acts The activities to be put in the table
     */
    private void fillActivities(ArrayList<Activity> acts) {
        // Clearing the table and observable list of activities
        actTable.getItems().clear();
        activities.clear();
        // Configuring the columns of the table
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        actDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        distCol.setCellValueFactory(new PropertyValueFactory<>("totalDistance"));
        vertCol.setCellValueFactory(new PropertyValueFactory<>("verticalDistance"));
        heartCol.setCellValueFactory(new PropertyValueFactory<>("avgHeartRate"));
        calCol.setCellValueFactory(new PropertyValueFactory<>("caloriesBurned"));
        avgSpeedCol.setCellValueFactory(new PropertyValueFactory<>("avgSpeed"));
        topSpeedCol.setCellValueFactory(new PropertyValueFactory<>("topSpeed"));
        slopeCol.setCellValueFactory(new PropertyValueFactory<>("slopeTime"));
        // Filling the table
        activities.addAll(acts);
        actTable.setItems(activities);
    }


    /**
     * This method fills the goals table with a passed ArrayList of goals
     * @param gls The goals to be put in the table
     */
    private void fillGoals(ArrayList<Goal> gls) {
        // Clearing the table and observable list of goals
        goalTable.getItems().clear();
        goals.clear();
        // Configuring the columns of the table
        goalNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        metricCol.setCellValueFactory(new  PropertyValueFactory<>("metric"));
        valCol.setCellValueFactory(new PropertyValueFactory<>("metricGoal"));
        goalDateCol.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        compCol.setCellValueFactory(new PropertyValueFactory<>("completed"));
        // Filling the table
        goals.addAll(gls);
        goalTable.setItems(goals);
    }


    /**
     * This method fills the alerts table with a passed ArrayList of alerts
     * @param alts The alerts to be put in the table
     */
    private void fillAlerts(ArrayList<Alert> alts) {
        // Clearing the table and observable list of alerts
        alertTable.getItems().clear();
        alerts.clear();
        // Configuring the columns of the table
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        alertDateCol.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        desCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        // Filling the table
        alerts.addAll(alts);
        alertTable.setItems(alerts);
    }


}
