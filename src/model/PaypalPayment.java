package model;
import exceptions.HotelSystemExceptions;
import model.Payment;
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
        if (payerEmail == null || !payerEmail.contains("@gmail.com") || !payerEmail.contains("@walla.co.il") || !payerEmail.contains("@yahoo.com"))
            throw new HotelSystemExceptions("Invalid email address");
        if (payerId == null || payerId.isEmpty() || payerId.matches("\\d{9}"))
            throw new HotelSystemExceptions("Invalid payer ID");
    }
}
