package seng202.team5.Model;

import java.util.ArrayList;

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
    public String getName() { return name;}
    
    public String toString() {
    	return name + "\n" + dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }
    
    // If date time ranges intersect then activities are equal
    public boolean equals(Activity otherActivity) {
    	ArrayList<DataPoint> dataPoints = dataSet.getDataPoints();
    	Long activityStart = dataPoints.get(0).getDateTime().getTime();
    	Long activityFinish = dataPoints.get(dataPoints.size()).getDateTime().getTime();
    	
    	ArrayList<DataPoint> otherDataPoints = otherActivity.getDataSet().getDataPoints();
    	Long otherActivityStart = otherDataPoints.get(0).getDateTime().getTime();
    	Long otherActivityFinish = otherDataPoints.get(otherDataPoints.size()).getDateTime().getTime();
    	
    	return (activityStart <= otherActivityFinish && otherActivityStart <= activityFinish);    	
    }

}
