package edu.csula.population;

import com.google.common.collect.Lists;
import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.LinkedList;


/**
 * Created by williamsalinas on 6/1/16.
 */

/*
 ```
 PUT /bd-populations
 {
     "mappings" : {
         "coordinates" : {
             "properties" : {
                 "location" : {
                     "type" : "geo_point"
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

public class ImportElasticsearch_amazon {

    public static void main(String[] args) {

        System.out.println("IMPORTING");

        LinkedList<String> list;
        list = OpenAddressDataRegionList.getCountryList();
        for (int i = 0; i < list.size() ; i++) {

            String path = "/Users/williamsalinas/Desktop/openaddr-collected-" + list.get(i) + "/total";

            System.out.println(path);
            File currentDir = new File(path);
            File [] files = currentDir.listFiles();

            for (File file: files) {
                try {
                    csvImport(path + "/" + file.getName());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                System.out.println(path + "/" + file.getName());
            }
        }

    }

    public static void csvImport(String filename) throws URISyntaxException {

        String indexName = "bd-populations";
        String typeName = "coordinates";
        String awsAddress = "http://search-cs594-acbszs2ao6gvdlvo5gcqwzor7m.us-west-2.es.amazonaws.com/";

        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(awsAddress)
                .multiThreaded(true)
                .build());
        JestClient client = factory.getObject();


        File csv = new File(filename);

        System.out.println("CSV READING");


        try {
            // after reading the csv file, we will use CSVParser to parse through
            // the csv files
            CSVParser parser = CSVParser.parse(
                    csv,
                    Charset.defaultCharset(),
                    CSVFormat.EXCEL.withHeader()
            );


            Collection<OpenAddressDataCoordinates> coordinates = Lists.newArrayList();
            int count = 0;

            // for each record, we will insert data into Elastic Search
            for (CSVRecord record: parser) {
                // cleaning up dirty data which doesn't have time or temperature
                if (
                        !record.get("LON").isEmpty() &&
                                !record.get("LAT").isEmpty()
                        ) {
                    OpenAddressDataCoordinates coord = new OpenAddressDataCoordinates();

                    coord.setLocation(record.get("LAT") + "," + record.get("LON"));
                    coord.setYear("2016");

                    if (count < 500) {
                        coordinates.add(coord);
                        count ++;
                    } else {
                        try {
                            Collection<BulkableAction> actions = Lists.newArrayList();
                            coordinates.stream()
                                    .forEach(tmp -> {
                                        actions.add(new Index.Builder(tmp).build());
                                    });
                            Bulk.Builder bulk = new Bulk.Builder()
                                    .defaultIndex(indexName)
                                    .defaultType(typeName)
                                    .addAction(actions);
                            client.execute(bulk.build());
                            count = 0;
                            coordinates = Lists.newArrayList();
                            System.out.println("Inserted 500 documents to cloud");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }

            Collection<BulkableAction> actions = Lists.newArrayList();
            coordinates.stream()
                    .forEach(tmp -> {
                        actions.add(new Index.Builder(tmp).build());
                    });
            Bulk.Builder bulk = new Bulk.Builder()
                    .defaultIndex(indexName)
                    .defaultType(typeName)
                    .addAction(actions);
            client.execute(bulk.build());


        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("COMPLETE");
    }

}
