package seng202.team5.Control;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;


public class ErrorController {

    /**
     * Creates a dialogPane popup which displays a message to the user
     * @param errorMessage message describing the error in a readable manner
     */
    public static void displayError(String errorMessage) {
        Alert errorPopup = new Alert(Alert.AlertType.ERROR);
        errorPopup.setTitle("Error Dialog");
        errorPopup.setHeaderText("Oops, you've fallen off your board!");
        errorPopup.setContentText(errorMessage);
        errorPopup.showAndWait();

        DialogPane dialogPane = errorPopup.getDialogPane();
        dialogPane.getStylesheets().add("../../../res/CSS/errorStyle.css");
        dialogPane.getStyleClass().add("error");
    }
}
