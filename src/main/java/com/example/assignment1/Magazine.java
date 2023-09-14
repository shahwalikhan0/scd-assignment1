package com.example.assignment1;

import java.util.Scanner;

public class Magazine extends Item {
    private String publisher;
    private String[] authors;

    public Magazine(String title, String publisher, String[] authors, int popularity, int cost) {
        super(title, popularity, cost);
        this.publisher = publisher;
        this.authors = authors;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Publisher: " + publisher);
        System.out.println("Authors: " + String.join(", ", authors));
    }
    @Override
    public void calculateCost() {
        double cost  = this.getCost() * this.getPopularity();
        System.out.println("Cost: " + cost);
    }

    @Override
    public void edit() {
        super.edit();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the title of the magazine: ");
        String title = scanner.nextLine();
        System.out.print("Enter the publisher of the magazine: ");
        String publisher = scanner.nextLine();
        System.out.print("Enter the number of authors of the magazine: ");
        int numAuthors = scanner.nextInt();
        scanner.nextLine();
        String[] authors = new String[numAuthors];
        for (int i = 0; i < numAuthors; i++) {
            System.out.print("Enter the name of author " + (i + 1) + ": ");
            authors[i] = scanner.nextLine();
        }
        setTitle(title);
        setPublisher(publisher);
        setAuthors(authors);
    }

    private void setAuthors(String[] authors) {
        this.authors = authors;
    }

    private void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public String[] getAuthors() {
        return authors;
    }
}
