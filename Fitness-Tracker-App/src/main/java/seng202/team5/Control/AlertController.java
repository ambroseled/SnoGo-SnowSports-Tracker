package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.Alert;
import seng202.team5.Model.User;

/**
 * This class is used to control the alerts view of the application.
 */
public class AlertController {

    // Java fx elements used in controller
    @FXML
    private TableColumn<Alert, String> nameCol;
    @FXML
    private TableColumn<Alert, String> dateCol;
    @FXML
    private TableColumn<Alert, String> desCol;
    @FXML
    private TableView alertTable;
    private ObservableList<Alert> alerts = FXCollections.observableArrayList();
    // Getting database controller and current user
    private User currentUser = App.getCurrentUser();
    private DataBaseController db = App.getDb();


    @FXML
    /**
     * Called by mouse movement on the anchor pane. It
     * fills the alerts table with all of the users alerts if the number
     * of alerts in teh table is not equal to teh number of alerts the user has.
     */
    public void viewData() {
        // Checking if the table needs to be refilled
        if (alertTable.getItems().size() != currentUser.getAlerts().size()) {
            alertTable.getItems().clear();
            // Getting the users alerts
            alerts.addAll(db.getAlerts(currentUser.getId()));
            // Setting table columns
            nameCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            desCol.setCellValueFactory(new PropertyValueFactory<>("message"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("dateString"));
            // Adding alerts to the table
            alertTable.setItems(alerts);
        }
    }


    @FXML
    /**
     * This method clears the current contents of the table and
     * refills it with all of the users alerts.
     */
    public void refreshData() {

        if (alertTable.getItems().isEmpty()) {
            // Setting table columns
            nameCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            desCol.setCellValueFactory(new PropertyValueFactory<>("message"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        } else {
            // Emptying the table
            alertTable.getItems().clear();
        }
        // Refilling the table
        alerts.addAll(db.getAlerts(currentUser.getId()));
        alertTable.setItems(alerts);

    }

    @FXML
    /**
     * This method is called by a press to the deleteButton. It gets the selected
     * goal from the goal table and removes it from teh user and the database. The
     * goal table is then updated.
     */
    private void removeAlert() {
        // Getting the selected alert
        Alert alert = (Alert) alertTable.getSelectionModel().getSelectedItem();
        // Removing the alert from the user and the database
        if (alert != null) {
            db.removeAlert(alert);
            currentUser.setAlerts(db.getAlerts(currentUser.getId()));
            // Refreshing the data in teh table
            refreshData();
        }

    }


}
