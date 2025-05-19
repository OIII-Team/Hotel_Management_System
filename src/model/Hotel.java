package model;
import structures.BookingList;
import structures.HotelTree;
import structures.ReviewList;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Scanner;


public class Hotel
{
    private String name;
    protected Region region;
    private double pricePerNight;
    protected Amenities[] amenities;
    protected Location location;
    private int totalRooms;
    private int maxCapacity; //Each room
    private double rating;
    private BookingList bookings;
    private HotelTree tree;
    private ReviewList reviewList;
    private int[][] availability;


    public Hotel(String name, Region region, Location location, double pricePerNight,
                 Amenities[] amenities, int totalRooms, int maxCapacity, double rating, int[][] availability,
                 BookingList bookings, HotelTree tree, ReviewList reviewList){
        this.name = name;
        this.region = region;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.amenities = amenities;
        this.totalRooms = totalRooms;
        this.maxCapacity = maxCapacity;
        this.rating = rating;
        this.availability = createDefaultAvailabilityMatrix(totalRooms);
        this.bookings = (bookings != null) ? bookings : new BookingList();
        this.tree = (tree != null) ? tree : new HotelTree();
        this.reviewList = (reviewList != null) ? reviewList : new ReviewList();
    }

    public Hotel() {
        // Default constructor
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setRegion(Region region) {
        if (region != null) {
            this.region = region;
        } else {
            System.out.println("Invalid region.");
        }
    }

    public Region getRegion()
    {
        return region;
    }

    public void setPricePerNight(double pricePerNight)
    {
        if(pricePerNight > 0)
        {
            this.pricePerNight = pricePerNight;
        }
        else
        {
            System.out.println("Price per night must be greater than 0.");
        }
    }

    public double getPricePerNight()
    {
        return pricePerNight;
    }

    public void setTotalRooms(int totalRooms)
    {
        if(totalRooms > 0)
        {
            this.totalRooms = totalRooms;
        }
        else
        {
            System.out.println("Total rooms must be greater than 0.");
        }
    }

    public int getTotalRooms()
    {
        return totalRooms;
    }

    public void setMaxCapacity(int maxCapacity)
    {
        if(maxCapacity > 0)
        {
            this.maxCapacity = maxCapacity;
        }
        else
        {
            System.out.println("Max capacity must be greater than 0.");
        }
    }

    public int getMaxCapacity()
    {
        return maxCapacity;
    }

    public ReviewList getReview(){
        return reviewList;
    }
    public void setReview(ReviewList reviewList)
    {
        this.reviewList = reviewList;
    }

    public double getRating()
    {
        return rating;
    }

    public void setRating(double rating)
    {
        if(rating >= 0 && rating <= 5)
        {
            this.rating = rating;
        }
        else
        {
            System.out.println("Rating must be between 0 and 5.");
        }
    }

    public BookingList getBookings()
    {
        return bookings;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }
    public Location getLocation()
    {
        return location;
    }

    public Amenities[] getAmenities()
    {
        return amenities;
    }

    public void printAmenities() {
        if (amenities == null || amenities.length == 0) {
            System.out.println("No amenities listed.");
        } else {
            for (Amenities a : amenities) {
                System.out.println(" - " + a);
            }
        }
    }

    public void addBooking(Booking booking)
    {
        bookings.addBooking(booking);
    }

    public void removeBooking(Booking booking)
    {
        bookings.removeBooking(booking);
    }

    public boolean isRoomAvailable(LocalDate checkIn, LocalDate checkOut)
    {
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn))
            throw new IllegalArgumentException("checkOut must be after checkIn");

        int overlapping = 0;

        for (Booking b : bookings.asList()) {
            boolean overlap = checkIn.isBefore(b.getCheckOut())
                    && b.getCheckIn().isBefore(checkOut);

            if (overlap) {
                overlapping++;
                if (overlapping >= totalRooms)
                    return false;
            }
        }
        return true;
    }

    public static int[][] createDefaultAvailabilityMatrix(int totalRooms) {
        int year = LocalDate.now().getYear();
        int[][] mat = new int[12][];
        for (int m = 1; m <= 12; m++) {
            int days = YearMonth.of(year, m).lengthOfMonth();
            mat[m-1] = new int[days];
            Arrays.fill(mat[m-1], totalRooms);
        }
        return mat;
    }

    public boolean isDateAvailable(LocalDate date) {
        int m = date.getMonthValue()-1, d = date.getDayOfMonth()-1;
        return availability[m][d] > 0;
    }


    public void setDateAvailability(LocalDate date, boolean available) {
        int m = date.getMonthValue() - 1;
        int d = date.getDayOfMonth()   - 1;
        if (m < 0 || m >= availability.length) return;
        if (d < 0 || d >= availability[m].length)   return;
        availability[m][d] = available ? totalRooms : 0;
    }

    public void updateMatrixForBooking(LocalDate checkIn, LocalDate checkOut) {
        LocalDate d = checkIn;
        while (d.isBefore(checkOut)) {
            int m = d.getMonthValue()-1, day = d.getDayOfMonth()-1;
            availability[m][day]--;
            d = d.plusDays(1);
        }
    }

    public void updateMatrixForCancellation(LocalDate checkIn, LocalDate checkOut) {
        LocalDate d = checkIn;
        while (d.isBefore(checkOut)) {
            int m = d.getMonthValue()-1, day = d.getDayOfMonth()-1;
            availability[m][day]++;
            d = d.plusDays(1);
        }
    }

    public void displayAvailabilityMatrix(int year, int month) {
        String[] months = { "Jan","Feb","Mar","Apr","May","Jun",
                "Jul","Aug","Sep","Oct","Nov","Dec" };
        int mIndex = month - 1;
        int[] days = availability[mIndex];
        int totalDays = days.length;
        int rows = 4;
        int perRow = (int) Math.ceil(totalDays / (double) rows);

        System.out.printf("\nAvailability for %s %d:%n%n", months[mIndex], year);

        for (int r = 0; r < rows; r++) {
            int start = r * perRow;
            if (start >= totalDays) break;
            int end = Math.min(start + perRow, totalDays);

            System.out.print("Day      ");
            for (int d = start + 1; d <= end; d++) {
                System.out.printf("%3d", d);
            }
            System.out.println();

            System.out.print("Status  ");
            for (int i = start; i < end; i++) {
                System.out.printf("%3s", days[i] > 0 ? "✓" : "✗");
            }
            System.out.println("");
        }
    }


    public void printHotelDetails()
    {
        System.out.println("Hotel Name: " + name);
        System.out.println("Region: " + region);
        System.out.println("Location: " + location.getCity() + ", " + location.getAddress());
        System.out.println("Price per Night: " + getPricePerNight() + "₪");
        System.out.println("Total Rooms: " + totalRooms);
        System.out.println("Max Capacity: " + maxCapacity);
        System.out.println("Rating: " + rating);
        System.out.println("Amenities: ");
        printAmenities();
    }


    public void addReview(Review review)
    {
        if (this.reviewList == null) {
            this.reviewList = new ReviewList();
        }
        reviewList.addReview(review);
    }

    public ReviewList getReviewList()
    {
        return reviewList;
    }

    public void printReviewList()
    {
        if (reviewList == null || reviewList.isEmpty()) {
            System.out.println("No reviews available.");
        } else {
            for (Review r : reviewList.asList()) {
                r.printReview();
            }
        }
    }

}
