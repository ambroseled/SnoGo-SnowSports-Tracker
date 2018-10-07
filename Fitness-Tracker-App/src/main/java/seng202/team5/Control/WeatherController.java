package seng202.team5.Control;

import com.opencsv.CSVReader;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.beans.value.ObservableValue;
import seng202.team5.Model.WeatherField;
import java.awt.*;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


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
    @FXML
    private TableView countryTable;
    @FXML
    private TableColumn<WeatherField, String> countryCol;
    @FXML
    private Pane coverView;


    private WebEngine webEngine;
    private ObservableList<WeatherField> countries = FXCollections.observableArrayList();
    private ObservableList<WeatherField> fields = parseFields();
    private boolean scriptLoaded = false;


    @FXML
    /**
     * This method is called on selection of the Weather tab. It fills the country table with all available countries
     * for which the user can select fields to view the weather for
     */
    public void showTables() {
        // Setting up the column of the table
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        countryTable.setItems(countries);
    }



    @FXML
    /**
     * This method is called by a mouse release on the country table. it displays the fields in the field table
     * that relate to the selected country.
     */
    public void showFields() {
        // Getting the selected weather field
        WeatherField weather = (WeatherField) countryTable.getSelectionModel().getSelectedItem();
        if (weather != null) {
            // Filling the field column if the weather field is not null
            ObservableList<WeatherField> forCountry = findFields(weather.getCountry());
            fieldCol.setCellValueFactory(new PropertyValueFactory<>("field"));
            fieldTable.setItems(forCountry);
        }
    }


    /**
     * This method finds all the WeatherFields that are for a passed country
     * @param country The country to find fields for
     * @return All the found fields
     */
    private ObservableList<WeatherField> findFields(String country) {
        ObservableList<WeatherField> forCountry = FXCollections.observableArrayList();
        // Looping over all fields to find those who have the passed country
        for (WeatherField x : fields) {
            if (x.getCountry().equals(country)) {
                // Adding the field to the list
                forCountry.add(x);
            }
        }
        return forCountry;
    }


    /**
     * This method is called on initialization of the class. It loads the html for the weather and then waits for
     * the javascript to load, when the script is loaded a boolean flag is raised which allows the user to select
     * a field to view.
     */
    public void initialize() {
        // Setting up the web view
        webEngine = webView.getEngine();
        webEngine.load(WeatherController.class.getResource("/View/weather.html").toExternalForm());
        // Watching for a change of the load worker to see when the java script has been loaded
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
          public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
              if (newState == Worker.State.SUCCEEDED) {
                  scriptLoaded = true;
                  hideMessage();
              } else {
                  scriptLoaded = false;
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
        // Checking if the os is linux based
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("nix") >=0 || os.indexOf("nux") >=0) {
            openLinux();
        } else {
            // Trying to open the browser
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(new URI("https://www.snow-forecast.com"));
                } else {
                    DialogController.displayError("Desktop integration no supported");
                }
            } catch (IOException e1) {
                DialogController.displayError("Issue while opening link");
            } catch (URISyntaxException e1) {
                DialogController.displayError("Issue while opening link");
            }
        }
    }


    /**
     * Used to open a link if the machine is linux based.
     */
    private void openLinux() {
        Runtime rt = Runtime.getRuntime();
        String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror",
                "netscape", "opera", "links", "lynx" };
        // Trying to open the browser
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
            DialogController.displayError("Issue while opening link");
        }
    }


    @FXML
    /**
     * Called by a selection on the fieldsTable. This method shows the weather for the selected field
     */
    public void showWeather() {
        // Grabbing the selected country
        WeatherField weatherField = (WeatherField) fieldTable.getSelectionModel().getSelectedItem();
        // Checking if the java script has been loaded

        if (weatherField != null && scriptLoaded) {
            hideMessage();
            // Displaying the weather for the selected field
            String url = weatherField.getUrl();
            webEngine.executeScript("changeMt(" + "'" + url + "');");
            webView.setVisible(true);
        }

        coverView.setVisible(false);

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
     * This method parses the csv file of weather fields into and observable list. It also builds an observable list
     * of weather fields where all country names are unique
     * @return the observable list of weather fields
     */
    private ObservableList<WeatherField> parseFields() {
        ArrayList<String> names = new ArrayList<>();
        ObservableList<WeatherField> weatherFields = FXCollections.observableArrayList();
        try {
            // making the csv reader to read the fields
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/snow-forecast.csv"));
            CSVReader csvReader = new CSVReader(reader);
            String[] nextField;
            // Reading all weather fields from the file
            while ((nextField = csvReader.readNext()) != null) {
                WeatherField field = new WeatherField(nextField[0], nextField[2], nextField[1]);
                weatherFields.add(field);
                // Adding the current field to the countries list if its country is not in the list
                if (!names.contains(field.getCountry())) {
                    names.add(field.getCountry());
                    countries.add(field);
                }
            }
        } catch (IOException e) {

        }
        return weatherFields;
    }


}
