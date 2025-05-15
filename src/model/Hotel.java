package model;
import structures.BookingList;
import structures.HotelTree;
import structures.ReviewList;
import java.time.LocalDate;


public class Hotel
{
    private String name;
    public Region region;
    private double pricePerNight;
    public Amenities[] amenities;
    public Location location;
    private int totalRooms;
    private int maxCapacity; //Each room
    private double rating;
    private BookingList bookings;
    private HotelTree tree;
    private ReviewList reviewList;


    public Hotel(String name, Region region, Location location, double pricePerNight,
                 Amenities[] amenities, int totalRooms, int maxCapacity, double rating, BookingList bookings, HotelTree tree, ReviewList reviewList){
        this.name = name;
        this.region = region;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.amenities = amenities;
        this.totalRooms = totalRooms;
        this.maxCapacity = maxCapacity;
        this.rating = rating;
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

    public void addHotelToTree(Hotel hotel)
    {
        if (tree == null) {
            tree = new HotelTree();
        }
        tree.addHotel(hotel);
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
            boolean overlap = !b.getCheckOut().isBefore(checkIn) && !checkOut.isBefore(b.getCheckIn());

            if (overlap) {
                overlapping++;
                if (overlapping > totalRooms)
                    return false;
            }
        }
        return true;
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
