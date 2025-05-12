package model;
import exceptions.HotelSystemExceptions;
import model.Payment;

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
