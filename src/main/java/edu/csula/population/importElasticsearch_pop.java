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
import java.util.List;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by williamsalinas on 5/19/16.
 */

/*
 ```
 PUT /bd-us-populations-gender
 {
     "mappings" : {
         "population" : {
             "properties" : {

                        "total": {"type":"integer"},
                        "age" : {"type" : "integer"},
                        "year" : {"type": "date"},
                        "males" :{"type": "integer"},
                        "females" : {"type": "integer"}

             }
         }
     }
 }
 ```
 */


public class importElasticsearch_pop {

    private final static String indexName = "bd-us-populations-gender";
    private final static String typeName = "population";

    public static void main(String[] args) {

        System.out.println("IMPORTING");

        //LinkedList<String> list;
        //list = WorldBankCountryList.getCountryListcode();

        int year = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = 1980; i <= year; i++) {

            try {
                jsonImport("United States",i);
            }catch (URISyntaxException e){
                e.printStackTrace();
            }
        }

    }

    public static void jsonImport(String country, int year) throws URISyntaxException {

        Node node = nodeBuilder().settings(Settings.builder()
                .put("cluster.name", "willy10871")
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
                String.format("http://api.population.io:80/1.0/population/%d/%s/",
                        year, country));

       for (int i = 0; i < json.getArray().length(); i++) {
           JSONObject b = new JSONObject(json.getArray().get(i).toString());

           //System.out.println(b.getInt("total") + " and " + b.getInt("age") + " and " + b.getInt("females") + " and " +
           //         b.getInt("males") + " and " +b.getInt("year"));

            PopulationRecord test = new PopulationRecord(b.getInt("total"),b.getInt("age"),b.getInt("females"),
                    b.getInt("males"),b.getInt("year"));

            bulkProcessor.add(new IndexRequest(indexName, typeName)
                    .source(gson.toJson(test))
            );
       }

        bulkProcessor.close();
        node.close();

        System.out.println("COMPLETE");
    }
}
