package seng202.team5.Control;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang3.time.DateUtils;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * This class is the controller class for the GraphsTab.fxml file.
 * It produces graphs for speed, distance, heart rate, distance, calories.
 * The values are retrieved from the DataPoint class, as each data point has
 * attributes that store information about each of these areas.
 *
 */
public class GraphsController{

    private ArrayList<Activity> activities;

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
    //Overall Stats FXML elements
    @FXML
    private BarChart<Number,Number> totDistChart;
    @FXML
    private BarChart<Number,Number> vertDistChart;
    @FXML
    private LineChart<Number,Number> avgHeartRateChart;
    @FXML
    private LineChart<Number,Number> caloriesChart;
    @FXML
    private LineChart<Number,Number> avgSpeedChart;
    @FXML
    private LineChart<Number,Number> runningDistChart;
    @FXML
    private LineChart<Number,Number> slopeTimeChart;
    @FXML
    private LineChart<Number,Number> topSpeedChart;
    @FXML
    private TableView actTable;
    @FXML
    private TableColumn<Activity, String> nameCol;
    @FXML
    private TableColumn<Activity, String> actDateCol;
    @FXML
    private TableColumn<Activity, Double> distCol;
    @FXML
    private TableColumn<Activity, Double> vertCol;
    @FXML
    private TableColumn<Activity, Integer> heartCol;
    @FXML
    private TableColumn<Activity, Double> calCol;
    @FXML
    private TableColumn<Activity, Double> avgSpeedCol;
    @FXML
    private TableColumn<Activity, Double> topSpeedCol;
    @FXML
    private TableColumn<Activity, String> slopeCol;


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
     * The method creates a series to be used for an overall statistics graph
     * @param chart The chart to make the series for
     * @param yLabel The y label of the chart
     * @return a xYChart.Series related to the passed chart
     */
    private XYChart.Series createOverallGraph(XYChart chart, String yLabel) {
        chart.getData().clear();
        Axis xAxis = chart.getXAxis();
        xAxis.setLabel("Activities");
        if (chart instanceof LineChart) {
            xAxis.setTickLabelsVisible(false);
        }

        //Defining the y axis
        NumberAxis yAxis = (NumberAxis) chart.getYAxis();
        yAxis.setLabel(yLabel);
        yAxis.setForceZeroInRange(false);

        chart.setTitle(yLabel + " Graph");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.getData();

        chart.setLegendVisible(false);

        return series;
    }


    /**
     * Sets the values of the speed chart for a passed activity
     * @param lineChart the speed over time line graph
     * @param series series storing all speed points for activity in an 2D format
     * @param activity currently selected activity
     */
    private void setSpeedChart(LineChart lineChart, XYChart.Series series, Activity activity) {
        ObservableList<DataPoint> dataList = getDataPointsList(activity);

        long startTime = dataList.get(0).getDateTime().getTime();
        long endTime = dataList.get(dataList.size() - 1).getDateTime().getTime();

        double timeDivisor = setTime(startTime, endTime, lineChart);
        for (DataPoint dataPoint : getDataPointsList(activity)) {
            double timeVal = ((dataPoint.getDateTime().getTime()) - startTime) / timeDivisor;
            double speedVal = dataPoint.getSpeed();
            series.getData().add(new XYChart.Data(timeVal, speedVal));
        }
        lineChart.getData().add(series);
    }


    /**
     * Sets the values of the distance chart for a passed activity
     * @param lineChart the distance over time line graph
     * @param series series storing all distance points for activity in an 2D format
     * @param activity currently selected activity
     */
    private void setDistanceChart(LineChart lineChart, XYChart.Series series, Activity activity) {
        ObservableList<DataPoint> dataList = getDataPointsList(activity);

        long startTime = dataList.get(0).getDateTime().getTime();
        long endTime = dataList.get(dataList.size() - 1).getDateTime().getTime();

        double timeDivisor = setTime(startTime, endTime, lineChart);
        for (DataPoint dataPoint : getDataPointsList(activity)) {
            double timeVal = ((dataPoint.getDateTime().getTime()) - startTime) / timeDivisor;
            double distanceVal = dataPoint.getDistance();
            series.getData().add(new XYChart.Data(timeVal, distanceVal));
        }
        lineChart.getData().add(series);
    }


    /**
     * Sets the values of the heart rate chart for a passed activity
     * @param lineChart the heart rate over time line graph
     * @param series series storing all heart rate points for activity in an 2D format
     * @param activity currently selected activity
     */
    private void setHeartRateChart(LineChart lineChart, XYChart.Series series, Activity activity) {
        ObservableList<DataPoint> dataList = getDataPointsList(activity);

        long startTime = dataList.get(0).getDateTime().getTime();
        long endTime = dataList.get(dataList.size() - 1).getDateTime().getTime();

        double timeDivisor = setTime(startTime, endTime, lineChart);
        for (DataPoint dataPoint : getDataPointsList(activity)) {
            double timeVal = ((dataPoint.getDateTime().getTime()) - startTime) / timeDivisor;
            int heartRateVal = dataPoint.getHeartRate();
            series.getData().add(new XYChart.Data(timeVal, heartRateVal));
        }
        lineChart.getData().add(series);
    }


    /**
     * Sets the values of the total distance chart for all activities in the activities list
     * @param barChart the total distance over activities graph
     * @param series series storing distance points for each activity in the 2D format
     */
    private void setTotalDistChart(BarChart barChart, XYChart.Series series) {
        int i = 0;
        barChart.setAnimated(false);
        for (Activity activity: activities) {
            double totalDistance = activity.getDataSet().getTotalDistance();
            series.getData().add(new XYChart.Data(activity.getName(), totalDistance));
            i += 1;
        }
        barChart.getData().addAll(series);
    }


    /**
     * Sets the values of the vertical distance chart for all activities in the activities list
     * @param barChart the vertical distance over activities graph
     * @param series series storing distance points for each activity in the 2D format
     */
    private void setVertDistChart(BarChart barChart, XYChart.Series series) {
        int i = 0;
        barChart.setAnimated(false);
        for (Activity activity: activities) {
            double verticalDistance = activity.getDataSet().getVerticalDistance();
            series.getData().add(new XYChart.Data(activity.getName(), verticalDistance));
            i += 1;
        }
        barChart.getData().addAll(series);
    }


    /**
     * Sets the values of the average heart rate chart for all activities in the activities list
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
     * Sets the values of the calories burned chart for all activities in the activities list
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
     * Sets the values of the average speed chart for all activities in the activities list
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
     * Sets running distance graph, which adds each activity's distance for all activities in the activities list
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
     * Sets the values of the slope time chart for all activities in the activities list
     * @param lineChart the slope time over activities graph
     * @param series series storing slope time points for each activity in the 2D format
     */
    private void setSlopeTimeChart(LineChart lineChart, XYChart.Series series) {
        int i = 0;
        for (Activity activity: activities) {
            series.getData().add(new XYChart.Data(i, activity.getDataSet().getSlopeTime()));
            i += 1;
        }
        lineChart.getData().add(series);
    }


    /**
     * Sets the values of thetop speed chart for all activities in the activities list
     * @param lineChart the slope time over activities graph
     * @param series series storing slope time points for each activity in the 2D format
     */
    private void setTopSpeedChart(LineChart lineChart, XYChart.Series series) {
        int i = 0;
        for (Activity activity: activities) {
            series.getData().add(new XYChart.Data(i, activity.getDataSet().getTopSpeed()));
            i += 1;
        }
        lineChart.getData().add(series);
    }



    /**
     * Sets the time scale for a passed chart also returning the time scale
     * @param startTime Point when the activity started
     * @param endTime Point when the activity ends
     * @return the time of the current data point relative to the start of the activity
     */
    private double setTime(long startTime, long endTime, LineChart lineChart) {
        double totalTime = endTime - startTime;
        double timeDivisor = 1000;
        String timeScale = "Seconds";

        if (totalTime > 60000) {
            timeDivisor *= 60;
            timeScale = "Minutes";
            if (totalTime > 3600000) {
                timeDivisor *= 60;
                timeScale = "Hours";
            }
        }

        lineChart.getXAxis().setLabel("Time (" + timeScale + ")");
        return timeDivisor;
    }

    
    /**
     * Runs when the tab is first switched to
     * Sets up the choiceBox to show all activities for current User
     */
    public void setChoiceBox() {
        ArrayList<Activity> inputActivities = db.getActivities(HomeController.getCurrentUser().getId());
        setActivities(inputActivities);

        ObservableList<Activity> activityNames = FXCollections.observableArrayList();
        for (Activity activity: activities) {
            activityNames.add(activity);
        }
        activityChoice.setItems(activityNames);

        showWeek();
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

            XYChart.Series speedSeries = createGraph(speedChart, "Speed (m/s)");
            setSpeedChart(speedChart, speedSeries, currentActivity);
            XYChart.Series distanceSeries = createGraph(distanceChart, "Distance (m)");
            setDistanceChart(distanceChart, distanceSeries, currentActivity);
            XYChart.Series heartRateSeries = createGraph(heartRateChart, "Heart Rate (bpm)");
            setHeartRateChart(heartRateChart, heartRateSeries, currentActivity);

            showActivity(currentActivity);

            scrollPane.setVisible(true);
            scrollPane.setDisable(false);
        }
    }


    /**
     * This method fills the activity table with the information from a passed activity
     * @param activity The activity to put in the table
     */
    private void showActivity(Activity activity) {
        // Defining an ObservableList to use to fill the table
        ObservableList<Activity> activities = FXCollections.observableArrayList();
        // Clearing the activity table
        actTable.getItems().clear();
        // Configuring the columns of the activity table
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        actDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        distCol.setCellValueFactory(new PropertyValueFactory<>("totalDistance"));
        vertCol.setCellValueFactory(new PropertyValueFactory<>("verticalDistance"));
        heartCol.setCellValueFactory(new PropertyValueFactory<>("avgHeartRate"));
        calCol.setCellValueFactory(new PropertyValueFactory<>("caloriesBurned"));
        avgSpeedCol.setCellValueFactory(new PropertyValueFactory<>("avgSpeed"));
        topSpeedCol.setCellValueFactory(new PropertyValueFactory<>("topSpeed"));
        slopeCol.setCellValueFactory(new PropertyValueFactory<>("slopeTime"));
        // Filling the table
        activities.add(activity);
        actTable.setItems(activities);
        actTable.setVisible(true);
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
        slopeTimeChart.getData().clear();
        topSpeedChart.getData().clear();
        scrollPane.setVisible(false);
        scrollPane.setDisable(true);
        actTable.setVisible(false);
    }



    /**
     * This method sets the activities list to a passed list of activities
     * @param inputActivities
     */
    private void setActivities(ArrayList<Activity> inputActivities) {
        activities = inputActivities;
    }


    /**
     * This method creates and returns an observable list of data points from a passed activity
     * @param activity the currently selected activity in the choiceBox
     * @return a list of all dataPoints
     */
    public ObservableList<DataPoint> getDataPointsList(Activity activity) {
        ObservableList<DataPoint> dataPointsList = FXCollections.observableArrayList();

        DataSet dataSet = activity.getDataSet();
        dataPointsList.addAll(dataSet.getDataPoints());
        return dataPointsList;
    }


    @FXML
    /**
     * This method is called by a press of the 'Last Week' button. it sets the scope of the overall graphs
     * to be within the last week
     */
    public void showWeek() {
        Date date = alterDate(7);
        setActivities(getActivitiesAfter(date));
        fillCharts();
    }


    @FXML
    /**
     * This method is called by a press of the 'Last Month' button. it sets the scope of the overall graphs
     * to be within the last month
     */
    public void showMonth() {
        Date date = alterDate(30);
        setActivities(getActivitiesAfter(date));
        fillCharts();
    }


    @FXML
    /**
     * This method is called by a press of the 'Last Year' button. it sets the scope of the overall graphs
     * to be within the last year
     */
    public void showYear() {
        Date date = alterDate(365);
        setActivities(getActivitiesAfter(date));
        fillCharts();
    }


    @FXML
    /**
     * This method is called by a press of the 'All Time' button. it sets the scope of the overall graphs
     * to be all of the users activities
     */
    public void showAll() {
        ArrayList<Activity> inputActivities = db.getActivities(HomeController.getCurrentUser().getId());
        setActivities(inputActivities);
        fillCharts();
    }


    /**
     * This method is used to minus a passed amount of days off of a date
     * @param numDays The number of days to minus off
     * @return The date corresponding to the current date minus the passed amount of days
     */
    private Date alterDate(int numDays) {
        DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
        // Minusing the days off the current date
        String dateString = dateTimeFormat.format(DateUtils.addDays(new Date(), -numDays));
        // Parsing the string into a date to be returned
        try {
            Date date = dateTimeFormat.parse(dateString);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }


    /**
     * This method gets a list of activities that are after a passed date
     * @param date The date
     * @return The list of activities
     */
    private ArrayList<Activity> getActivitiesAfter(Date date) {
        ArrayList<Activity> acts = new ArrayList<>();
        // Looping over all of the users activities
        for (Activity x : db.getActivities(HomeController.getCurrentUser().getId())) {
            // Checking if the activity is after the passed date
            if (x.getDataSet().getDateTime(0).getTime() >= date.getTime()) {
                acts.add(x);
            }
        }
        return acts;
    }


    /**
     * This method fills all of the overall graphs with the activities in the activities list
     */
    private void fillCharts() {
        XYChart.Series totalDistSeries = createOverallGraph(totDistChart, "Total Distance (m)");
        setTotalDistChart(totDistChart, totalDistSeries);

        XYChart.Series vertDistSeries = createOverallGraph(vertDistChart, "Vertical Distance (m)");
        setVertDistChart(vertDistChart, vertDistSeries);

        XYChart.Series avgHeartRateSeries = createOverallGraph(avgHeartRateChart, "Average Heart Rate");
        setAvgHeartRateChart(avgHeartRateChart, avgHeartRateSeries);

        XYChart.Series caloriesSeries = createOverallGraph(caloriesChart, "Calories Burned");
        setCaloriesChart(caloriesChart, caloriesSeries);

        XYChart.Series avgSpeedSeries = createOverallGraph(avgSpeedChart, "Average Speed (m/s)");
        setAvgSpeedChart(avgSpeedChart, avgSpeedSeries);

        XYChart.Series runningDistSeries = createOverallGraph(runningDistChart, "Running Distance (m)");
        setRunningDistChart(runningDistChart, runningDistSeries);

        XYChart.Series slopTimeSeries = createOverallGraph(slopeTimeChart, "Slope Time (min)");
        setSlopeTimeChart(slopeTimeChart, slopTimeSeries);

        XYChart.Series topSpeedSeries = createOverallGraph(topSpeedChart, "Top Speed (m/s)");
        setTopSpeedChart(topSpeedChart, topSpeedSeries);
    }



}