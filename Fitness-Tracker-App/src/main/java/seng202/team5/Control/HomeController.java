package seng202.team5.Control;



import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.Alert;
import seng202.team5.Model.CheckGoals;
import seng202.team5.Model.User;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


//TODO: Change color of border of tab when selected


/**
 * This class runs the application and also provides the profile functionality.
 */
public class HomeController {


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
//    private TableController tablesController;

    @FXML
    private GraphsController statsController;
    @FXML
    private MapController mapsController;
    @FXML
    private CompController compController;
    @FXML
    private CalController calController;
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
    boolean editing = false;

    private ArrayList<User> users;

    private ObservableList<User> userNames = FXCollections.observableArrayList();

    private DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");

    private static ObservableList<seng202.team5.Model.Alert> alerts = FXCollections.observableArrayList();



    private static DataBaseController db;

    private static User currentUser;


    private boolean backwards = false;
    private boolean pinguActivated = false;
    private double rotate = 0;
    private static boolean alert = false;
    private int count = 0;



    public ObservableList<seng202.team5.Model.Alert> getALerts() {
        return alerts;
    }


    public static void addAlert(seng202.team5.Model.Alert toAdd) {
        alert = true;
        alerts.add(toAdd);
    }


    /**
     * Setting timer for ester egg
     */
    public void initialize() {
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

                if (alert) {
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
        fillTable();

    }



    public void showAlerts() {
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
        alert = false;
        userTab.setStyle("-fx-background-color: #005e99;");
        alertsButton.setStyle("-fx-background-color: #005e99;");
       // alertTable.getItems().clear();
        // Setting table columns
        nameCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        desCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        // Adding alerts to the table
        alertTable.setItems(alerts);
    }


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
    }


    public void removeAlert() {
        // Getting the selected alert
        Alert alert = (Alert) alertTable.getSelectionModel().getSelectedItem();
        // Removing the alert from the user and the database
        if (alert != null) {
            db.removeAlert(alert);
            currentUser.setAlerts(db.getAlerts(HomeController.getCurrentUser().getId()));
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
        users = db.getUsers();
        if (userTable.getItems().size() != users.size()) {
            userTable.getItems().clear();
            userCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            userNames.addAll(users);
            userTable.setItems(userNames);
        }
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
    private void deleteUser(){
        User selectedUser = (User) userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            db.removeUser(selectedUser);
            refreshTable();
            if (HomeController.getCurrentUser() == selectedUser) {
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
        HomeController.setCurrentUser((User) userTable.getSelectionModel().getSelectedItem());
        if (HomeController.getCurrentUser() != null) {
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
    }


    @FXML
    /**
     * Setting the text fields for viewing a user
     */
    public void viewProfile() {
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
        // Setting all entry fields to the users current personal information
        nameText.setText(HomeController.getCurrentUser().getName());
        ageText.setText(Integer.toString(HomeController.getCurrentUser().getAge()));
        heightText.setText(Double.toString(HomeController.getCurrentUser().getHeight()));
        weightText.setText(Double.toString(HomeController.getCurrentUser().getWeight()));
        bmiText.setText(Double.toString(HomeController.getCurrentUser().getBmi()));
        datePicker.setValue(HomeController.getCurrentUser().getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }


    @FXML
    /**
     * Setting the fields ready to create a new user.
     */
    public void showCreate() {
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
     * Clearing all entry fields
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
            db.updateUser(HomeController.getCurrentUser());
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
                if (!duplicate) {
                    // Enabling the update button
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
        User user = new User(name, age, height, weight, date);
        db.storeNewUser(user);
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


    public void setUpStats() {
        statsController.setOverallStats();
        statsController.setChoiceBox();

    }


    public void setUpMap() {
        mapsController.fillTable();
    }


    public void setUpGoals() {
        goalsController.viewData();
    }


    public void setUpCal() {
        calController.setCurrent();
    }


    public void setUpComp() {
        compController.fillActTables();
        compController.clearBoxes();
    }

    public void setUpVideo() {
        System.out.println("Selected video view");
    }

    public void setUpTables() {
        dataController.fillTable();
    }


    /**
     *
     */
    public void updateTabs() {
        setUpTables();
        setUpMap();
        setUpStats();
        setUpCal();
        setUpComp();
        setUpGoals();
        setUpVideo();
    }



    public static void setDb(DataBaseController newDb) {
        db = newDb;
    }



}

