package model;
import java.util.Scanner;
import model.User;

public class Admin extends User
{
    private String password="123456789";

    public Admin(String name, String email, String ID, String password)
    {
        super(name, email, ID);
        this.password = "123456789";
    }

    public Admin(String password)
    {
        super();
        this.password = "123456789";
    }

    public static Admin adminLogin(Scanner scanner)
    {
        System.out.println("Welcome to the Admin Login Page!");
        System.out.print("Enter admin password: ");
        String inputPassword = scanner.nextLine();

        if (inputPassword.equals("123456789"))
        {
            System.out.println("Login successful!");
            return new Admin(inputPassword);
        }
        else
        {
            System.out.println("Invalid password. Please try again.");
            return adminLogin(scanner);
        }
    }

}
