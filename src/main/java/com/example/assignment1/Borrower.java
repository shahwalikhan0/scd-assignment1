package com.example.assignment1;

import java.util.ArrayList;
import java.util.List;

public class Borrower {
    String name;
    List<Item> borrowedItems;

    public Borrower(String name, List<Item> borrowedItems) {
        this.name = name;
        this.borrowedItems = borrowedItems;
    }

    public void borrowItem(Item item) {
        if(borrowedItems == null){
            borrowedItems = new ArrayList<>();
        }
        borrowedItems.add(item);
    }

    public void returnItem(Item item) {
        borrowedItems.remove(item);
    }

    public void display() {
        System.out.println("\nName: " + name);
        System.out.println("Borrowed Items: ");
        for (Item item : borrowedItems) {
            item.display();
        }
    }

    public String getName() {
        return name;
    }

    public List<Item> getBorrowedItems() {
        return borrowedItems;
    }

}
