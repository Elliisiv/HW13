package org.hw13.UserAPI;

public class Geo {
    private final double  lat;
    private final double  lng;

    public double  getLat() {
        return lat;
    }

    public double  getLng() {
        return lng;
    }

    public Geo(double  lat, double  lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Geo{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
