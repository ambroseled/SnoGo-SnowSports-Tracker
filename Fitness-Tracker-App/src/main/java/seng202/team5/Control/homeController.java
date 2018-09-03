package seng202.team5.Control;



import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;


public class homeController {

    private FXMLLoader loader = new FXMLLoader();
    Class c = getClass();



    public void changeScene(String filename) throws IOException {
        Stage appStage = appController.getAppStage();
        URL page = c.getResource(filename);
        Parent dataParent = loader.load(page);
        Scene dataScene = new Scene(dataParent);
        appStage.setScene(dataScene);
        appStage.show();
    }


    /**
     * Handles when the dataButton is pressed
     */
    public void dataButtonPress() throws IOException {
        System.out.println("Data button pressed");
        changeScene("/View/dataView.fxml");
    }


    /**
     * Handles when the mapButton is pressed
     */
    public void mapButtonPress() throws IOException {
        System.out.println("Map button pressed");
        changeScene("/View/mapView.fxml");
    }


    /**
     * Handles when the goalButton is pressed
     */
    public void goalButtonPress() throws IOException {
        System.out.println("Goal button pressed");
        changeScene("/View/goalView.fxml");
    }


    /**
     * Handles when the calendarButton is pressed
     */
    public void calButtonPress() throws IOException {
        System.out.println("Calender button pressed");
        changeScene("/View/calView.fxml");
    }


    /**
     * Handles when the alertButton is pressed
     */
    public void alertButtonPress() throws IOException {
        System.out.println("Alert button pressed");
        changeScene("/View/alertView.fxml");
    }


    /**
     * Handles when the profileButton is pressed
     */
    public void profButtonPress() throws IOException {
        System.out.println("Profile button pressed");
        changeScene("/View/profView.fxml");
    }


}
