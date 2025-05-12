package model;
import java.time.LocalDate;
import java.time.LocalDateTime;

import exceptions.HotelSystemExceptions;

public abstract class Payment implements Payable
{
    private double amount;
    public LocalDate paymentDate;

    public Payment(double amount, LocalDate paymentDate) throws HotelSystemExceptions
    {
        if (amount <= 0)
            throw new HotelSystemExceptions("Amount must be greater than zero");
        this.amount = amount;
        this.paymentDate = LocalDateTime.now().toLocalDate();
    }

    public double getAmount()
    {
        return amount;
    }

    public double calculateFee(double amount)
    {
        if (amount != this.amount)
            throw new HotelSystemExceptions("Amount does not match the payment amount");
        return amount * 0.025;
    }

    public abstract boolean processPayment(User user, double netAmount);
    protected abstract void validatePayment() throws HotelSystemExceptions;

}
