package seng202.team5.Model;

public class Activity {

    private String name;
    private DataSet dataSet;
    private int id;
    //private date

    public Activity(String newName, DataSet dataSet) {
        name = newName;
        this.dataSet = dataSet;
    }

    public Activity(int id, String newName) {
        name = newName;
        this.id = id;
    }

    public Activity(int id, String name, DataSet dataSet) {
        this.id = id;
        this.name = name;
        this.dataSet = dataSet;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public String toString() {
        return name + "\n" + dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public String getName() {
        return name;
    }



}
