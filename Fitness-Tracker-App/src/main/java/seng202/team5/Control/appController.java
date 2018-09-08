package seng202.team5.Control;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.team5.Model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class appController extends Application {

    private static Stage appStage;
    private static FXMLLoader loader = new FXMLLoader();
    private Class c = getClass();

    ////////////
    // Used for testing will later be the actual current user.
    private static User currentUser;

    ////////////

    public static Stage getAppStage() {
        return  appStage;
    }



    public void start(Stage primaryStage) throws Exception {
        String filename = "/View/tabMain.fxml";
        URL value1 = c.getResource(filename);
        System.out.println(value1);
        Parent root = loader.load(value1);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1280);

        appStage = primaryStage;
    }


    public static User getCurrentUser() {
        return currentUser;
    }


    public static void main(String[] args) {

        launch(args);
    }
}
