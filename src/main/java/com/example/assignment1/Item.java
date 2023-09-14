package com.example.assignment1;

interface Configuration {
    public void display();
}

public class Item implements Configuration{
    private static int nextId = 100;
    private String title;
    private int id;

    public Item(String title) {
        this.title = title;
        this.id = nextId++;
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
    }

    public void edit() {
        System.out.println("Enter Details: ");
    }
}

