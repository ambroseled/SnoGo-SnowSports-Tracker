package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.*;
import java.util.ArrayList;


/**
 * Controller class for the mapView.fxml class.
 * Displays the activities of a user onto a map (using the Google Maps API).
 */
public class MapController {

    // Java fx elements used in controller
    @FXML
    private WebView webView;
    @FXML
    private TableView actTable;
    @FXML
    private TableColumn<Activity, String> actCol;

    private ArrayList<Activity> activities;
    private WebEngine webEngine;
    private User user = App.getCurrentUser();
    private DataBaseController db = App.getDb();
    private ObservableList<Activity> activityNames = FXCollections.observableArrayList();


    /**
     * Initialises the web engine.
     */
    public void initialize() {
        webEngine = webView.getEngine();
        webEngine.load(MapController.class.getResource("/View/map.html").toExternalForm());
    }


    /**
     * Given a route, displays the route (path) on the map.
     * Calls webEngine, so the method requires an internet connection.
     * However, if there is no internet connection, the error is caught earlier in the stack trace.
     *
     * @param newRoute Route to be displayed
     */
    private void displayRoute(Route newRoute) {
        String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
        webEngine.executeScript(scriptToExecute);
    }


    @FXML
    /**
     * Called by a mouse click on the activity table. Shows the selected activity on the map
     */
    public void showData() {
        try {
            Activity activity =  (Activity) actTable.getSelectionModel().getSelectedItem();
            if (activity != null) {
                Route route = new Route(activity.getDataSet().getDataPoints());
                displayRoute(route);
            }
        } catch (netscape.javascript.JSException e) {
            ErrorController.displayError("Internet connection is need to view map");
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
