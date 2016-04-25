package edu.csula.datascience.acquisition;

import edu.csula.population.Collector;
import edu.csula.population.CountryPopulation;
import edu.csula.population.PopulationRecord;
import edu.csula.population.Source;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MockPopulationCollectorTest {

    private Collector<CountryPopulation, CountryPopulation> collector;
    private Source<CountryPopulation> source;

    @Before
    public void setup() {
        collector = new MockPopulationCollector();
        source = new MockPopulationSource();
    }

    @Test
    public void mungee() throws Exception {
        ArrayList<CountryPopulation> list = new ArrayList<CountryPopulation>(collector.mungee(source.next()));

        System.out.println(list.get(0).getRecords().size());

        Assert.assertEquals(list.get(0).getRecords().size(), 101);
    }

    @Test
    public void sourceTest() throws Exception {
        MockPopulationSource p = new MockPopulationSource();


        ArrayList<CountryPopulation> list = new ArrayList<CountryPopulation>();


        list.add(0, new CountryPopulation("Brazil"));
        CountryPopulation c0 = list.get(0);


        ArrayList<PopulationRecord> apr = new ArrayList<>();
        PopulationRecord pr = new PopulationRecord();
        pr.setAge(8);
        pr.setFemales(1);
        pr.setMales(1);
        pr.setTotal(2);
        pr.setYear(1980);

        apr.add(pr);
        c0.setRecords(apr);

        Assert.assertEquals(c0.getName(), "Brazil");

        Assert.assertEquals(c0.getRecords().size(), 1);

        while (p.hasNext()) {
            for (CountryPopulation pop : p.next()) {
                if (pop.getName().equals("Brazil")) {
                    Assert.assertEquals(pop.getName(), "Brazil");

                    for (PopulationRecord prs : pop.getRecords()) {
                        Assert.assertEquals(prs.getYear(), pr.getYear());
                    }
                }
            }
        }
    }

}