package edu.csula.population;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by williamsalinas on 4/24/16.
 */
public class OpenAddressDataCollector implements Collector<OpenAddressData, OpenAddressData> {

    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public OpenAddressDataCollector() {
        // establish database connection to MongoDB
        mongoClient = new MongoClient();
        database = mongoClient.getDatabase("countries-db");
        collection = database.getCollection("openaddresscoordinates");
    }

    @Override
    public Collection<OpenAddressData> mungee(Collection<OpenAddressData> src) {
        return src;
    }

    @Override
    public void save(Collection<OpenAddressData> data) {
        List<Document> documents = data.stream()
                .map(item -> {

                    Document country = new Document()
                            .append("name", item.getRegion());

                    Stream<Document> docs = item.getCoordinates().stream().map(i -> {
                        Document sub = new Document();

                        sub.append("latitude", i.getLatitude())
                                .append("longitude", i.getLongitude());

                        return sub;
                    });

                    country.append("coordinates", docs.collect(Collectors.toList()));

                    return country;
                })
                .collect(Collectors.toList());

        collection.insertMany(documents);
    }
}
