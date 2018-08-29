package seng202.team5.Model;

public class Activity {

	private String name;
    private DataSet dataSet;
    //private date
    
    public Activity(String newName) {
    	name = newName;
    	dataSet = new DataSet();
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



}
