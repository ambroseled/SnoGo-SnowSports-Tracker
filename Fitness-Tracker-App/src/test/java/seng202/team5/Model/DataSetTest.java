package seng202.team5.Model;

import org.junit.Before;
import org.junit.Test;
import seng202.team5.DataManipulation.InputDataParser;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataSetTest {

    ArrayList<Activity> activities;

    @Before
    public void getActivities() {
        InputDataParser parser = new InputDataParser();

        activities = parser.parseCSVToActivities("huttTestData.csv");
        activities.addAll(parser.parseCSVToActivities("huttTestData2.csv"));
    }


    @Test
    public void testNotContains() {
        assertFalse(activities.get(0).getDataSet().contains(activities.get(1).getDataSet()));
    }


    @Test
    public void testEqual() {
        assertTrue(activities.get(0).getDataSet().contains(activities.get(0).getDataSet()));
    }


    @Test
    public void testContains() {
        assertTrue(activities.get(8).getDataSet().contains(activities.get(2).getDataSet()));
    }



}