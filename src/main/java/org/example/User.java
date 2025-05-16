package org.example;

import org.bson.Document;

public class User {

    private String id;
    private String name;
    private String email;

    public User(){

    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Document toDocument(){
        Document doc = new Document("name", name)
                .append("email", email);
        return doc;
    }

    public static User fromDocument(Document doc){
        User user = new User();
        user.id = doc.getObjectId("_id").toHexString();
        user.name = doc.getString("name");
        user.email = doc.getString("email");
        return user;
    }
}
