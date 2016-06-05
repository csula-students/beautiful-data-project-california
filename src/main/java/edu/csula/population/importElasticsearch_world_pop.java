package edu.csula.population;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.mashape.unirest.http.JsonNode;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.node.Node;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by williamsalinas on 5/19/16.
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

public class importElasticsearch_world_pop {
    private final static String indexName = "bd-us-populations";
    private final static String typeName = "population";

    public static void main(String[] args) {

        System.out.println("IMPORTING");

        int year = Calendar.getInstance().get(Calendar.YEAR);

        try {
            jsonImport("US",(year -2) + "");
        }catch (URISyntaxException e){
            e.printStackTrace();
        }

    }

    public static void jsonImport(String country, String year) throws URISyntaxException {

        Node node = nodeBuilder().settings(Settings.builder()
                .put("cluster.name", "willy1087")
                .put("path.home", "elasticsearch-data")).node();
        Client client = node.client();


        System.out.println("API READING");

        // create bulk processor
        BulkProcessor bulkProcessor = BulkProcessor.builder(
                client,
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId,
                                           BulkRequest request) {
                    }

                    @Override
                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          BulkResponse response) {
                    }

                    @Override
                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          Throwable failure) {
                        System.out.println("Facing error while importing data to elastic search");
                        failure.printStackTrace();
                    }
                })
                .setBulkActions(10000)
                .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(1)
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();

        // Gson library for sending json to elastic search
        Gson gson = new Gson();


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


            bulkProcessor.add(new IndexRequest(indexName, typeName)
                    .source(gson.toJson(test))
            );

        }


        bulkProcessor.close();
        node.close();

        System.out.println("COMPLETE");
    }


}
