package org.example;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Book {

    private String Id;
    private String title;
    private String author;
    private int year;
    private String isbn;
    private String userId;

    public Book() {

    }
    public Book(String title, String author, int year, String isbn, String userId){
        this.title = title;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
        this.userId = userId;
    }

    public String getTitle(){        
        return this.title;
    }

    public String getAuthor(){        
        return this.author;
    }

    public int getYear() {
        return this.year;
    }
    
    public String getIsbn() {
        return this.isbn;
    }
    
    public String getUserId() {
        return this.userId;
    }
    
    public Document ToDocument() {
        return new Document("title", this.title)
                .append("author", this.author)
                .append("year", this.year)
                .append("isbn", this.isbn)
                .append("userId", userId != null ? new ObjectId(userId) : null);
    }

    public static Book fromDocument(Document doc){
        Book b = new Book();
        b.Id = doc.getObjectId("_id").toHexString();
        b.author = doc.getString("author");
        b.title = doc.getString("title");
        b.year = doc.getInteger("year");
        b.isbn = doc.getString("isbn");
        ObjectId uid = doc.getObjectId("userId");
        if (uid != null){
            b.userId = uid.toHexString();
        }
        else {
            b.userId = null;
        }
        return b;
    }
}
