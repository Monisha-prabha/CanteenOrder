package com.example.zxing_canteen;

import java.sql.*;
import java.util.*;
import java.io.File;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class CanteenOrder {
 
    private static final String URL = "jdbc:mysql://localhost:3306/canteenyy";
    private static final String USER = "root";
    private static final String PASS = "priyanka";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("=== Welcome to Yummy!!! Canteen ===");

            // Display menu
            displayMenu(conn);

            // Take order
            List<String> orderItems = new ArrayList<>();
            double totalPrice = 0;
            while (true) {
                System.out.print("Enter Item ID to order (0 to finish): ");
                int id = sc.nextInt();
                if (id == 0) break;

                String sql = "SELECT item_name, price FROM menu WHERE id=?";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setInt(1, id);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            String itemName = rs.getString("item_name");
                            double price = rs.getDouble("price");     
                            orderItems.add(itemName);
                            totalPrice += price;
                            System.out.println(itemName + " added to order.");
                        } else {
                            System.out.println("Invalid Item ID!");
                        }
                    }
                }
            }

             System.out.println("\nYour Order Summary:");
            for (String item : orderItems) {
                System.out.println(" - " + item);
            }
            System.out.println("Total Price: ₹" + totalPrice);

            // Generate QR Code for payment
            String upiLink = "upi://pay?pa=9940545485@upi&pn=Canteen&am=" + totalPrice + "&cu=INR";
            String filePath = "c:\\temp\\c.png";
            generateQRCode(upiLink, filePath, 300, 300);
            System.out.println("\nQR Code generated successfully: " + filePath);
            System.out.println("Scan the QR Code to make payment.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayMenu(Connection conn) throws SQLException {
        String sql = "SELECT * FROM menu";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- MENU ---");
            System.out.printf("%-5s %-15s %-10s\n", "ID", "Item Name", "Price");
            while (rs.next()) {
                System.out.printf("%-5d %-15s ₹%-10.2f\n",
                        rs.getInt("id"),
                        rs.getString("item_name"),
                        rs.getDouble("price"));
            }
            System.out.println("-------------");
        }
    }

    private static void generateQRCode(String data, String filePath, int width, int height)
            throws WriterException, java.io.IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", new File(filePath).toPath());
        
        
        

    }
}
