package seng202.team5.Control;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.net.URL;



public class appController extends Application {

    private static Stage appStage;


    public static Stage getAppStage() {
        return  appStage;
    }


    public void start(Stage primaryStage) throws Exception {
        Class c = getClass();
        String filename = "/View/firstPage.fxml";
        FXMLLoader loader = new FXMLLoader();
        URL value1 = c.getResource(filename);
        Parent root = loader.load(value1);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

        appStage = primaryStage;
    }

    public static void main(String[] args) {

        launch(args);
    }
}
