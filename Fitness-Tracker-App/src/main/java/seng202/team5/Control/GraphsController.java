package seng202.team5.Control;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;
import seng202.team5.Model.InputDataParser;

import java.io.IOException;
import java.util.ArrayList;

public class GraphsController extends Application{

    private ArrayList<Activity> activities;

    @Override public void start(Stage stage) throws IOException {
        stage.setTitle("Speed graph");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        //creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Speed graph");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Speed");
        //populating the series with data





        InputDataParser inputDataParser = new InputDataParser();
        ArrayList<Activity> inputActivities = inputDataParser.parseCSVToActivities("testData.csv");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/GraphsTab.fxml"));
        Parent root = loader.load();
        GraphsController controller = loader.getController();
        controller.setActivities(inputActivities);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();



        for (int i = 0; i < activities.size(); i++) {
            for (DataPoint dataPoint : controller.getDataPointsList(i)) {
                series.getData().add(new XYChart.Data(dataPoint.getSpeed(), dataPoint.getDateTime()));
            }
        }

        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setActivities(ArrayList<Activity> inputActivities) {
        activities = inputActivities;
    }

    public ObservableList<DataPoint> getDataPointsList(int index) {
        ObservableList<DataPoint> dataPointsList = FXCollections.observableArrayList();

        DataSet dataSet = activities.get(index).getDataSet();
        dataPointsList.addAll(dataSet.getDataPoints());
        return dataPointsList;
    }
}