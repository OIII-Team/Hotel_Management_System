package model;
import java.util.Scanner;
import model.User;
import structures.HotelTree;
import structures.BookingList;

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

    public static void addHotelInteractive(Scanner sc, HotelTree tree) {

        System.out.println("--Add New Hotel--");
        sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();


        /* ---------- Region ---------- */
        Region selectedRegion = null;
        while (selectedRegion == null) {
            System.out.println("Choose Region:");
            Region.printRegionOptions();
            int regChoice = sc.nextInt();
            sc.nextLine();
            selectedRegion = Region.getRegionFromChoice(regChoice);
            if (selectedRegion == null)
                System.out.println("Invalid region, try again.");
        }

        /* ---------- City & Address ---------- */
        City selectedCity = null;
        while (selectedCity == null) {
            System.out.println("Choose City in " + selectedRegion.getDisplayName() + ":");
            City.printCityOptions(selectedRegion);
            int cityChoice = sc.nextInt();
            sc.nextLine();
            selectedCity = City.getCityFromChoice(selectedRegion, cityChoice);
            if (selectedCity == null)
                System.out.println("Invalid city, try again.");
        }
        System.out.print("Address: ");
        String address = sc.nextLine();

        /* ---------- Price & Rooms ---------- */
        System.out.print("Price per night: ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.print("Total rooms: ");
        int totalRooms = sc.nextInt();
        sc.nextLine();

        /* ---------- Amenities ---------- */
        System.out.print("Amenities (comma-separated): ");
        String amenitiesInput = sc.nextLine();
        String[] tokens = amenitiesInput.split(",");
        Amenities[] amenities = new Amenities[tokens.length];
        for (int i = 0; i < tokens.length; i++)
            amenities[i] = Amenities.fromString(tokens[i].trim());

        /* ---------- Capacity & Rating ---------- */
        System.out.print("Max capacity: ");
        int maxCapacity = sc.nextInt();
        sc.nextLine();
        System.out.print("Rating: ");
        double rating = sc.nextDouble();
        sc.nextLine();

        Location loc   = new Location(selectedRegion,selectedCity, address);
        Hotel    hotel = new Hotel(name, selectedRegion, loc, price,
                amenities, totalRooms, maxCapacity, null, rating, new BookingList());

        tree.addHotel(hotel);
        System.out.println("Hotel \"" + name + "\" added.");
    }

    public static void viewHotelsByRegion(HotelTree tree) {
        Region.printRegionOptions();
        Region region = null;
        Scanner scanner = new Scanner(System.in);
        region = Region.getRegionFromChoice(scanner.nextInt());

        System.out.println("Hotels in " + region.getDisplayName() + ":");
        for (Hotel hotel : tree.getHotels(region, null)) {
            System.out.println("  - " + hotel.getName());
        }
    }

}
