package model;

import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import structures.BookingList;
import structures.UsersBookingStack;
import structures.UsersList;
import java.util.Scanner;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class User
{
    public String name;
    private String email;
    private String ID;
    public UsersBookingStack bookingStack;
    public UsersList Users;

    public User(String name, String email, String ID)
    {
        this.name = name;
        this.email = email;
        this.ID = ID;
        this.bookingStack = new UsersBookingStack();
    }

    public User()
    {
        this.bookingStack = new UsersBookingStack();
    }

    public void setName(String name)
    {
        if (name != null && name.matches("[a-zA-Z ]+"))
        {
            this.name = name;
        } else
        {
            System.out.println("Invalid name. Only letters and spaces are allowed.");
        }
    }

    public String getName()
    {
        return name;
    }

    public void setEmail(String email)
    {
        if (email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"))
        {
            this.email = email;
        } else
        {
            System.out.println("Invalid email format.");
        }
    }

    public String getEmail()
    {
        return email;
    }

    public void setID(String ID)
    {
        if (ID != null && ID.matches("\\d{9}"))
        {
            this.ID = ID;
        } else
        {
            System.out.println("Invalid ID. please enter a numeric ID, 9 digits only.");
        }
    }

    public String getID()
    {
        return ID;
    }


    public void viewUpcomingBookings()
    {
        if (bookingStack.isEmpty()) {
            System.out.println("You have no upcoming bookings.");
            return;
        }
        System.out.println("\nYour upcoming bookings (latest first):");
        bookingStack.printStack();
    }

    public void viewUpcomingBookingsLanterna(MultiWindowTextGUI gui) {
        if (bookingStack.isEmpty()) {
            gui.addWindowAndWait(new MessageDialogBuilder()
                    .setTitle("No Bookings")
                    .setText("You have no upcoming bookings.")
                    .addButton(MessageDialogButton.OK)
                    .build());
            return;
        }

        StringBuilder bookings = new StringBuilder("Your upcoming bookings:\n");
        for (Booking booking : bookingStack.asList()) {
            bookings.append(booking.toString()).append("\n");
        }

        gui.addWindowAndWait(new MessageDialogBuilder()
                .setTitle("Upcoming Bookings")
                .setText(bookings.toString())
                .addButton(MessageDialogButton.OK)
                .build());
    }

    //Method for registering a new user or logging in an existing one
    public static User loginOrRegister(Scanner scanner, UsersList users) {
        System.out.print("Enter your ID (9 digits): ");
        String id = scanner.nextLine();

        UsersList.UserNode current = users.getHead();
        while (current != null) {
            if (current.user.getID().equals(id)) {
                System.out.println("Welcome back, " + current.user.getName() + "!");
                return current.user;
            }
            current = current.next;
        }

        System.out.println("User not found. Please register:");
        User newUser = new User();

        newUser.setID(id);
        while (newUser.getID() == null) {
            System.out.print("Invalid ID. Please re-enter a valid 9-digit ID: ");
            id = scanner.nextLine();
            newUser.setID(id);
        }

        while (true) {
            System.out.print("Enter your full name: ");
            String name = scanner.nextLine();
            newUser.setName(name);
            if (newUser.getName() != null) break;
        }

        while (true) {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            newUser.setEmail(email);
            if (newUser.getEmail() != null) break;
        }

        users.addUser(newUser);
        System.out.println("Registration successful. Welcome, " + newUser.getName() + "!");
        return newUser;
    }

    public static User loginOrRegisterLanterna(MultiWindowTextGUI gui, UsersList users) {
        Window window = new BasicWindow("Login or Register");
        Panel panel = new Panel(new GridLayout(2));

        panel.addComponent(new Label("Enter your ID (9 digits):"));
        TextBox idBox = new TextBox();
        panel.addComponent(idBox);

        panel.addComponent(new Button("Login", () -> {
            String id = idBox.getText();
            UsersList.UserNode current = users.getHead();
            while (current != null) {
                if (current.user.getID().equals(id)) {
                    gui.addWindowAndWait(new MessageDialogBuilder()
                            .setTitle("Welcome")
                            .setText("Welcome back, " + current.user.getName() + "!")
                            .addButton(MessageDialogButton.OK)
                            .build());
                    window.close();
                    return;
                }
                current = current.next;
            }

            gui.addWindowAndWait(new MessageDialogBuilder()
                    .setTitle("Not Found")
                    .setText("User not found. Please register.")
                    .addButton(MessageDialogButton.OK)
                    .build());
        }));

        panel.addComponent(new Button("Register", () -> {
            String id = idBox.getText();
            User newUser = new User();
            newUser.setID(id);

            // Additional registration steps (name, email) can be added here

            users.addUser(newUser);
            gui.addWindowAndWait(new MessageDialogBuilder()
                    .setTitle("Registration Successful")
                    .setText("Welcome, " + newUser.getName() + "!")
                    .addButton(MessageDialogButton.OK)
                    .build());
            window.close();
        }));

        window.setComponent(panel);
        gui.addWindowAndWait(window);
        return null; // Replace with the actual user object
    }

    public void PrintUserDetails()
    {
        System.out.println("Name: " + name);
        System.out.println("User ID: " + ID);
        System.out.println("Email: " + email);
    }

    public void cancelLastBooking(Scanner sc) {
        Booking last = bookingStack.peek();
        if (last == null) {
            System.out.println("No active bookings to cancel.");
            return;
        }

        System.out.println("\nLast booking:");
        last.printLine();
        System.out.print("Cancel it? (yes/no) ");

        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
            System.out.println("Cancellation aborted.");
            return;
        }

        last.cancelBooking();
        System.out.println("Booking cancelled.");
    }

    public void cancelLastBookingLanterna(MultiWindowTextGUI gui) {
        Booking last = bookingStack.peek();
        if (last == null) {
            gui.addWindowAndWait(new MessageDialogBuilder()
                    .setTitle("No Bookings")
                    .setText("No active bookings to cancel.")
                    .addButton(MessageDialogButton.OK)
                    .build());
            return;
        }

        // Using OK to confirm, Cancel to abort instead of YES/NO
        MessageDialogButton result = new MessageDialogBuilder()
                .setTitle("Cancel Booking")
                .setText("Cancel the following booking?\n" + last)
                .addButton(MessageDialogButton.OK)
                .addButton(MessageDialogButton.Cancel)
                .build()
                .showDialog(gui);

        if (result == MessageDialogButton.OK) {
            last.cancelBooking();
            bookingStack.pop();
            gui.addWindowAndWait(new MessageDialogBuilder()
                    .setTitle("Cancelled")
                    .setText("Booking cancelled.")
                    .addButton(MessageDialogButton.OK)
                    .build());
        }
    }

    public void addBooking(Booking b)            { bookingStack.push(b); }
    public void removeBooking()            { bookingStack.pop(); }
}

