package edu.csula.datascience.acquisition;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.csula.population.Collector;
import edu.csula.population.Source;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by williamsalinas on 4/23/16.
 */
public class WorldBankPopulationCollectorTest {

    private Collector<MockBankCountryData, MockBankCountryData> collector;
    private Source<MockBankCountryData> source;

    @Before
    public void setup() {
        collector = new MockWorldBankPopulationCollector();
        source = new MockWorldBankPopulationSource();
    }

    @Test
    public void save_source_to_mongodb() throws Exception {

        Collection<MockBankCountryData> src= source.next();
        collector.save(src);

        //System.out.println(src);

        MongoClient mongoClient;
        MongoDatabase database;
        MongoCollection<Document> collection;

        mongoClient = new MongoClient();
        database = mongoClient.getDatabase("test-countries-db");
        collection = database.getCollection("test_world_bank_population");


        Assert.assertEquals(collection.count(),1);

    }

}