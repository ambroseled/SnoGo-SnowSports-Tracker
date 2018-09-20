package seng202.team5.Control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team5.Model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MapController implements Initializable {
    @FXML
    private ToggleGroup routeSelection;
    @FXML
    private RadioButton routeARadioButton;

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    private FXMLLoader loader = new FXMLLoader();
    private Class c = getClass();


    private User user = AppController.getCurrentUser();
    private ArrayList<DataPoint> dataPoints = new ArrayList<>(user.getActivities().get(0).getDataSet().getDataPoints());

    private Route skiRoute = new Route(dataPoints);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMap();

        routeSelection.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == routeARadioButton) {
                displayRoute(skiRoute);
            } else {
                displayRoute(skiRoute);
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
    }


}
