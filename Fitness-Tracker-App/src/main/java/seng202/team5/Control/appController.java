package seng202.team5.Control;



import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import seng202.team5.Model.User;

import java.io.IOException;
import java.net.URL;



public class appController extends Application {

    private static Stage appStage;
    private static FXMLLoader loader = new FXMLLoader();
    private Class c = getClass();
    private TableController table = new TableController();
    private User currentUser;


    public static Stage getAppStage() {
        return  appStage;
    }

    public static void changeScene(String filename, Class c) throws IOException {
        Stage appStage = appController.getAppStage();
        URL page = c.getResource(filename);
        Parent dataParent = loader.load(page);
        Scene dataScene = new Scene(dataParent);
        appStage.setScene(dataScene);
        appStage.show();
    }
/*
    public void showDataView() {
        try
        {
            table.show(appStage, loader);
        } catch(IOException io)

        {
            io.printStackTrace();
        }
    }
    */

<<<<<<< HEAD

   // public static void clearButtons()
=======
    // public static void clearButtons()
>>>>>>> 8f9fd1ef8e781e9a9a1f85939436125b4d513a4d


    public void start(Stage primaryStage) throws Exception {
        String filename = "/View/tabMain.fxml";
        URL value1 = c.getResource(filename);
        System.out.println(value1);
        Parent root = loader.load(value1);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1280);
      //  primaryStage.setResizable(false);

        appStage = primaryStage;

        //showDataView();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
