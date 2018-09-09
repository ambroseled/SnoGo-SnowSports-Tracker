package seng202.team5.Control;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng202.team5.Model.User;

import java.text.Format;
import java.text.SimpleDateFormat;


public class ProfController {
    @FXML
    private  TextField nameText;
    @FXML
    private  TextField ageText;
    @FXML
    private  TextField heightText;
    @FXML
    private TextField weightText;
    @FXML
    private TextField bmiText;
    @FXML
    private TextField dateText;
    @FXML
    private Button viewButton;
    User currentUser;



    @FXML
    /**
     * Displays all the information about the current user.
     */
    public void viewProfile() {
        viewButton.setDisable(true);
        viewButton.setVisible(false);
        currentUser = AppController.getCurrentUser();
        nameText.setText(currentUser.getName());
        ageText.setText(Integer.toString(currentUser.getAge()));
        heightText.setText(Double.toString(currentUser.getHeight()));
        weightText.setText(Double.toString(currentUser.getWeight()));
        bmiText.setText(Double.toString(currentUser.getBmi()));
        // Displaying date not working

       // Format formatter = new SimpleDateFormat("yyyy-MM-dd");
       // String dateString = formatter.format(currentUser.getBirthDate());

        dateText.setText("Need to fix");
    }


    ///////////
    // WIll get James to do as he is already doing user stuff
    ////////
    @FXML
    public void updateProfile() {

    }


}
