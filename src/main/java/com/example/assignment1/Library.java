package com.example.assignment1;

import java.io.*;
import java.util.*;

public class Library {
    private List<Item> items;
    private List<Borrower> borrowers;
    public Library() {
        items = new ArrayList<>();
        borrowers = new ArrayList<>();
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
            System.out.print("Enter the Price of the book: ");
            int price = scanner.nextInt();
            scanner.nextLine();
            if(price < 0) {
                System.out.println("Price cannot be negative");
                return;
            }
            Book book = new Book(title, author, year, 0, price);
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
            System.out.print("Enter the Price of the magazine: ");
            int price = scanner.nextInt();
            if(price < 0) {
                System.out.println("Price cannot be negative");
                return;
            }
            items.add(new Magazine(title, pub, authorsList.toArray(new String[0]), 0, price));
        } else if (type == 3) {
            System.out.print("Enter the publisher of the newspaper: ");
            String pub = scanner.nextLine();
            System.out.print("Enter the publication date of the newspaper DD-MM-YYYY: ");
            String date = scanner.nextLine();
            System.out.print("Enter the title of the newspaper: ");
            String title = scanner.nextLine();
            System.out.print("Enter the Price of the newspaper: ");
            int price = scanner.nextInt();
            if(price < 0) {
                System.out.println("Price cannot be negative");
                return;
            }
            items.add(new Newspaper(title, pub, date, 0, price));
        } else {
            System.out.println("Invalid input");
            return;
        }
        System.out.println("Item added successfully.");
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

    public void displayHotPicks() {
        boolean flag = false;
        System.out.println("HOT PICKS:");
        for (Item item : items) {
            if(item.getPopularity() > 0) {
                System.out.println('\n' + item.getClass().getSimpleName() + ": ");
                item.display();
                flag = true;
            }
        }
        if(!flag){
            System.out.println("No hot picks found all have 0 popularity.");
        }
    }

    public void loadItemsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int itemType = Integer.parseInt(parts[0]);
                String title = parts[1];
                int popularity, cost;
                switch (itemType) {
                    case 1:
                        String author = parts[2];
                        int year = Integer.parseInt(parts[3]);
                        popularity = Integer.parseInt(parts[4]);
                        cost = Integer.parseInt(parts[5]);
                        items.add(new Book(title, author, year, popularity, cost));
                        break;
                    case 2:
                        int count = 2;
                        String[] authors = new String[parts.length-5];
                        boolean flag = false;
                        for(; count< parts.length; count++){
                            if(parts[count].endsWith(".")){
                                authors[count-2] = parts[count].substring(0, parts[count].length()-1);
                                break;
                            }
                            authors[count-2] = parts[count];
                        }
                        count++;
                        String publisher = parts[count++];
                        popularity = Integer.parseInt(parts[count++]);
                        cost = Integer.parseInt(parts[count]);
                        items.add(new Magazine(title, publisher, authors, popularity, cost));
                        break;
                    case 3:
                        String newspaperPublisher = parts[2];
                        String publicationDate = parts[3];
                        popularity = Integer.parseInt(parts[4]);
                        cost = Integer.parseInt(parts[5]);
                        items.add(new Newspaper(title, newspaperPublisher, publicationDate, popularity, cost));
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
                    writer.println(1 + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getYear() + "," + book.getPopularity() + "," + book.getCost());
                }
                else if (item instanceof Magazine) {
                    Magazine magazine = (Magazine) item;
                    writer.println(2 + "," + magazine.getTitle() + "," + String.join(",", magazine.getAuthors()) + ".," + magazine.getPublisher() + "," + magazine.getPopularity() + "," + magazine.getCost());
                }
                else if (item instanceof Newspaper) {
                    Newspaper newspaper = (Newspaper) item;
                    writer.println(3 + "," + newspaper.getTitle() + "," + newspaper.getPublisher() + "," + newspaper.getPublicationDate() + "," + newspaper.getPopularity() + "," + newspaper.getCost());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void displayItem(Item item){
        item.display();
    }

    public void displayBorrowers() {
        System.out.println("Borrowers List: \n");
        for (Borrower borrower : borrowers) {
            borrower.display();
        }
    }

    public void borrowItem() {
        System.out.println("Enter the ID of the item you want to borrow: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        Item item = findItemById(id);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        Borrower borrower = findByName(name);
        if (borrower == null) {
            System.out.println("New user created.");
            borrower = new Borrower(name, null);
            borrowers.add(borrower);
        }
        if (item.isBorrowed()) {
            System.out.println("Item is already borrowed.");
            return;
        }
        if (item.hasReadTheItem(borrower)) {
            System.out.println("Borrower has already read the item.");
            return;
        }

        item.setBorrowed(true);
        item.calculateCost();
        item.increasePopularity();
        item.setBorrower(borrower);
        borrower.borrowItem(item);
        System.out.println("Item borrowed successfully.");
    }

    private Borrower findByName(String name) {
        for (Borrower borrower : borrowers) {
            if (borrower.getName().equals(name)) {
                return borrower;
            }
        }
        return null;
    }

    public void hotPicks() {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o2.getPopularity() - o1.getPopularity();
            }
        });
        displayHotPicks();
    }

    public void returnItem() {
        System.out.println("Enter the ID of the item you want to return: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        Item item = findItemById(id);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        Borrower borrower = findByName(name);
        if (borrower == null) {
            System.out.println("Borrower not found.");
            return;
        }
        if (!item.isBorrowed()) {
            System.out.println("Item is not borrowed.");
            return;
        }
        if (!item.hasReadTheItem(borrower)) {
            System.out.println("Borrower has not taken the item.");
            return;
        }
        item.setBorrowed(false);
        borrower.returnItem(item);
        System.out.println("Item returned successfully.");
    }
}
