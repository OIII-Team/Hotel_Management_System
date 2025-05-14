package model;

import structures.HotelTree;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.YearMonth;

public class HotelsView {

    private final HotelTree tree;
    private Region selectedRegion;
    private User currentUser;

    public HotelsView(HotelTree tree) { this.tree = tree; }

    /* ---------------- run ---------------- */
    public void run(Scanner sc, User user) {
        this.currentUser = user;

        Region region = chooseRegion(sc);
        if (region == null) return;
        this.selectedRegion = region;

        List<Hotel> view = new ArrayList<>(tree.getHotels(selectedRegion, null));
        if (view.isEmpty()) {
            System.out.println("No hotels in this region.");
            return;
        }

        applyFilters(sc, view);
        applySort(sc, view);

        if (view.isEmpty()) {
            System.out.println("No hotels match your criteria.");
            return;
        }
        printList(view);
        while (true)
        {
            System.out.println("\n Select hotel to view its details: ");
            int idx = readInt(sc);
            if (idx < 1 || idx > view.size()) break;
            Hotel hotel = view.get(idx - 1);
            hotel.printHotelDetails();
            System.out.println("Would you like to see hotel's reviews? (yes/no) ");
            String resp = sc.nextLine().trim();
            if (resp.equalsIgnoreCase("yes"))
            {
                hotel.printReviewList();
            } else if (resp.equalsIgnoreCase("no")) {
                System.out.println("Returning to hotel list...");
                continue;
            } else {
                System.out.println("Invalid response. Returning to hotel list...");
            }
            System.out.print("\nWould you like to book this hotel? (yes/no) ");
            String resp1 = sc.nextLine().trim();
            if (resp1.equalsIgnoreCase("yes"))
            {
                makeBookingFlow(sc, hotel);
                break;
            } else if (resp1.equalsIgnoreCase("no")) {
                System.out.println("Returning to hotel list...");
                printList(view);
                continue;
            } else {
                System.out.println("Invalid response. Returning to hotel list...");
            }
        }
    }

    /* =====================================================
       filters  –  price • rating • amenities • tsimmer
       ===================================================== */
    private void applyFilters(Scanner sc, List<Hotel> list)
    {
        /* ---------- price ---------- */
        System.out.print("Max price per night (0 = skip): ");
        double maxPrice = sc.nextDouble();
        sc.nextLine();
        if (maxPrice > 0)
            list.removeIf(h -> h.getPricePerNight() > maxPrice);

        /* ---------- rating ---------- */
        System.out.print("Min rating (0-5, 0 = skip): ");
        double minRating = sc.nextDouble();
        sc.nextLine();
        if (minRating > 0)
            list.removeIf(h -> h.getRating() < minRating);

        /* ---------- amenities ---------- */
        Amenities[] all = Amenities.values();
        Set<Amenities> wanted = new HashSet<>();

        while (true) {
            System.out.println("Select desired amenities (comma-separated numbers, 0 = none):");
            for (int i = 0; i < all.length; i++) {
                System.out.printf("%d. %s%n", i + 1, all[i]);
            }

            String line = sc.nextLine().trim();
            if (line.equals("0") || line.isBlank()) {
                break;
            }

            String[] tokens = line.split(",");
            boolean valid = true;
            wanted.clear();

            for (String tok : tokens) {
                tok = tok.trim();
                try {
                    int idx = Integer.parseInt(tok) - 1;
                    if (idx < 0 || idx >= all.length) {
                        valid = false;
                        break;
                    }
                    wanted.add(all[idx]);
                } catch (NumberFormatException e) {
                    valid = false;
                    break;
                }
            }

            if (!valid) {
                System.out.println("Invalid input. Please enter numbers between 1 and "
                        + all.length + " separated by commas, or 0 for none.");
                continue;
            }

            list.removeIf(h -> {
                Amenities[] ams = h.getAmenities();
                return ams == null || !Set.of(ams).containsAll(wanted);
            });
            break;
        }

        /* ---------- Tsimmer ---------- */
        System.out.print("Only Tsimmer (cabin)? (yes/no) ");
        while(true)
        {
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("yes") || line.equalsIgnoreCase("no"))
                break;
            System.out.print("Invalid response. Please enter 'yes' or 'no': ");
        }
    }

    /* =====================================================
       helper
       ===================================================== */
    private void applySort(Scanner sc, List<Hotel> list) {
        System.out.print("Sort by (1=price↑ 2=price↓ 3=rating↑ 4=rating↓ 0=none): ");
        switch (readInt(sc)) {
            case 1 -> list.sort(Comparator.comparingDouble(Hotel::getPricePerNight));
            case 2 -> list.sort(Comparator.comparingDouble(Hotel::getPricePerNight).reversed());
            case 3 -> list.sort(Comparator.comparingDouble(Hotel::getRating));
            case 4 -> list.sort(Comparator.comparingDouble(Hotel::getRating).reversed());
        }
    }

    /* =====================================================
       helper
       ===================================================== */
    private void makeBookingFlow(Scanner sc, Hotel hotel) {
        try {
            LocalDate today = LocalDate.now();
            LocalDate in;
            while (true) {
                System.out.print("Check-in (yyyy-MM-dd): ");
                String line = sc.nextLine().trim();
                try {
                    in = LocalDate.parse(line);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format or out‐of‐range values. Try again: ");
                    continue;
                }
                if (in.isBefore(today)) {
                    System.out.println("Date is in the past. Please choose a future date.");
                    continue;
                }
                break;
            }
            System.out.print("Nights: ");
            int nights = readInt(sc);
            LocalDate out = in.plusDays(nights);

            double total = hotel.getPricePerNight() * nights;
            System.out.printf("Amount: ₪%.0f%n", total);
            System.out.println("Select payment method:");
            System.out.println("1) Credit Card");
            System.out.println("2) PayPal");

            Payable payer;
            while (true) {
                int choice = readInt(sc);
                if (choice == 1) {
                    System.out.print("Card Number (16 digits): ");
                    String cardNum = sc.nextLine().trim();
                    System.out.print("CVV (3 digits): ");
                    String cvv = sc.nextLine().trim();
                    System.out.print("Expiry (MM/yyyy): ");
                    String[] parts = sc.nextLine().split("/");
                    YearMonth exp = YearMonth.of(
                            Integer.parseInt(parts[1].trim()),
                            Integer.parseInt(parts[0].trim()));
                    payer = new CreditCardPayment(total,today, cardNum, cvv, exp);
                    break;
                }
                if (choice == 2) {
                    System.out.print("PayPal Email: ");
                    String email = sc.nextLine().trim();
                    System.out.print("PayPal Account ID for confirmation: ");
                    String id = sc.nextLine().trim();
                    payer = new PaypalPayment(total,today, email, id);
                    break;
                }
                System.out.println("Invalid choice — please select 1 or 2.");
            }
            Booking booking = Booking.create(currentUser, hotel, in, out, payer);
            System.out.println("Booking confirmed. Thank you!\n");

            booking.printBookingDetails();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /* =====================================================
       utility
       ===================================================== */
    private Region chooseRegion(Scanner sc) {
        System.out.println("Select region:");
        Region.printRegionOptions();
        return Region.getRegionFromChoice(readInt(sc));
    }

    /* ------------- printList -------------- */
    private void printList(List<Hotel> list) {
        list.sort(Comparator.comparing((Hotel h) -> h.getLocation().getCity().getDisplayName()).thenComparing(Hotel::getName));

        String currentCity = "";
        int i = 1;

        for (Hotel h : list) {
            String cityName = h.getLocation().getCity().getDisplayName();

            if (!cityName.equals(currentCity)) {
                currentCity = cityName;
                System.out.println("\n--- " + currentCity + " ---");
            }

            System.out.printf("%d) %s | ₪%.0f | %.1f★%n",
                    i++, h.getName(), h.getPricePerNight(), h.getRating());
        }
    }

    private int readInt(Scanner sc) {
        int v = sc.nextInt(); sc.nextLine();
        return v;
    }
}