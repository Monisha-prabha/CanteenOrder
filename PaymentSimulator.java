package com.example.zxing_canteen;

import java.util.Scanner;

public class PaymentSimulator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Waiting for payment confirmation...");
        try {  
            Thread.sleep(3000); // Simulate waiting time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("Have you completed the payment? (Y/N): ");
        String response = sc.nextLine().trim().toUpperCase();

        if (response.equals("Y")) {
            System.out.println("Payment Successful!");
            System.out.println(" Your order is confirmed and is being prepared.");
        } else {
            System.out.println(" Sorry!! Your yummy order is cancelled.");
        }

        sc.close();
    }
}
