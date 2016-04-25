package edu.csula.population;

/**
 * Created by Access on 4/20/2016.
 */
public class WorldBankPopulationRecord {

    private String value;

    private String date;

    WorldBankPopulationRecord() {

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("value: %s, date: %s", value, date);
    }
}
