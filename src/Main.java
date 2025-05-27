import structures.*;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
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
        User user = new User("ofer avioz", "oferavioz@gmail.com", "212052385");

        // Add the user to the user's list
        users.addUser(user);

        // Review creation
        Review review1 = new Review(user,hotel1 , 5, "Great hotel!", LocalDateTime.now());
        hotel1.addReview(review1);

        // Booking creation
        Booking booking1 = new Booking(user, hotel1, LocalDate.now(), LocalDate.now().plusDays(3));
          // Payment creation for booking
        Payment payment1 = new CreditCardPayment(booking1.getTotalPrice(), LocalDateTime.now(), "1234123412341234", "123", "12/2025");
        booking1.create(booking1, payment1);  // Create also does push to the stack and add to the booking list

        // == Menu Functionality ==

        // View Hotel functionality - user's perspective
        //new HotelsView(hotelTree, waitList).run(new Scanner(System.in), user);

        // Search hotels by name - user's perspective
        //hotelTree.searchHotelByName(new Scanner("The Scots hotel\nyes"), hotelTree);

        // Cancel last booking - user's perspective
        //user.cancelLastBooking(new Scanner("yes"));

        // View upcoming bookings - user's perspective
        //user.viewUpcomingBookings();

        // Leave a review - user's perspective
        //user.leaveReview(new Scanner(System.in), hotelTree);

        // View notifications - user's perspective
        //user.viewNotifications(new Scanner(System.in));

        // Add hotel - admin's perspective
        //admin.addHotelInteractive(new java.util.Scanner(System.in), hotelTree);

        // Remove hotel - admin's perspective
        //admin.removeHotelInteractive(new java.util.Scanner(System.in), hotelTree);

        // View all users - admin's perspective, an inner method of the UsersList class - used in the Admin menu
        //users.printUsers();

        // View Hotels by region - admin's perspective
        //admin.viewHotelsByRegion(hotelTree);

        // == General Functionality ==

        // View hotel's availability matrix (by year and month)
        //hotel1.displayAvailabilityMatrix(2025, 5);

        // view hotel's availability matrix after booking the limited number of rooms
        //Booking booking2 = new Booking(user, hotel1, LocalDate.now(), LocalDate.now().plusDays(3));
        //booking2.create(booking2, payment1);
        //hotel1.displayAvailabilityMatrix(2025, 5);
        // Now we can see that the availability matrix has been updated after the booking - there are X's in the booked dates


        menu.run();
    }
}
