package edu.csula.datascience.acquisition;

/**
 * Created by williamsalinas on 4/23/16.
 */
public class MockWorldBankPopulationRecord {

    private String value;

    private String date;

    MockWorldBankPopulationRecord(String value, String date) {

        this.value = value;
        this.date = date;
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
}
