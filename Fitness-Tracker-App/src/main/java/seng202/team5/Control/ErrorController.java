package seng202.team5.Control;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;

/**
 * This class is in charge of displaying an error message.
 * The error message appears if a user tries to load a corrupt
 * or invalid file.
 */
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

        DialogPane dialogPane = errorPopup.getDialogPane();
        dialogPane.getStylesheets().add("errorStyle.css");
        dialogPane.getStyleClass().add("error");
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);

        errorPopup.showAndWait();


    }
}
