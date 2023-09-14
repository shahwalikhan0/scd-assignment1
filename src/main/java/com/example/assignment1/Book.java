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
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String t) {
        title = t;
    }

    public void setAuthor(String a) {
        author = a;
    }

    public void setYear(int y) {
        year = y;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nTitle: " + title + " by " + author + " (" + year + ")";
    }
}