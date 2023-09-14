package com.example.assignment1;

public class Book {
    private static int nextId = 1;

    private int id;
    private String title;
    private String author;
    private int year;

    public Book(String t, String a, int y) {
        id = nextId++;
        title = t;
        author = a;
        year = y;
    }

    public void display() {
        System.out.println("ID: " + id + "\nTitle: " + title + " by " + author + " (" + year + ")");
    }
}