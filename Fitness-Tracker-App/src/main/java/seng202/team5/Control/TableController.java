package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;

import java.util.ArrayList;
import java.util.Date;


public class TableController {
    private TableView table = new TableView();
    private ArrayList<Activity> activities;
    TableColumn<DataPoint, Date> dateTimeCol;

    TableColumn<DataPoint, Integer> heartRateCol;
    TableColumn<DataPoint, Double> latitudeCol;
    TableColumn<DataPoint, Double> longitudeCol;
    TableColumn<DataPoint, Double> elevationCol;

    public TableController(ArrayList<Activity> inputActivities){
        activities = inputActivities;
        initialise();
    }


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

    private ObservableList<DataPoint> getDataPointsList() {
        ObservableList<DataPoint> dataPointsList = FXCollections.observableArrayList();

        for (Activity activity: activities) {
            DataSet dataSet = activity.getDataSet();
            dataPointsList.addAll(dataSet.getDataPoints());
        }

        return dataPointsList;
    }
}
