// This is the main class of the project, this main initialize, run the system and demonstrate its functionality.
// There are syntax and input checks for every part of the code!!
// Not everything is shown here because we know its not the purpose of this project but they exists!!
// When you start the system and run the code- there are paths and tests of the system that are shown
// in order to demonstrate the functionality of the system.
// We hope the project answers the requirements and is easy to use and understand.
// Thanks a lot!

import structures.*;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        // Initialize the data structures
        HotelTree hotelTree = new HotelTree();
        UsersList users = new UsersList();
        ReviewList reviewList = new ReviewList();
        BookingList bookings = new BookingList();
        BookingQueue waitList = new BookingQueue();
        UsersBookingStack usersBookingStack = new UsersBookingStack();

        // Initialize the menu
        Menu menu = new Menu(hotelTree, users, reviewList, waitList, usersBookingStack);

        // Hotels initialization -> insertion into the tree
        //North
        Hotel hotel1 = new Hotel("The Scots Hotel",Region.NORTH,new Location(Region.NORTH,City.TIBERIAS,"Sea Road 1"),400.0,new Amenities[]{Amenities.POOL,Amenities.WIFI},2,2,4.5,bookings,hotelTree,reviewList);
        hotelTree.addHotel(hotel1);
        Hotel hotel2 = new Hotel("Leonardo Plaza Hotel Tiberias",Region.NORTH,new Location(Region.NORTH,City.TIBERIAS,"Kinneret St. 5"),450.0,new Amenities[]{Amenities.POOL,Amenities.RESTAURANTS},25,3,4.3,bookings,hotelTree,reviewList);
        hotelTree.addHotel(hotel2);
       //Jerusalem
        Hotel hotel3= new Hotel("King David Hotel",Region.JERUSALEM,new Location(Region.JERUSALEM,City.JERUSALEM,"King David 23"),500.0,new Amenities[]{Amenities.PARKING,Amenities.RESTAURANTS},15,2,4.8,bookings,hotelTree,reviewList);
        hotelTree.addHotel(hotel3);
        Hotel hotel4 = new Hotel("Mamilla Hotel",Region.JERUSALEM,new Location(Region.JERUSALEM,City.JERUSALEM,"Mamilla Mall"),350.0,new Amenities[]{Amenities.WIFI,Amenities.RESTAURANTS},30,3,4.6,bookings,hotelTree,reviewList);
        hotelTree.addHotel(hotel4);
        //Center
        Hotel hotel5 = new Hotel("The Norman Tel Aviv",Region.CENTER,new Location(Region.CENTER,City.TEL_AVIV,"Herbert Samuel 2"),600.0,new Amenities[]{Amenities.GYM,Amenities.WIFI},18,2,4.7,bookings,hotelTree,reviewList);
        hotelTree.addHotel(hotel5);
        Hotel hotel6 = new Hotel("Dan Panorama Tel Aviv",Region.CENTER,new Location(Region.CENTER,City.TEL_AVIV,"Bialik 23"),480.0,new Amenities[]{Amenities.POOL,Amenities.RESTAURANTS},25,3,4.4,bookings,hotelTree,reviewList);
        hotelTree.addHotel(hotel6);
        //South
        Hotel hotel7 = new Hotel("Dan Eilat",Region.SOUTH,new Location(Region.SOUTH,City.EILAT,"North Beach"),450.0,new Amenities[]{Amenities.POOL,Amenities.WIFI},40,3,4.2,bookings,hotelTree,reviewList);
        hotelTree.addHotel(hotel7);
        Hotel hotel8 = new Hotel("Kempinski Hotel Ishtar Dead Sea",Region.SOUTH,new Location(Region.SOUTH,City.DEAD_SEA,"Ishtar Gate"),700.0,new Amenities[]{Amenities.SPA,Amenities.POOL},50,4,4.6,bookings,hotelTree,reviewList);
        hotelTree.addHotel(hotel8);
        //Tsimmers
        Tsimmer tsimmer1 = new Tsimmer("Galim Tsimmer", Region.NORTH, new Location(Region.NORTH, City.TIBERIAS, "Lake View Rd. 5"), 300.0, new Amenities[]{Amenities.WIFI}, 5, 4, 3.9, bookings, hotelTree, reviewList, true, false, "Lake");
        hotelTree.addHotel(tsimmer1);
        Tsimmer tsimmer2 = new Tsimmer("Mitzpe Ramon Tsimmer", Region.SOUTH, new Location(Region.SOUTH, City.MITZPE_RAMON, "Desert Path 1"), 250.0, new Amenities[]{Amenities.PARKING}, 3, 2, 4.1, bookings, hotelTree, reviewList, false, true, "Desert");
        hotelTree.addHotel(tsimmer2);

        // Initialize the human objects - logging in
        Admin admin = new Admin("123456789");
        User user = new User("Nitzan Avioz", "Nitzanavioz@gmail.com", "123123123");

        // Add the user to the user's list
        users.addUser(user);

        // Review creation
        Review review1 = new Review(user,hotel1 , 5, "Great hotel!", LocalDateTime.now());
        hotel1.addReview(review1);

        // Booking creation
        Booking booking1 = new Booking(user, hotel1, LocalDate.of(2026, Month.AUGUST,15), LocalDate.of(2026, Month.AUGUST,15).plusDays(3));
          // Payment creation for booking
        Payment payment1 = new CreditCardPayment(booking1.getTotalPrice(), LocalDateTime.now(), "1234123412341234", "123", "12/2025");
        booking1.create(booking1, payment1);  // Create also does push to the stack and add to the booking list
        Booking booking2 = new Booking(user, hotel1, LocalDate.of(2026, Month.AUGUST,15), LocalDate.of(2026, Month.AUGUST,15).plusDays(4));
        Payment payment2 = new PaypalPayment(booking2.getTotalPrice(), LocalDateTime.now(), "Nitzan@yahoo.com", "123456789");
        booking2.create(booking2, payment2);

        // == Menu Functionality ==

        // --- User's Perspective ---

        // Run the menu
        menu.printInitialMessage();

        // User's login
         User user1 = User.loginOrRegister(users, new Scanner("212052385\nOfer Avioz\nOferavioz@gmail.com\n"));
         // To show that the user was added to the users list
         users.printUsers();

        // User's menu
        menu.printUserMenu();

        // View Hotel functionality - user's perspective
        System.out.println("\n---------------------View Hotels---------------------\n");
        System.out.println("--Tsimmer order--");
        // South, price filter - 700, rating filter - 3 (not under), amenities filter - 8 = parking, only tsimmers, skip sort, select 1 (Mitzpe Ramon Tsimmer), dont show reviews, book, checkin, nights, pay by paypal
        String script2 = "2\n700\n3\n8\nyes\n0\n1\nno\nyes\n04-09-2025\n4\n2\noferavioz@walla.co.il\n123123123\n";
        System.setIn(new java.io.ByteArrayInputStream(script2.getBytes()));
        new HotelsView(hotelTree, waitList).run(new Scanner(System.in), user1);
        System.out.println("\n--Hotel order--");
        // North, skip price filter, skip rating filter, skip amenities filter, not only tsimmers, sort by price (1), first hotel (The Scots Hotel), show review, book, checkin, nights, pay by credit card
        // There are 2 exceptions thrown here in the payment validation - 1. Invalid card number, 2. Invalid CVV
        // There are also their fixes!
        String script1 = "1\n0\n0\n0\nno\n1\n1\nyes\nyes\n01-08-2026\n3\n1\n123412341234124\n12\n08/2027\n1111222233334444\n12\n123\n";
        System.setIn(new java.io.ByteArrayInputStream(script1.getBytes()));
        new HotelsView(hotelTree, waitList).run(new Scanner(System.in), user1);

        // View upcoming bookings - user's perspective - for user1 who is now the current user in the system
        user1.viewUpcomingBookings();

        // View notifications - user's perspective
        // First, when no notifications exist
        user1.viewNotifications(new Scanner(System.in));

        // == Waitlist Functionality ==
        System.out.println("\n-----Hotel booking to demonstrate waitlist functionality(from view hotels)-----\n");
        String scriptForWaitlist = "1\n0\n0\n0\nno\n0\n1\nno\nyes\n15-08-2026\n3\nno\n";
        System.setIn(new java.io.ByteArrayInputStream(scriptForWaitlist.getBytes()));
        new HotelsView(hotelTree, waitList).run(new Scanner(System.in), user1);

        // Now, the user1 is on a waitlist for the hotel1 (The Scots Hotel) - which is fully booked
        System.out.println("\n--> After some booking are made and user1(ofer) is on a waitlist");
        user1.viewNotifications(new Scanner(System.in));


        // Cancel last booking - user's perspective
        // Now, cancelling one booking for the hotel so that the user can get off the waitlist and book it
        // The booking is canceled by user - Nitzan Avioz
        user.cancelLastBooking(new Scanner("yes\n"));

        // Now, user1 will be able to book the hotel1 (The Scots Hotel) - which is now available
        System.out.println("\n--> User1(ofer) got notified about The Scots Hotel availability and can book it now");
        user1.viewNotifications(new Scanner("yes\n1\n1111222211112222\n111\n09/2026\n"));
        // Again, the user will have no notifications after the booking is made
         user.viewNotifications(new Scanner(System.in));

        // Leave a review - user's perspective
        user1.leaveReview(new Scanner("dan eilat\n5\nThe BEST hotel!\nyes\n"), hotelTree);
        // So we can see that the review was added to the hotel1's review list
        hotel7.printReviewList();

        // Search hotels by name - user's perspective
        hotelTree.searchHotelByName(new Scanner("The Scots hotel\nyes"), hotelTree);

        // --- Admin's Perspective ---
        System.out.println("\n---------------------------------------------------------\n");
        // Admin's login
        menu.printInitialMessage();
        Admin admin1 = Admin.adminLogin(new Scanner("123456789\n"));

        // Add hotel - admin's perspective
        String scriptForAddHotel = "Malkat Shva Eilat\n2\n1\nDerech hayam 1\n500\n3\nwifi,parking\n4\n4\n";
        System.setIn(new java.io.ByteArrayInputStream(scriptForAddHotel.getBytes()));
        admin.addHotelInteractive(new java.util.Scanner(System.in), hotelTree);
        // Now we can see that the hotel was added to the hotelTree
        admin.viewHotelsByRegion(hotelTree, new Scanner("2\n"));

        // Remove hotel - admin's perspective
        admin.removeHotelInteractive(new java.util.Scanner("Mamilla hotel\n3\n1\nyes\n"), hotelTree);
        // Now we can see that the hotel was removed from the hotelTree
        admin.viewHotelsByRegion(hotelTree, new Scanner("3\n"));

        // View all users - admin's perspective, an inner method of the UsersList class - used in the Admin menu
        users.printUsers();

        System.out.println("\n\n====== Thank You For Using Our System <3 ======\n");

        // == General Functionality ==

        // View hotel's availability matrix (by year and month) - May (not booked yet)
        // hotel1.displayAvailabilityMatrix(2025, 5);
        // view hotel's availability matrix after booking the limited number of rooms
        // hotel1.displayAvailabilityMatrix(2025, 8);
        // Now we can see that the availability matrix has been updated after the booking - there are X's in the booked dates
    }
}
