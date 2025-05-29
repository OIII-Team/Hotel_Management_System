package model;

import java.util.Scanner;

public interface Payable
{
    boolean processPayment(double amount, Scanner scanner);
    double calculateFee(double amount);
}
