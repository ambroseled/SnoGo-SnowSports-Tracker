package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team5.Model.Activity;
import seng202.team5.Model.InputDataParser;
import seng202.team5.Model.Route;
import seng202.team5.Model.User;

import java.util.ArrayList;


/**
 * This class handles the controls of the map view
 */
public class MapController {

    private ArrayList<Activity> activities;
    private User currentUser;
    private InputDataParser parser = new InputDataParser();

    @FXML
    private ComboBox<String> activityCombo;
    @FXML
    private Button selectButton;
    @FXML
    private Button loadButton;
    @FXML
    private WebView webView;
    private WebEngine webEngine;
    private ObservableList<String> names = FXCollections.observableArrayList();



    //For testing
    User user = AppController.getCurrentUser();

    @FXML
    public void displayActivities() {
        selectButton.setVisible(true);
        loadButton.setVisible(false);
        loadButton.setDisable(true);
        currentUser = AppController.getCurrentUser();
       // activities = currentUser.getActivities();
        activities = parser.parseCSVToActivities("testData.csv");

        if (activities != null) {
            fillList(activities);
            activityCombo.setItems(names);
        }
    }


    
    @FXML
    public void selectButtonPress() {
        String name = activityCombo.getValue();
        System.out.println(name);
        /**
         * This needs to get the activity out of the list
         */
    }


    private void fillList(ArrayList<Activity> activities) {
        for (Activity activity : activities) {
            names.add(activity.getName());
        }
    }

/*

    public void initialize() {
        setUpMap();
        Route route = new Route(user.getActivities().get(0).getDataSet().getDataPoints());

        String executeString = "displayRoute(" + route.toJSONArray() + ");";
        webEngine.executeScript(executeString);
    }


    private void setUpMap() {
        webEngine = webView.getEngine();
        webEngine.load(this.getClass().getClassLoader().getResource("map.html").toExternalForm());
    }


    */


}
