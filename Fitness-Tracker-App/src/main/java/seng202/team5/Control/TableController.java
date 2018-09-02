package seng202.team5.Control;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;
import seng202.team5.Model.InputDataParser;

import java.util.ArrayList;
import java.util.Date;


public class TableController extends Application {
    private TableView table = new TableView();
    private ArrayList<Activity> activities;
    private Stage window;

    TableColumn<DataPoint, Date> dateTimeCol;
    TableColumn<DataPoint, Integer> heartRateCol;
    TableColumn<DataPoint, Double> latitudeCol;
    TableColumn<DataPoint, Double> longitudeCol;
    TableColumn<DataPoint, Double> elevationCol;

    public TableController() {}

    private void initialise() {

        // date and time column
        dateTimeCol = new TableColumn("Date Time");
        dateTimeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, Date>("dateTime"));

        //heart rate column
        heartRateCol = new TableColumn("Heart Rate");
        heartRateCol.setCellValueFactory(new PropertyValueFactory<DataPoint, Integer>("heartRate"));

        //latitude column
        latitudeCol = new TableColumn("Latitude");
        latitudeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, Double>("latitude"));

        //longitude column
        longitudeCol = new TableColumn("Longitude");
        longitudeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, Double>("longitude"));

        //elevation column
        elevationCol = new TableColumn("Elevation");
        elevationCol.setCellValueFactory(new PropertyValueFactory<DataPoint, Double>("elevation"));

        table.setItems(getDataPointsList());
        table.getColumns().addAll(dateTimeCol, heartRateCol, latitudeCol, longitudeCol, elevationCol);
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
        System.out.println(dataPointsList);
        return dataPointsList;
    }
    public static void main(String args[]) {
        launch(args);

    }

    private void setActivities(ArrayList<Activity> inputActivities) {
        activities = inputActivities;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("TableTab.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();


/*        InputDataParser inputDataParser = new InputDataParser();
        ArrayList<Activity> inputActivities = inputDataParser.parseCSVToActivities("testData.csv");

        this.setActivities(inputActivities);
        this.initialise();
        this.getDataPointsList();

        window = primaryStage;

        Parent root = null;

        root = FXMLLoader.load(getClass().getResource("TableTab.fxml"));
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();*/


//        VBox vbox = new VBox();
//        vbox.getChildren().addAll();

    }
}
