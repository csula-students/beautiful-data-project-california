package edu.csula.population;


import java.util.Collection;

/**
 * A simple example of using Twitter
 */
public class PopulationCollectorApp {
    public static void main(String[] args) {

        // get json from brazil
        // array of objects
        PopulationSource source = new PopulationSource();


        // put it in the collector
        PopulationCollector collector = new PopulationCollector();

        while (source.hasNext()) {
            Collection<CountryPopulation> pop = source.next();
            Collection<CountryPopulation> cleaned = collector.mungee(pop);
            collector.save(cleaned);
        }

    }
}
