package com.example.assignment1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Library library = new Library();

        library.loadItemsFromFile("data.txt");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System Menu:");
            System.out.println("1. Add an Item");
            System.out.println("2. Edit an Item");
            System.out.println("3. Delete an Item");
            System.out.println("4. View All Items");
            System.out.println("5. View a Specific Item by ID");
            System.out.println("6. Hot Picks");
            System.out.println("7. View borrowers list");
            System.out.println("8. Borrow an item");
            System.out.println("9. Return an item");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    library.addItem();
                    break;
                case 2:
                    library.editItem();
                    break;
                case 3:
                    library.deleteItem();
                    break;
                case 4:
                    System.out.println("\nAll Items:");
                    library.displayAllItems();
                    break;
                case 5:
                    library.findItemById();
                    break;
                case 6:
                    library.hotPicks();
                    break;
                case 7:
                    library.displayBorrowers();
                    break;
                case 8:
                    library.borrowItem();
                    break;
                case 9:
                    library.returnItem();
                    break;
                case 0:
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
