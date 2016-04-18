package edu.csula.population;

import java.util.List;

/**
 * Created by theory on 4/17/16.
 */
public class CountryPopulation {

    private String name;

    private List<PopulationTable> totals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PopulationTable> getTotals() {
        return totals;
    }

    public void setTotals(List<PopulationTable> totals) {
        this.totals = totals;
    }
}
