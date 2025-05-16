package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class UserService {

    private final MongoCollection<Document> usersCollection;

    public UserService(MongoDatabase database){
        usersCollection = database.getCollection("users");
    }

    public void createUser(User newUser){
        usersCollection.insertOne(newUser.toDocument());
        System.out.println("User created: " + newUser.getName());
    }

    public List<Document> getAllUsers() {
        return usersCollection.find().into(new ArrayList<>());
    }

    public Document getUserByName(String nm){
        return usersCollection.find(eq("name", nm)).first();
    }

    public void deleteUserByName(MongoCollection<Document> booksCollection, String nm){
        Document user = usersCollection.find(eq("name", nm)).first();
        List<Document> booksByUser= booksCollection.find(eq("userId", new ObjectId(user.getObjectId("_id")
                                                .toHexString()))).into(new ArrayList<>());
        for(Document book: booksByUser){
            String title = book.getString("title");
            booksCollection.findOneAndUpdate(eq("title", title), set("userId", null));
        }
        Document deletedUser = usersCollection.findOneAndDelete(eq("name", nm));

        if (deletedUser != null) {
            System.out.println("User deleted successfully - name: " + nm);
        }
        else {
            System.out.println("Could not find user with name: " + nm);
        }

    }
}
