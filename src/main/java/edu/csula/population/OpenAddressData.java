package edu.csula.population;

import java.util.ArrayList;

/**
 * Created by williamsalinas on 4/23/16.
 */
public class OpenAddressData {

    private String region;
    private ArrayList<OpenAddressDataCoordinates> coordinates;

    OpenAddressData(String region){
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public ArrayList<OpenAddressDataCoordinates> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<OpenAddressDataCoordinates> coordinates) {
        this.coordinates = coordinates;
    }
}
