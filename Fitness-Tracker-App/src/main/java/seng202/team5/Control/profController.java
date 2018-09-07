package seng202.team5.Control;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng202.team5.Model.User;


public class profController {
    @FXML
    TextField nameText;
    @FXML
    TextField ageText;
    @FXML
    TextField heightText;
    @FXML
    TextField weightText;
    @FXML
    TextField bmiText;
    @FXML
    TextField dateText;
    User currentUser;



    @FXML
    public void viewProfile() {
        currentUser = appController.getCurrentUser();
        nameText.setText(currentUser.getName());
        ageText.setText(Integer.toString(currentUser.getAge()));
        heightText.setText(Double.toString(currentUser.getHeight()));
        weightText.setText(Double.toString(currentUser.getWeight()));
        bmiText.setText(Double.toString(currentUser.getBmi()));
       // dateText.setText(currentUser.getBirthDate().toString());
        dateText.setText("NEED TO FIX");
    }


    ///////////
    // WIll get James to do as he is already doing user stuff
    ////////
    @FXML
    public void updateProfile() {

    }


}
