package model;
import exceptions.HotelSystemPaymentExceptions;

import java.time.LocalDateTime;
import java.util.Scanner;

public class PaypalPayment extends Payment
{
    private String payerEmail;
    private String payerId;

    public PaypalPayment(double amount, LocalDateTime paymentDate, String payerEmail, String payerId)
    {
        super(amount, paymentDate);
        this.payerEmail = payerEmail;
        this.payerId = payerId;
    }

    public void validatePayment() throws HotelSystemPaymentExceptions
    {
        if (payerEmail == null || !payerEmail.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"))
            throw new HotelSystemPaymentExceptions.InvalidEmailException();
        if (payerId == null || payerId.isEmpty() || !payerId.matches("\\d{9}"))
            throw new HotelSystemPaymentExceptions.InvalisPayerIdException();
    }


    public boolean processPayment(double netAmount, Scanner scanner){
        Scanner sc = new Scanner(System.in);
        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts) {
            try {
                validatePayment();
                return true;
            } catch (HotelSystemPaymentExceptions e) {
                System.out.println(e.getMessage());

                if (attempts >= maxAttempts) {
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

    public String getPayerEmail() {
        return payerEmail;
    }
}

