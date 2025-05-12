package model;

import structures.BookingList;
import structures.ReviewList;

public class Tsimmer extends Hotel
{
    private boolean isFamilyFriendly;
    private boolean isAllowPets;
    public String viewType;

    public Tsimmer(String name, Region region, Location location, double pricePerNight, Amenities[] amenities, int totalRooms, int maxCapacity,
                   ReviewList review, double rating, BookingList bookings, boolean isFamilyFriendly, boolean isAllowPets, String viewType) {
        super(name, region, location, pricePerNight, amenities, totalRooms, maxCapacity, review, rating, bookings);
        this.isFamilyFriendly = isFamilyFriendly;
        this.isAllowPets = isAllowPets;
        this.viewType = viewType;//enum
    }


}
