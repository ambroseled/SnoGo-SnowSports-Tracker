package seng202.team5.Model;

import java.util.ArrayList;

/**
 * Stores an array list of data points, and contains a method to
 * produce a string of the data points in the correct format to
 * be used by the Google Maps API.
 */
public class Route {

    //
    private ArrayList<DataPoint> route = new ArrayList<DataPoint>();


    /**
     * Adds all the data points in the input to the
     * route (array list of data points).
     * @param dataPoints Array list of data points to add to the route
     */
    public Route(ArrayList<DataPoint> dataPoints) {
        route.addAll(dataPoints);
    }


    /**
     * Returns the route (array list of data points) as a string
     * in the correct format to be used by the Google Maps API.
     * @return The route as a string in the correct format.
     */
    public String toJSONArray() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        route.forEach(pos -> stringBuilder.append(
                String.format("{lat: %f, lng: %f}, ", pos.getLatitude(), pos.getLongitude())));
        stringBuilder.append("]");
        return stringBuilder.toString();


    }
}
