package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.Activity;
import seng202.team5.Model.User;

import java.util.ArrayList;

public class UserController {

    @FXML
    private TableView userTable;
    @FXML
    private TableColumn<User, String> userCol;

    private ArrayList<User> users;

    private DataBaseController db = App.getDb();

    private ObservableList<User> userNames = FXCollections.observableArrayList();

    @FXML
    private void fillTable() {
        users = db.getUsers();
        System.out.println(users.size());
        for (User x : users) {
            System.out.println(x.getId());
        }
        if (userTable.getItems().size() != users.size()) {
            userCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            userNames.addAll(users);
            userTable.setItems(userNames);
        }

    }

}

