package model;
import exceptions.HotelSystemExceptions;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.YearMonth;

public class CreditCardPayment extends Payment
{
    private String cardNumber;
    private String cvv;
    private YearMonth expirationDate;

    public CreditCardPayment(double amount, LocalDate paymentDate, String cardNumber, String cvv, YearMonth expirationDate) {
        super(amount, paymentDate);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public boolean processPayment(User user, double netAmount)
    {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                validatePayment();
                return true;
            } catch (HotelSystemExceptions e) {
                System.out.println(e.getMessage());
                if (e.getMessage().contains("credit card number")) {
                    System.out.print("Re-enter Card Number: ");
                    this.cardNumber = sc.nextLine().trim();
                } else if (e.getMessage().contains("CVV")) {
                    System.out.print("Re-enter CVV: ");
                    this.cvv = sc.nextLine().trim();
                } else {
                    System.out.print("Re-enter Expiry (MM/yyyy): ");
                    String[] parts = sc.nextLine().split("/");
                    this.expirationDate = YearMonth.of(
                            Integer.parseInt(parts[1].trim()),
                            Integer.parseInt(parts[0].trim()));
                }
            }
        }
    }

    public void validatePayment() throws HotelSystemExceptions
    {
        if (!cardNumber.matches("\\d{16}"))
            throw new HotelSystemExceptions("Invalid credit card number");
        if (!cvv.matches("\\d{3}"))
            throw new HotelSystemExceptions("Invalid CVV");
        if (expirationDate.isBefore(YearMonth.now()))
            throw new HotelSystemExceptions("Card expired");
    }

    public String getCardNumber() {
        return cardNumber;
    }
    public String getCvv() {
        return cvv;
    }

}
