package model;
import exceptions.HotelSystemExceptions;

import java.time.LocalDate;

public class PaypalPayment extends Payment
{
    private String payerEmail;
    private String payerId;

    public PaypalPayment(double amount, LocalDate paymentDate, String payerEmail, String payerId) {
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
    public boolean processPayment(User user, double netAmount)
    {
        try {
            validatePayment();
            return true;
        } catch (HotelSystemExceptions e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
