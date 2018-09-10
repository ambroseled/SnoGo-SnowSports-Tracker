package seng202.team5.Model;


/**
 *
 */
public class Activity {

    private String name;
    private DataSet dataSet = new DataSet();
    private int id;


    /**
     *
     * @param newName
     * @param dataSet
     */
    public Activity(String newName, DataSet dataSet) {
        name = newName;
        this.dataSet = dataSet;
    }


    /**
     *
     * @param id
     * @param newName
     */
    public Activity(int id, String newName) {
        name = newName;
        this.id = id;
    }


    /**
     *
     * @param newName
     */
    public Activity(String newName) {
        name = newName;
    }


    /**
     *
     * @param id
     * @param name
     * @param dataSet
     */
    public Activity(int id, String name, DataSet dataSet) {
        this.id = id;
        this.name = name;
        this.dataSet = dataSet;
    }


    /**
     *
     * @return
     */
    public DataSet getDataSet() {
        return dataSet;
    }


    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }


    /**
     *
     * @return
     */
    public String toString() {
        return name + "\n" + dataSet;
    }


    /**
     *
     * @param dataSet
     */
    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }


    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }
}
