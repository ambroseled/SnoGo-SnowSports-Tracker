package seng202.team5.Control;



import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.Alert;
import seng202.team5.DataManipulation.CheckGoals;
import seng202.team5.Model.User;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


//TODO Change color of border of tab when selected


/**
 * This is the controller for the tabMain.fxml file. it provides the user entry, deletion and creation functionality,
 * the alerts viewing and deletion functionality, as well as the connectivity functionality of the entire application.
 */
public class HomeController {


    // FXML objects used by the controller
    @FXML
    private Tab dataTab;
    @FXML
    private Tab statsTab;
    @FXML
    private Tab mapTab;
    @FXML
    private Tab goalsTab;
    @FXML
    private Tab calTab;
    @FXML
    private Tab compTab;
    @FXML
    private Tab videoTab;
    @FXML
    private Tab userTab;
    @FXML
    private Tab weatherTab;
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
    private DatePicker datePicker;
    @FXML
    private CheckBox nameCheck;
    @FXML
    private CheckBox weightCheck;
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
    @FXML
    private Text ageLabel;
    @FXML
    private ImageView pingu;
    @FXML
    private ImageView logo;
    @FXML
    private GridPane gridPane;
    @FXML
    private GoalController goalsController;
    @FXML
    private DataController dataController;
    @FXML
    private GraphsController statsController;
    @FXML
    private MapController mapsController;
    @FXML
    private CompController compController;
    @FXML
    private CalController calController;
    @FXML
    private VideoController videoController;
    @FXML
    private WeatherController weatherController;
    @FXML
    private TableColumn<seng202.team5.Model.Alert, String> nameCol;
    @FXML
    private TableColumn<seng202.team5.Model.Alert, String> dateCol;
    @FXML
    private TableColumn<seng202.team5.Model.Alert, String> desCol;
    @FXML
    private TableView alertTable;
    @FXML
    private Button alertsButton;
    @FXML
    private Button hideButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button researchButton;


    boolean editing = false;
    // An array list and observable list of users used to display all users in the user table
    private ArrayList<User> users;
    private ObservableList<User> userNames = FXCollections.observableArrayList();
    // The date formatter used in profile operations
    private DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
    // an observable list used to display the users alerts
    private static ObservableList<seng202.team5.Model.Alert> alerts = FXCollections.observableArrayList();
    // The database connection used by the application
    private static DataBaseController db;
    // The current user
    private static User currentUser;
    // Variables used for the easter egg
    private boolean backwards = false;
    private boolean pinguActivated = false;
    private double rotate = 0;
    // Variables used for displaying alerts
    private static boolean alert = false;
    private int count = 0;



    /**
     * A method used when an alert is created to add to the alerts list. This method also sets a flag to notify the
     * user that the have a new alert.
     * @param toAdd The alert to add
     */
    public static void addAlert(seng202.team5.Model.Alert toAdd) {
        alert = true;
        alerts.add(toAdd);
    }




    /**
     * This method fills the user table on opening of the application and also defines a timer used for the eater egg
     * and to notify the user of an unseen alert.
     */
    public void initialize() {

        researchButton.setVisible(false);
        researchButton.setStyle("-fx-background-color: #005e99;");

        researchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = (Alert) alertTable.getSelectionModel().getSelectedItem();
                String message = alert.getMessage();
                if (message.contains("Tachycardia")) {
                    String url = "https://www.mayoclinic.org/diseases-conditions/tachycardia/symptoms-causes/syc-20355127";
                    openLink(url);
                } else if (message.contains("Bradycardia")) {
                    String url = "https://www.mayoclinic.org/diseases-conditions/bradycardia/symptoms-causes/syc-20355474";
                    openLink(url);
                }

            }
        });

        Object object =  alertTable.getSelectionModel().selectedItemProperty().get();
        int index = alertTable.getSelectionModel().selectedIndexProperty().get();

        alertTable.getSelectionModel().selectedIndexProperty().addListener((num) -> indexChangeTable());

        AnimationTimer timer = new AnimationTimer(){
            @Override 
            public void handle(long now) {
                if (pinguActivated) {
                    if (pingu.getX() > 1100) {
                        backwards = true;
                        Image image = new Image("pingu2.png");
                        pingu.setImage(image);
                    } else if (pingu.getX() < 10) {
                        backwards = false;
                        Image image = new Image("Pingu3D.png");
                        pingu.setImage(image);
                    }


                    // Testing color flash to alert the user of an alert
                    if (backwards) {
                        pingu.setX(pingu.getX() - 7.5 );
                    } else {
                        pingu.setX(pingu.getX() + 7.5 );
                    }
                    logo.setRotate(rotate++);
                }
                else {
                    logo.setRotate(0);
                }
                // Checking if the user needs to be alerted of an alert
                if (alert) {
                    // Periodically changing the colour of the User tab to signify that there is unread alerts
                    if (count <= 25) {
                        userTab.setStyle("-fx-background-color: red;");
                        alertsButton.setStyle("-fx-background-color: #005e99;");
                    } else {
                        userTab.setStyle("-fx-background-color: #005e99;");
                        alertsButton.setStyle("-fx-background-color: red;");
                    }
                    count++;
                    if (count > 50) {
                        count = 0;
                    }
                }
            }
        };
        timer.start();
        // Filling the user table
        fillTable();
    }

    private void openLink(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    private void indexChangeTable() {
        Alert alert = (Alert) alertTable.getSelectionModel().getSelectedItem();
        String type = alert.getType();

        if (type.contains("Heart")) {
            researchButton.setVisible(true);
            System.out.println(type);

        } else {
            researchButton.setVisible(false);

        }
    }



    /**
     * This method is called by the 'View Alerts' button. It disables the bulk of the table and makes the alerts
     * functionality available to the user.
     */
    public void showAlerts() {
        // Disabling and enabling features of the application
        alertsButton.setVisible(false);
        alertsButton.setDisable(true);
        hideButton.setDisable(false);
        hideButton.setVisible(true);
        deleteButton.setDisable(false);
        deleteButton.setVisible(true);


        gridPane.setOpacity(0.5);
        gridPane.setDisable(true);
        alertTable.setVisible(true);
        alertTable.setDisable(false);
        // Stopping the alerts notification
        alert = false;
        userTab.setStyle("-fx-background-color: #005e99;");
        alertsButton.setStyle("-fx-background-color: #005e99;");
       // alertTable.getItems().clear();
        // Setting table columns
        // Configuring the columns of the alerts table
        nameCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        desCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        // Filling the alerts table
        alertTable.setItems(alerts);



        if (alertTable.getSelectionModel().getSelectedItem() != null) {
            Alert selectedAlert = (Alert) alertTable.getSelectionModel().getSelectedItem();
            if (selectedAlert.getType().contains("Heart")) {
                researchButton.setVisible(true);
            }
        }
    }



    /**
     * This method is called by a press of the 'Hide Alerts' button. It disables the alerts functionality of the
     * application and rea-enables all other functionality to the user.
     */
    public void hideAlerts() {
        alertsButton.setVisible(true);
        alertsButton.setDisable(false);
        hideButton.setDisable(true);
        hideButton.setVisible(false);
        gridPane.setOpacity(1);
        gridPane.setDisable(false);
        alertTable.setVisible(false);
        alertTable.setDisable(true);
        deleteButton.setDisable(true);
        deleteButton.setVisible(false);
        researchButton.setVisible(false);
    }


    /**
     * This method is called by a press of the 'Remove Alert' button. It removes a selected alert from the application
     */
    public void removeAlert() {
        // Getting the selected alert
        Alert alert = (Alert) alertTable.getSelectionModel().getSelectedItem();
        // Removing the alert from the user and the database
        if (alert != null) {
            db.removeAlert(alert);
            currentUser.setAlerts(db.getAlerts(currentUser.getId()));
            alerts.clear();
            alerts.addAll(currentUser.getAlerts());
            showAlerts();
        }
    }


    /**
     * Gets the current user which is used by other controllers of the application
     * @return The current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }


    /**
     * This method sets the current user of the application
     * @param user The new current user
     */
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
     * This method populated the shown table with the current users in the database
     */
    private void fillTable() {
        // Getting the users in the database
        users = db.getUsers();
        if (userTable.getItems().size() != users.size()) {
            // Filling the table
            userTable.getItems().clear();
            userCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            userNames.addAll(users);
            userTable.setItems(userNames);
        }
        // Setting the user entry/edit data picker
        try {
            Date date = dateTimeFormat.parse("08/09/1990");
            datePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } catch (ParseException e) {

        }
    }


    /**
     * Refreshes the user table.
     */
    private void refreshTable(){
        users = db.getUsers();
        userTable.getItems().clear();
        userCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        userNames.addAll(users);
        userTable.setItems(userNames);
    }


    @FXML
    /**
     * Deletes a selected user from the database.
     */
    private void deleteUser() {
        // Getting the selected user
        User selectedUser = (User) userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Removing the user
            db.removeUser(selectedUser);
            refreshTable();
            if (currentUser == selectedUser) {
                // Disabling the tabs as the current user was deleted
                currentUser =  null;
                disableTabs();
            }
            showCreate();
        }
    }


    @FXML
    /**
     * Enabling buttons if a user is selected
     */
    private void checkUserSelected(){
        if (userTable.getSelectionModel().getSelectedItem() != null){
            removeButton.setDisable(false);
            selectButton.setDisable(false);
        } else {
            removeButton.setDisable(true);
            selectButton.setDisable(true);
        }
    }


    @FXML
    /**
     * Setting the current user of the HomeController class
     */
    private void setSelectedUser(){
        // Setting the current user
        setCurrentUser((User) userTable.getSelectionModel().getSelectedItem());
        if (currentUser != null) {
            // A user was selected, application functionality is now enabled
            enableTabs();
            viewProfile();
            checkPingu();
            updateTabs();
            statsController.resetData();
            alerts.clear();
            alerts.addAll(currentUser.getAlerts());
            alertsButton.setDisable(false);
        }
    }


    /**
     * Checking if the users name is 'pingu' or 'Pingu'. Used for eater egg.
     */
    private void checkPingu() {
        if (currentUser.getName().equals("pingu") | currentUser.getName().equals("Pingu")) {
            pinguActivated = true;
            pingu.setVisible(true);
        } else {
            pinguActivated = false;
            pingu.setVisible(false);
        }
    }


    /**
     * Disabling all the tabs as the current user is null
     */
    private void disableTabs() {
        dataTab.setDisable(true);
        statsTab.setDisable(true);
        mapTab.setDisable(true);
        goalsTab.setDisable(true);
        calTab.setDisable(true);
        compTab.setDisable(true);
        videoTab.setDisable(true);
        weatherTab.setDisable(true);
    }


    /**
     * Enabling all tabs as the current user is no not null
     */
    private void enableTabs() {
        dataTab.setDisable(false);
        statsTab.setDisable(false);
        mapTab.setDisable(false);
        goalsTab.setDisable(false);
        calTab.setDisable(false);
        compTab.setDisable(false);
        videoTab.setDisable(false);
        weatherTab.setDisable(false);
    }


    @FXML
    /**
     * Setting the text fields for viewing a user
     */
    public void viewProfile() {
        // Making extra user mechanisms visible
        bmiText.setVisible(true);
        ageLabel.setVisible(true);
        ageText.setVisible(true);
        bmiLabel.setVisible(true);
        editButton.setVisible(true);
        abortButton.setVisible(true);
        abortButton.setDisable(false);
        createButton.setDisable(true);
        createButton.setVisible(false);
        editing = true;
        // Setting the text fields to that of the current user
        nameText.setText(currentUser.getName());
        ageText.setText(Integer.toString(currentUser.getAge()));
        heightText.setText(Double.toString(currentUser.getHeight()));
        weightText.setText(Double.toString(currentUser.getWeight()));
        bmiText.setText(Double.toString(currentUser.getBmi()));
        datePicker.setValue(currentUser.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }


    @FXML
    /**
     * Setting the fields ready to create a new user.
     */
    public void showCreate() {
        // Setting the user creation mechanisms back to creation state
        bmiText.setVisible(false);
        bmiLabel.setVisible(false);
        ageText.setVisible(false);
        ageLabel.setVisible(false);
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


    /**
     * Clearing all user entry fields
     */
    private void clearFields() {
        nameText.clear();
        weightText.clear();
        try {
            Date date = dateTimeFormat.parse("08/09/1990");
            datePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } catch (ParseException e) {

        }
        heightText.clear();
    }


    /**
     * Clearing all the check boxes
     */
    private void clearChecks() {
        nameCheck.setSelected(false);
        weightCheck.setSelected(false);
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
            Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            int age = calcAge(date);
            // Setting the new values of the users information
            currentUser.setName(name);
            currentUser.setWeight(weight);
            currentUser.setHeight(height);
            currentUser.setAge(age);
            currentUser.setBirthDate(date);
            // Updating the user in the database
            db.updateUser(currentUser);
            clearChecks();
            clearFields();
            viewProfile();
            users = db.getUsers();
            userTable.getItems().clear();
            userCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            userNames.addAll(users);
            userTable.setItems(userNames);
            checkPingu();
        } catch (Exception e) {
            // Showing error dialogue to user
            ErrorController.displayError("Error updating user information");
        }
    }


    /**
     * Calculates the age of a user according to the current year.
     * @param birth The users birth date
     * @return The users age
     */
    private int calcAge(Date birth) {
        Date age = new Date();
        String[] vals = dateTimeFormat.format(age).split("/");
        int[] intVals = CheckGoals.convertDate(vals);
        int[] ageVals = CheckGoals.convertDate(dateTimeFormat.format(birth).split("/"));
        return intVals[2] - ageVals[2];
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
            // Checking if all entry fields are valid
            boolean name = checkName(nameText.getText());
            boolean weight = checkWeight(weightText.getText());
            String dateText = dateTimeFormat.format(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            boolean date = checkDate(dateText);
            boolean height = checkHeight(heightText.getText());
            // Checking all entry fields values are valid
            if (name && weight && date && height) {
                // Parsing the none string values
                double weightVal = Double.parseDouble(weightText.getText());
                double heightVal = Double.parseDouble(heightText.getText());
                Date dateVal = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                // Checking that newly entered data isn't the same ass the users information
                boolean duplicate = false;
                for (User user : db.getUsers()) {
                    if (weightVal == user.getWeight()
                            && heightVal == user.getHeight()
                            && nameText.getText().equals(user.getName())
                            && dateText.equals(dateTimeFormat.format(user.getBirthDate()))) {
                        // Disabling update button
                        duplicate = true;
                        clearChecks();
                    }
                }
        // Enabling or disabling the create or edit button dependent of the result of the validity checks
                if (!duplicate) {
                    if (editing) {
                        editButton.setDisable(false);
                    } else {
                        createButton.setDisable(false);
                    }
                } else {
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
    /**
     * Creating a new user from the entry fields values. The user is added to the database and user table
     */
    public void createUser() {
        // Getting data from entry fields
        String name = nameText.getText();
        double weight = Double.parseDouble(weightText.getText());
        double height = Double.parseDouble(heightText.getText());
        Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        int age = calcAge(date);
        // Creating the user
        User user = new User(name, age, height, weight, date);
        // Storing the new user in the database
        db.storeNewUser(user);
        // Refreshing state of the page after the user creation
        refreshTable();
        clearChecks();
        clearFields();
    }



    /**
     * This method checks if the newly entered name is valid.
     * @param name The name to be checked.
     * @return A boolean flag holding the result of the check.
     */
    private boolean checkName(String name) {
        // Checking the name is of valid length and is all alphabetical
        if (name.length() > 3 && name.length() < 30) {
            nameCheck.setSelected(true);
            return true;
        } else {
            nameCheck.setSelected(false);
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
    private boolean checkDate(String date) {
        // Trying to parse string to double returning if the parse was successful
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date x = dateTimeFormat.parse(date);
            Date current = new Date();
            if (x.getTime() > current.getTime()) {
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


    /**
     * This method is called by a press on the 'Stats' tab. It set up the overall graphs and the activity choice box
     */
    public void setUpStats() {
        statsController.showWeek();
        statsController.setChoiceBox();
    }


    /**
     * This method is called by a press on te 'Map' tab. It sets the data in the activity table
     */
    public void setUpMap() {
        mapsController.fillTable();
    }


    /**
     * This method is called by a press on the 'Goals' tab, It sets the data in the goals table
     */
    public void setUpGoals() {
        goalsController.viewData();
    }


    /**
     * This method is called by a press on the 'Calendar' tab. It fills both of the activity tables
     */
    public void setUpCal() {
        calController.setCurrent(false);
    }


    /**
     * This method is called by a press on the 'Compare' tab. It fills both of the activity tables
     */
    public void setUpComp() {
        compController.fillActTables();
        compController.clearBoxes();
    }


    /**
     * This method is called by a press of the 'Video' tab. ...
     */
    public void setUpVideo() {
        videoController.initialize();
    }


    /**
     * This method is called by a press on the 'Data' tab. ...
     */
    public void setUpTables() {
        dataController.fillTable();
    }


    /**
     * This method is called by a press on the 'User' tab. It checks if any of the users goals are now expired
     * or completed
     */
    public void checkGoals() {
        if (currentUser != null && db != null) {
            CheckGoals.markGoals(currentUser, db, currentUser.getActivities());
        }
    }


    /**
     * This method is called by a press on the 'Weather' tab. It fills the fields table.
     */
    public void setUpWeather() {
        weatherController.showTables();
    }


    /**
     * This method calls all of the on selection methods of all of the application tabs. It updates the state of
     * all tabs
     */
    public void updateTabs() {
        setUpTables();
        setUpMap();
        setUpStats();
        calController.setCurrent(true);
        setUpComp();
        setUpGoals();
        setUpVideo();
        checkGoals();
        setUpWeather();
    }


    /**
     * This method sets DataBaseController of the application
     * @param newDb The new DataBaseController to be used
     */
    public static void setDb(DataBaseController newDb) {
        db = newDb;
    }

}

