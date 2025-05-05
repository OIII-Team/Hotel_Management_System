package model;
import structures.BookingList;
import structures.ReviewList;

import java.util.List;

public class Hotel
{
    private String name;
    public Region region;
    private double pricePerNight;
    public Amenities[] amenities;
    public Location location;
    private int totalRooms;
    private int maxCapacity; //Each room
    private ReviewList review;
    private double rating;
    private BookingList bookings;

    public Hotel(String name, Region region, Location location, double pricePerNight,
                 Amenities[] amenities, int totalRooms, int maxCapacity,
                 ReviewList review, double rating, BookingList bookings) {
        this.name = name;
        this.region = region;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.amenities = amenities;
        this.totalRooms = totalRooms;
        this.maxCapacity = maxCapacity;
        this.review = review;
        this.rating = rating;
        this.bookings = bookings;
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
        return review;
    }
    public void setReview(ReviewList review)
    {
        this.review = review;
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

}
