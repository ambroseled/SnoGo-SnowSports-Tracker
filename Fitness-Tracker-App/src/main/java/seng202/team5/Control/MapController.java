package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * 
 */
public class MapController implements Initializable {

    // Java fx elements used in controller
    @FXML
    private WebView webView;
    @FXML
    private TableView actTable;
    @FXML
    private TableColumn<Activity, String> actCol;
    private ArrayList<Activity> activities;
    private WebEngine webEngine;
    private User user = AppController.getCurrentUser();
    private ArrayList<DataPoint> dataPoints;
    private DataBaseController db = AppController.getDb();
    private ObservableList<Activity> activityNames = FXCollections.observableArrayList();
    private Route skiRoute;


    @Override
    /**
     *
     */
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


    /**
     *
     */
    private void initMap() {
        webEngine = webView.getEngine();
        webEngine.load(MapController.class.getResource("/View/map.html").toExternalForm());
    }


    /**
     *
     * @param newRoute
     */
    private void displayRoute(Route newRoute) {
        String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
        webEngine.executeScript(scriptToExecute);
    }


    /**
     *
     * @param act
     */
    private void displayAct(Activity act) {
        dataPoints = act.getDataSet().getDataPoints();
        skiRoute = new Route(dataPoints);
        displayRoute(skiRoute);
    }


    @FXML
    /**
     * Called by a mouse click on the activity table. Shows the selected activity on the map
     */
    public void showData() {
        Activity activity =  (Activity) actTable.getSelectionModel().getSelectedItem();
        if (activity != null) {
            displayAct(activity);
        }
    }


    @FXML
    /**
     * Called by a mouse movement on the anchor pane. Fills the table with all of
     * the users activities if the number of activities in the table is not equal
     * to the number of activities the user has.
     */
    public void fillTable() {
        if (actTable.getItems().size() != user.getActivities().size()) {
            activities = db.getActivities(user.getId());
            actCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            activityNames.addAll(activities);
            actTable.setItems(activityNames);
        }
    }


}
