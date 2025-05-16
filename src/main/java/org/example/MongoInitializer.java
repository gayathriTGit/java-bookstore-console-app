package org.example;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoInitializer {
    private final MongoDatabase database;

    public MongoDatabase getDatabase(){
        return database;
    }

    public MongoInitializer(String uri){

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi)
                .build();

        // Create a new client and connect to the server
        MongoClient mongoClient = MongoClients.create(settings);
        // Send a ping to confirm a successful connection
        database = mongoClient.getDatabase("library");
        database.runCommand(new Document("ping",1));
        System.out.println("Successfully connected to MongoDB!");
    }

}
