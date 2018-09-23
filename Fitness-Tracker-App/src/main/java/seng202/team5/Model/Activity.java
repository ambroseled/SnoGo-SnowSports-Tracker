package seng202.team5.Model;


/**
 * This class holds a single activity that is in the application.
 * It stores the name of the activity, the data set corresponding to the activity,
 * and the ID.
 */
public class Activity {

    private String name;
    private DataSet dataSet = new DataSet();
    private int id = -1;


    /**
     * A constructor for an activity. This is used for a new blank activity.
     * @param newName The name of the new Activity.
     * @param dataSet The DataSet for the new Activity.
     */
    public Activity(String newName, DataSet dataSet) {
        name = newName;
        this.dataSet = dataSet;
    }


    /**
     * This constructor is used for when an activity is read from the database.
     * @param id The id from the database of the activity.
     * @param name The name of the activity.
     */
    public Activity(int id, String name) {
        this.name = name;
        this.id = id;
    }


    /**
     * Another constructor that is used for making a new blank activity.
     * @param newName The name of the new Activity.
     */
    public Activity(String newName) {
        name = newName;
    }


    /**
     * An overloaded version of the constructor that is used when an activity
     * is read from the database.
     * @param id The id from the database of the activity.
     * @param name The name of the activity.
     * @param dataSet The DataSet for the Activity.
     */
    public Activity(int id, String name, DataSet dataSet) {
        this.id = id;
        this.name = name;
        this.dataSet = dataSet;
    }


    public DataSet getDataSet() {
        return dataSet;
    }


    public int getId() {
        return id;
    }


    public String toString() {
        return name + ", " + dataSet.getDateTime(0) + " - " + dataSet.getDateTime(dataSet.getDataPoints().size() - 1);
    }


    public String getName() {
        return name;
    }


    public void setId(int id) {
        this.id = id;
    }


}
