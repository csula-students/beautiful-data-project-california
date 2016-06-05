package edu.csula.datascience.acquisition;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.csula.population.Collector;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by williamsalinas on 4/23/16.
 */
public class MockWorldBankPopulationCollector implements Collector<MockBankCountryData, MockBankCountryData> {

    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public MockWorldBankPopulationCollector() {
        // establish database connection to MongoDB
        mongoClient = new MongoClient();
        database = mongoClient.getDatabase("test-countries-db");
        collection = database.getCollection("test_world_bank_population");
    }

    @Override
    public Collection<MockBankCountryData> mungee(Collection<MockBankCountryData> src) {

        Iterator<MockBankCountryData> iterator = src.iterator();
        Collection<MockBankCountryData> other = new ArrayList<>();

        while (iterator.hasNext()) {
            MockBankCountryData a = iterator.next();

            MockBankCountryData b = new MockBankCountryData(a.getCountry());
            ArrayList<MockWorldBankPopulationRecord> list = new ArrayList<MockWorldBankPopulationRecord>();
            for (int i = 0; i < a.getRecords().size(); i++) {
                if (!(a.getRecords().get(i).getDate() == null && a.getRecords().get(i).getValue() == null)) {
                    list.add(new MockWorldBankPopulationRecord(a.getRecords().get(i).getValue(), a.getRecords().get(i).getDate()));
                    b.setRecords(list);
                }
            }

            other.add(b);
        }

        return other;
    }

    @Override
    public void save(Collection<MockBankCountryData> data) {
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
