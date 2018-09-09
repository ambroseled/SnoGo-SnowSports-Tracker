package seng202.team5.Control;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.Model.*;


import java.util.Date;


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
    private TableColumn<Goal, Date> dateCol;
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
    private DatePicker dateEntry;
    @FXML
    private CheckBox dateCheck;
    @FXML
    private ComboBox<Double> valueCombo;
    @FXML
    private CheckBox valueCheck;

    private ObservableList<Goal> goals = FXCollections.observableArrayList();
    private User currentUser = AppController.getCurrentUser();


    @FXML
    /**
     * Fills the TableView with all of the uses goals.
     */
    public void viewData() {
        viewButton.setVisible(false);
        viewButton.setDisable(true);
        refreshButton.setVisible(true);
        refreshButton.setDisable(false);
        Goal goal = new Goal("test", "Top speed", 20, "04/03/2019 09:47:00", false);
        currentUser.addGoal(goal);
        for (Goal i : currentUser.getGoals()) {
            goals.add(i);
        }


        nameCol.setCellValueFactory(new PropertyValueFactory<Goal, String>("name"));
        metricCol.setCellValueFactory(new PropertyValueFactory<Goal, String>("metric"));
        valueCol.setCellValueFactory(new PropertyValueFactory<Goal, Double>("metricGoal"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Goal, Date>("completionDate"));
        compCol.setCellValueFactory(new PropertyValueFactory<Goal, Boolean>("completed"));

        goalTable.setItems(goals);
    }


    @FXML
    /**
     * Refreshes the data in the TableView.
     */
    public void refreshData() {
        goalTable.getItems().clear();


        for (Goal i : currentUser.getGoals()) {
            goals.add(i);
        }

        goalTable.setItems(goals);

    }


    @FXML
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
        // Until date entry is fixed
        dateCheck.setSelected(true);
        ////////

        checkChecks();
    }


    private void fillCombo() {
        ObservableList<String> metrics = FXCollections.observableArrayList();
        metrics.addAll("Top Speed, (m/s)", "Distance Traveled, (km)", "Vertical Distance, (km)", "Average Heart Rate, (bpm)");
        metricCombo.getItems().addAll(metrics);
    }


    @FXML
    public void checkMetricCombo() {
        boolean selected = metricCombo.getSelectionModel().isEmpty();
        if (!selected) {
            metricCheck.setSelected(true);
            fillValueCombo(metricCombo.getSelectionModel().getSelectedItem());
        } else {
            metricCheck.setSelected(true);
        }
        checkChecks();
    }


    @FXML
    public void checkDate() {

    }


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
    public void checkValueCombo() {
        boolean selected = valueCombo.getSelectionModel().isEmpty();
        if (!selected) {
            valueCheck.setSelected(true);
        } else {
            valueCheck.setSelected(true);
        }
        checkChecks();
    }


    @FXML
    public void createGoal() {
        String name = goalName.getText();
        String metric = metricCombo.getSelectionModel().getSelectedItem();
        double value = valueCombo.getSelectionModel().getSelectedItem();
        metric = getMetric(metric);
        Goal newGoal = new Goal(name, metric, value, "04/08/2019 07:45:00", false);
        currentUser.addGoal(newGoal);
    }


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


    private void checkChecks() {
        if (valueCheck.isSelected() && metricCheck.isSelected() && dateCheck.isSelected() && nameCheck.isSelected()) {
            createButton.setDisable(false);
        } else {
            createButton.setDisable(true);
        }
    }

}
