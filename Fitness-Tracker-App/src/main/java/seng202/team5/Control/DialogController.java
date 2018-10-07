package seng202.team5.Control;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;

/**
 * This class provides the functionality for dialogues to be shown to the user.
 * It has functionality for information and error dialogues
 */
public class DialogController {

    /**
     * Creates a dialogPane popup which displays an error message to the user
     * @param errorMessage message describing the error in a readable manner
     */
    public static void displayError(String errorMessage) {
        Alert errorPopup = new Alert(Alert.AlertType.ERROR);
        errorPopup.setTitle("Error Dialog");
        errorPopup.setHeaderText("Oops, you've fallen off your board!");
        errorPopup.setContentText(errorMessage);

        DialogPane dialogPane = errorPopup.getDialogPane();
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);

        errorPopup.showAndWait();
    }


    /**
     * This method creates an information dialogue for the status of a file export
     * @param message The message for the dialogue
     */
    public static void displayExportMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info Dialog");
        alert.setHeaderText("File Export Successful");
        alert.setContentText(message + " to your home directory");

        DialogPane dodialog = alert.getDialogPane();
        dodialog.setMinHeight(Region.USE_PREF_SIZE);

        alert.showAndWait();
    }


}
