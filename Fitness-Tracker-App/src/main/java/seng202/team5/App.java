package seng202.team5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seng202.team5.Control.HomeController;
import seng202.team5.DataManipulation.DataBaseController;

import java.net.URL;



public class App extends Application {


    private static DataBaseController db = new DataBaseController();


    private static FXMLLoader loader = new FXMLLoader();

    private Class c = getClass();

    /**
     * Creates the application GUI scene, based on tabMain.fxml file
     * @param primaryStage
     * @throws Exception IOException
     */
    public void start(Stage primaryStage) throws Exception {
        HomeController.setDb(db);
        URL value1 = c.getResource("/View/tabMain.fxml");
        Parent root = loader.load(value1);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SnoGo");
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(1280);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.show();

    }




    @Override
    /**
     * Closing the database connection when the application is closed
     */
    public void stop(){
        db.closeConnection();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
