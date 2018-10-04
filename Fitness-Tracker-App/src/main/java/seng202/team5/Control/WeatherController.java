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

import java.util.concurrent.TimeUnit;


//TODO Add reference or something to web site used

public class WeatherController {

    @FXML
    private WebView webView;
    @FXML
    private TableView countryTable;
    @FXML
    private TableView fieldTable;
    @FXML
    private TableColumn<WeatherField, String> countryCol;
    @FXML
    private TableColumn<WeatherField, String> fieldCol;


    private WebEngine webEngine;
    private ObservableList<WeatherField> fields = FXCollections.observableArrayList();
    private ObservableList<WeatherField> country = FXCollections.observableArrayList();
    private boolean scriptLoaded = false;


    @FXML
    public void showTables() {
        WeatherField test = new WeatherField("Broken River", "New Zealand", "Broken-River");
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        fieldCol.setCellValueFactory(new PropertyValueFactory<>("field"));

        fields.add(test);
        countryTable.setItems(fields);
        fieldTable.setItems(fields);
    }



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
    }


    @FXML
    public void showWeather() {
        WeatherField weatherField = (WeatherField) fieldTable.getSelectionModel().getSelectedItem();
        if (weatherField != null && scriptLoaded) {
            String url = weatherField.getUrl();
            webEngine.executeScript("changeMt(" + "'" + url + "');");
            webView.setVisible(true);
        }
    }



}
