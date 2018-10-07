package seng202.team5.Model;


public class WeatherField {


    private String field;
    private String url;


    public WeatherField(String field, String url) {
        this.field = field;
        this.url = url;
    }


    public String getField() {
        return field;
    }


    public String getUrl() {
        return url;
    }


}
