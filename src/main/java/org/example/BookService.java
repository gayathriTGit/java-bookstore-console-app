package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class BookService {
    private final MongoCollection<Document> booksCollection;

    public BookService(MongoDatabase db){
        booksCollection = db.getCollection("books");
    }

    public MongoCollection<Document> getBooksCollection(){
        return booksCollection;
    }

    public void createBook(Book newBook){
        booksCollection.insertOne(newBook.ToDocument());
        System.out.println("Book created: " + newBook.getTitle());
    }

    public Document getBookByTitle(String title){
        return booksCollection.find(eq("title", title)).first();
    }

    public List<Document> getAllBooks(){
        return booksCollection.find().into(new ArrayList<>());
    }

    public boolean assignUserToBook(String title, String userId){
        Document result= booksCollection.findOneAndUpdate(eq("title", title), set("userId", new ObjectId(userId)));
        return (result != null);
    }

    public List<Document> getBooksByUser(String userId){
        return booksCollection.find(eq("userId", new ObjectId(userId))).into(new ArrayList<>());
    }

    public void updateAuthor(String title, String author){
      UpdateResult result = booksCollection.updateOne(eq("title", title), set("author" , author));
      if (result.getModifiedCount() > 0){
          System.out.println("Author changed to " + author + " for book " + title + " successfully!");
      }
      else {
          System.out.println("Could not find book with title " + title);
      }

    }
    public void deleteBookByTitle(String title){
       Document deletedBook = booksCollection.findOneAndDelete(eq("title",title));
       if (deletedBook != null) {
           System.out.println("Book deleted successfully - title: " + title);
       }
       else {
           System.out.println("Could not find book with title: " + title);
       }

    }
}
