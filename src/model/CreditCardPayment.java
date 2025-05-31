package model;
import exceptions.HotelSystemPaymentExceptions;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.YearMonth;

public class CreditCardPayment extends Payment
{
    private String cardNumber;
    private String cvv;
    private String expirationDateString;

    public CreditCardPayment(double amount, LocalDateTime paymentDate, String cardNumber, String cvv, String expirationDateString)
    {
        super(amount, paymentDate);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expirationDateString = expirationDateString;
    }

    public void validatePayment() throws HotelSystemPaymentExceptions
    {
        if (!cardNumber.matches("\\d{16}"))
            throw new HotelSystemPaymentExceptions.InvalidCardNumberException();
        if (!cvv.matches("\\d{3}"))
            throw new HotelSystemPaymentExceptions.InvalidCVVException();
        try {
            String[] parts = expirationDateString.split("/");
            if (parts.length != 2)
                throw new HotelSystemPaymentExceptions.DateException.InvalidFormatException();

            int m = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            if (m < 1 || m > 12)
                throw new HotelSystemPaymentExceptions.DateException.InvalidFormatException();

            YearMonth candidate = YearMonth.of(y, m);
            if (candidate.isBefore(YearMonth.now()))
                throw new HotelSystemPaymentExceptions.DateException.ExpiredException();
        } catch (NumberFormatException e) {
            throw new HotelSystemPaymentExceptions.DateException.InvalidFormatException();
        }
    }

    public boolean validateSucceeded()
    {
        try {
            validatePayment();
            return true;
        } catch (HotelSystemPaymentExceptions e) {
            return false;
        }
    }

    public boolean processPayment(double netAmount, Scanner scanner)
    {
        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts)
        {
            if (!validateSucceeded()){
            System.out.println("Attempts remaining: " + (maxAttempts - attempts));
        }
            try
            {
                validatePayment();
                return true;
            } catch (HotelSystemPaymentExceptions.InvalidCardNumberException ex)
            {
                System.out.println(ex.getMessage());
                System.out.print("Re-enter Card Number: ");
                cardNumber = scanner.nextLine().trim();
                System.out.println(cardNumber);

            } catch (HotelSystemPaymentExceptions.InvalidCVVException ex)
            {
                System.out.println(ex.getMessage());
                System.out.print("Re-enter CVV: ");
                cvv = scanner.nextLine().trim();
                System.out.println(cvv);
            } catch (HotelSystemPaymentExceptions.DateException ex)
            {
                System.out.println(ex.getMessage());
                System.out.print("Re-enter Expiry (MM/yyyy): ");
                expirationDateString = scanner.nextLine().trim();
                System.out.println(expirationDateString);
            }

            if (!validateSucceeded())
            {
                attempts++;
                if (attempts >= maxAttempts)
                {
                    System.out.println("Maximum attempts reached.");
                    return false;
                }
            }
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
