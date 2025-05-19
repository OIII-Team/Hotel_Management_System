package model;

import structures.*;
import java.util.Scanner;

public class Menu {

    private final HotelTree hotelTree;
    private final BookingQueue waitlist;
    private final UsersList users;
    private User currentUser;
    private Admin admin;

    public Menu(HotelTree tree, BookingQueue waitlist, UsersList users) {
        this.hotelTree = tree;
        this.waitlist  = waitlist;
        this.users     = users;
        User.setWaitlist(waitlist);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Welcome to Hotel Management System! ===");
            System.out.println("1. User");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input.");
                scanner.nextLine();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> { currentUser = User.loginOrRegister(users); userMenu(scanner); }
                case 2 -> { admin = Admin.adminLogin(scanner); adminMenu(scanner); }
                case 3 -> { System.out.println("Good-bye!"); return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    /* ---------- user menu ---------- */
    private void userMenu(Scanner sc) {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View hotels");
            System.out.println("2. Search hotels by name");
            System.out.println("3. Cancel last booking");
            System.out.println("4. View my upcoming bookings");
            System.out.println("5. Leave a review");
            System.out.println("6. View my notifications");
            System.out.println("0. Back");
            System.out.print("Choice: ");

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
                case 3 -> admin.viewHotelsByRegion(hotelTree);
                case 4 -> users.printUsers();
                case 0 -> { return; }
                default -> System.out.println("Invalid input.");
            }
        }
    }
}