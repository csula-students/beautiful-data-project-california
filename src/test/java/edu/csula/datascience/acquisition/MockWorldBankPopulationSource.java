package edu.csula.datascience.acquisition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import edu.csula.population.Source;
import edu.csula.population.Tools;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Created by williamsalinas on 4/23/16.
 */
public class MockWorldBankPopulationSource implements Source<MockBankCountryData>{

    int index = 0;

    private LinkedList<String> list;
    private Iterator<String> iterator;
    private MockBankCountryData wcpop;


    public MockBankCountryData requestWorldBankCountryPopulation(String country, String year) {

        //http://api.worldbank.org/countries/PER/indicators/SP.POP.TOTL?per_page=50&date=1980:2016&format=json

//        JsonNode json = Tools.requestJson(
//                String.format("http://api.worldbank.org/countries/%s/indicators/SP.POP.TOTL?per_page=50&date=1980:%s&format=json",
//                        country, year));


        String fakeJsonString = "[{'indicator':{'id':'SP.POP.TOTL','value':'Population, total'},'country':{'id':'AR','value':'Argentina'},'value':'41222875','decimal':'0','date':'2010'},{'indicator':{'id':'SP.POP.TOTL','value':'Population, total'},'country':{'id':'AR','value':'Argentina'},'value':'40798641','decimal':'0','date':'2009'}]";

//        String jsonString = fakeJsonString;
//
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode actualObj = null;
//        try {
//            actualObj = mapper.readTree(fakeJsonString);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
        JSONArray a = new JSONArray(fakeJsonString);
        ObjectMapper objectMapper = new ObjectMapper();

        JSONArray c = new JSONArray();

        for (int i = 0; i < a.length(); i++) {

            JSONObject b = new JSONObject(a.get(i).toString());


            ObjectNode treeRootNode = objectMapper.createObjectNode();

            treeRootNode.put("value", b.get("value").toString());
            treeRootNode.put("date", b.get("date").toString());

            c.put(treeRootNode);


        }

//
        String d = c.toString().replace("\\", "");
        d = d.replace("\"{", "{");
        d = d.replace("}\"", "}");



        MockBankCountryData wbpop = new MockBankCountryData(country);

        ArrayList<MockWorldBankPopulationRecord> wbrecords = new ArrayList<>();

        try {

            wbrecords = objectMapper.readValue(
                    d,
                    objectMapper.getTypeFactory().constructCollectionType(
                            List.class, MockWorldBankPopulationRecord.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        wbpop.setRecords(wbrecords);

        return wbpop;

    }


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
        list.add(19,null);

        MockBankCountryData a = new MockBankCountryData("Peru",list);

        a.getCountry();

        return Lists.newArrayList(
               a

        );
    }
}
