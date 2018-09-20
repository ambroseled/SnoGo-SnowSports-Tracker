package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MapController implements Initializable {

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    @FXML
    private ChoiceBox activityChoice;



    private User user = AppController.getCurrentUser();
    private ArrayList<DataPoint> dataPoints;
    private DataBaseController db = AppController.getDb();

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
    /**
     * Clears all data from graphs
     * Sets values of labelled data (max, min etc)
     * Sets graphs to the values of activity selected in choiceBox
     */
    public void selectData() {
        Activity currentActivity = (Activity) activityChoice.getValue();
        if (!(currentActivity == null)) {
            displayAct(currentActivity);
        }
    }

    @FXML
    /**
     * Runs when the tab is first switched to
     * Sets up the choiceBox to show all activities for current User
     */
    public void setChoiceBox() {
        if (activityChoice.getItems().size() != db.getActivities(user.getId()).size()) {
            ArrayList<Activity> inputActivities = db.getActivities(user.getId());

            ObservableList<Activity> activityNames = FXCollections.observableArrayList();
            for (Activity activity: inputActivities) {
                activityNames.add(activity);
            }
            activityChoice.setItems(activityNames);
        }

    }



}
