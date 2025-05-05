import model.Admin;
import model.Region;
import model.User;
import structures.HotelTree;
import structures.UsersList;
import java.util.Scanner;

public class Menu {
    private static UsersList users = new UsersList();
    private static HotelTree hotelTree = new HotelTree();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
while (true)
{
    System.out.println("Welcome to Hotel Management System!");
    System.out.println("Please select one of the following options:");
    System.out.println("1. User");
    System.out.println("2. Admin");
    System.out.println("3. Exit");

    System.out.print("Enter your choice: ");
    int choice = scanner.nextInt();
    scanner.nextLine(); // Consume newline character

    switch (choice)
    {
        case 1:
            User user = User.loginOrRegister(scanner, users);
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

            switch (userChoice) {
                case 1:
                    System.out.println("Select a region to view hotels:");
                    Region.printRegionOptions();
                    int regionChoice = scanner.nextInt();
                    scanner.nextLine();

                    Region selectedRegion = Region.getRegionFromChoice(regionChoice);
                    hotelTree.printHotelsByRegion(selectedRegion);
                    break;
                case 2:
                    // search hotels by name
                    break;
                case 3:
                    // pop from BookingStack
                    break;
                case 4:
                    // view upcoming bookings
                    break;
                case 5:
                    // write Review
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
            System.out.println("3. View all users in the system");
            System.out.println("0. Back to main menu");
            System.out.print("Choice: ");

            adminChoice = scanner.nextInt();

            switch (adminChoice) {
                case 1:
                    // add Hotel to HotelTree
                    break;
                case 2:
                    // remove hotel by name
                    break;
                case 3:
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