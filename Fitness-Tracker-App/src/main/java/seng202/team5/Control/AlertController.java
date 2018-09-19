package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML
    private TableColumn<Alert, String> nameCol;
    @FXML
    private TableColumn<Alert, String> dateCol;
    @FXML
    private TableColumn<Alert, String> desCol;
    @FXML
    private TableView alertTable;
    private ObservableList<Alert> alerts = FXCollections.observableArrayList();
    private User currentUser = AppController.getCurrentUser();
    private DataBaseController db = AppController.getDb();


    @FXML
    /**
     * Called by a button press of the viewButton. It
     * fills the alerts table with all of the users alerts.
     */
    public void viewData() {
        if (alertTable.getItems().size() != currentUser.getAlerts().size()) {
            alerts.addAll(db.getAlerts(currentUser.getId()));


            nameCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("type"));
            desCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("message"));
            dateCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("dateString"));


            alertTable.setItems(alerts);
        }
    }


    @FXML
    /**
     * Called byt a button press of the refreshButton. It clears the current
     * contents of the table and refills it with all of the users alerts.
     */
    public void refreshData() {
        alertTable.getItems().clear();

        alerts.addAll(db.getAlerts(currentUser.getId()));


        alertTable.setItems(alerts);

    }

    @FXML
    /**
     * This method is called by a press to teh deleteButton. It gets the selected
     * goal from the goal table and removes it from teh user and the database. The
     * goal table is then updated.
     */
    private void removeAlert() {
        Alert alert = (Alert) alertTable.getSelectionModel().getSelectedItem();
        db.removeAlert(alert);
        currentUser.removeAlert(alert);
        refreshData();
    }


}
