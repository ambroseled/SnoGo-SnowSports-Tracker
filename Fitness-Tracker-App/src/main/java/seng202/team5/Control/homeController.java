package seng202.team5.Control;




import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;



public class homeController{

    private FXMLLoader loader = new FXMLLoader();
    private Class c = getClass();






    /**
     * Handles when the dataButton is pressed
     */
    @FXML
    public void dataButtonPress(ActionEvent e) throws IOException {
        System.out.println("Data button pressed");
        appController.changeScene("/View/dataView.fxml", c);
    }


    /**
     * Handles when the mapButton is pressed
     */
    @FXML
    public void mapButtonPress() throws IOException {
        System.out.println("Map button pressed");
        appController.changeScene("/View/mapView.fxml", c);
    }


    /**
     * Handles when the goalButton is pressed
     */
    @FXML
    public void goalButtonPress() throws IOException {
        System.out.println("Goal button pressed");
        appController.changeScene("/View/goalView.fxml", c);
    }


    /**
     * Handles when the calendarButton is pressed
     */
    @FXML
    public void calButtonPress() throws IOException {
        System.out.println("Calender button pressed");
        appController.changeScene("/View/calView.fxml", c);
    }


    /**
     * Handles when the alertButton is pressed
     */
    @FXML
    public void alertButtonPress() throws IOException {
        System.out.println("Alert button pressed");
        appController.changeScene("/View/alertView.fxml", c);
    }


    /**
     * Handles when the profileButton is pressed
     */
    @FXML
    public void profButtonPress() throws IOException {
        System.out.println("Profile button pressed");
        appController.changeScene("/View/profView.fxml", c);
    }


}
