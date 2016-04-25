package edu.csula.datascience.acquisition;

import edu.csula.population.Collector;
import edu.csula.population.CountryPopulation;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * An example of Collector
 */
public class MockPopulationCollector implements Collector<CountryPopulation, CountryPopulation> {


    public MockPopulationCollector() {
    }

    @Override
    public Collection<CountryPopulation> mungee(Collection<CountryPopulation> src) {
        return src
                .stream()
                .filter(data -> data.getRecords() != null)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Collection<CountryPopulation> data) {
    }
}
