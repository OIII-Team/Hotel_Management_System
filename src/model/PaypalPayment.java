package model;
import exceptions.HotelSystemExceptions;

import java.time.LocalDate;
import java.util.Scanner;

public class PaypalPayment extends Payment
{
    private String payerEmail;
    private String payerId;

    public PaypalPayment(double amount, LocalDate paymentDate, String payerEmail, String payerId)
    {
        super(amount, paymentDate);
        this.payerEmail = payerEmail;
        this.payerId = payerId;
    }

    public void validatePayment() throws HotelSystemExceptions
    {
        if (payerEmail == null || !payerEmail.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"))
            throw new HotelSystemExceptions("Invalid email address");
        if (payerId == null || payerId.isEmpty() || !payerId.matches("\\d{9}"))
            throw new HotelSystemExceptions("Invalid payer ID");
    }


    public boolean processPayment(User user, double netAmount) {
        Scanner sc = new Scanner(System.in);
        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts) {
            try {
                validatePayment();
                return true;
            } catch (HotelSystemExceptions e) {
                System.out.println(e.getMessage());

                if (attempts >= maxAttempts - 1) {
                    System.out.println("Maximum attempts reached. Payment cancelled.");
                    return false;
                }

                System.out.println("Attempts remaining: " + (maxAttempts - attempts));
                System.out.print("Re-enter PayPal Email: ");
                this.payerEmail = sc.nextLine().trim();
                System.out.print("Re-enter PayPal Account ID: ");
                this.payerId = sc.nextLine().trim();

                attempts++;
            }
        }
        return false;
    }
}

