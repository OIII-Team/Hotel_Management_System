package model;
import exceptions.HotelSystemPaymentExceptions;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.YearMonth;

public class CreditCardPayment extends Payment
{
    private String cardNumber;
    private String cvv;
    private YearMonth expirationDate;

    public CreditCardPayment(double amount, LocalDateTime paymentDate, String cardNumber, String cvv, YearMonth expirationDate) {
        super(amount, paymentDate);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public void validatePayment() throws HotelSystemPaymentExceptions
    {
        if (!cardNumber.matches("\\d{16}"))
            throw new HotelSystemPaymentExceptions.InvalidCardNumberException();
        if (!cvv.matches("\\d{3}"))
            throw new HotelSystemPaymentExceptions.InvalidCVVException();
        if (!expirationDate.toString().matches("\\d{4}-\\d{2}"))
            throw new HotelSystemPaymentExceptions.DateException.InvalidFormatException();
        if (expirationDate.isBefore(YearMonth.now()))
            throw new HotelSystemPaymentExceptions.DateException.ExpiredException();
    }

    public boolean processPayment(User user, double netAmount)
    {
        Scanner sc = new Scanner(System.in);
        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts)
        {
            try
            {
                validatePayment();
                return true;
            } catch (HotelSystemPaymentExceptions.InvalidCardNumberException ex)
            {
                System.out.println(ex.getMessage());
                System.out.print("Re-enter Card Number: ");
                cardNumber = sc.nextLine().trim();
            } catch (HotelSystemPaymentExceptions.InvalidCVVException ex)
            {
                System.out.println(ex.getMessage());
                System.out.print("Re-enter CVV: ");
                cvv = sc.nextLine().trim();
            } catch (HotelSystemPaymentExceptions.DateException.InvalidFormatException ex)
            {
                System.out.println(ex.getMessage());
                while (true)
                {
                    System.out.print("Re-enter Expiry (MM/yyyy): ");
                    String[] parts = sc.nextLine().trim().split("/");
                    if (parts.length != 2)
                    {
                        System.out.println(ex.getMessage());
                        continue;
                    }
                    try
                    {
                        int m = Integer.parseInt(parts[0].trim());
                        int y = Integer.parseInt(parts[1].trim());
                        if (m < 1 || m > 12)
                        {
                            System.out.println(ex.getMessage());
                            continue;
                        }
                        expirationDate = YearMonth.of(y, m);
                        break;
                    } catch (NumberFormatException e)
                    {
                        System.out.println(ex.getMessage());
                    }
                }
            } catch (HotelSystemPaymentExceptions.DateException.ExpiredException ex)
            {
                System.out.println(ex.getMessage());
                while (true)
                {
                    System.out.print("Re-enter Expiry (MM/yyyy): ");
                    String[] parts = sc.nextLine().trim().split("/");
                    if (parts.length != 2)
                    {
                        System.out.println(new HotelSystemPaymentExceptions.DateException.InvalidFormatException().getMessage());
                        continue;
                    }
                    try
                    {
                        int m = Integer.parseInt(parts[0].trim());
                        int y = Integer.parseInt(parts[1].trim());
                        if (m < 1 || m > 12)
                        {
                            System.out.println(new HotelSystemPaymentExceptions.DateException.InvalidFormatException().getMessage());
                            continue;
                        }
                        YearMonth candidate = YearMonth.of(y, m);
                        if (candidate.isBefore(YearMonth.now()))
                        {
                            System.out.println(ex.getMessage());
                            continue;
                        }
                        expirationDate = candidate;
                        break;
                    } catch (NumberFormatException e)
                    {
                        System.out.println(new HotelSystemPaymentExceptions.DateException.InvalidFormatException().getMessage());
                    }
                }
            }
            attempts++;
            if (attempts >= maxAttempts) {
                System.out.println("Maximum attempts reached.");
                return false;
            }
            System.out.println("Attempts remaining: " + (maxAttempts - attempts));
        }
        return false;
    }


    public String getCardNumber() {
        return cardNumber;
    }
    public String getCvv() {
        return cvv;
    }

}
