package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.Model.Alert;
import seng202.team5.Model.Goal;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class alertController {

    @FXML
    private Button viewButton;
    @FXML
    TableColumn<Alert, String> nameCol;
    @FXML
    TableColumn<Alert, Date> dateCol;
    @FXML
    TableColumn<Alert, String> desCol;
    @FXML
    TableColumn<Alert, String> webCol;
    @FXML
    TableView alertTable;
    private ObservableList<Alert> alerts = FXCollections.observableArrayList();

    @FXML
    /**
     * Fills the TableView with all of the uses alerts.
     */
    public void viewData() {
        viewButton.setVisible(false);

        Alert alert = new Alert("04/02/2020", "www.bean.com", "Health issue detected", "Health issue");
        alerts.add(alert);

        nameCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("name"));
        desCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("message"));
        webCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("webLink"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Alert, Date>("dateTime"));


        alertTable.setItems(alerts);
    }

}
