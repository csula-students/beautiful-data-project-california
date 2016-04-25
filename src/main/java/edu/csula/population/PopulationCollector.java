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
 * An example of Collector
 */
public class PopulationCollector implements Collector<CountryPopulation, CountryPopulation> {
    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public PopulationCollector() {
        // establish database connection to MongoDB
        mongoClient = new MongoClient();
        database = mongoClient.getDatabase("countries-db");
        collection = database.getCollection("population");
    }

    @Override
    public Collection<CountryPopulation> mungee(Collection<CountryPopulation> src) {
        return src;
    }

    @Override
    public void save(Collection<CountryPopulation> data) {
        List<Document> documents = data.stream()
                .map(item -> {

                    Document country = new Document()
                            .append("name", item.getName());

                    Stream<Document> docs = item.getRecords().stream().map(i -> {
                        Document sub = new Document();

                        sub.append("age", i.getAge())
                                .append("year", i.getYear())
                                .append("male", i.getMales())
                                .append("female", i.getFemales());

                        return sub;
                    });

                    country.append("records", docs.collect(Collectors.toList()));

                    return country;
                })
                .collect(Collectors.toList());

        collection.insertMany(documents);
    }
}
