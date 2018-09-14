package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.Data.DataBaseController;
import seng202.team5.Model.Alert;
import seng202.team5.Model.User;

/**
 * This class is used to control the alerts view of the application.
 */
public class AlertController {

    @FXML
    private Button viewButton;
    @FXML
    private TableColumn<Alert, String> nameCol;
    @FXML
    private TableColumn<Alert, String> dateCol;
    @FXML
    private TableColumn<Alert, String> desCol;
    @FXML
    private TableColumn<Alert, String> webCol;
    @FXML
    private TableView alertTable;
    @FXML
    private Button refreshButton;
    private ObservableList<Alert> alerts = FXCollections.observableArrayList();
    private User currentUser = AppController.getCurrentUser();
    private DataBaseController db = new DataBaseController();


    @FXML
    /**
     * Called by a button press of the viewButton. It
     * fills the alerts table with all of the users alerts.
     */
    public void viewData() {
        viewButton.setVisible(false);
        viewButton.setDisable(true);
        refreshButton.setVisible(true);
        refreshButton.setDisable(false);


        alerts.addAll(db.getAlerts(currentUser.getId()));


        nameCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("name"));
        desCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("message"));
        webCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("webLink"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("dateString"));


        alertTable.setItems(alerts);
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

}
