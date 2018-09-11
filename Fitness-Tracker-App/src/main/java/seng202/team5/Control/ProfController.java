package seng202.team5.Control;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng202.team5.Model.User;

import java.text.Format;
import java.text.SimpleDateFormat;


/**
 * THis class handles the controls of the profile viewing tab of the application.
 */
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
     * Called by a press to the viewButton, this method displays all of the users
     * personal information.
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

        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
        String dateString = formatter.format(currentUser.getBirthDate());
        dateText.setText(dateString);
    }


    ///////////
    // WIll get James to do as he is already doing user stuff
    ////////
    @FXML
    public void updateProfile() {

    }


}
