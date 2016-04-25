package edu.csula.population;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;

import java.io.IOException;
import java.util.*;

/**
 * An example of Source implementation using JSON
 */
public class PopulationSource implements Source<CountryPopulation> {

    // [ {}, {}, {} ]

    private LinkedList<String> list;

    private Iterator<String> iterator;

    private CountryPopulation pop;

    public PopulationSource() {
        list = CountryList.getCountryList();
        iterator = list.iterator();
    }

    public static void main(String[] args) {
        PopulationSource p = new PopulationSource();
//        p.requestCountryList();
        p.requestCountryPopulation("Brazil", 1980);
    }

    /**
     * Gets the list of countries from the API resource
     *
     * @return
     */
    private List<String> requestCountryList() {
        JsonNode json = Tools.requestJson("http://api.population.io/1.0/countries/");

        JSONArray arr = json.getObject().getJSONArray("countries");

        List<String> list = new ArrayList<>();
        arr.forEach(i -> list.add(i.toString()));

        list.remove(list.size() - 1);

        return list;
    }

    private CountryPopulation requestCountryPopulation(String country, int year) {
        JsonNode json = Tools.requestJson(
                String.format("http://api.population.io:80/1.0/population/%d/%s/",
                        year, country));

        ObjectMapper objectMapper = new ObjectMapper();

        CountryPopulation pop = new CountryPopulation(country);

        ArrayList<PopulationRecord> records = new ArrayList<>();

        try {
            records = objectMapper.readValue(
                    json.toString(),
                    objectMapper.getTypeFactory().constructCollectionType(
                            List.class, PopulationRecord.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pop.setRecords(records);

        return pop;
    }

    private Collection<CountryPopulation> query(String country) {
        List<CountryPopulation> list = Lists.newArrayList();

        int year = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = 1980; i <= year; i++) {
            list.add(requestCountryPopulation(country, i));
        }

        return list;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public Collection<CountryPopulation> next() {
        String country = iterator.next();

        return query(country);
    }

}
