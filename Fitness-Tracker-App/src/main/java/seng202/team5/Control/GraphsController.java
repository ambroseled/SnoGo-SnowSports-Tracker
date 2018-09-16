package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;
import seng202.team5.Model.User;

import java.util.ArrayList;
import java.util.Date;


public class GraphsController{

    private ArrayList<Activity> activities;
    private boolean setChoices = false;

    @FXML
    ChoiceBox activityChoice;
    @FXML
    Button selectButton;
    @FXML
    ScrollPane scrollPane;

    @FXML
    LineChart<Number,Number> speedChart;
    @FXML
    LineChart<Number,Number> distanceChart;
    @FXML
    LineChart<Number,Number> heartRateChart;
    @FXML
    LineChart<Number,Number> caloriesChart;

    @FXML
    Label nameLabel;
    @FXML
    Label dateLabel;
    @FXML
    Label speedLabel;
    @FXML
    Label distanceLabel;
    @FXML
    Label heartRateLabel;
    @FXML
    Label vertDistanceLabel;

    @FXML
    TableView activityTable;

    private DataBaseController db = AppController.getDb();
    private User currentUser = AppController.getCurrentUser();

    /**
     * Creates the basic empty lineChart
     * @param lineChart One of the lineCharts from FXML document
     * @param yLabel String value to label lineChart y-axis
     * @return Empty XYChart.Series with name set to yLabel
     */
    private XYChart.Series createGraph(LineChart lineChart, String yLabel) {
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        xAxis.setLabel("Time (Seconds)");
        xAxis.setForceZeroInRange(false); //Stops the chart starting at (0,0) every time

        //Defining the y axis
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();
        yAxis.setLabel(yLabel);

        lineChart.setTitle(yLabel + " Graph");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.getData();
        series.setName(yLabel); //
        //populating the series with data

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
            double timeVal = setTime(startTime, dataPoint);
            double speedVal = dataPoint.getSpeed();
            series.getData().add(new XYChart.Data(timeVal, speedVal));
        }
        lineChart.getData().add(series);
        //((NumberAxis) lineChart.getXAxis()).setLowerBound(getDataPointsList(activity).get(0).getDateTime().getTime());
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
            double timeVal = setTime(startTime, dataPoint);
            double distanceVal = dataPoint.getDistance();
            series.getData().add(new XYChart.Data(timeVal, distanceVal));
        }
        lineChart.getData().add(series);
        //((NumberAxis) lineChart.getXAxis()).setLowerBound(getDataPointsList(activity).get(0).getDateTime().getTime());
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
            double timeVal = setTime(startTime, dataPoint);
            int heartRateVal = dataPoint.getHeartRate();
            series.getData().add(new XYChart.Data(timeVal, heartRateVal));
        }
        lineChart.getData().add(series);
        //((NumberAxis) lineChart.getXAxis()).setLowerBound(getDataPointsList(activity).get(0).getDateTime().getTime());
    }

/*    private void setCaloriesChart(LineChart lineChart, XYChart.Series series, Activity activity) {
        for (DataPoint dataPoint : getDataPointsList(activity)) {
            long timeVal = (dataPoint.getDateTime().getTime());
            double speedVal = dataPoint.getSpeed();
            series.getData().add(new XYChart.DataManipulation(timeVal, speedVal));
        }
        lineChart.getData().add(series);
    }*/ //Get calories

    /**
     * @param startTime Point when the activity started
     * @param dataPoint Currently studied datapoint
     * @return the time of the current datapoint relative to the start of the activity
     */
    private double setTime(long startTime, DataPoint dataPoint) {
        long currentTime = dataPoint.getDateTime().getTime();
        double newTime = currentTime - startTime;
        newTime = newTime / 1000;
        return newTime;
    }


    /**
     * Runs when the tab is first switched to
     * Sets up the choiceBox to show all activities for current User
     */
    public void setChoiceBox() {
        if (setChoices) {
            return;
        }

        ArrayList<Activity> inputActivities = db.getActivities(currentUser.getId());
        setActivities(inputActivities);

        ObservableList<Activity> activityNames = FXCollections.observableArrayList();
        for (Activity activity: activities) {
            activityNames.add(activity);
        }
        activityChoice.setItems(activityNames);
        setChoices = true;
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
            caloriesChart.getData().clear();

            nameLabel.setText(currentActivity.getName());
            DataSet dataSet = currentActivity.getDataSet();
            dateLabel.setText(dataSet.getDateTime(0) + " - " + dataSet.getDateTime(dataSet.getDataPoints().size() - 1));
            dateLabel.setCenterShape(true);
            speedLabel.setText(Double.toString(Math.round (dataSet.getTopSpeed() * 100.0) / 100.0));            //Rounds values to 2 decimal places
            //distanceLabel.setText(Double.toString(Math.round (dataSet.getTotalDistance() * 100.0) / 100.0));
            distanceLabel.setText(Double.toString(dataSet.getTotalDistance()));
            heartRateLabel.setText(Double.toString(Math.round (dataSet.getAvgHeartRate() * 100.0) / 100.0));
            vertDistanceLabel.setText(Double.toString(Math.round (dataSet.getVerticalDistance() * 100.0) / 100.0));


            XYChart.Series speedSeries = createGraph(speedChart, "Speed");
            setSpeedChart(speedChart, speedSeries, currentActivity);
            XYChart.Series distanceSeries = createGraph(distanceChart, "Distance");
            setDistanceChart(distanceChart, distanceSeries, currentActivity);
            XYChart.Series heartRateSeries = createGraph(heartRateChart, "Heart Rate");
            setHeartRateChart(heartRateChart, heartRateSeries, currentActivity);
            //XYChart.Series caloriesSeries = createGraph(caloriesChart, "Calories");
            //setCaloriesChart(caloriesChart, caloriesSeries, currentActivity);
            createTable(currentActivity);

            scrollPane.setVisible(true);
        }
    }

    private void createTable(Activity activity) {
        activityTable.getColumns().clear();

        // date and time column
        TableColumn<String, String> activityCol = new TableColumn("Activity");
        activityCol.setCellValueFactory(new PropertyValueFactory("activity"));

        //heart rate column
        TableColumn<String, String> totalDistCol = new TableColumn("Distance Travelled");
        totalDistCol.setCellValueFactory(new PropertyValueFactory("totalDist"));

        //latitude column
        TableColumn<String, String> vertDistCol = new TableColumn("Vertical Distance Travelled");
        vertDistCol.setCellValueFactory(new PropertyValueFactory("vertDist"));

        //longitude column
        TableColumn<String, String> avgHeartRateCol = new TableColumn("Average Heart Rate");
        avgHeartRateCol.setCellValueFactory(new PropertyValueFactory("avgHeartRate"));

        //elevation column
        TableColumn<String, String> caloriesCol = new TableColumn("Calories Burnt");
        caloriesCol.setCellValueFactory(new PropertyValueFactory("calories"));

        //distance column
        TableColumn<String, String> avgSpeedCol = new TableColumn("Average Speed");
        avgSpeedCol.setCellValueFactory(new PropertyValueFactory("avgSpeed"));


        activityTable.getColumns().addAll(activityCol, totalDistCol, vertDistCol, avgHeartRateCol, caloriesCol, avgSpeedCol);

        activityTable.setItems(getTableList(activity));

        System.out.println(activityCol);
    }

    private void setActivities(ArrayList<Activity> inputActivities) {
        activities = inputActivities;
    }

    private ObservableList<String> getTableList(Activity activity) {
        String activityName = activity.getName();
        String totalDistVal = Double.toString(activity.getDataSet().getTotalDistance());
        String vertDistVal = Double.toString(activity.getDataSet().getVerticalDistance());
        String avgHeartVal = Double.toString(activity.getDataSet().getAvgHeartRate());
        String caloriesVal = Double.toString(activity.getDataSet().getCaloriesBurned());
        String avgSpeedVal = Double.toString(activity.getDataSet().getAvgSpeed());

        System.out.println(activityName);
        System.out.println(totalDistVal);
        System.out.println(vertDistVal);
        System.out.println(avgHeartVal);
        System.out.println(caloriesVal);
        System.out.println(avgSpeedVal);

        ObservableList<String> calculatedVals = FXCollections.observableArrayList();
        calculatedVals.addAll(activityName, totalDistVal, vertDistVal, avgHeartVal, avgHeartVal, caloriesVal, avgSpeedVal);
        System.out.println(calculatedVals);
        return calculatedVals;
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