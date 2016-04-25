package edu.csula.population;

import java.util.Collection;

/**
 * Created by williamsalinas on 4/21/16.
 */
public class WorldBankPopulationCollectorApp {

    public static void main(String[] args) {

        // get json from brazil
        // array of objects
        WorldBankPopulationSource source = new WorldBankPopulationSource();


        // put it in the collector
        WorldBankPopulationCollector collector = new WorldBankPopulationCollector();

        while (source.hasNext()) {
            Collection<WorldBankCountryPopulation> popi = source.next();
            Collection<WorldBankCountryPopulation> cleaned = collector.mungee(popi);
            collector.save(cleaned);
        }

    }

}
