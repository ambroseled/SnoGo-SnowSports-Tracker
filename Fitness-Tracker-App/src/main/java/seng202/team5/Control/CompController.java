package seng202.team5.Control;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import seng202.team5.DataManipulation.DataAnalyser;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataSet;


/**
 * This class is the controller of the dataCompView.fxml file. It provides the functionality for the user
 * to compare their activities.
 */
public class CompController {


    // FXML objects used by the controller
    @FXML
    private TableView actTable;
    @FXML
    private TableView actTable1;
    @FXML
    private GridPane grid1;
    @FXML
    private GridPane grid2;
    @FXML
    private TableColumn<Activity, String> nameCol;
    @FXML
    private TableColumn<Activity, String> dateCol;
    @FXML
    private TableColumn<Activity, String> nameCol1;
    @FXML
    private TableColumn<Activity, String> dateCol1;
    @FXML
    private Text totDistText;
    @FXML
    private Text topSpeedText;
    @FXML
    private Text vertText;
    @FXML
    private Text heartText;
    @FXML
    private Text calText;
    @FXML
    private Text avgSpeedText;
    @FXML
    private Text totDistText1;
    @FXML
    private Text topSpeedText1;
    @FXML
    private Text vertText1;
    @FXML
    private Text heartText1;
    @FXML
    private Text calText1;
    @FXML
    private Text avgSpeedText1;
    @FXML
    private Text slopeText;
    @FXML
    private Text slopeText1;
    @FXML
    private Text timeText;
    @FXML
    private Text timeText1;
    @FXML
    private Rectangle topSpeedBox;
    @FXML
    private Rectangle totalDistBox;
    @FXML
    private Rectangle vertBox;
    @FXML
    private Rectangle calBox;
    @FXML
    private Rectangle avgSpeedBox;
    @FXML
    private Rectangle slopeBox;
    @FXML
    private Rectangle timeBox;
    @FXML
    private Rectangle topSpeedBox1;
    @FXML
    private Rectangle totalDistBox1;
    @FXML
    private Rectangle vertBox1;
    @FXML
    private Rectangle calBox1;
    @FXML
    private Rectangle avgSpeedBox1;
    @FXML
    private Rectangle slopeBox1;
    @FXML
    private Rectangle timeBox1;
    @FXML
    private Text topSpeedLabel;
    @FXML
    private Text topSpeedLabel1;
    @FXML
    private Text totDistLabel;
    @FXML
    private Text totDistLabel1;
    @FXML
    private Text vertLabel;
    @FXML
    private Text vertLabel1;
    @FXML
    private Text calLabel;
    @FXML
    private Text calLabel1;
    @FXML
    private Text avgSpeedLabel;
    @FXML
    private Text avgSpeedLabel1;
    @FXML
    private Text slopeLabel;
    @FXML
    private Text slopeLabel1;
    @FXML
    private Text timeLabel;
    @FXML
    private Text timeLabel1;


    // Observable list used to hold the users activities to be displayed in the activity tables
    private ObservableList<Activity> activities = FXCollections.observableArrayList();


    /**
     * This method is called on selection of the Compare tab. It fills the two activity tbales
     * with the users current activities.
     */
    public void fillActTables() {
        // Clearing the tables
        actTable.getItems().clear();
        actTable1.getItems().clear();
        // Adding the users activities to the list
        activities.addAll(HomeController.getDb().getActivities(HomeController.getCurrentUser().getId()));
        // Configuring the tables for and fill the first table
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        actTable.setItems(activities);
        // Configuring the tables for and fill the second table
        nameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCol1.setCellValueFactory(new PropertyValueFactory<>("date"));
        actTable1.setItems(activities);
    }


    @FXML
    /**
     * This method is called by a press of the 'Compare Selected Activities' button. It fills the comparison mechanisms
     * with the data from the two selected activities
     */
    public void showComparison() {
        // Getting the selected activities
        Activity act = (Activity) actTable.getSelectionModel().getSelectedItem();
        Activity act1 = (Activity) actTable1.getSelectionModel().getSelectedItem();
        // Checking that two activities have actually been selected
        if (act != null && act1 != null) {
            // Clearing the display boxes
            clearBoxes();
            // Showing the data for the two activities
            showText(act.getDataSet(), topSpeedText, totDistText, vertText, heartText, calText, avgSpeedText, slopeText, timeText);
            showText(act1.getDataSet(), topSpeedText1, totDistText1, vertText1, heartText1, calText1, avgSpeedText1, slopeText1, timeText1);
            // Performing the comparison of the two activities
            compareActivities(act.getDataSet(), act1.getDataSet());
        }
        grid1.setVisible(true);
        grid2.setVisible(true);
    }


    /**
     * This method performs a comparison of two passed activities
     * @param set The DataSet from the first activity to compare
     * @param set1 The DataSet from the second activity to compare
     */
    private void compareActivities(DataSet set, DataSet set1) {
        // Comparing the top speed of the activities
        if (set.getTopSpeed() > set1.getTopSpeed()) {
            setColours(topSpeedBox, topSpeedBox1, topSpeedText, topSpeedText1, topSpeedLabel, topSpeedLabel1);
        } else if (set.getTopSpeed() < set1.getTopSpeed()) {
            setColours(topSpeedBox1, topSpeedBox, topSpeedText, topSpeedText1, topSpeedLabel, topSpeedLabel1);
        }
        // Comparing the vertical distance of the activities
        if (set.getVerticalDistance() > set1.getVerticalDistance()) {
            setColours(vertBox, vertBox1, vertText, vertText1, vertLabel, vertLabel1);
        } else if (set.getVerticalDistance() < set1.getVerticalDistance()) {
            setColours(vertBox1, vertBox, vertText, vertText1, vertLabel, vertLabel1);
        }
        // Comparing the total distance of the activities
        if (set.getTotalDistance() > set1.getTotalDistance()) {
            setColours(totalDistBox, totalDistBox1, totDistText, totDistText1, totDistLabel, totDistLabel1);
        } else if (set.getTotalDistance() < set1.getTotalDistance()) {
            setColours(totalDistBox1, totalDistBox, totDistText, totDistText1, totDistLabel, totDistLabel1);
        }
        // Comparing the calories burned of the activities
        if (set.getCaloriesBurned() > set1.getCaloriesBurned() ) {
            setColours(calBox, calBox1, calText, calText1, calLabel, calLabel1);
        } else if (set.getCaloriesBurned()  < set1.getCaloriesBurned() ) {
            setColours(calBox1, calBox, calText, calText1, calLabel, calLabel1);
        }
        // Comparing the average speed of the activities
        if (set.getAvgSpeed() > set1.getAvgSpeed() ) {
            setColours(avgSpeedBox, avgSpeedBox1, avgSpeedText, avgSpeedText1, avgSpeedLabel, avgSpeedLabel1);
        } else if (set.getAvgSpeed()  < set1.getAvgSpeed() ) {
            setColours(avgSpeedBox1, avgSpeedBox, avgSpeedText, avgSpeedText1, avgSpeedLabel, avgSpeedLabel1);
        }
        // Comparing the slope time of the activities
        if (set.getSlopeTime() > set1.getSlopeTime() ) {
            setColours(slopeBox, slopeBox1, slopeText, slopeText1, slopeLabel, slopeLabel1);
        } else if (set.getSlopeTime()  < set1.getSlopeTime() ) {
            setColours(slopeBox1, slopeBox, slopeText, slopeText1, slopeLabel, slopeLabel1);
        }
        // Comparing the total time of the activities
        if (getTime(set) > getTime(set1) ) {
            setColours(timeBox, timeBox1, timeText, timeText1, timeLabel, timeLabel1);
        } else if (getTime(set)  < getTime(set1) ) {
            setColours(timeBox1, timeBox, timeText, timeText1, timeLabel, timeLabel1);
        }
    }


    /**
     * This method sets the colours of passed Rectangle objects and Text objects. This is done to give the user
     * a visual view of the comparison.
     * @param green The rectangle of the metric of higher value
     * @param red The rectangle of the metric of lower value
     * @param label1 The text of the higher value
     * @param label2 The text of the lower value
     * @param label3 The label of the higher value
     * @param label4 The label of the lower value
     */
    private void setColours(Rectangle green, Rectangle red, Text label1, Text label2, Text label3, Text label4) {
        // Setting the colours of the object
        green.setFill(Color.GREEN);
        red.setFill(Color.RED);
        label1.setFill(Color.WHITE);
        label2.setFill(Color.WHITE);
        label3.setFill(Color.WHITE);
        label4.setFill(Color.WHITE);
        // Performing a transmission on the rectangle object of higher value to emphasise that it has a higher value
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(green);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setByX(0.1);
        scaleTransition.setByY(0.1);
        scaleTransition.setCycleCount(4);
        scaleTransition.setDuration(Duration.seconds(0.50));
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }


    /**
     * This method makes a passed set of text objects visible along with their corresponding values for a passed
     * DataSet
     * @param set The DataSet to display values for
     * @param speed The speed text
     * @param dist The distance text
     * @param vert The vertical distance text
     * @param heart The heart rate text
     * @param cal the calories burned text
     * @param avgSpeed the average speed text
     */
    private void showText(DataSet set, Text speed, Text dist, Text vert, Text heart, Text cal, Text avgSpeed, Text slope, Text time) {
        // Displaying all passed text objects with their values
        speed.setText(set.getTopSpeed() + " m/s");
        dist.setText(set.getTotalDistance() + " m");
        vert.setText(set.getVerticalDistance() + " m");
        heart.setText(set.getAvgHeartRate() + " bpm");
        cal.setText(Double.toString(set.getCaloriesBurned()));
        avgSpeed.setText(set.getAvgSpeed() + " m/s");
        slope.setText(formatTime(set.getSlopeTime()));
        time.setText(formatTime(getTime(set)) + " m");
    }


    /**
     * This method gets the duration of a passed data set in milliseconds
     * @param set The data set to get the time for
     * @return The duration of the data set
     */
    private double getTime(DataSet set) {
        double total = set.getDateTime(set.getDataPoints().size() - 1).getTime() - set.getDateTime(0).getTime();
        return total;
    }


    /**
     * This method clears all of the text objects on the comparison view, it also resets all of the colours to default.
     */
    public void clearBoxes() {
        grid1.setVisible(false);
        grid2.setVisible(false);
    }


    /**
     * This method converts a time value in milliseconds to the string format '{hours}h, {minutes}m, {seconds}s'
     * @param timeMilli The time in milliseconds
     * @return The formatted time string
     */
    public static String formatTime(double timeMilli) {
        // Getting number of seconds and hours
        double seconds = timeMilli / 1000;
        int hours = (int) seconds / 3600;
        if (hours < 1) {
            // Formatting to only minutes and seconds as less than an hour of time
            int minutes = (int) seconds / 60;
            if (minutes < 1) {

                // Formatting to only seconds as time is less than a minute
                return DataAnalyser.roundNum(seconds) + " s";
            }
            // Getting seconds to format
            int sec = (int) seconds - (minutes * 60);
            return minutes + "m " + sec + " s";
        } else {
            // Formatting to hours, minutes and seconds
            int minutes = (int) seconds - (hours * 3600);
            int sec = (int) seconds - (minutes * 60 + hours * 3600);
            return hours + "h " + minutes + "m " + sec + " s";
        }
    }


}
