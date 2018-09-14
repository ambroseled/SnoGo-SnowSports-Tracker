package seng202.team5.Control;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng202.team5.Model.User;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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

    @FXML
    private TextField nameEdit;
    @FXML
    private TextField weightEdit;
    @FXML
    private TextField ageEdit;
    @FXML
    private TextField dateEdit;
    @FXML
    private TextField heightEdit;
    @FXML
    private Button updateButton;

    private User currentUser = AppController.getCurrentUser();
    private DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");



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
        String name = nameEdit.getText();
        String weight = weightText.getText();
        String age = ageEdit.getText();
        String date = dateEdit.getText();
        String height = heightEdit.getText();

        if (checkName(name) && checkDouble(weight) && checkDouble(height) && checkInt(age) & checkDate(date)) {
            double x = Double.parseDouble(weight);
        }
    }


    private boolean checkName(String name) {
        if (name.length() > 4 && name.length() < 30) {
            return true;
        } else {
            return false;
        }
    }


    private boolean checkInt(String value) {
        try {
            int x = Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private boolean checkDouble(String value) {
        try {
            double x = Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private boolean checkDate(String date) {
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date x = dateTimeFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


}
