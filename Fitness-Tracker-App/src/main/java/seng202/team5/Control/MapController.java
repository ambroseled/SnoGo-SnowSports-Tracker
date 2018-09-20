
package seng202.team5.Control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team5.Model.Activity;
import seng202.team5.Model.InputDataParser;
import seng202.team5.Model.Route;
import seng202.team5.Model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable {
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

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    private FXMLLoader loader = new FXMLLoader();
    private Class c = getClass();


    private Route routeA = new Route(
            new Position(37.772, -122.214),
            new Position(21.291, -157.821),
            new Position(-18.142, 178.431),
            new Position(-27.467, 153.027)
    );

    private Route routeB = new Route(
            new Position(-33.946111, 151.177222),
            new Position(-43.489444, 172.532222)
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMap();

        routeSelection.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == routeARadioButton) {
                displayRoute(routeA);
            } else {
                displayRoute(routeB);
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