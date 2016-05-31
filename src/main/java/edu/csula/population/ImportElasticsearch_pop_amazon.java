package edu.csula.population;

import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Collection;



/**
 * Created by williamsalinas on 5/30/16.
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

public class ImportElasticsearch_pop_amazon {
    public static void main(String[] args) throws URISyntaxException, IOException {

        System.out.println("IMPORTING");

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

        String indexName = "bd-us-populations-gender";
        String typeName = "population";
        String awsAddress = "http://search-cs594-acbszs2ao6gvdlvo5gcqwzor7m.us-west-2.es.amazonaws.com/";

        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(awsAddress)
                .multiThreaded(true)
                .build());
        JestClient client = factory.getObject();


        System.out.println("API READING");


        Collection<PopulationRecord> records = Lists.newArrayList();
        int count = 0;


        JsonNode json = Tools.requestJson(
                String.format("http://api.population.io:80/1.0/population/%d/%s/",
                        year, country));

        for (int i = 0; i < json.getArray().length(); i++) {
            JSONObject b = new JSONObject(json.getArray().get(i).toString());

            System.out.println(b.getInt("total") + " and " + b.getInt("age") + " and " + b.getInt("females") + " and " +
                     b.getInt("males") + " and " +b.getInt("year"));

            PopulationRecord test = new PopulationRecord(b.getInt("total"),b.getInt("age"),b.getInt("females"),
                    b.getInt("males"),b.getInt("year"));


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


        System.out.println("COMPLETE");

    }


}
