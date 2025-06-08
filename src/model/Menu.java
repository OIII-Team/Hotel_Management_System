package model;

import structures.*;
import java.util.Scanner;

// This Menu Class was created to handle the user and admin interactions in the system interactively.
// Nadav said that the whole system has to be controlled by the Main class instead of the Menu class
// so i changed everything to be as he wanted.
// However, I still think that the Menu class is a good idea to handle the user and admin interactions so i kept it
// in order to use it after the project is finished.
// The functions that we used in the project to implement the main properly are -
// Prints (printInitialMessage, printUserMenu, adminMenu)
// and the run function and user/admin menus are not used as switch cases in the Main class as they were in the Menu class.
// Basicly, the Menu class was created to make the system flow like a real one.
// This project is shown as a prototype of a real hotel management system. like Nadav said :)

public class Menu {

    private final HotelTree hotelTree;
    private final BookingQueue waitlist;
    private final UsersList users;
    private final ReviewList reviewList;
    private final UsersBookingStack usersBookingStack;
    private User currentUser;
    private Admin admin;

    public Menu(HotelTree hotelTree, UsersList users, ReviewList reviewList, BookingQueue waitList, UsersBookingStack usersBookingStack){
        this.usersBookingStack = usersBookingStack;
        this.reviewList = reviewList;
        this.hotelTree = hotelTree;
        this.waitlist  = waitList;
        this.users     = users;
        User.setWaitlist(waitList);
    }

    public void run(Scanner scanner) {

        while (true) {
            printInitialMessage();

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input.");
                scanner.nextLine();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> { currentUser = User.loginOrRegister(users, scanner); userMenu(scanner); }
                case 2 -> { admin = Admin.adminLogin(scanner); adminMenu(scanner); }
                case 3 -> { System.out.println("Good-bye!"); return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public void printInitialMessage() {
        System.out.println("\n=== Welcome to Hotel Management System! ===");
        System.out.println("1. User");
        System.out.println("2. Admin");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    /* ---------- user menu ---------- */
    private void userMenu(Scanner sc) {
        while (true) {
            printUserMenu();

            int c = sc.nextInt();
            sc.nextLine();
            switch (c) {
                case 1 -> new HotelsView(hotelTree, waitlist).run(sc, currentUser);
                case 2 -> hotelTree.searchHotelByName(sc, hotelTree);
                case 3 -> currentUser.cancelLastBooking(sc);
                case 4 -> currentUser.viewUpcomingBookings();
                case 5 -> currentUser.leaveReview(sc, hotelTree);
                case 6 -> currentUser.viewNotifications(sc);
                case 0 -> { return; }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    public void printUserMenu() {
        System.out.println("\n--- User Menu ---");
        System.out.println("1. View hotels");
        System.out.println("2. Search hotels by name");
        System.out.println("3. Cancel last booking");
        System.out.println("4. View my upcoming bookings");
        System.out.println("5. Leave a review");
        System.out.println("6. View my notifications");
        System.out.println("0. Back");
    }

    /* ---------- admin menu ---------- */
    private void adminMenu(Scanner sc) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add hotel");
            System.out.println("2. Remove hotel");
            System.out.println("3. View hotels by region");
            System.out.println("4. View all users");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            int c = sc.nextInt();
            sc.nextLine();
            switch (c) {
                case 1 -> admin.addHotelInteractive(sc, hotelTree);
                case 2 -> admin.removeHotelInteractive(sc, hotelTree);
                case 3 -> admin.viewHotelsByRegion(hotelTree, sc);
                case 4 -> users.printUsers();
                case 0 -> { return; }
                default -> System.out.println("Invalid input.");
            }
        }
    }
}