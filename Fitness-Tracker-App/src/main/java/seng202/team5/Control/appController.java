package seng202.team5.Control;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;



public class appController extends Application {

    private static Stage appStage;
    private static FXMLLoader loader = new FXMLLoader();
    private Class c = getClass();


    public static Stage getAppStage() {
        return  appStage;
    }

    public static void changeScene(String filename, Class c) throws IOException {
        Stage appStage = appController.getAppStage();
        URL page = c.getResource(filename);
        Parent dataParent = loader.load(page);
        Scene dataScene = new Scene(dataParent);
        appStage.setScene(dataScene);
        appStage.show();
    }

   // public static void clearButtons()


    public void start(Stage primaryStage) throws Exception {
        String filename = "/View/mainPage2.fxml";
        URL value1 = c.getResource(filename);
        System.out.println(value1);
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
