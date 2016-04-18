package edu.csula.population;

import com.google.common.collect.Lists;
import org.json.JSONArray;

import java.util.*;

/**
 * An example of Source implementation using JSON
 */
public class PopulationSource implements Source<CountryPopulation> {

    // [ {}, {}, {} ]

    private ArrayList<String> list;

    private Iterator<String> iter;

    public PopulationSource() {
        list = CountryList.getCountryList();
    }

    private void getJson(String url) {
        return;
    }

    @Override
    public boolean hasNext() {
        return this.iter.hasNext();
    }

    @Override
    public Collection<CountryPopulation> next() {
        // get next country
        List<CountryPopulation> list = Lists.newArrayList();

        String country = iter.next();

        //list = getJson(country);


        return list;
    }

}
