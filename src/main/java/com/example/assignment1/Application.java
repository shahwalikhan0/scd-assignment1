package com.example.assignment1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Library library = new Library();

        library.loadBooksFromFile("data.txt");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System Menu:");
            System.out.println("1. Add a Book");
            System.out.println("2. Edit a Book");
            System.out.println("3. Delete a Book");
            System.out.println("4. View All Books");
            System.out.println("5. View a Specific Book by ID");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    library.addBook();
                    System.out.println("Book added successfully.");
                    break;
                case 2:
                    library.editBook();
                    break;
                case 3:
                    library.deleteBook();
                    break;
                case 4:
                    System.out.println("\nAll Books:");
                    library.displayAllBooks();
                    break;
                case 5:
                    library.findBookById();
                    break;
                case 6:
                    library.saveBooksToFile("data.txt");
                    scanner.close();
                    System.out.println("Library Management System closed.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

}
