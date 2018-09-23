package seng202.team5.Control;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.User;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static seng202.team5.Model.CheckGoals.convertDate;

/**
 *
 */
public class App extends Application {


    @FXML
    private Tab dataTab;
    @FXML
    private Tab statsTab;
    @FXML
    private Tab mapTab;
    @FXML
    private Tab alertsTab;
    @FXML
    private Tab goalsTab;
    @FXML
    private Tab profTab;
    @FXML
    private TableView userTable;
    @FXML
    private TableColumn<User, String> userCol;
    @FXML
    private Button removeButton;
    @FXML
    private Button selectButton;
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
    private CheckBox nameCheck;
    @FXML
    private CheckBox weightCheck;
    @FXML
    private CheckBox ageCheck;
    @FXML
    private CheckBox dateCheck;
    @FXML
    private CheckBox heightCheck;
    @FXML
    private Button editButton;
    @FXML
    private Button abortButton;
    @FXML
    private Button createButton;
    @FXML
    private Text bmiLabel;


    boolean editing = false;

    private ArrayList<User> users;

    private ObservableList<User> userNames = FXCollections.observableArrayList();

    private DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");

    private static FXMLLoader loader = new FXMLLoader();

    private Class c = getClass();

    private static DataBaseController db = new DataBaseController();

    private static User currentUser;



    /**
     * Creates the application GUI scene, based on tabMain.fxml file
     * @param primaryStage
     * @throws Exception IOException
     */
    public void start(Stage primaryStage) throws Exception {
        URL value1 = c.getResource("/View/tabMain.fxml");
        Parent root = loader.load(value1);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("SnoGo");
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(1280);
        primaryStage.setResizable(false);
    }


    @Override
    /**
     * Closing the database connection when the application is closed
     */
    public void stop(){
        db.closeConnection();
    }



    /**
     * Gets the current user which is used by other controllers of the application
     * @return The current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }


    public static void setCurrentUser(User user) {
        currentUser = user;
    }


    /**
     * Gets the dataBaseController which is used by other controllers of the application
     * @return The current DataBaseController
     */
    public static DataBaseController getDb() {
        return db;
    }


    @FXML
    /**
     * Called by mouse movement onto the tab; this method populated the shown table with the current users in the database
     */
    private void fillTable() {
        users = db.getUsers();
        if (userTable.getItems().size() != users.size()) {
            userTable.getItems().clear();
            userCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            userNames.addAll(users);
            userTable.setItems(userNames);
        }
    }


    private void refreshTable(){
        users = db.getUsers();
        userTable.getItems().clear();
        userCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        userNames.addAll(users);
        userTable.setItems(userNames);
    }



    @FXML
    private void deleteUser(){
        User selectedUser = (User) userTable.getSelectionModel().getSelectedItem();
        db.removeUser(selectedUser);
        refreshTable();
        if (App.getCurrentUser() == selectedUser) {
            currentUser =  null;
            disableTabs();
        }
    }

    @FXML
    private void checkUserSelected(){
        if (userTable.getSelectionModel().getSelectedItem() != null){
            removeButton.setDisable(false);
            selectButton.setDisable(false);
        }
    }

    @FXML
    private void setSelectedUser(){
        App.setCurrentUser((User) userTable.getSelectionModel().getSelectedItem());
        if (App.getCurrentUser() != null) {
            enableTabs();
            viewProfile();
        }

    }


    private void disableTabs() {
        dataTab.setDisable(true);
        statsTab.setDisable(true);
        mapTab.setDisable(true);
        alertsTab.setDisable(true);
        goalsTab.setDisable(true);
        profTab.setDisable(true);
    }


    private void enableTabs() {
        dataTab.setDisable(false);
        statsTab.setDisable(false);
        mapTab.setDisable(false);
        alertsTab.setDisable(false);
        goalsTab.setDisable(false);
        profTab.setDisable(false);
    }


    @FXML
    public void viewProfile() {
        bmiText.setDisable(false);
        bmiText.setVisible(true);
        bmiLabel.setVisible(true);
        editButton.setVisible(true);
        abortButton.setVisible(true);
        abortButton.setDisable(false);
        createButton.setDisable(true);
        createButton.setVisible(false);
        editing = true;
        // Setting all entry fields to the users current personal information
        nameText.setText(App.getCurrentUser().getName());
        ageText.setText(Integer.toString(App.getCurrentUser().getAge()));
        heightText.setText(Double.toString(App.getCurrentUser().getHeight()));
        weightText.setText(Double.toString(App.getCurrentUser().getWeight()));
        bmiText.setText(Double.toString(App.getCurrentUser().getBmi()));
        String dateString = dateTimeFormat.format(App.getCurrentUser().getBirthDate());
        dateText.setText(dateString);
    }


    @FXML
    public void showCreate() {
        bmiText.setDisable(true);
        bmiText.setVisible(false);
        bmiLabel.setVisible(false);
        editButton.setVisible(false);
        editButton.setDisable(true);
        abortButton.setVisible(false);
        abortButton.setDisable(true);
        createButton.setDisable(false);
        createButton.setVisible(true);
        clearFields();
        editing = false;
        clearChecks();
    }



    private void clearFields() {
        nameText.clear();
        weightText.clear();
        ageText.clear();
        dateText.clear();
        heightText.clear();
    }



    private void clearChecks() {
        nameCheck.setSelected(false);
        weightCheck.setSelected(false);
        ageCheck.setSelected(false);
        dateCheck.setSelected(false);
        heightCheck.setSelected(false);
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
            db.updateUser(App.getCurrentUser());
            clearChecks();
            clearFields();
            viewProfile();
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

        try {
            boolean name = checkName(nameText.getText());
            boolean weight = checkWeight(weightText.getText());
            boolean age = checkAge(ageText.getText());
            boolean date = checkDate(dateText.getText(), Integer.parseInt(ageText.getText()));
            boolean height = checkHeight(heightText.getText());
            // Checking all entry fields values are valid
            if (name && weight && age && date && height) {
                // Parsing the none string values
                double weightVal = Double.parseDouble(weightText.getText());
                double heightVal = Double.parseDouble(heightText.getText());
                int ageVal = Integer.parseInt(ageText.getText());
                try {
                    Date dateVal = dateTimeFormat.parse(dateText.getText());
                    // Checking that newly entered data isn't the same ass the users information
                    boolean duplicate = false;
                    for (User user : db.getUsers()) {
                        if (weightVal == user.getWeight()
                                && heightVal == user.getHeight()
                                && ageVal == user.getAge()
                                && nameText.getText().equals(user.getName())
                                && dateVal == user.getBirthDate()) {
                            // Disabling update button
                            if (editing) {
                                editButton.setDisable(true);
                            } else {
                                createButton.setDisable(true);
                            }
                        }
                    }
                    if (!duplicate) {
                        // Enabling the update button
                        if (editing) {
                            editButton.setDisable(false);
                        } else {
                            createButton.setDisable(false);
                        }
                    }
                } catch (ParseException e) {
                    // Disabling update button
                    if (editing) {
                        editButton.setDisable(true);
                    } else {
                        createButton.setDisable(true);
                    }
                }
            } else {
                // Disabling update button
                if (editing) {
                    editButton.setDisable(true);
                } else {
                    createButton.setDisable(true);
                }
            }
        } catch (NumberFormatException e) {
            // Disabling update button
            if (editing) {
                editButton.setDisable(true);
            } else {
                createButton.setDisable(true);
            }
        }
    }


    @FXML
    public void createUser() {
        try {
            // Getting data from entry fields
            String name = nameText.getText();
            double weight = Double.parseDouble(weightText.getText());
            double height = Double.parseDouble(heightText.getText());
            int age = Integer.parseInt(ageText.getText());
            Date date = dateTimeFormat.parse(dateText.getText());
            User user = new User(name, age, height, weight, date);
            db.storeNewUser(user);
            refreshTable();
            clearChecks();
            clearFields();
        } catch (ParseException e) {
            ErrorController.displayError("Error parsing birth date");
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
            nameCheck.setSelected(true);
            return true;
        } else {
            nameCheck.setSelected(false);
            return false;
        }
    }


    /**
     * This method checks if a newly entered int value is valid.
     * @param value The int value to check.
     * @return A boolean flag holding the result of the check.
     */
    private boolean checkAge(String value) {
        // Trying to parse string to int returning if the parse was successful
        try {
            int x = Integer.parseInt(value);
            if (x > 115 | x < 0) {
                ageCheck.setSelected(false);
                return false;
            }
            ageCheck.setSelected(true);
            return true;
        } catch (NumberFormatException e) {
            ageCheck.setSelected(false);
            return false;
        }
    }


    /**
     * This method checks if a newly entered weight value is valid. Valid is
     * considered to be between 25 and 175 kg.
     * @param value The weight value to be checked.
     * @return A boolean flag holding the result of the check.
     */
    private boolean checkWeight(String value) {
        // Trying to parse string to double returning if the parse was successful
        try {
            double x = Double.parseDouble(value);
            // Checking weight is within a reasonable range
            if (x < 25 || x > 175 ) {
                weightCheck.setSelected(false);
                return false;
            } else {
                weightCheck.setSelected(true);
                return true;
            }
        } catch (NumberFormatException e) {
            weightCheck.setSelected(false);
            return false;
        }
    }


    /**
     * This method checks if a newly entered height is valid. Valid is considered to be
     * between 2 and 10 ft.
     * @param height The height value to be checked.
     * @return A boolean flag holding the result of the check.
     */
    private boolean checkHeight(String height) {
        // Trying to parse string to double returning if the parse was successful
        try {
            // Checking height is within a reasonable range
            double x = Double.parseDouble(height);
            if (x < 0.7 || x > 3 ) {
                heightCheck.setSelected(false);
                return false;
            } else {
                heightCheck.setSelected(true);
                return true;
            }
        } catch (NumberFormatException e) {
            heightCheck.setSelected(false);
            return false;
        }
    }


    /**
     * This method checks if a newly entered birth date is valid. A valid date is
     * considered to be a date which is consistent with the currently entered age.
     * @param date The date string to be checked.
     * @return A boolean flag holding the result of the check.
     */
    private boolean checkDate(String date, int newAge) {
        // Trying to parse string to double returning if the parse was successful
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date x = dateTimeFormat.parse(date);
            Date current = new Date();
            // Checking that the birth date lines up with the users age
            int[] currentDate = convertDate(dateTimeFormat.format(current).split("/"));
            int[] compDate = convertDate(date.split("/"));
            if (compDate[2] != currentDate[2] - newAge) {
                dateCheck.setSelected(false);
                return false;
            } else {
                dateCheck.setSelected(true);
                return true;
            }
        } catch (ParseException e) {
            dateCheck.setSelected(false);
            return false;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

