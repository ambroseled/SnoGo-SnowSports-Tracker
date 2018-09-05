package seng202.team5.Control;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;
import seng202.team5.Model.InputDataParser;

import java.util.ArrayList;
import java.util.Date;


public class TableController extends Application {

    @FXML
    private TableView<DataPoint> table;

    private TableColumn<DataPoint, Date> dateTimeCol;
    private TableColumn<DataPoint, Integer> heartRateCol;
    private TableColumn<DataPoint, Double> latitudeCol;
    private TableColumn<DataPoint, Double> longitudeCol;
    private TableColumn<DataPoint, Double> elevationCol;
    private TableColumn<DataPoint, Double> distanceCol;
    private TableColumn<DataPoint, Double> speedCol;

    private ArrayList<Activity> activities;

    public TableController() {}

    private void initialise() {
        // date and time column
        dateTimeCol = new TableColumn("Date and Time");
        dateTimeCol.setCellValueFactory(new PropertyValueFactory("dateTime"));

        //heart rate column
        heartRateCol = new TableColumn("Heart Rate");
        heartRateCol.setCellValueFactory(new PropertyValueFactory("heartRate"));

        //latitude column
        latitudeCol = new TableColumn("Latitude");
        latitudeCol.setCellValueFactory(new PropertyValueFactory("latitude"));

        //longitude column
        longitudeCol = new TableColumn("Longitude");
        longitudeCol.setCellValueFactory(new PropertyValueFactory("longitude"));

        //elevation column
        elevationCol = new TableColumn("Elevation");
        elevationCol.setCellValueFactory(new PropertyValueFactory("elevation"));

        //distance column
        distanceCol = new TableColumn("Distance");
        distanceCol.setCellValueFactory(new PropertyValueFactory("distance"));

        //speed column
        speedCol = new TableColumn("Speed");
        speedCol.setCellValueFactory(new PropertyValueFactory("speed"));


        table.getColumns().addAll(dateTimeCol, heartRateCol, latitudeCol, longitudeCol, elevationCol, distanceCol, speedCol);
        table.setItems(getDataPointsList());

    }

    public ObservableList<DataPoint> getDataPointsList() {
        ObservableList<DataPoint> dataPointsList = FXCollections.observableArrayList();

        DataSet dataSet = activities.get(0).getDataSet();
        dataPointsList.addAll(dataSet.getDataPoints());
      /*  for (Activity activity: activities) {
            DataSet dataSet = activity.getDataSet();
            for (DataPoint dataPoint : dataSet.getDataPoints()) {
                System.out.println(dataPoint);
            }
            //dataPointsList.addAll(dataSet.getDataPoints());
        }
        System.out.println();
        //System.out.println(dataPointsList);
       */
        return dataPointsList;
    }
    public static void main(String[] args) {
        launch(args);

    }

    private void setActivities(ArrayList<Activity> inputActivities) {
        activities = inputActivities;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        InputDataParser inputDataParser = new InputDataParser();
        ArrayList<Activity> inputActivities = inputDataParser.parseCSVToActivities("testData.csv");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TableTab.fxml"));
        Parent root = loader.load();
        TableController controller = loader.getController();
        controller.setActivities(inputActivities);
        //this.initialise();


        controller.initialise();
        //Parent root = FXMLLoader.load(getClass().getResource("/View/tableTab.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
