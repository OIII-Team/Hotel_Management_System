package model;
import java.util.List;
import java.util.Scanner;

import structures.HotelTree;

public class HotelsView
{
    private HotelTree hotelTree;

    public HotelsView(HotelTree hotelTree)
    {
        this.hotelTree = hotelTree;
    }
    public void displayHotels()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a region to view hotels:");
        Region.printRegionOptions();
        int regionChoice   = scanner.nextInt();
        scanner.nextLine();
        Region selectedRegion = Region.getRegionFromChoice(regionChoice);
        if (selectedRegion == null) {
            System.out.println("Invalid region choice.");
            return;
        }

        System.out.println("Select a city to view hotels:");
        City.printCityOptions(selectedRegion);
        int cityChoice     = scanner.nextInt();
        scanner.nextLine();
        City selectedCity  = City.getCityFromChoice(selectedRegion, cityChoice);
        if (selectedCity == null) {
            System.out.println("Invalid city choice.");
            return;
        }
        List<Hotel> hotels = hotelTree.getHotels(selectedRegion, selectedCity);
        System.out.println("\nHotels in " + selectedCity.getDisplayName() + ":");

        if (hotels.isEmpty())
        {
            System.out.println("No hotels found ):");
        } else
        {
            for (Hotel h : hotels)
                System.out.println("  - " + h.getName());
        }
    }
}
