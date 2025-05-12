package model;

public interface Payable
{
    boolean processPayment(User user, double amount);
    double calculateFee(double amount);
}
