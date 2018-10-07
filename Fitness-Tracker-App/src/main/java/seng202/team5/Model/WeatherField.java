package seng202.team5.Model;


public class WeatherField {


    private String field;
    private String url;
    private String country;


    public WeatherField(String field, String url) {
        this.field = field;
        this.url = url;
    }
    public WeatherField(String country, String field, String url) {
        this.country = country;
        this.field = field;
        this.url = url;
    }


    public String getField() {
        return field;
    }


    public String getUrl() {
        return url;
    }


    public String getCountry() {
        return country;
    }
}
