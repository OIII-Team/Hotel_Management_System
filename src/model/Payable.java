package model;
import exceptions.HotelSystemExceptions;

public interface Payable
{
    boolean processPayment(User user, double amount) throws HotelSystemExceptions;
    double calculateFee(double amount);
}
