package edu.csula.population;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Created by Access on 4/20/2016.
 */
public class WorldBankPopulationSource implements Source<WorldBankCountryPopulation> {


    private LinkedList<String> list;
    private Iterator<String> iterator;
    private WorldBankCountryPopulation wcpop;


    public WorldBankPopulationSource() {
        list = WorldBankCountryList.getCountryListcode();
        iterator = list.iterator();
    }


    public static void main(String[] args) {
        WorldBankPopulationSource w = new WorldBankPopulationSource();

        //System.out.println(w.requestWorldBankCountryPopulation("AR","2016"));


    }

    private WorldBankCountryPopulation requestWorldBankCountryPopulation(String country, String year) {

        //http://api.worldbank.org/countries/PER/indicators/SP.POP.TOTL?per_page=50&date=1980:2016&format=json

        JsonNode json = Tools.requestJson(
                String.format("http://api.worldbank.org/countries/%s/indicators/SP.POP.TOTL?per_page=50&date=1980:%s&format=json",
                        country, year));

        ObjectMapper objectMapper = new ObjectMapper();

        JSONArray a = new JSONArray(json.getArray().get(1).toString());

        JSONArray c = new JSONArray();

        for (int i = 0; i < a.length(); i++) {

            JSONObject b = new JSONObject(a.get(i).toString());


            ObjectNode treeRootNode = objectMapper.createObjectNode();

            treeRootNode.put("value", b.get("value").toString());
            treeRootNode.put("date", b.get("date").toString());

            c.put(treeRootNode);

            //System.out.println(b.get("value") + " and " + b.get("date"));

        }

        //System.out.println(country);

        String d = c.toString().replace("\\", "");
        d = d.replace("\"{", "{");
        d = d.replace("}\"", "}");

        //System.out.println(d);


        //JsonNode node = objectMapper.readvalue(treeRootNode,JsonNode.class);
        //ObjectNode treeRootNode = objectMapper.createObjectNode();


        WorldBankCountryPopulation wbpop = new WorldBankCountryPopulation(country);

        ArrayList<WorldBankPopulationRecord> wbrecords = new ArrayList<>();

        //String str = "{ \"name\": \"Alice\", \"age\": 20 }";

        //System.out.println(json.getArray().length());
        //JSONObject j = new JSONObject(str);
        //System.out.println(j.length());


        //JSONObject obj  = new JSONObject(str);
//        try{
//            JsonNode rootNode = objectMapper.readTree(str);
//        }catch (IOException e){
//            e.printStackTrace();
//        }

//

        try {

            wbrecords = objectMapper.readValue(
                    d,
                    objectMapper.getTypeFactory().constructCollectionType(
                            List.class, WorldBankPopulationRecord.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
////
        wbpop.setRecords(wbrecords);

        return wbpop;

    }

    private Collection<WorldBankCountryPopulation> query(String country) {

        List<WorldBankCountryPopulation> list = Lists.newArrayList();

        int year = Calendar.getInstance().get(Calendar.YEAR);

        //for (int i = 1980; i <= year; i++) {
        list.add(requestWorldBankCountryPopulation(country, (year - 2) + ""));
        //}

        return list;
    }


    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public Collection<WorldBankCountryPopulation> next() {
        String country = iterator.next();

        return query(country);
    }

}
