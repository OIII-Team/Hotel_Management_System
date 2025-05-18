package model;

import structures.BookingQueue;
import structures.UsersBookingStack;
import structures.UsersList;
import structures.HotelTree;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class User
{
    public String name;
    private String email;
    private String ID;
    public UsersBookingStack bookingStack;
    public UsersList Users;
    private static BookingQueue waitlist;

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
            System.out.println("\nYou have no upcoming bookings.");
            return;
        }
        System.out.println("\nYour upcoming bookings (latest first):");
        bookingStack.printStack();
    }

    //Method for registering a new user or logging in an existing one
    public static User loginOrRegister(Scanner scanner, UsersList users) {
        System.out.println("Welcome to User's login page!");
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
        bookingStack.pop();
        last.getHotel().removeBooking(last);
        last.getHotel().updateMatrixForCancellation(last.getCheckIn(), last.getCheckOut());
        System.out.println("Booking cancelled.");


        while (!waitlist.isEmpty())
        {
            BookingQueue.BookingRequest req = waitlist.peek();
            if (req.getHotel().equals(last.getHotel())
                    && req.getCheckIn().equals(last.getCheckIn())
                    && req.getCheckOut().equals(last.getCheckOut()))
            {
                waitlist.dequeue();
                req.getUser().addWaitlistNotification(req);
                req.getUser().removeWaitlistNotification(req);
                System.out.println("Notified " + req.getUser().getName() + " about availability.");
            } else
            {
                break;
            }
        }
    }

    public void addBooking(Booking b) { bookingStack.push(b); }

    public void removeBooking() { bookingStack.pop(); }

    public void leaveReview(Scanner sc, HotelTree tree) {
        System.out.print("Enter hotel name to review: ");
        String name = sc.nextLine().trim();
        Hotel hotel = tree.findHotel(name);
        if (hotel == null) {
            System.out.println("Hotel not found. Returning to menu.");
            return;
        }

        System.out.print("\n-- Leave a Review for " + hotel.getName() + " --\n");
        System.out.print("Your rating (1–5): ");
        int rating;
        while (true) {
            String line = sc.nextLine().trim();
            try {
                rating = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number 1–5: ");
                continue;
            }
            if (rating >= 1 && rating <= 5) break;
            System.out.print("Invalid range. Enter 1–5: ");
        }

        System.out.print("Your comment: ");
        String comment = sc.nextLine().trim();
        while (comment.isBlank()) {
            System.out.print("Cannot be empty. Try again: ");
            comment = sc.nextLine().trim();
        }
        System.out.print("Are you sure you want to submit this review? (yes/no) ");
        String confirmation = sc.nextLine().trim();
        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Review submission cancelled.");
            return;
        }
        Review review = new Review(this, hotel, rating, comment, LocalDateTime.now());
        hotel.getReview().addReview(review);

        System.out.println("Thank you! Your review has been added.");
    }

    //BookingQueue methods - notification center for the user
    private final List<BookingQueue.BookingRequest> waitlistNotifications = new ArrayList<>();

    public static void setWaitlist(BookingQueue waitlist)
    {
        User.waitlist = waitlist;
    }

    public void removeWaitlistNotification(BookingQueue.BookingRequest req) {
        waitlistNotifications.remove(req);
    }

    public void addWaitlistNotification(BookingQueue.BookingRequest req) {
        waitlistNotifications.add(req);
    }

    public void viewNotifications(Scanner sc) {
        if (waitlistNotifications.isEmpty()) {
            System.out.println("You have no notifications.");
            return;
        }

        Iterator<BookingQueue.BookingRequest> it = waitlistNotifications.iterator();
        while (it.hasNext()) {
            BookingQueue.BookingRequest req = it.next();
            Hotel hotel = req.getHotel();
            LocalDate in = req.getCheckIn();
            LocalDate out = req.getCheckOut();
            System.out.printf("%s: %s → %s%n", hotel.getName(), in, out);

            if (hotel.isRoomAvailable(in, out)) {
                System.out.print("Room is available! Complete payment and book? (yes/no): ");
                String resp = sc.nextLine().trim();
                if (!resp.equalsIgnoreCase("yes")) {
                    System.out.println("OK, your booking request is cancelled.");
                    it.remove();
                    req.getUser().removeWaitlistNotification(req);
                    continue;
                }

                double amount = hotel.getPricePerNight() * ChronoUnit.DAYS.between(in, out);
                Payable payer;
                String paymentRef = "";
                while (true) {
                    System.out.println("Select payment method:");
                    System.out.println("1) Credit Card");
                    System.out.println("2) PayPal");
                    String choice = sc.nextLine().trim();

                    if (choice.equals("1")) {
                        System.out.print("Card Number (16 digits): ");
                        String cardNum = sc.nextLine().trim();
                        System.out.print("CVV (3 digits): ");
                        String cvv = sc.nextLine().trim();
                        System.out.print("Expiry (MM/yyyy): ");
                        String[] parts = sc.nextLine().split("/");
                        YearMonth exp = YearMonth.of(
                                Integer.parseInt(parts[1].trim()),
                                Integer.parseInt(parts[0].trim())
                        );
                        payer = new CreditCardPayment(amount, LocalDate.now(), cardNum, cvv, exp);
                        paymentRef = cardNum.length() > 4 ? cardNum.substring(cardNum.length() - 4) : cardNum;
                        break;
                    } else if (choice.equals("2")) {
                        System.out.print("PayPal Email: ");
                        String email = sc.nextLine().trim();
                        System.out.print("PayPal Account ID for confirmation: ");
                        String id = sc.nextLine().trim();
                        payer = new PaypalPayment(amount, LocalDate.now(), email, id);
                        int at = email.indexOf('@');
                        paymentRef = email.substring(0, Math.min(at, 4));
                        break;
                    } else {
                        System.out.println("Invalid choice — please select 1 or 2.");
                    }
                }

                Booking booking = Booking.create(this, hotel, in, out, payer);
                req.getUser().removeWaitlistNotification(req);
                booking.getHotel().updateMatrixForBooking(in, out);
                if (booking == null) {
                    System.out.println("Payment failed. You remain on the waitlist.");
                    continue;
                }
                System.out.println("\nBooking confirmed.");
                if (payer instanceof CreditCardPayment) {
                    System.out.println("Credit card ends with : XXXX-XXXX-XXXX-" + paymentRef);
                } else if (payer instanceof PaypalPayment) {
                    System.out.println("PayPal account ref : " + paymentRef + "XX@" + email.substring(email.indexOf('@')));
                }
                System.out.println("Thank you! see you soon :)\n");

                booking.printBookingDetails();
                it.remove();
            } else {
                System.out.println("-> Still waiting for availability.");
            }
        }
    }

}
