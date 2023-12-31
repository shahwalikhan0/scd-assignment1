package com.example.assignment1;

import java.util.Scanner;

public class Newspaper extends Item {
    private String publisher;
    private String publicationDate;

    public Newspaper(String title, String publisher, String publicationDate, int popularity, int cost) {
        super(title, popularity, cost);
        this.publisher = publisher;
        this.publicationDate = publicationDate;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Publisher: " + publisher);
        System.out.println("Publication Date: " + publicationDate);
    }

    @Override
    public void edit() {
        super.edit();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the title of the newspaper: ");
        String title = scanner.nextLine();
        System.out.print("Enter the publisher of the newspaper: ");
        String publisher = scanner.nextLine();
        System.out.print("Enter the publication date of the newspaper: ");
        String publicationDate = scanner.nextLine();
        setTitle(title);
        setPublisher(publisher);
        setPublicationDate(publicationDate);
    }

    @Override
    public void calculateCost() {
        double cost = 10+5;
        System.out.println("Cost: " + cost);
    }
    private void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    private void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublicationDate() {
        return publicationDate;
    }
}
