import model.*;
import structures.HotelTree;
import structures.UsersList;
import java.util.Scanner;

public class Menu {
    private static UsersList users = new UsersList();
    private static HotelTree hotelTree;
    public static void setHotelTree(HotelTree tree) {
        hotelTree = tree;
    }
    private static User currentUser;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
while (true)
{
    System.out.println("\nWelcome to Hotel Management System!");
    System.out.println("Please select one of the following options:");
    System.out.println("1. User");
    System.out.println("2. Admin");
    System.out.println("3. Exit");

    System.out.print("Enter your choice: ");
    int choice = scanner.nextInt();
    scanner.nextLine();

    switch (choice)
    {
        case 1:
            currentUser = User.loginOrRegister(scanner, users);
            userMenu(scanner);
            break;
        case 2:
            Admin admin = Admin.adminLogin(scanner);
            adminMenu(scanner);
            break;
        case 3:
            System.out.println("Exiting the system. Goodbye!");
            scanner.close();
            break;
        default:
            System.out.println("Invalid choice. Please try again.");
    }
}
}


    public static void userMenu(Scanner scanner) {
        int userChoice;
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View hotels");
            System.out.println("2. Search hotels by name");
            System.out.println("3. Cancel last booking");
            System.out.println("4. View my upcoming bookings");
            System.out.println("5. Leave a review");
            System.out.println("0. Back to main menu");
            System.out.print("Choice: ");

            userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    HotelsView hotelsView = new HotelsView(hotelTree);
                    hotelsView.run(scanner, currentUser);
                    break;
                case 2:
                    // search hotels by name
                    hotelTree.searchHotelByName(scanner, hotelTree);
                    break;
                case 3:
                    // pop from BookingStack
                    currentUser.cancelLastBooking(scanner);
                    break;
                case 4:
                    // view upcoming bookings
                    currentUser.viewUpcomingBookings();
                    break;
                case 5:
                    // write Review
                    currentUser.leaveReview(scanner, hotelTree);
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    public static void adminMenu(Scanner scanner) {
        int adminChoice;
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add hotel");
            System.out.println("2. Remove hotel");
            System.out.println("3. View Hotels by region");
            System.out.println("4. View all users in the system");
            System.out.println("0. Back to main menu");
            System.out.print("Choice: ");

            adminChoice = scanner.nextInt();

            switch (adminChoice){
                case 1:
                    // add hotel
                    Admin.addHotelInteractive(scanner, hotelTree);
                    break;
                case 2:
                    // remove hotel by name
                    Admin.removeHotelInteractive(scanner, hotelTree);
                    break;
                case 3:
                    // view hotels by region
                    System.out.println("Select a region to view hotels:");
                    Admin.viewHotelsByRegion(hotelTree);
                    break;
                case 4:
                    users.printUsers();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }
}
