package seng202.team5.Control;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.beans.value.ObservableValue;
import seng202.team5.Model.WeatherField;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * This class provides functionality for the user to view the weather forecast for multiple ski fields over
 * New Zealand. The weather functionality is provided through the use of the free weather widget provided by
 * www.snow-forecast.com.
 */
public class WeatherController {

    @FXML
    private WebView webView;
    @FXML
    private TableView fieldTable;
    @FXML
    private TableColumn<WeatherField, String> fieldCol;
    @FXML
    private ImageView warningImage;
    @FXML
    private Text warningText;


    private WebEngine webEngine;
    private ObservableList<WeatherField> fields = makeFields();
    private boolean scriptLoaded = false;


    @FXML
    /**
     * This method is called on selection of the Weather tab. It fills the fields table with all available fields
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
                  hideMessage();
              } else {
                  scriptLoaded = false;
                  showMessage();
              }
          }
        });
        if (!scriptLoaded) {
            showMessage();
        }
    }


    @FXML
    /**
     * This method is called by pressing the hyperlink below the weather view. it
     * takes the user to the full website from which the weather widget is provided
     */
    public void openSnowForecast() {
        Runtime rt = Runtime.getRuntime();
        String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror",
                "netscape", "opera", "links", "lynx" };

        StringBuffer cmd = new StringBuffer();
        for (int i = 0; i < browsers.length; i++)
            if(i == 0)
                cmd.append(String.format(    "%s \"%s\"", browsers[i], "https://www.snow-forecast.com"));
            else
                cmd.append(String.format(" || %s \"%s\"", browsers[i], "https://www.snow-forecast.com"));
        // If the first didn't work, try the next browser and so on

        try {
            rt.exec(new String[] { "sh", "-c", cmd.toString() });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    /**
     * Called by a selection on the fieldsTable. This method shows the weather for the selected field
     */
    public void showWeather() {
        WeatherField weatherField = (WeatherField) fieldTable.getSelectionModel().getSelectedItem();
        if (!scriptLoaded) {
            showMessage();
        } else {
            hideMessage();
        }

        if (weatherField != null && scriptLoaded) {
            hideMessage();
            String url = weatherField.getUrl();
            webEngine.executeScript("changeMt(" + "'" + url + "');");
            webView.setVisible(true);
        }

    }


    /**
     * This method shows the internet error message
     */
    private void showMessage() {
        warningText.setVisible(true);
        warningImage.setVisible(true);
    }


    /**
     * This method hides the internet error message
     */
    private void hideMessage() {
        warningText.setVisible(false);
        warningImage.setVisible(false);
    }



    /**
     * Making an observable list of all ski fields that the weather can be viewed for.
     * @return An ObservableList holding WeatherFields for all fields.
     */
    private ObservableList<WeatherField> makeFields() {
        ObservableList<WeatherField> fieldsNames = FXCollections.observableArrayList();
        fieldsNames.add(new WeatherField("Alpure Peaks", "AlpurePeaks"));
        fieldsNames.add(new WeatherField("Awakino Ski Area", "AwakinoSkiArea"));
        fieldsNames.add(new WeatherField("Broken River", "Broken-River"));
        fieldsNames.add(new WeatherField("Cardrona", "Cardrona"));
        fieldsNames.add(new WeatherField("Coronet Peak", "Coronet-Peak"));
        fieldsNames.add(new WeatherField("Craigieburn", "Craigieburn"));
        fieldsNames.add(new WeatherField("Fox Peak", "FoxPeak"));
        fieldsNames.add(new WeatherField("HeliPark NZ", "Mt-Potts"));
        fieldsNames.add(new WeatherField("Mount Cheeseman", "Mount-Cheeseman"));
        fieldsNames.add(new WeatherField("Mount Cook", "Mount-Cook"));
        fieldsNames.add(new WeatherField("Mount Dobson", "Mount-Dobson"));
        fieldsNames.add(new WeatherField("Mount Hutt", "Mount-Hutt"));
        fieldsNames.add(new WeatherField("Mount Lyford", "Mount-Lyford"));
        fieldsNames.add(new WeatherField("Mount Olympus", "Mount-Olympus"));
        fieldsNames.add(new WeatherField("Mount Roon", "MountRoon"));
        fieldsNames.add(new WeatherField("Ohau", "Ohau"));
        fieldsNames.add(new WeatherField("Porter Heights", "Porters-Heights"));
        fieldsNames.add(new WeatherField("Rainbow", "Rainbow"));
        fieldsNames.add(new WeatherField("Remarkables", "Remarkables"));
        fieldsNames.add(new WeatherField("Round Hill", "Round-Hill"));
        fieldsNames.add(new WeatherField("Soho Basin", "Soho-Basin"));
        fieldsNames.add(new WeatherField("Snow Farm", "Snow-Farm"));
        fieldsNames.add(new WeatherField("Snow Park", "SnowPark"));
        fieldsNames.add(new WeatherField("Temple Basin", "Temple-Basin"));
        fieldsNames.add(new WeatherField("Treble Cone", "Treble-Cone"));
        fieldsNames.add(new WeatherField("Tukino", "Tukino"));
        fieldsNames.add(new WeatherField("Turoa", "Turoa"));
        fieldsNames.add(new WeatherField("Whakapapa", "Whakapapa"));
        return fieldsNames;
    }


}
