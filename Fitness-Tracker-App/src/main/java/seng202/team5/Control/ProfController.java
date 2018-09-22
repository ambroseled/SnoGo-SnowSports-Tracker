package seng202.team5.Control;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.User;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static seng202.team5.Model.CheckGoals.convertDate;


/**
 * This class handles the controls of the profile viewing tab of the application.
 * It provides the functionality to view and edit a users profile.
 */
public class ProfController {

    // Java fx elements used in the controller
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
    private Button updateButton;
    // Getting the database controller and current user
    private User currentUser = App.getCurrentUser();
    private DataBaseController db = App.getDb();
    // Setting the date format for the users birth date
    private DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");


    @FXML
    /**
     * Called by a mouse movement on the anchor pane, this method displays all of the users
     * personal information.
     */
    public void viewProfile() {
        // Checking if all entry fields are empty
        if (nameText.getText().isEmpty() && ageText.getText().isEmpty() && heightText.getText().isEmpty() &&
                weightText.getText().isEmpty() && bmiText.getText().isEmpty() && dateText.getText().isEmpty())
        {
            // Setting all entry fields to the users current personal information
            nameText.setText(currentUser.getName());
            ageText.setText(Integer.toString(currentUser.getAge()));
            heightText.setText(Double.toString(currentUser.getHeight()));
            weightText.setText(Double.toString(currentUser.getWeight()));
            bmiText.setText(Double.toString(currentUser.getBmi()));
            String dateString = dateTimeFormat.format(currentUser.getBirthDate());
            dateText.setText(dateString);
        }
    }


    @FXML
    /**
     * Called by a press to the updateButton, this method gets the current information
     * out of the text fields on the profile view and updates the current users entry in the database.
     */
    public void updateProfile() {
        try {
            // Getting data from entry fields
            String name = nameText.getText();
            double weight = Double.parseDouble(weightText.getText());
            double height = Double.parseDouble(heightText.getText());
            int age = Integer.parseInt(ageText.getText());
            Date date = dateTimeFormat.parse(dateText.getText());
            // Setting the new values of the users information
            currentUser.setName(name);
            currentUser.setWeight(weight);
            currentUser.setHeight(height);
            currentUser.setAge(age);
            currentUser.setBirthDate(date);
            // Updating the user in the database
            db.updateUser(currentUser);
            // Disabling update button as data in entry fields is the same as the user object
            updateButton.setDisable(true);
        } catch (Exception e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error updating user information");
        }
    }


    @FXML
    /**
     * Called by a key release on any of the editable text fields in the profile view. This
     * method checks that if the data in all the text fields is valid to update the user. If so
     * the updateButton is enabled but if the data is invalid the button is disabled.
     */
    public void checkProfile() {
        // Getting all information from entry fields
        String name = nameText.getText();
        String weight = weightText.getText();
        String age = ageText.getText();
        String date = dateText.getText();
        String height = heightText.getText();
        try {
            // Checking all entry fields values are valid
            if (checkName(name) && checkWeight(weight) && checkHeight(height) && checkInt(age) & checkDate(date, Integer.parseInt(age))) {
                // Parsing the none string values
                double weightVal = Double.parseDouble(weight);
                double heightVal = Double.parseDouble(height);
                int ageVal = Integer.parseInt(age);
                try {
                    Date dateVal = dateTimeFormat.parse(date);
                    // Checking that newly entered data isn't the same ass the users information
                    if (weightVal == currentUser.getWeight() && heightVal == currentUser.getHeight() && ageVal == currentUser.getAge()
                            && name.equals(currentUser.getName()) && dateVal == currentUser.getBirthDate()) {
                        // Disabling update button
                        updateButton.setDisable(true);
                    } else {
                        // Enabling the update button
                        updateButton.setDisable(false);
                        updateButton.setVisible(true);
                    }
                } catch (ParseException e) {
                    // Disabling update button
                    updateButton.setDisable(true);
                }
            } else {
                // Disabling update button
                updateButton.setDisable(true);
            }
        } catch (NumberFormatException e) {
            // Disabling update button
            updateButton.setDisable(true);
        }
    }


    /**
     * This method checks if the newly entered name is valid.
     * @param name The name to be checked.
     * @return A boolean flag holding the result of the check.
     */
    private boolean checkName(String name) {
        // Checking the name is of valid length and is all alphabetical
        if (name.length() > 4 && name.length() < 30) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * This method checks if a newly entered int value is valid.
     * @param value The int value to check.
     * @return A boolean flag holding the result of the check.
     */
    private boolean checkInt(String value) {
        // Trying to parse string to int returning if the parse was successful
        try {
            int x = Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * This method checks if a newly entered weight value is valid. Valid is
     * considered to be between 25 and 175 kg.
     * @param value The weight value to be checked.
     * @return A boolean flag holding the result of the check.
     */
    boolean checkWeight(String value) {
        // Trying to parse string to double returning if the parse was successful
        try {
            double x = Double.parseDouble(value);
            // Checking weight is within a reasonable range
            if (x < 25 || x > 175 ) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * This method checks if a newly entered height is valid. Valid is considered to be
     * between 2 and 10 ft.
     * @param height The height value to be checked.
     * @return A boolean flag holding the result of the check.
     */
    boolean checkHeight(String height) {
        // Trying to parse string to double returning if the parse was successful
        try {
            // Checking height is within a reasonable range
            double x = Double.parseDouble(height);
            if (x < 0.7 || x > 3 ) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * This method checks if a newly entered birth date is valid. A valid date is
     * considered to be a date which is consistent with the currently entered age.
     * @param date The date string to be checked.
     * @return A boolean flag holding the result of the check.
     */
    boolean checkDate(String date, int newAge) {
        // Trying to parse string to double returning if the parse was successful
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date x = dateTimeFormat.parse(date);
            Date current = new Date();
            // Checking that the birth date lines up with the users age
            int[] currentDate = convertDate(dateTimeFormat.format(current).split("/"));
            int[] compDate = convertDate(date.split("/"));
            if (compDate[2] != currentDate[2] - newAge) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
    }
}
