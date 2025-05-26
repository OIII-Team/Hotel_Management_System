package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import exceptions.HotelSystemPaymentExceptions;

public abstract class Payment implements Payable
{
    private double amount;
    protected LocalDateTime paymentDate;

    public Payment(double amount, LocalDateTime paymentDate) throws HotelSystemPaymentExceptions
    {
        if (amount <= 0)
            throw new HotelSystemPaymentExceptions("Amount must be greater than zero");
        this.amount = amount;
        this.paymentDate = LocalDateTime.now();
    }

    public double getAmount()
    {
        return amount;
    }

    public LocalDateTime getPaymentDate()
    {
        return paymentDate;
    }

    public double calculateFee(double amount)
    {
        if (amount != this.amount)
            throw new HotelSystemPaymentExceptions("Amount does not match the payment amount");
        return amount * 0.025;
    }

    public String getClassName()
    {
        return getClass().getSimpleName();
    }

    public String toString()
    {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        return String.format("Total price: ₪%.2f, completed on %s, by %s", amount, paymentDate.format(fmt), getClassName());
    }

    public abstract boolean processPayment(User user, double netAmount);
    protected abstract void validatePayment() throws HotelSystemPaymentExceptions;

}
