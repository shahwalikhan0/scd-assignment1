package com.example.assignment1;

import java.io.*;
import java.util.*;

public class Library {
    private List<Item> items;

    public Library() {
        items = new ArrayList<>();
    }

    public int getItemType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter item type 1 for book, 2 for Magazine, 3 for Newspaper: ");
        int type = scanner.nextInt();
        return type;
    }

    public void addItem() {
        Scanner scanner = new Scanner(System.in);
        int type = getItemType();
        if (type == 1) {
            System.out.print("Enter the title of the book: ");
            String title = scanner.nextLine();
            System.out.print("Enter the author of the book: ");
            String author = scanner.nextLine();
            System.out.print("Enter the year of publication of the book: ");
            int year = scanner.nextInt();
            scanner.nextLine();
            Book book = new Book(title, author, year);
            items.add(book);
        } else if (type == 2) {
            System.out.print("Enter the authors of the magazine, press 0 to finish: ");
            List<String> authorsList = new ArrayList<>();
            String author = scanner.nextLine();
            while (!author.equals("0")) {
                authorsList.add(author);
                author = scanner.nextLine();
            }
            System.out.print("Enter the publisher name: ");
            String pub = scanner.nextLine();
            System.out.print("Enter the title of the magazine: ");
            String title = scanner.nextLine();
            items.add(new Magazine(title, pub, authorsList.toArray(new String[0])));
        } else if (type == 3) {
            System.out.print("Enter the publisher of the newspaper: ");
            String pub = scanner.nextLine();
            System.out.print("Enter the publication date of the newspaper DD-MM-YYYY: ");
            String date = scanner.nextLine();
            System.out.print("Enter the title of the newspaper: ");
            String title = scanner.nextLine();
            items.add(new Newspaper(title, pub, date));
        } else {
            System.out.println("Invalid input");
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void editItem() {
        System.out.println("Enter the ID of the item you want to edit: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        Item item = findItemById(id);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        item.edit();
        System.out.println("Item edited successfully.");
    }

    public void deleteItem() {
        System.out.println("Enter the ID of the item you want to delete: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        Item item = findItemById(id);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        items.remove(item);
        System.out.println("Item deleted successfully.");
    }

    public void findItemById() {
        System.out.println("Enter the ID of the item you want to find: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        Item item = findItemById(id);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        displayItem(item);
    }

    public Item findItemById(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void displayAllItems() {
        for (Item item : items) {
            System.out.println('\n'+item.getClass().getSimpleName() + ": ");
            item.display();
        }
    }

    public void loadItemsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int itemType = Integer.parseInt(parts[0]);
                String title = parts[1];
                switch (itemType) {
                    case 1:
                        String author = parts[2];
                        int year = Integer.parseInt(parts[3]);
                        items.add(new Book(title, author, year));
                        break;
                    case 2:
                        String publisher = parts[2];
                        String[] authors = parts[3].split(";");
                        items.add(new Magazine(title, publisher, authors));
                        break;
                    case 3:
                        String newspaperPublisher = parts[2];
                        String publicationDate = parts[3];
                        items.add(new Newspaper(title, newspaperPublisher, publicationDate));
                        break;
                    default:
                        System.out.println("Invalid item type: " + itemType);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBooksToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Item item : items) {
                if (item instanceof Book) {
                    Book book = (Book) item;
                    writer.println(1 + "," + book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getYear());
                }
                else if (item instanceof Magazine) {
                    Magazine magazine = (Magazine) item;
                    writer.println(2 + "," + magazine.getId() + "," + magazine.getTitle() + "," + magazine.getPublisher() + "," + String.join(";", magazine.getAuthors()));
                }
                else if (item instanceof Newspaper) {
                    Newspaper newspaper = (Newspaper) item;
                    writer.println(3 + "," + newspaper.getId() + "," + newspaper.getTitle() + "," + newspaper.getPublisher() + "," + newspaper.getPublicationDate());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void displayItem(Item item){
        item.display();
    }
}
