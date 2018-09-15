package seng202.team5.Control;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;
import seng202.team5.Model.InputDataParser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import static jdk.nashorn.internal.objects.ArrayBufferView.length;

public class GraphsController extends Application{

    private ArrayList<Activity> activities;

    @FXML
    ChoiceBox activityChoice;

    @FXML
    Button selectButton;

    @FXML
    LineChart<Number,Number> speedChart;

    @FXML
    LineChart<Number,Number> distanceChart;

    @FXML
    LineChart<Number,Number> heartRateChart;

    @FXML
    LineChart<Number,Number> caloriesChart;



    private XYChart.Series createGraph(LineChart lineChart, String yLabel) {
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        xAxis.setLabel("Time");
        xAxis.setForceZeroInRange(false); //Stops the chart starting at (0,0) every time

        //Defining the y axis
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();
        yAxis.setLabel(yLabel);

        lineChart.setTitle(yLabel + "graph");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.getData();
        series.setName(yLabel); //
        //populating the series with data

        return series;
    }

    private void setSpeedChart(LineChart lineChart, XYChart.Series series, Activity activity) {
        for (DataPoint dataPoint : getDataPointsList(activity)) {
            long timeVal = (dataPoint.getDateTime().getTime());
            double speedVal = dataPoint.getSpeed();
            series.getData().add(new XYChart.Data(timeVal, speedVal));
        }
        lineChart.getData().add(series);
    }

    private void setDistanceChart(LineChart lineChart, XYChart.Series series, Activity activity) {
        for (DataPoint dataPoint : getDataPointsList(activity)) {
            long timeVal = (dataPoint.getDateTime().getTime());
            double distanceVal = dataPoint.getDistance();
            series.getData().add(new XYChart.Data(timeVal, distanceVal));
        }
        lineChart.getData().add(series);
    }

    private void setHeartRateChart(LineChart lineChart, XYChart.Series series, Activity activity) {
        for (DataPoint dataPoint : getDataPointsList(activity)) {
            long timeVal = (dataPoint.getDateTime().getTime());
            int heartRateVal = dataPoint.getHeartRate();
            series.getData().add(new XYChart.Data(timeVal, heartRateVal));
        }
        lineChart.getData().add(series);
    }

/*    private void setCaloriesChart(LineChart lineChart, XYChart.Series series, Activity activity) {
        for (DataPoint dataPoint : getDataPointsList(activity)) {
            long timeVal = (dataPoint.getDateTime().getTime());
            double speedVal = dataPoint.getSpeed();
            series.getData().add(new XYChart.Data(timeVal, speedVal));
        }
        lineChart.getData().add(series);
    }*/ //Get calories

    private void populateGraph(LineChart lineChart, XYChart.Series series, Activity activity) {




            /////////////////
            /**
             * You should save the time of the first dataPoint and then set it to zero,
             * and then for all other DataPoints minus the time of the first dataPoint to
             * get their relative time value.
             */
            /////////////////


        for (DataPoint dataPoint : getDataPointsList(activity)) {
            long timeVal = (dataPoint.getDateTime().getTime());
//                if ((timeVal/1000) > 1451000889) {
//                    System.out.println(dataPoint);
//                }

            double speedVal = dataPoint.getSpeed();
            series.getData().add(new XYChart.Data(timeVal, speedVal));
        }

//        xAxis.setLowerBound(getDataPointsList(0).get(0).getDateTime().getTime());
//        ArrayList<DataPoint> dataPoints = activities.get(activities.size() - 1).getDataSet().getDataPoints();
//        xAxis.setUpperBound((dataPoints.get(dataPoints.size() - 1)).getDateTime().getTime());
        lineChart.getData().add(series);
    }

    private void setChoiceBox() {
        ObservableList<Activity> activityNames = FXCollections.observableArrayList();
        for (Activity activity: activities) {
            activityNames.add(activity);
        }
        activityChoice.setItems(activityNames);
    }

    public void selectData() {
        Activity currentActivity = (Activity) activityChoice.getValue();
        System.out.println(speedChart.getData().removeAll());


        XYChart.Series speedSeries = createGraph(speedChart, "Speed");
        setSpeedChart(speedChart, speedSeries, currentActivity);
        XYChart.Series distanceSeries = createGraph(distanceChart, "Distance");
        setDistanceChart(distanceChart, distanceSeries, currentActivity);
        XYChart.Series heartRateSeries = createGraph(heartRateChart, "Heart Rate");
        setHeartRateChart(heartRateChart, heartRateSeries, currentActivity);
        //XYChart.Series caloriesSeries = createGraph(caloriesChart, "Calories");
        //setCaloriesChart(caloriesChart, caloriesSeries, currentActivity);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Statistics");


        InputDataParser inputDataParser = new InputDataParser();
        ArrayList<Activity> inputActivities = inputDataParser.parseCSVToActivities("TestData.csv");


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/GraphsTab.fxml"));
        Parent root = loader.load();
        GraphsController controller = loader.getController();

        controller.setActivities(inputActivities);
        controller.setChoiceBox();

//        XYChart.Series series = controller.createGraph();
//        controller.populateGraph(series);


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

    public ObservableList<DataPoint> getDataPointsList(Activity activity) {
        ObservableList<DataPoint> dataPointsList = FXCollections.observableArrayList();

        DataSet dataSet = activity.getDataSet();
        dataPointsList.addAll(dataSet.getDataPoints());
        return dataPointsList;
    }
}