package edu.csula.population;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An example of Collector implementation using Twitter4j with MongoDB Java driver
 */
public class PopulationCollector implements Collector<PopulationTable, PopulationTable> {
    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public PopulationCollector() {
        // establish database connection to MongoDB
        mongoClient = new MongoClient();
        // select `bd-example` as testing database
        database = mongoClient.getDatabase("bd-example");

        // select collection by name `tweets`
        collection = database.getCollection("population");
    }

    @Override
    public Collection<PopulationTable> mungee(Collection<PopulationTable> src) {
        return src;
    }

    @Override
    public void save(Collection<PopulationTable> data) {
        List<Document> documents = data.stream()
            .map(item -> new Document()
                .append("total", item.getTotal())
                .append("age", item.getAge())
                .append("year", item.getYear())
                .append("male", item.getMales())
                .append("female", item.getFemales()))
            .collect(Collectors.toList());

        collection.insertMany(documents);
    }
}
