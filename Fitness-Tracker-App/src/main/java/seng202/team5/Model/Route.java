package seng202.team5.Model;

import java.util.ArrayList;

/**
 *
 */
public class Route {

    //
    private ArrayList<DataPoint> route = new ArrayList<DataPoint>();


    /**
     *
     * @param dataPoints
     */
    public Route(ArrayList<DataPoint> dataPoints) {
        route.addAll(dataPoints);
    }


    /**
     *
     * @return
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
