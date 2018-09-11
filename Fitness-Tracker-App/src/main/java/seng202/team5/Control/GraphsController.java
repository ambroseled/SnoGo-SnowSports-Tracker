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
    LineChart<Number,Number> lineChart;

    @FXML
    NumberAxis xAxis;

    @FXML
    NumberAxis yAxis;


    private XYChart.Series createGraph() {

        //defining the axes
//        long lowerBound = getDataPointsList(0).get(0).getDateTime().getTime();
//        System.out.println(lowerBound/1000);
//        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
//        xAxis.setLowerBound(lowerBound);

        xAxis.setLabel("Time");
        xAxis.setForceZeroInRange(false);

        //Defining the y axis
        yAxis.setLabel("Speed");
        //creating the chart

        lineChart.setTitle("Speed graph");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.getData();
        series.setName("Speed");
        //populating the series with data

        return series;
    }

    private void populateGraph(XYChart.Series series) {
        for (int i = 0; i < activities.size(); i++) {


             //Need to set lower and upper bounds, but this command isn't working



            for (DataPoint dataPoint : getDataPointsList(i)) {
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
        lineChart.getData().add(series);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Speed graph");


        InputDataParser inputDataParser = new InputDataParser();
        ArrayList<Activity> inputActivities = inputDataParser.parseCSVToActivities("testData.csv");


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