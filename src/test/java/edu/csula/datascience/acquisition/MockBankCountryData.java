package edu.csula.datascience.acquisition;

import java.util.ArrayList;

/**
 * Created by williamsalinas on 4/23/16.
 */
public class MockBankCountryData {

    private String country;

    private ArrayList<MockWorldBankPopulationRecord> records;

   MockBankCountryData(String country) {
        this.country = country;
    }

    MockBankCountryData(String country, ArrayList<MockWorldBankPopulationRecord> records){
        this.country = country;
        this.records = records;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<MockWorldBankPopulationRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<MockWorldBankPopulationRecord> records) {
        this.records = records;
    }

    public static MockBankCountryData build(MockBankCountryData data){
        return new MockBankCountryData(data.getCountry(),data.getRecords());
    }

}
