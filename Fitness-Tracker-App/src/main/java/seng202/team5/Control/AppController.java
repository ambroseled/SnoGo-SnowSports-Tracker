package seng202.team5.Control;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.team5.Model.User;

import java.net.URL;


public class AppController extends Application {

    private static Stage appStage;
    private static FXMLLoader loader = new FXMLLoader();
    private Class c = getClass();
    private static DataBaseController db = new DataBaseController();
    private AlertController alertTab = new AlertController();


    ////////////
    // Used for testing will later be the actual current user.
    private static User currentUser = db.getUsers().get(0);

    ////////////

    public static Stage getAppStage() {
        return  appStage;
    }


    public void start(Stage primaryStage) throws Exception {
        String filename = "/View/tabMain.fxml";
        URL value1 = c.getResource(filename);
        Parent root = loader.load(value1);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(1280);

        //
        currentUser.setGoals(db.getGoals(currentUser.getId()));
        currentUser.setAlerts(db.getAlerts(currentUser.getId()));
        //


        appStage = primaryStage;
    }


    public static User getCurrentUser() {
        return currentUser;
    }


    public static void main(String[] args) {

        launch(args);
    }
}
