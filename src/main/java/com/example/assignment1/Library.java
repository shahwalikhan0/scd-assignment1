package com.example.assignment1;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    public void addItem(int type, String title, String author, int year, int price, String content) {
        if (type == 1) {
            if(price < 0) {
                System.out.println("Price cannot be negative");
                return;
            }
            Book book = new Book(title, author, year, 0, price, content);
            items.add(book);
        } else {
            System.out.println("Invalid input");
            return;
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void editBook(int id, String title, String author, int year, int cost) {
        Item item = findItemById(id);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        if (item instanceof Book) {
            Book book = (Book) item;
            book.setTitle(title);
            book.setAuthor(author);
            book.setYear(year);
            book.setCost(cost);
            System.out.println("Book edited successfully.");
        } else {
            System.out.println("Invalid item type.");
        }
    }

    public void deleteItem(int id) {
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
                        String content = parts[6];
                        items.add(new Book(title, author, year, popularity, cost, content));
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
                else {
                    System.out.println("Invalid item type.");
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

public Object[][] getDataForJTable() {
    Object[][] data = new Object[items.size()][5];
    for (int i = 0; i < items.size(); i++) {
        Item item = items.get(i);
        data[i][0] = item.getId();
        data[i][1] = item.getTitle();
        data[i][2] = item.getAuthor();
        data[i][3] = item.getCost();
        JButton readButton = new JButton("Read");
        readButton.addActionListener(e -> displayContent(item));
        data[i][4] = readButton;
    }
    return data;
}

    private void displayContent(Item item) {
        JFrame contentFrame = new JFrame("Book Content");
        contentFrame.setSize(400, 300);

        JTextArea contentArea = new JTextArea();
        Book book = (Book) item;
        contentArea.setText("Content: " + book.getContent());
        contentArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(contentArea);
        contentFrame.add(scrollPane);

        contentFrame.setVisible(true);

        contentFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(contentFrame, "Are you sure you want to finish reading?", "Close Window?", JOptionPane.YES_NO_OPTION);
                if (option == 1) {
                    contentFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                } else {
                    contentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });
    }


    public List getItems() {
        return items;
    }
}
