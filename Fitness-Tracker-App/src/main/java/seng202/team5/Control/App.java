package seng202.team5.Control;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.User;
import java.net.URL;


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

    private static FXMLLoader loader = new FXMLLoader();
    private Class c = getClass();
    private static DataBaseController db = new DataBaseController();
    ////////////
    // Used for testing will later be the actual current user.
    private static User currentUser;
    ////////////


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



    public void enableTabs() {
        dataTab.setDisable(false);
        statsTab.setDisable(false);
        mapTab.setDisable(false);
        alertsTab.setDisable(false);
        goalsTab.setDisable(false);
        profTab.setDisable(false);
    }


    public void disableTabs() {
        dataTab.setDisable(true);
        statsTab.setDisable(true);
        mapTab.setDisable(true);
        alertsTab.setDisable(true);
        goalsTab.setDisable(true);
        profTab.setDisable(true);
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


    public static void main(String[] args) {
        launch(args);
    }
}

