package edu.csula.population;

import java.util.ArrayList;

public class CountryPopulation {

    private String name;

    private ArrayList<PopulationRecord> records;

    public CountryPopulation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<PopulationRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<PopulationRecord> records) {
        this.records = records;
    }
}
