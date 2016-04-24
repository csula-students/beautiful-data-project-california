package edu.csula.population;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Reader;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


/**
 * Created by williamsalinas on 4/23/16.
 */
public class OpenAddressDataSource implements Source<OpenAddressData>{

    private LinkedList<String> list;
    private Iterator<String> iterator;
    private OpenAddressData wcpop;


    public OpenAddressDataSource() {
        list = OpenAddressDataRegionList.getCountryList();
        iterator = list.iterator();
    }

    public static void main(String[] args) {
        OpenAddressDataSource w = new OpenAddressDataSource();

        System.out.println(w.requestOpenAddressData("asia"));


    }

    private OpenAddressData requestOpenAddressData(String country) {

        int i = 0;

        JSONArray coordinates = new JSONArray();
        ArrayList<String> ar = new ArrayList<String>();

        File dir = new File("/Users/williamsalinas/Desktop/openaddr-collected-"+ country);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if(child.getName().split("\\.")[1].equals("csv")){

                    long startTime = System.nanoTime();
                    try {
//                        Path file = Paths.get("/Users/williamsalinas/Desktop/openaddr-collected-"+ country + "/test/" + child.getName());
//
//                        Stream<String> lines = Files.lines( file, StandardCharsets.UTF_8 );
//                        Scanner scanner = new Scanner(file);
//                        //read line by line
//                        while(scanner.hasNextLine()){
//                            //process each line
//                            ar.add(scanner.nextLine());
//                        }
//                        for( String line : (Iterable<String>) lines::iterator )
//                        {
//                            JSONObject coordinate = new JSONObject();
//                            coordinate.put("longitude", output[0]);
//                            coordinate.put("latitude", output[1]);
//                            coordinates.put(coordinate);

                            //System.out.println(output[0]);
//                        }
                        //Reader in = new FileReader("/Users/williamsalinas/Desktop/openaddr-collected-"+ country +"/" + child);
                        //Reader in = new FileReader("/Users/williamsalinas/Desktop/openaddr-collected-"+ country +"/" + "akita.csv");
                        Reader in = new FileReader(child);
                        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
                        for (CSVRecord record : records) {
                            String longitude = record.get("LON");
                            String latitude = record.get("LAT");

                            JSONObject coordinate = new JSONObject();
                            coordinate.put("longitude", longitude);
                            coordinate.put("latitude", latitude);
                            coordinates.put(coordinate);

                            i++;
                            //System.out.println("id = "+ i +"longitude: " + longitude + " and " + "latitude " + latitude );
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    long endTime = System.nanoTime();
                    long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
                    System.out.println("Total elapsed time: " + elapsedTimeInMillis + " ms");
                }


            }
        }
        //else {
//            // Handle the case where dir is not really a directory.
//            // Checking dir.isDirectory() above would not be sufficient
//            // to avoid race conditions with another process that deletes
//            // directories.
//        }



        //System.out.println(coordinates.toString());

        ObjectMapper objectMapper = new ObjectMapper();

        OpenAddressData newlocation = new OpenAddressData(country);

        ArrayList<OpenAddressDataCoordinates> wbrecords = new ArrayList<>();

        try {

            wbrecords = objectMapper.readValue(
                    coordinates.toString(),
                    objectMapper.getTypeFactory().constructCollectionType(
                            List.class, OpenAddressDataCoordinates.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        newlocation.setCoordinates(wbrecords);


        return newlocation;
    }


    private Collection<OpenAddressData> query(String country) {

        List<OpenAddressData> list = Lists.newArrayList();

        //add country region
        list.add(requestOpenAddressData(country));

        return list;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public Collection<OpenAddressData> next() {
        String country = iterator.next();

        return query(country);
    }

}
