package structures;

import model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HotelTree
{
    private final List<RegionNode> regions = new ArrayList<>();

    public void addHotel(Hotel hotel)
    {
        if (hotel == null || hotel.getRegion() == null) return;
        City city = cityOf(hotel);
        if (city == null) return;

        RegionNode rNode = regionNode(hotel.getRegion(), true);
        CityNode cNode = cityNode(rNode, city, true);
        cNode.hotels.add(hotel);
    }

    public HotelTree()
    {
        addHotel(new Hotel("Galilee Resort",
                Region.NORTH,
                new Location(Region.NORTH, City.TIBERIAS, "Sea Road 1"),
                450.0,
                new Amenities[]{Amenities.POOL, Amenities.WIFI},
                20,
                3,
                null,
                4.5,
                new BookingList()));
    }

    public boolean removeHotel(Region region, City city, String hotelName)
    {
        RegionNode rNode = regionNode(region, false);
        if (rNode == null) return false;

        CityNode cNode = cityNode(rNode, city, false);
        if (cNode == null) return false;

        boolean removed = cNode.hotels.removeIf(h -> h.getName().equalsIgnoreCase(hotelName));

        if (cNode.hotels.isEmpty()) rNode.cities.remove(cNode);
        if (rNode.cities.isEmpty()) regions.remove(rNode);

        return removed;
    }

    public List<Hotel> getHotels(Region region, City city)
    {
        RegionNode rNode = regionNode(region, false);
        if (rNode == null)
        {
            System.out.println("No hotels found in " + region.getDisplayName());
            return List.of();
        }

        if (city == null)
        {
            List<Hotel> all = new ArrayList<>();
            for (CityNode c : rNode.cities)
                all.addAll(c.hotels);
            return all;
        }
        CityNode cNode = cityNode(rNode, city, false);
        return (cNode == null) ? List.of() : new ArrayList<>(cNode.hotels);
    }


    public Hotel findHotel(String hotelName) {
        for (RegionNode r : regions)
            for (CityNode c : r.cities)
                for (Hotel h : c.hotels)
                    if (h.getName().equalsIgnoreCase(hotelName))
                        return h;
        return null;
    }

    public boolean isEmpty() { return regions.isEmpty(); }

    public void printTree() {
        if (regions.isEmpty()) { System.out.println("(empty)"); return; }

        System.out.println("=== Hotel Tree ===");
        for (RegionNode r : regions) {
            System.out.println("Region: " + r.region.getDisplayName());
            for (CityNode c : r.cities) {
                System.out.println("  City: " + c.city);
                for (Hotel h : c.hotels)
                    System.out.println("    - " + h.getName());
            }
        }
    }

    /// Helper classes
    private static class RegionNode {
        final Region region;
        final List<CityNode> cities = new ArrayList<>();
        RegionNode(Region region) { this.region = region; }
    }

    private static class CityNode {
        final City city;
        final List<Hotel> hotels = new ArrayList<>();
        CityNode(City city) { this.city = city; }
    }

    private RegionNode regionNode(Region region, boolean create) {
        for (RegionNode r : regions)
            if (r.region == region)
                return r;
        if (create) {
            RegionNode r = new RegionNode(region);
            regions.add(r);
            return r;
        }
        return null;
    }

    private CityNode cityNode(RegionNode rNode, City city, boolean create) {
        if (rNode == null) return null;
        for (CityNode c : rNode.cities)
            if (c.city == city)
                return c;
        if (create) {
            CityNode c = new CityNode(city);
            rNode.cities.add(c);
            return c;
        }
        return null;
    }

    private static City cityOf(Hotel h) {
        Location loc = h.getLocation();
        return (loc == null) ? null : loc.getCity();
    }

    public void printCities(Region region) {
        RegionNode rNode = regionNode(region, false);
        if (rNode == null) {
            System.out.println("No cities found for region: " + region.getDisplayName());
            return;
        }

        List<CityNode> sorted = new ArrayList<>(rNode.cities);
        sorted.sort(Comparator.comparing(c -> c.city.getDisplayName()));

        System.out.println("Cities in " + region.getDisplayName() + ":");
        for (int i = 0; i < sorted.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + sorted.get(i).city);
        }
    }

    public void printHotelsByCity(Region region, int city) {

        City cityEnum = City.getCityFromChoice(region, city);
        if (cityEnum == null) {
            System.out.println("Invalid city choice.");
            return;
        }
        City cityName = cityEnum;
        CityNode cNode = cityNode(regionNode(region, false),cityName, false);
        if (cNode == null) {
            System.out.println("No hotels found in " + city + ", " + region.getDisplayName());
            return;
        }
        System.out.println("Hotels in " + cityEnum + ", " + region.getDisplayName() + ":");
        for (Hotel h : cNode.hotels)
            System.out.println("  - " + h.getName());
    }
}