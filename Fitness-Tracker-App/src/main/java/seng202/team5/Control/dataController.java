package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team5.Model.Activity;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;

import java.util.ArrayList;
import java.util.Date;


public class dataController {
    private User user;
    TableColumn<DataPoint, Date> dateTimeCol;

    TableColumn<DataPoint, Integer> heartRateCol;
    TableColumn<DataPoint, Double> latitudeCol;
    TableColumn<DataPoint, Double> longitudeCol;
    TableColumn<DataPoint, Double> elevationCol;

    public dataController(User tempUser){
        user = tempUser;
        initialise();
    }


    private void initialise() {

    }

    private ObservableList<DataPoint> getDataPointsList() {
    }
}
