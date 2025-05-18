package model;
import java.time.LocalDate;
import java.time.LocalDateTime;

import exceptions.HotelSystemPaymentExceptions;

public abstract class Payment implements Payable
{
    private double amount;
    protected LocalDate paymentDate;

    public Payment(double amount, LocalDate paymentDate) throws HotelSystemPaymentExceptions
    {
        if (amount <= 0)
            throw new HotelSystemPaymentExceptions("Amount must be greater than zero");
        this.amount = amount;
        this.paymentDate = LocalDateTime.now().toLocalDate();
    }

    public double getAmount()
    {
        return amount;
    }

    public LocalDate getPaymentDate()
    {
        return paymentDate;
    }

    public double calculateFee(double amount)
    {
        if (amount != this.amount)
            throw new HotelSystemPaymentExceptions("Amount does not match the payment amount");
        return amount * 0.025;
    }

    public abstract boolean processPayment(User user, double netAmount);
    protected abstract void validatePayment() throws HotelSystemPaymentExceptions;

}
