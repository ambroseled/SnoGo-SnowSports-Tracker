package seng202.team5.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by brad on 9/09/16.
 */
public class Route {
    private ArrayList<DataPoint> points = new ArrayList<>();

    public Route(ArrayList<DataPoint> points) {
        this.points = points;
    }

    public String toJSONArray() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        points.forEach(point -> stringBuilder.append(
                String.format("{lat: %f, lng: %f}, ", point.getLatitude(), point.getLongitude())));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}