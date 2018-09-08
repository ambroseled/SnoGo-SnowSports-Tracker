package seng202.team5.Control;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.Model.*;

import java.util.Date;


public class goalController {

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

    private ObservableList<Goal> goals = FXCollections.observableArrayList();
    

    @FXML
    public void viewData() {
        Goal goal = new Goal("test", "Top speed", 20, "04/03/2019", false);
        goals.add(goal);

        nameCol.setCellValueFactory(new PropertyValueFactory<Goal, String>("name"));
        metricCol.setCellValueFactory(new PropertyValueFactory<Goal, String>("metric"));
        valueCol.setCellValueFactory(new PropertyValueFactory<Goal, Double>("metricGoal"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Goal, Date>("completionDate"));
        compCol.setCellValueFactory(new PropertyValueFactory<Goal, Boolean>("completed"));

        goalTable.setItems(goals);
    }
}
