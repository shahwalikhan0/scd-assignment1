package com.example.assignment1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Library library = new Library();
        library.loadItemsFromFile("data.txt");
        Scanner scanner = new Scanner(System.in);

        JFrame frame = new JFrame("Library Management System");
        frame.setSize(500, 500);
        frame.setLayout(null);

        Object[] columns = {"ID", "Title", "Author", "Cost", "Book"};

        JTable table = new JTable(library.getDataForJTable(), columns);
        table.setBounds(0, 0, 500, 200);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 500, 200);
        frame.add(scrollPane);

        JButton addBookButton = new JButton("Add Book");
        addBookButton.setBounds(0, 250, 100, 50);
        JButton editItemButton = new JButton("Edit Item");
        editItemButton.setBounds(120, 250, 100, 50);
        JButton deleteItemButton = new JButton("Delete Item");
        deleteItemButton.setBounds(240, 250, 100, 50);

        JButton viewPopularityButton = new JButton("View Popularity");
        viewPopularityButton.setBounds(360, 250, 120, 50);

        styleButton(addBookButton);
        styleButton(editItemButton);
        styleButton(deleteItemButton);
        styleButton(viewPopularityButton);

        frame.add(viewPopularityButton);
        frame.add(addBookButton);
        frame.add(editItemButton);
        frame.add(deleteItemButton);



        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        table.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                JTable target = (JTable) e.getSource();
                int row = target.rowAtPoint(e.getPoint());
                if (row != -1) {
                    target.clearSelection();
                    target.addRowSelectionInterval(row, row);
                }
            }
        });
        viewPopularityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame popularityFrame = new JFrame("Popularity Graph");
                popularityFrame.setSize(500, 400);
                JPanel chartPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        int barWidth = 50;
                        int x = 50;
                        for (int i = 0; i < library.getItems().size(); i++) {
                            Book book = (Book) library.getItems().get(i);
                            int popularity = book.getPopularity();
                            int barHeight = popularity * 5;

                            g.setColor(Color.blue);
                            g.fillRect(x, 300 - barHeight, barWidth, barHeight);

                            g.setColor(Color.black);
                            g.drawString(Integer.toString(popularity), x + 10, 295 - barHeight);
                            g.drawString(String.valueOf(book.getId()), x, 320);
                            x += 70;
                        }
                    }
                };

                popularityFrame.add(chartPanel);
                popularityFrame.setVisible(true);
            }
        });


        addBookButton.addActionListener(e -> {
            JFrame addItemFrame = new JFrame("Add Book");
            addItemFrame.setSize(500, 500);
            addItemFrame.setLayout(new GridLayout(10, 2));

            JTextField titleField = new JTextField();
            JTextField authorField = new JTextField();
            JTextField yearField = new JTextField();
            JTextField priceField = new JTextField();
            JTextField contentField = new JTextField();

            JLabel titleLabel = new JLabel("Title: ");
            JLabel authorLabel = new JLabel("Author: ");
            JLabel yearLabel = new JLabel("Year: ");
            JLabel priceLabel = new JLabel("Price: ");
            JLabel contentLabel = new JLabel("Content: ");

            JButton submitButton = new JButton("Add");

            addItemFrame.add(titleLabel);
            addItemFrame.add(titleField);
            addItemFrame.add(authorLabel);
            addItemFrame.add(authorField);
            addItemFrame.add(yearLabel);
            addItemFrame.add(yearField);
            addItemFrame.add(priceLabel);
            addItemFrame.add(priceField);
            addItemFrame.add(contentLabel);
            addItemFrame.add(contentField);
            addItemFrame.add(submitButton);

            addItemFrame.setVisible(true);
            submitButton.addActionListener(submitEvent -> {
                String title = titleField.getText();
                String author = authorField.getText();
                int year = Integer.parseInt(yearField.getText());
                int price = Integer.parseInt(priceField.getText());
                String content = contentField.getText();

                library.addItem(1, title, author, year, price, content);

                updateTable(table, library.getDataForJTable());
                addItemFrame.setVisible(false);
            });
        });

        editItemButton.addActionListener(e -> {
            JFrame editBookButton = new JFrame("Edit Book");
            editBookButton.setSize(400, 300);
            editBookButton.setLayout(new GridLayout(10, 2));


            JTextField idField = new JTextField();
            JTextField titleField = new JTextField();
            JTextField authorField = new JTextField();
            JTextField yearField = new JTextField();
            JTextField costField = new JTextField();

            JLabel idLabel = new JLabel("Enter the ID of the item to be edited: ");
            JLabel titleLabel = new JLabel("Title: ");
            JLabel authorLabel = new JLabel("Author: ");
            JLabel yearLabel = new JLabel("Year: ");
            JLabel costLabel = new JLabel("Cost: ");
            JButton submitButton = new JButton("Edit");

            editBookButton.add(idLabel);
            editBookButton.add(idField);
            editBookButton.add(titleLabel);
            editBookButton.add(titleField);
            editBookButton.add(authorLabel);
            editBookButton.add(authorField);
            editBookButton.add(yearLabel);
            editBookButton.add(yearField);
            editBookButton.add(costLabel);
            editBookButton.add(costField);
            editBookButton.add(submitButton);

            editBookButton.setVisible(true);
            submitButton.addActionListener(submitEvent -> {
                int id = Integer.parseInt(idField.getText());
                String title = titleField.getText();
                String author = authorField.getText();
                int year = Integer.parseInt(yearField.getText());
                int cost = Integer.parseInt(costField.getText());
                library.editBook(id, title, author, year, cost);
                updateTable(table, library.getDataForJTable());
                editBookButton.setVisible(false);
            });
        });
        
        deleteItemButton.addActionListener(e -> {
            JFrame deleteItemFrame = new JFrame("Delete Book");
            deleteItemFrame.setSize(400, 300);
            deleteItemFrame.setLayout(new GridLayout(4, 2));

            JTextField idField = new JTextField();
            JLabel idLabel = new JLabel("Enter the ID of the item to be deleted: ");
            JButton submitButton = new JButton("Delete");

            deleteItemFrame.add(idLabel);
            deleteItemFrame.add(idField);

            deleteItemFrame.add(submitButton);

            deleteItemFrame.setVisible(true);

            submitButton.addActionListener(submitEvent -> {
                int id = Integer.parseInt(idField.getText());
                library.deleteItem(id);
                updateTable(table, library.getDataForJTable());
                deleteItemFrame.setVisible(false);
            });
        });

        DefaultTableCellRenderer buttonRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof JButton) {
                    JButton button = (JButton) value;
                    button.setOpaque(true);
                    button.setBorderPainted(true);
                    button.setContentAreaFilled(false);
                    button.setBackground(Color.WHITE);
                    return button;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };

        buttonRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(4).setCellRenderer(buttonRenderer);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = table.getColumnModel().getColumnIndex("Book");
                int row = table.rowAtPoint(e.getPoint());

                if (row >= 0 && column == 4) {
                    JButton button = (JButton) table.getValueAt(row, column);
                    button.doClick();
                }
            }
        });

    }
    private static void styleButton(JButton button) {
        button.setBackground(Color.GREEN);
        button.setForeground(Color.BLACK);
        button.setBorderPainted(true);
        button.setOpaque(true);
    }
    private static void updateTable(JTable table, Object[][] dataForJTable) {
        table.setModel(new javax.swing.table.DefaultTableModel(
                dataForJTable,
                new String[]{
                        "ID", "Title", "Author", "Cost", "Book"
                }
        ));
    }

}
