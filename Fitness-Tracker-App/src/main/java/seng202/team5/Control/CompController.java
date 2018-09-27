package seng202.team5.Control;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataSet;


public class CompController {


    @FXML
    private TableView actTable;
    @FXML
    private TableView actTable1;
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
    private Rectangle topSpeedBox;
    @FXML
    private Rectangle totalDistBox;
    @FXML
    private Rectangle vertBox;
    @FXML
    private Rectangle heartBox;
    @FXML
    private Rectangle calBox;
    @FXML
    private Rectangle avgSpeedBox;
    @FXML
    private Rectangle topSpeedBox1;
    @FXML
    private Rectangle totalDistBox1;
    @FXML
    private Rectangle vertBox1;
    @FXML
    private Rectangle heartBox1;
    @FXML
    private Rectangle calBox1;
    @FXML
    private Rectangle avgSpeedBox1;
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
    private Text heartLabel;
    @FXML
    private Text heartLabel1;
    @FXML
    private Text calLabel;
    @FXML
    private Text calLabel1;
    @FXML
    private Text avgSpeedLabel;
    @FXML
    private Text avgSpeedLabel1;






    private ObservableList<Activity> activities = FXCollections.observableArrayList();




    public void fillActTables() {
        actTable.getItems().clear();
        actTable1.getItems().clear();

        activities.addAll(HomeController.getCurrentUser().getActivities());

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        actTable.setItems(activities);

        nameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCol1.setCellValueFactory(new PropertyValueFactory<>("date"));
        actTable1.setItems(activities);
    }


    @FXML
    public void showComparison() {
        Activity act = (Activity) actTable.getSelectionModel().getSelectedItem();
        Activity act1 = (Activity) actTable1.getSelectionModel().getSelectedItem();
        if (act != null && act1 != null) {
            clearBoxes();
            showText(act.getDataSet(), topSpeedText, totDistText, vertText, heartText, calText, avgSpeedText);
            showText(act1.getDataSet(), topSpeedText1, totDistText1, vertText1, heartText1, calText1, avgSpeedText1);
            compareActivities(act.getDataSet(), act1.getDataSet());
        }
    }



    private void compareActivities(DataSet set, DataSet set1) {

        if (set.getTopSpeed() > set1.getTopSpeed()) {
            setColours(topSpeedBox, topSpeedBox1, topSpeedText, topSpeedText1, topSpeedLabel, topSpeedLabel1);
        } else if (set.getTopSpeed() < set1.getTopSpeed()) {
            setColours(topSpeedBox1, topSpeedBox, topSpeedText, topSpeedText1, topSpeedLabel, topSpeedLabel1);
        }

        if (set.getVerticalDistance() > set1.getVerticalDistance()) {
            setColours(vertBox, vertBox1, vertText, vertText1, vertLabel, vertLabel1);
        } else if (set.getVerticalDistance() < set1.getVerticalDistance()) {
            setColours(vertBox1, vertBox, vertText, vertText1, vertLabel, vertLabel1);
        }

        if (set.getTotalDistance() > set1.getTotalDistance()) {
            setColours(totalDistBox, totalDistBox1, totDistText, totDistText1, totDistLabel, totDistLabel1);
        } else if (set.getTotalDistance() < set1.getTotalDistance()) {
            setColours(totalDistBox1, totalDistBox, totDistText, totDistText1, totDistLabel, totDistLabel1);
        }
    /* Not sure if heart rate should be coloured as not sure if a higher heart rate is better
        if (set.getAvgHeartRate() > set1.getAvgHeartRate() ) {
            setColours(heartBox, heartBox1, heartText, heartText1, heartLabel, heartLabel1);
        } else if (set.getAvgHeartRate()  < set1.getAvgHeartRate() ) {
            setColours(heartBox1, heartBox, heartText, heartText1, heartLabel, heartLabel1);
        } */

        if (set.getCaloriesBurned() > set1.getCaloriesBurned() ) {
            setColours(calBox, calBox1, calText, calText1, calLabel, calLabel1);
        } else if (set.getCaloriesBurned()  < set1.getCaloriesBurned() ) {
            setColours(calBox1, calBox, calText, calText1, calLabel, calLabel1);
        }

        if (set.getAvgSpeed() > set1.getAvgSpeed() ) {
            setColours(avgSpeedBox, avgSpeedBox1, avgSpeedText, avgSpeedText1, avgSpeedLabel, avgSpeedLabel1);
        } else if (set.getAvgSpeed()  < set1.getAvgSpeed() ) {
            setColours(avgSpeedBox1, avgSpeedBox, avgSpeedText, avgSpeedText1, avgSpeedLabel, avgSpeedLabel1);
        }
    }


    private void setColours(Rectangle green, Rectangle red, Text label1, Text label2, Text label3, Text label4) {
        green.setFill(Color.GREEN);
        red.setFill(Color.RED);
        label1.setFill(Color.WHITE);
        label2.setFill(Color.WHITE);
        label3.setFill(Color.WHITE);
        label4.setFill(Color.WHITE);

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


    private void showText(DataSet set, Text speed, Text dist, Text vert, Text heart, Text cal, Text avgSpeed) {
        speed.setText(set.getTopSpeed() + " m/s");
        dist.setText(set.getTotalDistance() + " m");
        vert.setText(set.getVerticalDistance() + " m");
        heart.setText(set.getAvgHeartRate() + " bpm");
        cal.setText(Double.toString(set.getCaloriesBurned()));
        avgSpeed.setText(set.getAvgSpeed() + " m/s");
    }


    public void clearBoxes() {
        topSpeedBox.setFill(Color.WHITE);
        totalDistBox.setFill(Color.WHITE);
        vertBox.setFill(Color.WHITE);
        heartBox.setFill(Color.WHITE);
        calBox.setFill(Color.WHITE);
        avgSpeedBox.setFill(Color.WHITE);

        topSpeedBox1.setFill(Color.WHITE);
        totalDistBox1.setFill(Color.WHITE);
        vertBox1.setFill(Color.WHITE);
        heartBox1.setFill(Color.WHITE);
        calBox1.setFill(Color.WHITE);
        avgSpeedBox1.setFill(Color.WHITE);

        topSpeedText.setFill(Color.BLACK);
        totDistText.setFill(Color.BLACK);
        vertText.setFill(Color.BLACK);
        heartText.setFill(Color.BLACK);
        calText.setFill(Color.BLACK);
        avgSpeedText.setFill(Color.BLACK);

        topSpeedText1.setFill(Color.BLACK);
        totDistText1.setFill(Color.BLACK);
        vertText1.setFill(Color.BLACK);
        heartText1.setFill(Color.BLACK);
        calText1.setFill(Color.BLACK);
        avgSpeedText1.setFill(Color.BLACK);

        topSpeedLabel.setFill(Color.BLACK);
        totDistLabel.setFill(Color.BLACK);
        vertLabel.setFill(Color.BLACK);
        heartLabel.setFill(Color.BLACK);
        calLabel.setFill(Color.BLACK);
        avgSpeedLabel.setFill(Color.BLACK);

        topSpeedLabel1.setFill(Color.BLACK);
        totDistLabel1.setFill(Color.BLACK);
        vertLabel1.setFill(Color.BLACK);
        heartLabel1.setFill(Color.BLACK);
        calLabel1.setFill(Color.BLACK);
        avgSpeedLabel1.setFill(Color.BLACK);
    }


}
