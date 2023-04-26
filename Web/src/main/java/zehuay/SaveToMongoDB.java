package zehuay;

/**
 * Author: Bobby Yang
 * Email: zehuay@andrew.cmu.edu
 * Name: SaveToMongoDB
 */

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class SaveToMongoDB {

    MongoDatabase database;

    // Connecting to MongoDB Altas
    public void connectMongoDB(){
        // Reference： https://cloud.mongodb.com/v2/642f9d9449420c6dfe51168a#/clusters/connect?clusterId=Cluster0
        ConnectionString connectionString = new ConnectionString("mongodb+srv://dbcbby:yzh15840215113@cluster0.sdv8lne.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("Cluster0");
    }

    // Save one player info to the db
    public void saveDoc(Document document) {
        database.getCollection("Player").insertOne(document);
    }

    // Get info from db
    public List<Document> saveLog() {
        List<Document> doc_list = new ArrayList<>();
        FindIterable<Document> documents = database.getCollection("Player").find();
        for (Document doc : documents) {
            doc_list.add(doc);
        }
        return doc_list;
    }

}
