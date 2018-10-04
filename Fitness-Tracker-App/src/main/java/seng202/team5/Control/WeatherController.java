package seng202.team5.Control;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.beans.value.ObservableValue;
import seng202.team5.Model.WeatherField;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


//TODO Add reference or something to web site used https://www.snow-forecast.com/resorts/Porters-Heights/6day/mid

/**
 * This class provides functionality for the user to view the weather forecast for multiple ski fields over
 * New Zealand.
 */
public class WeatherController {

    @FXML
    private WebView webView;
    @FXML
    private TableView fieldTable;
    @FXML
    private TableColumn<WeatherField, String> fieldCol;


    private WebEngine webEngine;
    private ObservableList<WeatherField> fields = makeFields();
    private boolean scriptLoaded = false;


    @FXML
    /**
     * This mehtod is called on selection of the Weather tab. It fills the fields table with all available fields
     * for which the user can view the weather.
     */
    public void showTables() {
        fieldCol.setCellValueFactory(new PropertyValueFactory<>("field"));

        fieldTable.setItems(fields);
    }


    /**
     * This method is called on initialization of the class. It loads the html for the weather and then waits for
     * the javascript to load, when the script is loaded a boolean flag is raised which allows the user to select
     * a field to view.
     */
    public void initialize() {
      webEngine = webView.getEngine();
      webEngine.load(WeatherController.class.getResource("/View/weather.html").toExternalForm());
      webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
          public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
              if (newState == Worker.State.SUCCEEDED) {
                  scriptLoaded = true;
              } else {
                  scriptLoaded = false;
              }
          }
      });
      webView.setStyle("-fx-background-color: red;");
    }


    @FXML
    /**
     * Called by a selection on the fieldsTable. This method shows the weather for the selected field
     */
    public void showWeather() {
        WeatherField weatherField = (WeatherField) fieldTable.getSelectionModel().getSelectedItem();
        if (weatherField != null && scriptLoaded) {
            String url = weatherField.getUrl();
            webEngine.executeScript("changeMt(" + "'" + url + "');");
            webView.setVisible(true);
        }
    }


    /**
     * Making an observable list of all ski fields that the weather can be viewed for.
     * @return An ObservableList holding WeatherFields for all fields.
     */
    private ObservableList<WeatherField> makeFields() {
        ObservableList<WeatherField> fieldsNames = FXCollections.observableArrayList();
        fieldsNames.add(new WeatherField("Broken River", "Broken-River"));
        fieldsNames.add(new WeatherField("Cardrona", "Cardrona"));
        fieldsNames.add(new WeatherField("Coronet Peak", "Coronet-Peak"));
        fieldsNames.add(new WeatherField("Craigieburn", "Craigieburn"));
        fieldsNames.add(new WeatherField("Mount Cheeseman", "Mount-Cheeseman"));
        fieldsNames.add(new WeatherField("Mount Cook", "Mount-Cook"));
        fieldsNames.add(new WeatherField("Mount Dobson", "Mount-Dobson"));
        fieldsNames.add(new WeatherField("Mount Hutt", "Mount-Hutt"));
        fieldsNames.add(new WeatherField("Mount Lyford", "Mount-Lyford"));
        fieldsNames.add(new WeatherField("Mount Olympus", "Mount-Olympus"));
        fieldsNames.add(new WeatherField("Ohau", "Ohau"));
        fieldsNames.add(new WeatherField("Porter Heights", "Porters-Heights"));
        fieldsNames.add(new WeatherField("Rainbow", "Rainbow"));
        fieldsNames.add(new WeatherField("Remarkables", "Remarkables"));
        fieldsNames.add(new WeatherField("Round Hill", "Round-Hill"));
        fieldsNames.add(new WeatherField("Soho Basin", "Soho-Basin"));
        fieldsNames.add(new WeatherField("Temple Basin", "Temple-Basin"));
        fieldsNames.add(new WeatherField("Treble Cone", "Treble-Cone"));
        fieldsNames.add(new WeatherField("Tukino", "Tukino"));
        fieldsNames.add(new WeatherField("Turoa", "Turoa"));
        fieldsNames.add(new WeatherField("Whakapapa", "Whakapapa"));
        return fieldsNames;
    }


}
