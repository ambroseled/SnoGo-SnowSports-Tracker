package seng202.team5.Control;

import javafx.scene.control.Alert;

public class ErrorController {

    public static void displayError(String errorMessage) {
        Alert errorPopup = new Alert(Alert.AlertType.ERROR);
        errorPopup.setTitle("Error Dialog");
        errorPopup.setContentText(errorMessage);

        errorPopup.showAndWait();
    }

}
