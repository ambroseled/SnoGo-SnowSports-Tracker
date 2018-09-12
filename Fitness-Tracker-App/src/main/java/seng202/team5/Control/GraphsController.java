package seng202.team5.Control;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import java.util.Date;

public class GraphsController extends Application{

    private ArrayList<Activity> activities;

    @FXML
    LineChart<Number,Number> speedChart;

    @FXML
    NumberAxis xAxis;

    @FXML
    NumberAxis yAxis;


    private XYChart.Series createGraph() {
        xAxis.setLabel("Time");
        xAxis.setForceZeroInRange(false); //Stops the chart starting at (0,0) every time

        //Defining the y axis
        yAxis.setLabel("Speed");

        speedChart.setTitle("Speed graph");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.getData();
        series.setName("Speed"); //
        //populating the series with data

        return series;
    }

    private void populateGraph(XYChart.Series series) {
        for (int i = 0; i < activities.size(); i++) {


             //Need to set lower and upper bounds, but this command isn't working


            /////////////////
            /**
             * You should save the time of the first dataPoint and then set it to zero,
             * and then for all other DataPoints minus the time of the first dataPoint to
             * get their relative time value.
             */
            /////////////////


            for (DataPoint dataPoint : getDataPointsList(3)) {
                long timeVal = (dataPoint.getDateTime().getTime());
//                if ((timeVal/1000) > 1451000889) {
//                    System.out.println(dataPoint);
//                }

                double speedVal = dataPoint.getSpeed();
                series.getData().add(new XYChart.Data(timeVal, speedVal));
            }
        }

//        xAxis.setLowerBound(getDataPointsList(0).get(0).getDateTime().getTime());
//        ArrayList<DataPoint> dataPoints = activities.get(activities.size() - 1).getDataSet().getDataPoints();
//        xAxis.setUpperBound((dataPoints.get(dataPoints.size() - 1)).getDateTime().getTime());
        speedChart.getData().add(series);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Speed graph");


        InputDataParser inputDataParser = new InputDataParser();
        ArrayList<Activity> inputActivities = inputDataParser.parseCSVToActivities("huttTestData.csv");


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/GraphsTab.fxml"));
        Parent root = loader.load();
        GraphsController controller = loader.getController();

        controller.setActivities(inputActivities);
        XYChart.Series series = controller.createGraph();
        controller.populateGraph(series);


        Scene scene = new Scene(root);
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