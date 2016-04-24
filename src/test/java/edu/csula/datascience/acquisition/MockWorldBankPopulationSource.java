package edu.csula.datascience.acquisition;

import com.google.common.collect.Lists;
import edu.csula.population.Source;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by williamsalinas on 4/23/16.
 */
public class MockWorldBankPopulationSource implements Source<MockBankCountryData>{

    int index = 0;

    @Override
    public boolean hasNext() {
        return index < 1;
    }

    @Override
    public Collection<MockBankCountryData> next() {
        ArrayList<MockWorldBankPopulationRecord> list = new ArrayList<MockWorldBankPopulationRecord>();
        list.add(0,new MockWorldBankPopulationRecord("30973148","2014"));
        list.add(1,new MockWorldBankPopulationRecord("30565461","2013"));
        list.add(2,new MockWorldBankPopulationRecord("30158768","2012"));
        list.add(3,new MockWorldBankPopulationRecord("29759891","2011"));
        list.add(4,new MockWorldBankPopulationRecord("29373644","2009"));
        list.add(5,new MockWorldBankPopulationRecord("28642048","2008"));
        list.add(6,new MockWorldBankPopulationRecord("28292768","2007"));
        list.add(7,new MockWorldBankPopulationRecord("27949958","2006"));
        list.add(8,new MockWorldBankPopulationRecord("27610406","2005"));
        list.add(9,new MockWorldBankPopulationRecord("27273188","2004"));
        list.add(10,new MockWorldBankPopulationRecord("26601463","2002"));
        list.add(11,new MockWorldBankPopulationRecord("26261363","2001"));
        list.add(12,new MockWorldBankPopulationRecord("25914875","2000"));
        list.add(13,new MockWorldBankPopulationRecord("25561297","1999"));
        list.add(14,new MockWorldBankPopulationRecord("25199744","1998"));
        list.add(15,new MockWorldBankPopulationRecord("24827409","1997"));
        list.add(16,new MockWorldBankPopulationRecord("24441076","1996"));
        list.add(17,new MockWorldBankPopulationRecord("24038761","1995"));
        list.add(18,new MockWorldBankPopulationRecord("23619358","1994"));

        MockBankCountryData a = new MockBankCountryData("Peru",list);

        a.getCountry();

        return Lists.newArrayList(
               a

        );
    }
}
