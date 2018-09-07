package seng202.team5.Control;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class mapController {

    private FXMLLoader loader = new FXMLLoader();
    private Class c = getClass();

    public void homeButtonPress() throws IOException {
        System.out.println("Home button pressed");
        appController.changeScene("/View/mainPage.fxml", c);
    }

    public void dataButtonPress() throws IOException {
        System.out.println("Data button pressed");
        appController.changeScene("/View/dataView.fxml", c);
    }

    public void calendarButtonPress() throws IOException {
        System.out.println("Calendar button pressed");
        appController.changeScene("/View/calView.fxml", c);
    }

    public void goalsButtonPress() throws IOException {
        System.out.println("Goals button pressed");
        appController.changeScene("/View/goalView.fxml", c);
    }

    public void profileButtonPress() throws IOException {
        System.out.println("profile button pressed");
        appController.changeScene("/View/profView.fxml", c);
    }

    public void alertsButtonPress() throws IOException {
        System.out.println("Alerts button pressed");
        appController.changeScene("/View/alertView.fxml", c);
    }

}
