package edu.csula.population;

import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Created by williamsalinas on 4/23/16.
 */
public class OpenAddressDataSource implements Source<OpenAddressData> {

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

//        int i = 0;

        OpenAddressData location = new OpenAddressData(country);
        ArrayList<OpenAddressDataCoordinates> coordinates = new ArrayList<>();

        File dir = new File("/Users/theory/Downloads/openaddr-collected-global/" + country); //System.getenv("CSV_PATH")
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.getName().split("\\.")[1].equals("csv")) {

                    long startTime = System.nanoTime();
                    try {
                        Reader in = new FileReader(child);
                        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
                        for (CSVRecord record : records) {
                            String longitude = record.get("LON");
                            String latitude = record.get("LAT");

                            OpenAddressDataCoordinates c = new OpenAddressDataCoordinates(longitude, latitude);
                            coordinates.add(c);

//                            i++;
//                            System.out.println("id = " + i + "longitude: " + longitude + " and " + "latitude " + latitude);
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

        location.setCoordinates(coordinates);

        return location;
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
