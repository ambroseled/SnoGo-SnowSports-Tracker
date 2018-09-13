package seng202.team5.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Route {
    private List<Position> route = new ArrayList<>();

    public Route(Position ...points) {
        Collections.addAll(route, points);
    }

    public String toJSONArray() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        route.forEach(pos -> stringBuilder.append(
                String.format("{lat: %f, lng: %f}, ", pos.lat, pos.lng)));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
