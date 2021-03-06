package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.*;
import java.util.ArrayList;


/**
 * Controller class for the mapView.fxml class.
 * Displays the activities of a HomeController.getCurrentUser() onto a map (using the Google Maps API).
 */
public class MapController {

    // Java fx elements used in controller
    @FXML
    private WebView webView;
    @FXML
    private TableView actTable;
    @FXML
    private TableColumn<Activity, String> actCol;
    @FXML
    private ImageView warningImage;
    @FXML
    private Text warningText;


    private ArrayList<Activity> activities;
    private WebEngine webEngine;
    private DataBaseController db = HomeController.getDb();
    private ObservableList<Activity> activityNames = FXCollections.observableArrayList();


    /**
     * Initialises the web engine and load the html of the map
     */
    public void initialize() {
        webEngine = webView.getEngine();
        webEngine.load(MapController.class.getResource("/View/map.html").toExternalForm());
    }


    /**
     * Given a route, displays the route (path) on the map.
     * Calls webEngine, so the method requires an internet connection.
     * However, if there is no internet connection the error will be
     * caught and a message displayed to the user
     * @param newRoute Route to be displayed
     */
    public void displayRoute(Route newRoute) {
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
            warningText.setVisible(false);
            warningImage.setVisible(false);
        } catch (netscape.javascript.JSException e) {
            warningText.setVisible(true);
            warningImage.setVisible(true);
        }
    }

    @FXML
    /**
    * Fills the table with all of the HomeController.getCurrentUser()s activities if the number of
    * activities in the table is not equal to the number of activities the HomeController.getCurrentUser() has.
    */
    public void fillTable() {
      actTable.getItems().clear();
      if (HomeController.getCurrentUser() != null) {
          if (actTable.getItems().size() != HomeController.getCurrentUser().getActivities().size()) {
          activities = db.getActivities(HomeController.getCurrentUser().getId());
          actCol.setCellValueFactory(new PropertyValueFactory("name"));
          activityNames.addAll(activities);
          actTable.setItems(activityNames);
          actTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
          }
      }
    }


}
