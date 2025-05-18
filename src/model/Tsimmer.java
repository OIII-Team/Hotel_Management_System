package model;

import structures.BookingList;
import structures.HotelTree;
import structures.ReviewList;

public class Tsimmer extends Hotel
{
    private boolean isFamilyFriendly;
    private boolean isAllowPets;
    public String viewType;

    public Tsimmer(String name, Region region, Location location, double pricePerNight, Amenities[] amenities, int totalRooms,
                   int maxCapacity, double rating, int[][] availability, BookingList bookings, HotelTree tree,
                   ReviewList reviewList, boolean isFamilyFriendly, boolean isAllowPets, String viewType) {
        super(name, region, location, pricePerNight, amenities, totalRooms, maxCapacity, rating,availability, bookings, tree,reviewList);
        this.isFamilyFriendly = isFamilyFriendly;
        this.isAllowPets = isAllowPets;
        this.viewType = viewType;//enum
    }

    public boolean isFamilyFriendly() {
        return isFamilyFriendly;
    }
    public void setFamilyFriendly(boolean isFamilyFriendly) {
        this.isFamilyFriendly = isFamilyFriendly;
    }
    public boolean isAllowPets() {
        return isAllowPets;
    }
    public void setAllowPets(boolean isAllowPets) {
        this.isAllowPets = isAllowPets;
    }
    public String getViewType()
    {
        return viewType;
    }

    public void printTsimmerDetails() {
        super.printHotelDetails();
        System.out.println("Family Friendly: " + (isFamilyFriendly ? "Yes" : "No"));
        System.out.println("Allows Pets: " + (isAllowPets ? "Yes" : "No"));
        System.out.println("View Type: " + viewType);
    }







}
