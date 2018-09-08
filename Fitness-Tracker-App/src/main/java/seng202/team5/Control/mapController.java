package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import seng202.team5.Model.Activity;
import seng202.team5.Model.InputDataParser;
import seng202.team5.Model.User;

import java.io.IOException;
import java.util.ArrayList;

public class mapController {

    private ArrayList<Activity> activities;
    private User currentUser;
    private InputDataParser parser = new InputDataParser();

    @FXML
    private ComboBox<String> activityCombo;
    @FXML
    private Button selectButton;
    @FXML
    private Button loadButton;
    private ObservableList<String> names = FXCollections.observableArrayList();

    @FXML
    public void displayActivities() {
        selectButton.setVisible(true);
        loadButton.setVisible(false);
        loadButton.setDisable(true);
        currentUser = appController.getCurrentUser();
       // activities = currentUser.getActivities();
        activities = parser.parseCSVToActivities("testData.csv");

        if (activities != null) {
            fillList(activities);
            activityCombo.setItems(names);
        }
    }

    @FXML
    public void selectButtonPress() {
        String name = activityCombo.getValue();
        System.out.println(name);
        /**
         * This needs to get the activity out of the list
         */
    }


    private void fillList(ArrayList<Activity> activities) {
        for (Activity activity : activities) {
            names.add(activity.getName());
        }
    }


}
