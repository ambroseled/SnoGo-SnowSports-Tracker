package seng202.team5.Control;

import com.opencsv.CSVReader;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
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
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;


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
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        countryTable.setItems(countries);
    }



    @FXML
    /**
     * This method is called by a mouse release on the country table. it displays the fields in the field table
     * that relate to the selected country.
     */
    public void showFields() {
        WeatherField weather = (WeatherField) countryTable.getSelectionModel().getSelectedItem();
        if (weather != null) {
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
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("nix") >=0 || os.indexOf("nux") >=0) {
            openLinux();
        } else {
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
        if (!scriptLoaded) {
            showMessage();
        } else {
            hideMessage();
        }

        if (weatherField != null && scriptLoaded) {
            hideMessage();
            // Displaying the weather for the selected field
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
