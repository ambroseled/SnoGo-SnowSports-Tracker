package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;

import java.util.ArrayList;

/**
 * This class is the controller class for the GraphsTab.fxml class.
 * It produces graphs for speed, distance, heart rate, distance, calories.
 * The values are retrieved from the DataPoint class, as each data point has
 * attributes that store information about each of these areas.
 *
 */
public class GraphsController{

    private ArrayList<Activity> activities;
    private boolean visited = false;

    @FXML
    private ChoiceBox activityChoice;
    @FXML
    private ScrollPane scrollPane;
    //Chart FXML elements
    @FXML
    private LineChart<Number,Number> speedChart;
    @FXML
    private  LineChart<Number,Number> distanceChart;
    @FXML
    private  LineChart<Number,Number> heartRateChart;
    //Table FXML elements
    @FXML
    private AnchorPane tablePane;
    @FXML
    private Label activityName;
    @FXML
    private Label totalDistance;
    @FXML
    private Label vertDistance;
    @FXML
    private Label avgHeartRate;
    @FXML
    private Label calories;
    @FXML
    private Label avgSpeed;
    //Overall Stats FXML elements
    @FXML
    private LineChart<Number,Number> totDistChart;
    @FXML
    private LineChart<Number,Number> vertDistChart;
    @FXML
    private LineChart<Number,Number> avgHeartRateChart;
    @FXML
    private LineChart<Number,Number> caloriesChart;
    @FXML
    private LineChart<Number,Number> avgSpeedChart;
    @FXML
    private LineChart<Number,Number> runningDistChart;
    // Getting database controller and current user
    private DataBaseController db = HomeController.getDb();





    /**
     * Creates the basic empty lineChart
     * @param lineChart One of the lineCharts from FXML document
     * @param yLabel String value to label lineChart y-axis
     * @return Empty XYChart.Series with name set to yLabel
     */
    private XYChart.Series createGraph(LineChart lineChart, String yLabel) {
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
//        xAxis.setLabel("Time (Seconds)");
        xAxis.setForceZeroInRange(false); //Stops the chart starting at (0,0) every time

        //Defining the y axis
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();
        yAxis.setLabel(yLabel);

        lineChart.setTitle(yLabel + " Graph");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.getData();

        lineChart.setLegendVisible(false);

        return series;
    }


    /**
     *
     * @param lineChart
     * @param yLabel
     * @return
     */
    private XYChart.Series createOverallGraph(LineChart lineChart, String yLabel) {
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        xAxis.setLabel("Activities");
        xAxis.setTickLabelsVisible(false);

        //Defining the y axis
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();
        yAxis.setLabel(yLabel);
        yAxis.setForceZeroInRange(false);

        lineChart.setTitle(yLabel + " Graph");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.getData();

        lineChart.setLegendVisible(false);

        return series;
    }


    /**
     * Sets speed graph
     * @param lineChart the speed over time line graph
     * @param series series storing all speed points for activity in an 2D format
     * @param activity currently selected activity
     */
    private void setSpeedChart(LineChart lineChart, XYChart.Series series, Activity activity) {
        long startTime = getDataPointsList(activity).get(0).getDateTime().getTime();
        for (DataPoint dataPoint : getDataPointsList(activity)) {
            double timeVal = setTime(startTime, dataPoint, lineChart);
            double speedVal = dataPoint.getSpeed();
            series.getData().add(new XYChart.Data(timeVal, speedVal));
        }
        lineChart.getData().add(series);
    }


    /**
     * Sets distance graph
     * @param lineChart the distance over time line graph
     * @param series series storing all distance points for activity in an 2D format
     * @param activity currently selected activity
     */
    private void setDistanceChart(LineChart lineChart, XYChart.Series series, Activity activity) {
        long startTime = getDataPointsList(activity).get(0).getDateTime().getTime();
        for (DataPoint dataPoint : getDataPointsList(activity)) {
            double timeVal = setTime(startTime, dataPoint, lineChart);
            double distanceVal = dataPoint.getDistance();
            series.getData().add(new XYChart.Data(timeVal, distanceVal));
        }
        lineChart.getData().add(series);
    }


    /**
     * Sets heart rate graph
     * @param lineChart the heart rate over time line graph
     * @param series series storing all heart rate points for activity in an 2D format
     * @param activity currently selected activity
     */
    private void setHeartRateChart(LineChart lineChart, XYChart.Series series, Activity activity) {
        long startTime = getDataPointsList(activity).get(0).getDateTime().getTime();
        for (DataPoint dataPoint : getDataPointsList(activity)) {
            double timeVal = setTime(startTime, dataPoint, lineChart);
            int heartRateVal = dataPoint.getHeartRate();
            series.getData().add(new XYChart.Data(timeVal, heartRateVal));
        }
        lineChart.getData().add(series);
    }


    /**
     * Sets total distance graph
     * @param lineChart the total distance over activities graph
     * @param series series storing distance points for each activity in the 2D format
     */
    private void setTotalDistChart(LineChart lineChart, XYChart.Series series) {
        int i = 0;
        for (Activity activity: activities) {
            double totalDistance = activity.getDataSet().getTotalDistance();
            series.getData().add(new XYChart.Data(i, totalDistance));
            i += 1;
        }
        lineChart.getData().add(series);
    }


    /**
     * Sets vertical distance travelled graph
     * @param lineChart the vertical distance over activities graph
     * @param series series storing distance points for each activity in the 2D format
     */
    private void setVertDistChart(LineChart lineChart, XYChart.Series series) {
        int i = 0;
        for (Activity activity: activities) {
            double verticalDistance = activity.getDataSet().getVerticalDistance();
            series.getData().add(new XYChart.Data(i, verticalDistance));
            i += 1;
        }
        lineChart.getData().add(series);
    }


    /**
     * Sets Average Heart Rate graph
     * @param lineChart the Average Heart Rate over activities graph
     * @param series series storing heart rate points for each activity in the 2D format
     */
    private void setAvgHeartRateChart(LineChart lineChart, XYChart.Series series) {
        int i = 0;
        for (Activity activity: activities) {
            double avgHeartRate = activity.getDataSet().getAvgHeartRate();
            series.getData().add(new XYChart.Data(i, avgHeartRate));
            i += 1;
        }
        lineChart.getData().add(series);
    }


    /**
     * Sets calories burned graph
     * @param lineChart the calories burned over activities graph
     * @param series series storing calories points for each activity in the 2D format
     */
    private void setCaloriesChart(LineChart lineChart, XYChart.Series series) {
        int i = 0;
        for (Activity activity: activities) {
            double caloriesBurned = activity.getDataSet().getCaloriesBurned();
            series.getData().add(new XYChart.Data(i, caloriesBurned));
            i += 1;
        }
        lineChart.getData().add(series);
    }


    /**
     * Sets average speed graph
     * @param lineChart the average speed over activities graph
     * @param series series storing average speed for each activity in a 2D format
     */
    private void setAvgSpeedChart(LineChart lineChart, XYChart.Series series) {
        int i = 0;
        for (Activity activity: activities) {
            double avgSpeed = activity.getDataSet().getAvgSpeed();
            series.getData().add(new XYChart.Data(i, avgSpeed));
            i += 1;
        }
        lineChart.getData().add(series);
    }

    /**
     * Sets running distance graph, which adds each activity's distance
     * @param lineChart the running distance over activities graph
     * @param series series storing distance points for each activity in the 2D format
     */
    private void setRunningDistChart(LineChart lineChart, XYChart.Series series) {
        int i = 0;
        double runningDistance = 0;
        for (Activity activity: activities) {
            runningDistance += activity.getDataSet().getTotalDistance();
            series.getData().add(new XYChart.Data(i, runningDistance));
            i += 1;
        }
        lineChart.getData().add(series);
    }


    /**
     * @param startTime Point when the activity started
     * @param dataPoint Currently studied datapoint
     * @return the time of the current datapoint relative to the start of the activity
     */
    private double setTime(long startTime, DataPoint dataPoint, LineChart lineChart) {
        long currentTime = dataPoint.getDateTime().getTime();
        double newTime = currentTime - startTime;
        newTime = newTime / 1000;
        String timeScale = "Seconds";

        lineChart.getXAxis().setLabel("Time (" + timeScale + ")");
        return newTime;
    }

    /**
     * Runs when the tab is first switched to
     * Sets up the choiceBox to show all activities for current User
     */
    public void setChoiceBox() {
        if (activityChoice.getItems().size() != db.getActivities(HomeController.getCurrentUser().getId()).size()) {
            resetData();

            ArrayList<Activity> inputActivities = db.getActivities(HomeController.getCurrentUser().getId());
            if (inputActivities != activities) {
                speedChart.getData().clear();
                distanceChart.getData().clear();
                heartRateChart.getData().clear();
                scrollPane.setVisible(false);
            }
            setActivities(inputActivities);

            ObservableList<Activity> activityNames = FXCollections.observableArrayList();
            for (Activity activity: activities) {
                activityNames.add(activity);
            }
            activityChoice.setItems(activityNames);
            visited = true;

            setOverallStats();
        }

    }

    /**
     * Clears all data from graphs
     * Sets values of labelled data (max, min etc)
     * Sets graphs to the values of activity selected in choiceBox
     */
    public void selectData() {
        Activity currentActivity = (Activity) activityChoice.getValue();
        if (!(currentActivity == null)) {
            speedChart.getData().clear();
            distanceChart.getData().clear();
            heartRateChart.getData().clear();

            XYChart.Series speedSeries = createGraph(speedChart, "Speed");
            setSpeedChart(speedChart, speedSeries, currentActivity);
            XYChart.Series distanceSeries = createGraph(distanceChart, "Distance");
            setDistanceChart(distanceChart, distanceSeries, currentActivity);
            XYChart.Series heartRateSeries = createGraph(heartRateChart, "Heart Rate");
            setHeartRateChart(heartRateChart, heartRateSeries, currentActivity);

            setTable(currentActivity);
            tablePane.setVisible(true);

            scrollPane.setVisible(true);
        }
    }

    /**
     * Resets all graphs to base state
     */
    public void resetData() {
        totDistChart.getData().clear();
        vertDistChart.getData().clear();
        avgHeartRateChart.getData().clear();
        caloriesChart.getData().clear();
        avgSpeedChart.getData().clear();
        runningDistChart.getData().clear();
    }

    /**
     * Creates the lineCharts for all activities
     */
    public void setOverallStats() {
        ArrayList<Activity> inputActivities = db.getActivities(HomeController.getCurrentUser().getId());
        setActivities(inputActivities);

        XYChart.Series totalDistSeries = createOverallGraph(totDistChart, "Total Distance");
        setTotalDistChart(totDistChart, totalDistSeries);

        XYChart.Series vertDistSeries = createOverallGraph(vertDistChart, "Vertical Distance");
        setVertDistChart(vertDistChart, vertDistSeries);

        XYChart.Series avgHeartRateSeries = createOverallGraph(avgHeartRateChart, "Average Heart Rate");
        setAvgHeartRateChart(avgHeartRateChart, avgHeartRateSeries);

        XYChart.Series caloriesSeries = createOverallGraph(caloriesChart, "Calories Burned");
        setCaloriesChart(caloriesChart, caloriesSeries);

        XYChart.Series avgSpeedSeries = createOverallGraph(avgSpeedChart, "Average Speed");
        setAvgSpeedChart(avgSpeedChart, avgSpeedSeries);

        XYChart.Series runningDistSeries = createOverallGraph(runningDistChart, "Running Distance");
        setRunningDistChart(runningDistChart, runningDistSeries);
    }

    private void setActivities(ArrayList<Activity> inputActivities) {
        activities = inputActivities;
    }

    /**
     * Resets choice box and overall stats
     */
    public void setVisited() {
        visited = false;
        setChoiceBox();
    }


    /**
     * Sets a table at the bottom to show textual statistics of activity
     * @param activity the currently selected activity
     */
    private void setTable(Activity activity) {
        activityName.setText(activity.getName());
        totalDistance.setText(Double.toString(activity.getDataSet().getTotalDistance()));
        vertDistance.setText(Double.toString(activity.getDataSet().getVerticalDistance()));
        avgHeartRate.setText(Double.toString(activity.getDataSet().getAvgHeartRate()));
        calories.setText(Double.toString(activity.getDataSet().getCaloriesBurned()));
        avgSpeed.setText(Double.toString(activity.getDataSet().getAvgSpeed()));
    }

    /**
     * @param activity the currently selected activity in the choiceBox
     * @return a list of all dataPoints
     */
    public ObservableList<DataPoint> getDataPointsList(Activity activity) {
        ObservableList<DataPoint> dataPointsList = FXCollections.observableArrayList();

        DataSet dataSet = activity.getDataSet();
        dataPointsList.addAll(dataSet.getDataPoints());
        return dataPointsList;
    }
}