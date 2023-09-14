package com.example.assignment1;

import java.util.List;

interface Configuration {
    public void display();
    public void calculateCost();
}

public class Item implements Configuration{
    private static int nextId = 100;
    private String title;
    private int id;
    private int popularity;
    private int cost;
    List<Borrower> borrowers;
    private boolean borrowed = false;

    public Item(String title, int popularity, int cost) {
        this.title = title;
        this.id = nextId++;
        this.cost = cost;
        this.popularity = popularity;
        borrowers = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Popularity: " + popularity);
        System.out.println("Cost: " + cost);
    }

    public void edit() {
        System.out.println("Enter Details: ");
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public boolean hasReadTheItem(Borrower borrower) {
        if (borrowers == null) {
            return false;
        }
        return borrowers.contains(borrower);
    }

    public void setBorrowed(boolean b) {
        borrowed = b;
    }
    public void increasePopularity() {
        popularity++;
    }
    public int getPopularity() {
        return popularity;
    }
    public int getCost() {
        return cost;
    }

    public void setBorrower(Borrower borrower) {
        if (borrowers == null) {
            borrowers = new java.util.ArrayList<Borrower>();
        }
        borrowers.add(borrower);
    }

    public void removeBorrower(Borrower borrower) {
        borrowers.remove(borrower);
    }

    public void calculateCost() {
    }
}

