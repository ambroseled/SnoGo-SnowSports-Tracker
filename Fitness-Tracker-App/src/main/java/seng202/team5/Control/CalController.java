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
 *
 */
public class CalController {


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


    private ObservableList<Activity> activities = FXCollections.observableArrayList();
    private ObservableList<Goal> goals = FXCollections.observableArrayList();
    private ObservableList<Alert> alerts = FXCollections.observableArrayList();
    private DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy");
    private DataBaseController db = HomeController.getDb();


    @FXML
    /**
     *
     */
    public void showData() {
        // Catching case where one datePicker has a value but the other doesn't
        if (datePicker.getValue() != null && datePicker1.getValue() != null) {
            Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date date1 = Date.from(datePicker1.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            fillActivities(findActivities(parseDate(date), parseDate(date1)));
            fillGoals(findGoals(parseDate(date), parseDate(date1)));
            fillAlerts(findAlerts(parseDate(date), parseDate(date1)));
        }
    }


    /**
     *
     */
    public void setCurrent() {
        if (datePicker.getValue() == null || datePicker1.getValue() == null) {
            Date date = new Date();
            datePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            datePicker1.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            fillActivities(findActivities(parseDate(date), parseDate(date)));
            fillGoals(findGoals(parseDate(date), parseDate(date)));
            fillAlerts(findAlerts(parseDate(date), parseDate(date)));
        }

    }


    private Date parseDate(Date date) {
        String dateString = dateTimeFormat.format(date);
        try {
            return dateTimeFormat.parse(dateString);
        } catch(ParseException e) {
            return null;
        }
    }



    /**
     *
     * @param date
     * @return
     */
    private ArrayList<Activity> findActivities(Date date, Date date1) {
        ArrayList<Activity> acts = new ArrayList<>();
        for (Activity activity : db.getActivities(HomeController.getCurrentUser().getId())) {
            Date actDate = parseDate(activity.getDataSet().getDateTime(0));
            if (actDate.getTime() >= date.getTime() && actDate.getTime() <= date1.getTime()) {
                acts.add(activity);
            }
        }
        return acts;
    }


    /**
     *
     * @param date
     * @return
     */
    private ArrayList<Goal> findGoals(Date date, Date date1) {
        ArrayList<Goal> gls = new ArrayList<>();
        for (Goal goal : db.getGoals(HomeController.getCurrentUser().getId())) {
            Date goalDate = parseDate(goal.getCompletionDate());
            if (goalDate.getTime() >= date.getTime() && goalDate.getTime() <= date1.getTime()) {
                gls.add(goal);
            }
        }
        return gls;
    }


    /**
     *
     * @param date
     * @return
     */
    private ArrayList<Alert> findAlerts(Date date, Date date1) {
        ArrayList<Alert> alts = new ArrayList<>();
        for (Alert alert : db.getAlerts(HomeController.getCurrentUser().getId())) {
            Date alertDate = parseDate(alert.getDate());
            if (alertDate.getTime() >= date.getTime() && alertDate.getTime() <= date1.getTime()) {
                alts.add(alert);
            }
        }
        return alts;
    }


    /**
     *
     * @param acts
     */
    private void fillActivities(ArrayList<Activity> acts) {
        actTable.getItems().clear();
        activities.clear();

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        actDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        distCol.setCellValueFactory(new PropertyValueFactory<>("totalDistance"));
        vertCol.setCellValueFactory(new PropertyValueFactory<>("verticalDistance"));
        heartCol.setCellValueFactory(new PropertyValueFactory<>("avgHeartRate"));
        calCol.setCellValueFactory(new PropertyValueFactory<>("caloriesBurned"));
        avgSpeedCol.setCellValueFactory(new PropertyValueFactory<>("avgSpeed"));
        topSpeedCol.setCellValueFactory(new PropertyValueFactory<>("topSpeed"));

        activities.addAll(acts);
        actTable.setItems(activities);
    }


    /**
     *
     * @param gls
     */
    private void fillGoals(ArrayList<Goal> gls) {
        goalTable.getItems().clear();
        goals.clear();

        goalNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        metricCol.setCellValueFactory(new  PropertyValueFactory<>("metric"));
        valCol.setCellValueFactory(new PropertyValueFactory<>("metricGoal"));
        goalDateCol.setCellValueFactory(new PropertyValueFactory<>("completionDate"));
        compCol.setCellValueFactory(new PropertyValueFactory<>("dateString"));

        goals.addAll(gls);
        goalTable.setItems(goals);
    }


    /**
     *
     * @param alts
     */
    private void fillAlerts(ArrayList<Alert> alts) {
        alertTable.getItems().clear();
        alerts.clear();

        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        alertDateCol.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        desCol.setCellValueFactory(new PropertyValueFactory<>("message"));

        alerts.addAll(alts);
        alertTable.setItems(alerts);
    }

}
