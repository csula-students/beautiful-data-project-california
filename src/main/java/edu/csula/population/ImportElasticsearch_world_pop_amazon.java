package edu.csula.population;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by williamsalinas on 6/1/16.
 */

/*
 ```
 PUT /bd-us-populations
 {
     "mappings" : {
         "population" : {
             "properties" : {

                        "value" : {"type" : "double"},
                        "date" : {"type": "date"}

             }
         }
     }
 }
 ```
 */

public class ImportElasticsearch_world_pop_amazon {

    public static void main(String[] args) {

        System.out.println("IMPORTING");

        int year = Calendar.getInstance().get(Calendar.YEAR);

        try {
            jsonImport("US",(year) + "");
        }catch (URISyntaxException e){
            e.printStackTrace();
        }

    }

    public static void jsonImport(String country, String year) throws URISyntaxException {


        String indexName = "bd-us-populations";
        String typeName = "population";
        String awsAddress = "http://search-cs594-acbszs2ao6gvdlvo5gcqwzor7m.us-west-2.es.amazonaws.com/";

        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(awsAddress)
                .multiThreaded(true)
                .build());
        JestClient client = factory.getObject();


        System.out.println("API READING");


        int count = 0;
        Collection<WorldBankPopulationRecord> records = Lists.newArrayList();


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

            WorldBankPopulationRecord test = new WorldBankPopulationRecord(b.get("value").toString(),b.get("date").toString());


            if (count < 500) {
                records.add(test);
                count ++;
            } else {
                try {
                    Collection<BulkableAction> actions = Lists.newArrayList();
                    records.stream()
                            .forEach(tmp -> {
                                actions.add(new Index.Builder(tmp).build());
                            });
                    Bulk.Builder bulk = new Bulk.Builder()
                            .defaultIndex(indexName)
                            .defaultType(typeName)
                            .addAction(actions);
                    client.execute(bulk.build());
                    count = 0;
                    records = Lists.newArrayList();
                    System.out.println("Inserted 500 documents to cloud");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Collection<BulkableAction> actions = Lists.newArrayList();
                records.stream()
                        .forEach(tmp -> {
                            actions.add(new Index.Builder(tmp).build());
                        });
                Bulk.Builder bulk = new Bulk.Builder()
                        .defaultIndex(indexName)
                        .defaultType(typeName)
                        .addAction(actions);
                client.execute(bulk.build());
            }catch (IOException e) {
                e.printStackTrace();
            }

        }

        System.out.println("COMPLETE");


    }
}
