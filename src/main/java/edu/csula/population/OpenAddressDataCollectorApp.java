package edu.csula.population;

import java.util.Collection;

/**
 * Created by williamsalinas on 4/24/16.
 */
public class OpenAddressDataCollectorApp {

    public static void main(String[] args) {

        // get json from brazil
        // array of objects
        OpenAddressDataSource source = new OpenAddressDataSource();


        // put it in the collector
        OpenAddressDataCollector collector = new OpenAddressDataCollector();

        while (source.hasNext()) {
            Collection<OpenAddressData> popi = source.next();
            Collection<OpenAddressData> cleaned = collector.mungee(popi);
            collector.save(cleaned);
        }

    }
}
