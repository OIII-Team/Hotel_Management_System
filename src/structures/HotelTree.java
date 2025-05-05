package structures;

import java.util.Scanner;
import model.Hotel;
import model.Region;
import model.Amenities;
import model.Location;
import java.util.ArrayList;
import java.util.List;

public class HotelTree {
    private List<Hotel> northHotels;
    private List<Hotel> southHotels;
    private List<Hotel> jerusalemHotels;
    private List<Hotel> centerHotels;

    public HotelTree() {
        northHotels = new ArrayList<>();
        southHotels = new ArrayList<>();
        jerusalemHotels = new ArrayList<>();
        centerHotels = new ArrayList<>();

        addHotel(new Hotel(
                "Galilee Resort",
                Region.NORTH,
                new Location(Region.NORTH, "Tiberias", "Sea Road 1"),
                450.0,
                new Amenities[]{Amenities.POOL, Amenities.WIFI},
                20,
                3,
                null,
                4.5,
                new BookingList()
        ));
    }

    public void addHotel(Hotel hotel) {
        if (hotel == null || hotel.getRegion() == null) return;
        switch (hotel.getRegion()) {
            case NORTH:
                northHotels.add(hotel);
                break;
            case SOUTH:
                southHotels.add(hotel);
                break;
            case JERUSALEM:
                jerusalemHotels.add(hotel);
                break;
            case CENTER:
                centerHotels.add(hotel);
                break;
        }
    }

    public boolean removeHotel(String hotelName) {
        return removeFromList(northHotels, hotelName)
                || removeFromList(southHotels, hotelName)
                || removeFromList(jerusalemHotels, hotelName)
                || removeFromList(centerHotels, hotelName);
    }

    private boolean removeFromList(List<Hotel> list, String name) {
        return list.removeIf(h -> h.getName().equalsIgnoreCase(name));
    }

    public List<Hotel> getHotelsByRegion(Region region) {
        switch (region) {
            case NORTH:
                return northHotels;
            case SOUTH:
                return southHotels;
            case JERUSALEM:
                return jerusalemHotels;
            case CENTER:
                return centerHotels;
            default:
                return new ArrayList<>();
        }
    }

    public Hotel findHotelByName(String name) {
        for (Hotel h : northHotels) {
            if (h.getName().equalsIgnoreCase(name)) return h;
        }
        for (Hotel h : southHotels) {
            if (h.getName().equalsIgnoreCase(name)) return h;
        }
        for (Hotel h : jerusalemHotels) {
            if (h.getName().equalsIgnoreCase(name)) return h;
        }
        for (Hotel h : centerHotels) {
            if (h.getName().equalsIgnoreCase(name)) return h;
        }
        return null;
    }

    public void printTree() {
        System.out.println("=== Hotel Tree ===");
        printList("North", northHotels);
        printList("South", southHotels);
        printList("Jerusalem", jerusalemHotels);
        printList("Center", centerHotels);
    }

    private void printList(String regionName, List<Hotel> list) {
        System.out.println("Region: " + regionName);
        if (list.isEmpty()) {
            System.out.println("  (No hotels)");
        } else {
            for (Hotel h : list) {
                System.out.println("  - " + h.getName());
            }
        }
    }

    public boolean isEmpty() {
        return northHotels.isEmpty()
                && southHotels.isEmpty()
                && jerusalemHotels.isEmpty()
                && centerHotels.isEmpty();
    }

    public void printHotelsByRegion(Region region) {
        if (region == null) {
            System.out.println("Invalid region.");
            return;
        }

        List<Hotel> hotels = getHotelsByRegion(region);
        System.out.println("Hotels in " + region.getDisplayName() + ":");

        if (hotels.isEmpty()) {
            System.out.println("  (No hotels)");
        } else {
            for (Hotel h : hotels) {
                System.out.println("  - " + h.getName());
            }
        }
    }
}