package seng202.team5.Model;


/**
 *
 */
public class Activity {

    private String name;
    private DataSet dataSet = new DataSet();
    private int id;


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


    /**
     * Gets the activities DataSet.
     * @return The activities DataSet.
     */
    public DataSet getDataSet() {
        return dataSet;
    }


    /**
     * Gets the activities id.
     * @return The activities id.
     */
    public int getId() {
        return id;
    }


    /**
     * Gets the string representation of the activity
     * @return A string showing the activity.
     */
    public String toString() {
        return name + "\n" + dataSet;
    }


    /**
     * Sets the DataSet of the activity.
     * @param dataSet The new DataSet to be added to the activity.
     */
    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }


    /**
     * Gets the name of the activity.
     * @return The name of the activity.
     */
    public String getName() {
        return name;
    }
}
