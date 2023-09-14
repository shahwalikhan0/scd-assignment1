package com.example.assignment1;

import java.io.*;
import java.util.*;

public class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the title of the book: ");
        String title = scanner.nextLine();
        System.out.print("Enter the author of the book: ");
        String author = scanner.nextLine();
        System.out.print("Enter the year of publication of the book: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        Book book = new Book(title, author, year);
        books.add(book);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void editBook() {
        System.out.println("Enter the ID of the book you want to edit: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        System.out.println("Enter the new title of the book: ");
        String title = scanner.nextLine();
        System.out.println("Enter the new author of the book: ");
        String author = scanner.nextLine();
        System.out.println("Enter the new year of publication of the book: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        book.setTitle(title);
        book.setAuthor(author);
        book.setYear(year);
        System.out.println("Book updated:");
        book.display();
    }

    public void deleteBook() {
        System.out.println("Enter the ID of the book you want to delete: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        books.remove(book);
        System.out.println("Book deleted:");
    }

    public void findBookById() {
        System.out.println("Enter the ID of the book you want to find: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        for(Book book : books) {
            if (book.getId() == id) {
                book.display();
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public Book findBookById(int id){
        for(Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public void displayAllBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void loadBooksFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                String author = parts[2];
                int year = Integer.parseInt(parts[3]);
                Book book = new Book(title, author, year);
                addBook(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBooksToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Book book : books) {
                writer.println(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getYear());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
