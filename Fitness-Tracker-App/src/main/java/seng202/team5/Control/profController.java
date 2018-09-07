package seng202.team5.Control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import seng202.team5.Model.User;

import java.io.IOException;

public class profController {
    @FXML
    TextField nameText;
    @FXML
    TextField ageText;
    @FXML
    TextField heightText;
    @FXML
    TextField weightText;
    @FXML
    TextField bmiText;

    @FXML
    public void test() {
        ageText.setText("Bean");
        System.out.println(nameText.getText());
    }



    public void displayUser(User user) {
        nameText.setText(user.getName());
    }



}
