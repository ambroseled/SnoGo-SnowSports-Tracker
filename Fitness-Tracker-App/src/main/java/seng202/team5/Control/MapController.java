
package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team5.Model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MapController {
    @FXML
    private ToggleGroup routeSelection;
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


    private FXMLLoader loader = new FXMLLoader();
    private Class c = getClass();



    /*
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMap();

        routeSelection.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == routeARadioButton) {
                displayRoute(skiRoute);
            } else {
                displayRoute(new Route(g));
            }
        });
    }

    private void initMap() {
        webEngine = webView.getEngine();
        webEngine.load(MapController.class.getResource("/View/map.html").toExternalForm());
    }

    private void displayRoute(Route newRoute) {
        String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
        webEngine.executeScript(scriptToExecute);
    } */



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





}