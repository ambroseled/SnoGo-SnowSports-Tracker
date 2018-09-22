package seng202.team5.Model;

import org.junit.Test;
import seng202.team5.DataManipulation.InputDataParser;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class RouteTest {

  @Test
  public void toJSONArray() {}


  @Test
  public void testRoute() {
    ArrayList<DataPoint> dataPoints = new ArrayList<>();
    InputDataParser inputDataParser = new InputDataParser();
    Activity activity = inputDataParser.parseCSVToActivities("src/main/resources/TestFiles/routeTestData.csv").get(0);
    Route route = new Route(activity.getDataSet().getDataPoints());

    String correctFormat = "[{lat: -43.489889, lng: 171.537634}, {lat: -43.489887, lng: 171.537676}, {lat: -43.489887, lng: 171.537725}, ]";
    assertEquals(route.toJSONArray(), correctFormat);
  }

}