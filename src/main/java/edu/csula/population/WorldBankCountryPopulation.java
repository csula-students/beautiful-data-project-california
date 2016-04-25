package edu.csula.population;

import java.util.ArrayList;

/**
 * Created by Access on 4/20/2016.
 */
public class WorldBankCountryPopulation {

    private String country;

    private ArrayList<WorldBankPopulationRecord> records;

    WorldBankCountryPopulation(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<WorldBankPopulationRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<WorldBankPopulationRecord> records) {
        this.records = records;
    }
}
