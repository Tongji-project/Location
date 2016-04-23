package sse.tongji.localization;

/**
 * Created by tanjingru on 4/22/16.
 */
public class Location {
    private String latitude;
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Location(String longitude, String latitude) {

        this.longitude = longitude;
        this.latitude = latitude;
    }
}
