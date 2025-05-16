package org.example;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Scanner;

public class BookTrackerApp
{
    public static void main( String[] args )
    {
        String mongoDbConnectionuri = "mongodb+srv://gayathritMongodb:kWds6P8FCuFk5fRl@cluster0.bfcevkq.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        MongoDatabase libraryDb;
        MongoInitializer initializer = new MongoInitializer(mongoDbConnectionuri);
        libraryDb = initializer.getDatabase();
        BookService bookService = new BookService(libraryDb);
        UserService usrService = new UserService(libraryDb);

        boolean running = true;
        System.out.println("================== Book Tracker =============");
        while (running) {
            System.out.println("\n📚 Library Menu:");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. Add Book");
            System.out.println("4. View All Books");
            System.out.println("5. Find book by title");
            System.out.println("6. Assign Book to User");
            System.out.println("7. View Books by User");
            System.out.println("8. Delete Book by Title");
            System.out.println("9. Delete User");
            System.out.println("10. Quit");
            System.out.print("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    scanner.nextLine();
                    System.out.print("Enter name of user:");
                    String name = scanner.nextLine();
                    System.out.print("Enter email of user:");
                    String email = scanner.nextLine();
                    User usr = new User(name, email);
                    usrService.createUser(usr);
                    break;
                case 2:
                    System.out.println("\nAll Users");
                    for(Document doc: usrService.getAllUsers()){
                        System.out.println(doc.toJson());
                    }
                    break;
                case 3:
                    System.out.print("Enter title of book: ");
                    String title = scanner.nextLine();
                    title = scanner.nextLine();
                    System.out.print("Enter author of book: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter the year book was published: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter the book ISBN: ");
                    String isbn = scanner.nextLine();
                    Book newBook = new Book(title, author, year, isbn, null);
                    bookService.createBook(newBook);
                    break;
                case 4:
                    System.out.println("\nAll Books");
                    for(Document doc: bookService.getAllBooks()){
                        System.out.println(doc.toJson());
                    }
                    break;
                case 5:
                    System.out.println("Enter the title of the book you are looking for:");
                    scanner.nextLine();
                    String search  = scanner.nextLine();
                    Document book = bookService.getBookByTitle(search);
                    if (book != null) {
                        System.out.println(book.toJson());
                    }
                    else{
                        System.out.println("Book not found!");
                    }
                    break;
                case 6:
                    System.out.println("\nPick a book by title");
                    for(Document doc: bookService.getAllBooks()){
                        System.out.println(doc.toJson());
                    }
                    scanner.nextLine();
                    String selectedTitle = scanner.nextLine();
                    System.out.println("\nPick a user by name");
                    for(Document doc: usrService.getAllUsers()){
                        System.out.println(doc.toJson());
                    }
                    String selectedUser = scanner.nextLine();
                    Document user = usrService.getUserByName(selectedUser);
                    boolean result = bookService.assignUserToBook(selectedTitle, user.getObjectId("_id").toHexString());
                    if (result){
                        System.out.println("User " + selectedUser + " assigned to book " + selectedTitle + " successfully!");
                    }
                    break;
                case 7:
                    System.out.println("\nPick a user by name");
                    for(Document doc: usrService.getAllUsers()){
                        System.out.println(doc.toJson());
                    }
                    scanner.nextLine();
                    String selectedUsr = scanner.nextLine();
                    Document usrDoc = usrService.getUserByName(selectedUsr);
                    for(Document doc: bookService.getBooksByUser(usrDoc.getObjectId("_id").toHexString())){
                        System.out.println(doc.getString("title"));
                    }
                    break;
                case 8:
                    System.out.println("\nPick a book to delete by title");
                    for(Document doc: bookService.getAllBooks()){
                        System.out.println(doc.toJson());
                    }
                    scanner.nextLine();
                    String selectTitle = scanner.nextLine();
                    bookService.deleteBookByTitle(selectTitle);
                    break;
                case 9:
                    System.out.println("\nPick a user to delete by name");
                    for(Document doc: usrService.getAllUsers()){
                        System.out.println(doc.toJson());
                    }
                    scanner.nextLine();
                    String selectUsr = scanner.nextLine();
                    usrService.deleteUserByName(bookService.getBooksCollection(), selectUsr);
                    break;
                case 10:
                    running = false;
                    break;
            }
        }

    }
}
