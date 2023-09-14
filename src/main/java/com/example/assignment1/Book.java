package com.example.assignment1;

import java.util.Scanner;

public class Book extends Item {

    private String author;
    private int year;

    public Book(String t, String a, int y) {
        super(t);
        author = a;
        year = y;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Author: " + author);
        System.out.println("Year: " + year);
    }

    public String getTitle() {
        return super.getTitle();
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void setTitle(String t) {
        super.setTitle(t);
    }

    public void setAuthor(String a) {
        author = a;
    }

    public void setYear(int y) {
        year = y;
    }

    @Override
    public void edit() {
        super.edit();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the title of the book: ");
        String title = scanner.nextLine();
        System.out.print("Enter the author of the book: ");
        String author = scanner.nextLine();
        System.out.print("Enter the year of publication of the book: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        setTitle(title);
        setAuthor(author);
        setYear(year);
    }
}