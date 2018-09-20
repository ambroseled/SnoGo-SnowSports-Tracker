package seng202.team5.Control;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;


public class ErrorController {

    public static void displayError(String errorMessage) {
        Alert errorPopup = new Alert(Alert.AlertType.ERROR);
        errorPopup.setTitle("Error Dialog");
        errorPopup.setHeaderText("Oops, you've fallen off your board!");
        errorPopup.setContentText(errorMessage);

        DialogPane dialogPane = errorPopup.getDialogPane();
        dialogPane.getStylesheets().add("errorStyle.css");
        dialogPane.getStyleClass().add("error");

        errorPopup.showAndWait();


    }
}
