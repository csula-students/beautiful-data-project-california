package edu.csula.datascience.acquisition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import edu.csula.population.CountryList;
import edu.csula.population.CountryPopulation;
import edu.csula.population.PopulationRecord;
import edu.csula.population.Source;

import java.io.IOException;
import java.util.*;

/**
 * An example of Source implementation using JSON
 */
public class MockPopulationSource implements Source<CountryPopulation> {

    // [ {}, {}, {} ]

    private LinkedList<String> list;

    private Iterator<String> iterator;

    private CountryPopulation pop;

    public MockPopulationSource() {
        list = CountryList.getCountryList();
        iterator = list.iterator();
    }

    public static void main(String[] args) {
        MockPopulationSource p = new MockPopulationSource();
        p.requestCountryPopulation("Brazil", 1980);
    }

    private CountryPopulation requestCountryPopulation(String country, int year) {
        JsonNode json = new JsonNode("[{\"total\":3643507,\"age\":0,\"males\":1844792,\"females\":1798715,\"year\":1980},{\"total\":3510106,\"age\":1,\"males\":1772216,\"females\":1737890,\"year\":1980},{\"total\":3392655,\"age\":2,\"males\":1708851,\"females\":1683804,\"year\":1980},{\"total\":3289992,\"age\":3,\"males\":1653989,\"females\":1636003,\"year\":1980},{\"total\":3200956,\"age\":4,\"males\":1606929,\"females\":1594027,\"year\":1980},{\"total\":3124386,\"age\":5,\"males\":1566965,\"females\":1557421,\"year\":1980},{\"total\":3059120,\"age\":6,\"males\":1533394,\"females\":1525726,\"year\":1980},{\"total\":3004001,\"age\":7,\"males\":1505511,\"females\":1498490,\"year\":1980},{\"total\":2957865,\"age\":8,\"males\":1482614,\"females\":1475251,\"year\":1980},{\"total\":2919554,\"age\":9,\"males\":1463997,\"females\":1455557,\"year\":1980},{\"total\":2885965,\"age\":10,\"males\":1448108,\"females\":1437857,\"year\":1980},{\"total\":2853996,\"age\":11,\"males\":1433394,\"females\":1420602,\"year\":1980},{\"total\":2832191,\"age\":12,\"males\":1423395,\"females\":1408796,\"year\":1980},{\"total\":2823268,\"age\":13,\"males\":1419104,\"females\":1404164,\"year\":1980},{\"total\":2820244,\"age\":14,\"males\":1417269,\"females\":1402975,\"year\":1980},{\"total\":2816198,\"age\":15,\"males\":1415076,\"females\":1401122,\"year\":1980},{\"total\":2815843,\"age\":16,\"males\":1414798,\"females\":1401045,\"year\":1980},{\"total\":2788633,\"age\":17,\"males\":1400839,\"females\":1387794,\"year\":1980},{\"total\":2719710,\"age\":18,\"males\":1365687,\"females\":1354023,\"year\":1980},{\"total\":2623605,\"age\":19,\"males\":1316726,\"females\":1306879,\"year\":1980},{\"total\":2529196,\"age\":20,\"males\":1268590,\"females\":1260606,\"year\":1980},{\"total\":2430098,\"age\":21,\"males\":1218041,\"females\":1212057,\"year\":1980},{\"total\":2339644,\"age\":22,\"males\":1171956,\"females\":1167688,\"year\":1980},{\"total\":2267193,\"age\":23,\"males\":1135139,\"females\":1132054,\"year\":1980},{\"total\":2205653,\"age\":24,\"males\":1103952,\"females\":1101701,\"year\":1980},{\"total\":2139219,\"age\":25,\"males\":1070281,\"females\":1068938,\"year\":1980},{\"total\":2071810,\"age\":26,\"males\":1036126,\"females\":1035684,\"year\":1980},{\"total\":2000489,\"age\":27,\"males\":1000037,\"females\":1000452,\"year\":1980},{\"total\":1922449,\"age\":28,\"males\":960598,\"females\":961851,\"year\":1980},{\"total\":1840618,\"age\":29,\"males\":919280,\"females\":921338,\"year\":1980},{\"total\":1761851,\"age\":30,\"males\":879526,\"females\":882325,\"year\":1980},{\"total\":1686149,\"age\":31,\"males\":841327,\"females\":844822,\"year\":1980},{\"total\":1610453,\"age\":32,\"males\":803169,\"females\":807284,\"year\":1980},{\"total\":1534383,\"age\":33,\"males\":764866,\"females\":769517,\"year\":1980},{\"total\":1460097,\"age\":34,\"males\":727487,\"females\":732610,\"year\":1980},{\"total\":1387906,\"age\":35,\"males\":691174,\"females\":696732,\"year\":1980},{\"total\":1315079,\"age\":36,\"males\":654572,\"females\":660507,\"year\":1980},{\"total\":1259090,\"age\":37,\"males\":626366,\"females\":632724,\"year\":1980},{\"total\":1227818,\"age\":38,\"males\":610475,\"females\":617343,\"year\":1980},{\"total\":1212303,\"age\":39,\"males\":602442,\"females\":609861,\"year\":1980},{\"total\":1197257,\"age\":40,\"males\":594663,\"females\":602594,\"year\":1980},{\"total\":1187614,\"age\":41,\"males\":589590,\"females\":598024,\"year\":1980},{\"total\":1165559,\"age\":42,\"males\":578372,\"females\":587187,\"year\":1980},{\"total\":1121286,\"age\":43,\"males\":556137,\"females\":565149,\"year\":1980},{\"total\":1063936,\"age\":44,\"males\":527422,\"females\":536514,\"year\":1980},{\"total\":1010921,\"age\":45,\"males\":500880,\"females\":510041,\"year\":1980},{\"total\":956903,\"age\":46,\"males\":473863,\"females\":483040,\"year\":1980},{\"total\":915202,\"age\":47,\"males\":452959,\"females\":462243,\"year\":1980},{\"total\":893601,\"age\":48,\"males\":442021,\"females\":451580,\"year\":1980},{\"total\":884331,\"age\":49,\"males\":437204,\"females\":447127,\"year\":1980},{\"total\":872781,\"age\":50,\"males\":431263,\"females\":441518,\"year\":1980},{\"total\":862985,\"age\":51,\"males\":426182,\"females\":436803,\"year\":1980},{\"total\":844133,\"age\":52,\"males\":416674,\"females\":427459,\"year\":1980},{\"total\":809729,\"age\":53,\"males\":399547,\"females\":410182,\"year\":1980},{\"total\":765650,\"age\":54,\"males\":377674,\"females\":387976,\"year\":1980},{\"total\":724321,\"age\":55,\"males\":357154,\"females\":367167,\"year\":1980},{\"total\":683315,\"age\":56,\"males\":336806,\"females\":346509,\"year\":1980},{\"total\":645493,\"age\":57,\"males\":317944,\"females\":327549,\"year\":1980},{\"total\":613549,\"age\":58,\"males\":301847,\"females\":311702,\"year\":1980},{\"total\":585768,\"age\":59,\"males\":287717,\"females\":298051,\"year\":1980},{\"total\":557276,\"age\":60,\"males\":273249,\"females\":284027,\"year\":1980},{\"total\":528504,\"age\":61,\"males\":258638,\"females\":269866,\"year\":1980},{\"total\":502904,\"age\":62,\"males\":245610,\"females\":257294,\"year\":1980},{\"total\":481540,\"age\":63,\"males\":234714,\"females\":246826,\"year\":1980},{\"total\":462940,\"age\":64,\"males\":225211,\"females\":237729,\"year\":1980},{\"total\":444735,\"age\":65,\"males\":215904,\"females\":228831,\"year\":1980},{\"total\":427597,\"age\":66,\"males\":207132,\"females\":220465,\"year\":1980},{\"total\":408458,\"age\":67,\"males\":197386,\"females\":211072,\"year\":1980},{\"total\":385616,\"age\":68,\"males\":185826,\"females\":199790,\"year\":1980},{\"total\":360476,\"age\":69,\"males\":173150,\"females\":187326,\"year\":1980},{\"total\":336369,\"age\":70,\"males\":161007,\"females\":175362,\"year\":1980},{\"total\":312878,\"age\":71,\"males\":149186,\"females\":163692,\"year\":1980},{\"total\":289308,\"age\":72,\"males\":137378,\"females\":151930,\"year\":1980},{\"total\":265718,\"age\":73,\"males\":125628,\"females\":140090,\"year\":1980},{\"total\":242421,\"age\":74,\"males\":114076,\"females\":128345,\"year\":1980},{\"total\":219523,\"age\":75,\"males\":102756,\"females\":116767,\"year\":1980},{\"total\":196829,\"age\":76,\"males\":91592,\"females\":105237,\"year\":1980},{\"total\":176272,\"age\":77,\"males\":81461,\"females\":94811,\"year\":1980},{\"total\":158771,\"age\":78,\"males\":72778,\"females\":85993,\"year\":1980},{\"total\":143475,\"age\":79,\"males\":65170,\"females\":78305,\"year\":1980},{\"total\":128794,\"age\":80,\"males\":57911,\"females\":70883,\"year\":1980},{\"total\":115255,\"age\":81,\"males\":51214,\"females\":64041,\"year\":1980},{\"total\":101503,\"age\":82,\"males\":44616,\"females\":56887,\"year\":1980},{\"total\":86776,\"age\":83,\"males\":37837,\"females\":48939,\"year\":1980},{\"total\":71872,\"age\":84,\"males\":31162,\"females\":40710,\"year\":1980},{\"total\":58214,\"age\":85,\"males\":25086,\"females\":33128,\"year\":1980},{\"total\":45352,\"age\":86,\"males\":19432,\"females\":25920,\"year\":1980},{\"total\":34691,\"age\":87,\"males\":14761,\"females\":19930,\"year\":1980},{\"total\":27018,\"age\":88,\"males\":11374,\"females\":15644,\"year\":1980},{\"total\":21579,\"age\":89,\"males\":8962,\"females\":12617,\"year\":1980},{\"total\":16736,\"age\":90,\"males\":6862,\"females\":9874,\"year\":1980},{\"total\":12692,\"age\":91,\"males\":5148,\"females\":7544,\"year\":1980},{\"total\":9435,\"age\":92,\"males\":3785,\"females\":5650,\"year\":1980},{\"total\":6751,\"age\":93,\"males\":2667,\"females\":4084,\"year\":1980},{\"total\":4613,\"age\":94,\"males\":1784,\"females\":2829,\"year\":1980},{\"total\":3175,\"age\":95,\"males\":1219,\"females\":1956,\"year\":1980},{\"total\":2352,\"age\":96,\"males\":932,\"females\":1420,\"year\":1980},{\"total\":1728,\"age\":97,\"males\":713,\"females\":1015,\"year\":1980},{\"total\":1087,\"age\":98,\"males\":456,\"females\":631,\"year\":1980},{\"total\":505,\"age\":99,\"males\":202,\"females\":303,\"year\":1980},{\"total\":1073,\"age\":100,\"males\":410,\"females\":663,\"year\":1980}]");

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
