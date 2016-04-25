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
 * Created by williamsalinas on 4/21/16.
 */
public class WorldBankPopulationCollector implements Collector<WorldBankCountryPopulation, WorldBankCountryPopulation>{

    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public WorldBankPopulationCollector() {
        // establish database connection to MongoDB
        mongoClient = new MongoClient();
        database = mongoClient.getDatabase("countries-db");
        collection = database.getCollection("worldbankpopulation");
    }

    @Override
    public Collection<WorldBankCountryPopulation> mungee(Collection<WorldBankCountryPopulation> src) {
        return src;
    }

    @Override
    public void save(Collection<WorldBankCountryPopulation> data) {
        List<Document> documents = data.stream()
                .map(item -> {

                    Document country = new Document()
                            .append("name", item.getCountry());

                    Stream<Document> docs = item.getRecords().stream().map(i -> {
                        Document sub = new Document();

                        sub.append("total", i.getValue())
                                .append("year", i.getDate());

                        return sub;
                    });

                    country.append("records", docs.collect(Collectors.toList()));

                    return country;
                })
                .collect(Collectors.toList());

        collection.insertMany(documents);
    }
}
