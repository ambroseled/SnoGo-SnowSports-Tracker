package seng202.team5.Control;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class alertController {

    private FXMLLoader loader = new FXMLLoader();

    public void homeButtonPress() throws IOException {
        Class c = getClass();
        Stage appStage = appController.getAppStage();
        URL page = c.getResource("/View/firstPage.fxml");
        Parent dataParent = loader.load(page);
        Scene dataScene = new Scene(dataParent);
        appStage.setScene(dataScene);
        appStage.show();
    }
}
