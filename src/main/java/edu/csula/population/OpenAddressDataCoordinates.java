package edu.csula.population;

/**
 * Created by williamsalinas on 4/23/16.
 */
public class OpenAddressDataCoordinates {

    private String longitude;
    private String latitude;
    private String location;
    private String year;
    private Integer count;

    OpenAddressDataCoordinates(){

    }

    OpenAddressDataCoordinates(String longitude, String latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

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

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}