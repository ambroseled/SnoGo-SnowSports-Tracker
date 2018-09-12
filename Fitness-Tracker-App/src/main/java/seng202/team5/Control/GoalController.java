package seng202.team5.Control;



import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.Model.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class handles controlling the goal view of the application.
 */
public class GoalController {

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
    private Button viewButton;
    @FXML
    private Button refreshButton;
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
    private TextField dateEntry;
    @FXML
    private CheckBox dateCheck;
    @FXML
    private ComboBox<Double> valueCombo;
    @FXML
    private CheckBox valueCheck;

    private ObservableList<Goal> goals = FXCollections.observableArrayList();
    private User currentUser = AppController.getCurrentUser();
    private DataBaseController db = AppController.getDb();


    @FXML
    /**
     * Called by a press of the viewButton, this method fills the goal table
     * with all of the users goals.
     */
    public void viewData() {
        viewButton.setVisible(false);
        viewButton.setDisable(true);
        refreshButton.setVisible(true);
        refreshButton.setDisable(false);

        for (Goal i : currentUser.getGoals()) {
            goals.add(i);
        }


        nameCol.setCellValueFactory(new PropertyValueFactory<Goal, String>("name"));
        metricCol.setCellValueFactory(new PropertyValueFactory<Goal, String>("metric"));
        valueCol.setCellValueFactory(new PropertyValueFactory<Goal, Double>("metricGoal"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Goal, String>("dateString"));
        compCol.setCellValueFactory(new PropertyValueFactory<Goal, Boolean>("completed"));

        goalTable.setItems(goals);
    }


    @FXML
    /**
     * Called by a press of the refreshButton, this method clears and then refills
     * the goal table with all of the users goals.
     */
    public void refreshData() {
        goalTable.getItems().clear();


        for (Goal i : currentUser.getGoals()) {
            goals.add(i);
        }

        goalTable.setItems(goals);

    }


    @FXML
    /**
     *
     */
    public void nameEntry() {
        String text = goalName.getText();
        if (text.length() > 4 && text.length() < 25) {
            nameCheck.setSelected(true);
        } else {
            nameCheck.setSelected(false);
        }
        if (metricCombo.getItems().size() == 0) {
            fillCombo();
        }

        checkChecks();
    }


    /**
     *
     */
    private void fillCombo() {
        ObservableList<String> metrics = FXCollections.observableArrayList();
        metrics.addAll("Top Speed, (m/s)", "Distance Traveled, (km)", "Vertical Distance, (km)", "Average Heart Rate, (bpm)");
        metricCombo.getItems().addAll(metrics);
    }


    @FXML
    /**
     *
     */
    public void checkMetricCombo() {
        boolean selected = metricCombo.getSelectionModel().isEmpty();
        if (!selected) {
            metricCheck.setSelected(true);
            fillValueCombo(metricCombo.getSelectionModel().getSelectedItem());
        } else {
            metricCheck.setSelected(false);
        }
        checkChecks();
    }


    @FXML
    /**
     *
     */
    public void checkDate() {
        String text = dateEntry.getText();
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/mm/yyyy");
            Date date = dateTimeFormat.parse(text);
            dateCheck.setSelected(true);
        } catch (ParseException e) {
            dateCheck.setSelected(false);
        }
    }


    /**
     *
     * @param metric
     */
    private void fillValueCombo(String metric) {
        valueCombo.setDisable(false);
        valueCombo.getItems().clear();
        ObservableList<Double> values = FXCollections.observableArrayList();
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
            for (double i = 50; i < 75; i++) {
                values.add(i);
            }
            valueCombo.getItems().addAll(values);
        }
    }


    @FXML
    /**
     *
     */
    public void checkValueCombo() {

        boolean selected = valueCombo.getSelectionModel().isEmpty();
        if (!selected) {
            valueCheck.setSelected(true);
        } else {
            valueCheck.setSelected(false);
        }
        checkChecks();
    }


    @FXML
    /**
     *
     */
    public void createGoal() {
        String name = goalName.getText();
        String metric = metricCombo.getSelectionModel().getSelectedItem();
        double value = valueCombo.getSelectionModel().getSelectedItem();
        String dateString = dateEntry.getText();
        metric = getMetric(metric);
        Goal newGoal = new Goal(name, metric, value, dateString, false);
        currentUser.addGoal(newGoal);

        // Store the goal into the database
        /*
        db.storeGoal(newGoal, currentUser.getId());
        */


        nameCheck.setSelected(false);
        metricCheck.setSelected(false);
        dateCheck.setSelected(false);
        valueCheck.setSelected(false);
        goalName.clear();
        metricCombo.getItems().clear();
        dateEntry.clear();
        valueCombo.getItems().clear();
        refreshData();
    }




    /**
     *
     * @param metric
     * @return
     */
    private String getMetric(String metric) {
        int index = 0;
        for (int i = 0; i < metric.length(); i++) {
            if (metric.charAt(i) == ',') {
                index = i;
                break;
            }
        }
        return metric.substring(0, index);
    }


    /**
     *
     */
    private void checkChecks() {
        if (valueCheck.isSelected() && metricCheck.isSelected() && dateCheck.isSelected() && nameCheck.isSelected()) {
            createButton.setDisable(false);
            createButton.setOpacity(0.7);
        } else {
            createButton.setDisable(true);
        }
    }

}
