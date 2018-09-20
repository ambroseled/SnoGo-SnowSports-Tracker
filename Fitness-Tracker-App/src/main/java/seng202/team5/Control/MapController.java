package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.*;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MapController implements Initializable {

    @FXML
    private WebView webView;

    private ArrayList<Activity> activities;
    private WebEngine webEngine;

    @FXML
    private TableView actTable;
    @FXML
    private TableColumn<Activity, String> actCol;



    private User user = AppController.getCurrentUser();
    private ArrayList<DataPoint> dataPoints;
    private DataBaseController db = AppController.getDb();
    private ObservableList<Activity> activityNames = FXCollections.observableArrayList();
    private Route skiRoute;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMap();
/*
        routeSelection.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == routeARadioButton) {
                displayRoute(skiRoute);
            } else {
                displayRoute(skiRoute);
            }
        });
        */
    }

    private void initMap() {
        webEngine = webView.getEngine();
        webEngine.load(MapController.class.getResource("/View/map.html").toExternalForm());
    }

    private void displayRoute(Route newRoute) {
        String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
        webEngine.executeScript(scriptToExecute);
    }



    private void displayAct(Activity act) {
        dataPoints = act.getDataSet().getDataPoints();
        skiRoute = new Route(dataPoints);
        displayRoute(skiRoute);
    }



    @FXML
    public void showData() {
        Activity activity =  (Activity) actTable.getSelectionModel().getSelectedItem();
        if (activity != null) {
            displayAct(activity);
        }
    }



    @FXML
    public void fillTable() {
        if (actTable.getItems().size() != user.getActivities().size()) {
            activities = db.getActivities(user.getId());

            actCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            activityNames.addAll(activities);
            actTable.setItems(activityNames);
        }
    }



}
