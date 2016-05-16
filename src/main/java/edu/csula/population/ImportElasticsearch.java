package edu.csula.population;

import com.google.gson.Gson;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/*
 ```
 PUT /bd-populations
 {
     "mappings" : {
         "coordinates" : {
             "properties" : {
                 "location" : {
                     "type" : "geo_point",
                     "index" : "not_analyzed"
                 },
                 "year": {
                     "type": "date"
                 }
             }
         }
     }
 }
 ```
 */

/**
 * A quick elastic search example app
 * <p>
 * It will parse the csv file from the resource folder under main and send these
 * data to elastic search instance running locally
 * <p>
 * After that we will be using elastic search to do full text search
 * <p>
 * gradle command to run this app `gradle esExample`
 */
public class ImportElasticsearch {
    private final static String indexName = "bd-populations";
    private final static String typeName = "coordinates";

    public static void main(String[] args) {
        try {
            System.out.println("IMPORTING");
//            csvImport("/Users/theory/Downloads/openaddr-collected-global/at/31254.csv");
            csvImport("/Users/theory/Downloads/openaddr-collected-global/au/city_of_canberra.csv");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void csvImport(String filename) throws URISyntaxException {

        Node node = nodeBuilder().settings(Settings.builder()
                .put("cluster.name", "elasticsearch_theory")
                .put("path.home", "elasticsearch-data")).node();
        Client client = node.client();

        File csv = new File(filename);

        System.out.println("CSV READING");

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

        try {
            // after reading the csv file, we will use CSVParser to parse through
            // the csv files
            CSVParser parser = CSVParser.parse(
                    csv,
                    Charset.defaultCharset(),
                    CSVFormat.EXCEL.withHeader()
            );

            // for each record, we will insert data into Elastic Search
            parser.forEach(record -> {
                // cleaning up dirty data which doesn't have time or temperature
                if (
                        !record.get("LON").isEmpty() &&
                                !record.get("LAT").isEmpty()
                        ) {
                    OpenAddressDataCoordinates coord = new OpenAddressDataCoordinates();

                    coord.setLocation(record.get("LAT") + "," + record.get("LON"));
                    coord.setYear(2016);

                    bulkProcessor.add(new IndexRequest(indexName, typeName)
                            .source(gson.toJson(coord))
                    );
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("COMPLETE");
    }


}

