package seng202.team5.Control;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.net.URL;



public class homeController extends Application {


    /**
     * Handles when the dataButton is pressed
     */
    public void dataButtonPress() {
        System.out.println("Data button pressed");
    }


    /**
     * Handles when the mapButton is pressed
     */
    public void mapButtonPress() {

    }


    /**
     * Handles when the goalButton is pressed
     */
    public void goalButtonPress() {

    }


    /**
     * Handles when the calendarButton is pressed
     */
    public void calButtonPress() {

    }


    /**
     * Handles when the alertButton is pressed
     */
    public void alertButtonPress() {

    }


    /**
     * Handles when the profileButton is pressed
     */
    public void profButtonPress() {

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
    }

    public static void main(String[] args) {

        launch(args);
    }

}
