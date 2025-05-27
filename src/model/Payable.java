package model;

public interface Payable
{
    boolean processPayment(double amount);
    double calculateFee(double amount);
}
