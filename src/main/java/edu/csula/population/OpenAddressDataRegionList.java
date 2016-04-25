package edu.csula.population;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by williamsalinas on 4/23/16.
 */
public class OpenAddressDataRegionList {

    final static String[] regions = new String[]{
            "asia",
            //"europe"
    };

    public static LinkedList<String> getCountryList() {
        return new LinkedList<>(Arrays.asList(regions));
    }


}
