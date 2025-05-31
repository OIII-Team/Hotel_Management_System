package model;

import exceptions.HotelSystemPaymentExceptions;
import structures.BookingQueue;
import structures.HotelTree;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.YearMonth;
import java.util.stream.Collectors;


public class HotelsView {

    private final HotelTree tree;
    private Region selectedRegion;
    private User currentUser;
    private BookingQueue waitlist;

    public HotelsView(HotelTree tree, BookingQueue waitlist) { this.tree = tree;
    this.waitlist = waitlist;}

    /* ---------------- run ---------------- */
    public void run(Scanner sc, User user) {
        this.currentUser = user;
        Region region = chooseRegion(sc);
        if (region == null) return;
        this.selectedRegion = region;

        List<Hotel> view = new ArrayList<>(tree.getHotels(selectedRegion, null));
        if (view.isEmpty()) {
            System.out.println("No hotels/tsimmers in this region.");
            return;
        }

        applyFilters(sc, view);
        System.out.println();
        applySort(sc, view);


        List<Hotel> hotels = view.stream().filter(h -> !(h instanceof Tsimmer)).collect(Collectors.toCollection(ArrayList::new));
        List<Hotel> tsimmers = view.stream().filter(h ->   h instanceof Tsimmer).collect(Collectors.toCollection(ArrayList::new));
        while (true)
        {
        if (view.isEmpty()) {
            System.out.println("No hotels/tsimmers match your criteria.");
            return;
        }
        if (!hotels.isEmpty())
        {
            System.out.println("\n==== Hotels ====");
            printList(hotels, 1);
        }
        if (tsimmers.isEmpty()) {
            System.out.println("No tsimmers match your criteria.");
            return;
        }
        System.out.println("\n==== Tsimmers ====");
        printList(tsimmers, (hotels.size() + 1));

            System.out.println("\nSelect hotel/tsimmer to view its details: ");
            int idx = readInt(sc);
            if (idx < 1 || idx > hotels.size() + tsimmers.size()) break;
            Hotel chosen;
            if (idx <= hotels.size()) {
                chosen = hotels.get(idx - 1);
            } else {
                chosen = tsimmers.get(idx - hotels.size() - 1);
            }
            System.out.print("\n");
            if (chosen instanceof Tsimmer) {
                ((Tsimmer) chosen).printTsimmerDetails();
            } else {
                chosen.printHotelDetails();
            }
            System.out.print("\nWould you like to see hotel's/tsimmer's reviews? (yes/no) ");
            String resp = sc.nextLine().trim();
            if (resp.equalsIgnoreCase("yes"))
            {
                chosen.printReviewList();
                System.out.println();
            } else if (resp.equalsIgnoreCase("no")) {
            } else {
                System.out.println("\nInvalid response. Returning to the options list...");
                return;
            }
            System.out.print("Would you like to book a reservation? (yes/no) ");
            String resp1 = sc.nextLine().trim();
            if (resp1.equalsIgnoreCase("yes"))
            {
                makeBookingFlow(sc, chosen);
                break;
            } else if (resp1.equalsIgnoreCase("no")) {
                System.out.println("Returning to the options list...");
                continue;
            } else {
                System.out.println("Invalid response. Returning to the options list...");
            }
        }
    }

    /* =====================================================
       filters  –  price • rating • amenities • tsimmer
       ===================================================== */
    private void applyFilters(Scanner sc, List<Hotel> list)
    {
        /* ---------- price ---------- */
        double maxPrice;
        while (true) {
            System.out.println("Max price per night (0 = skip): ");
            try {
                maxPrice = Double.parseDouble(sc.nextLine().trim());
                if (maxPrice >= 0) break;
            } catch (NumberFormatException e) {}
            System.out.println("Please enter a non-negative number.");
        }
        if (maxPrice > 0) {
            double finalMaxPrice = maxPrice;
            list.removeIf(h -> h.getPricePerNight() > finalMaxPrice);
        }

        /* ---------- rating ---------- */
        double minRating;
        while (true) {
            System.out.println("Min rating (0–5, 0 = skip): ");
            try {
                minRating = Double.parseDouble(sc.nextLine().trim());
                if (minRating >= 0 && minRating <= 5) break;
            } catch (NumberFormatException e) {}
            System.out.println("Please enter a number between 0 and 5.");
        }
        if (minRating > 0) {
            double finalMinRating = minRating;
            list.removeIf(h -> h.getRating() < finalMinRating);
        }

        /* ---------- amenities ---------- */
        Amenities[] all = Amenities.values();
        Set<Amenities> wanted = new HashSet<>();

        while (true) {
            System.out.println("Select desired amenities (comma-separated numbers, 0 = none):");
            Amenities.printOptions();

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
            if (line.equalsIgnoreCase("yes")) {
                list.removeIf(h -> !(h instanceof Tsimmer));
                break;
            }
            if (line.equalsIgnoreCase("no")) {
                break;
            }
            System.out.println("Invalid response. Please enter 'yes' or 'no': ");
        }
    }

    /* =====================================================
       helper
       ===================================================== */
    private void applySort(Scanner sc, List<Hotel> list) {
        System.out.println("Sort by (1=price↑ 2=price↓ 3=rating↑ 4=rating↓ 0=none): ");
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
            LocalDateTime today = LocalDateTime.now();
            LocalDate in;
            int nights;
            LocalDate out;

            while (true) {
                while (true) {
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    System.out.println();
                    System.out.println("Check-in (dd-MM-yyyy): ");
                    String line = sc.nextLine().trim();
                    try {
                        in = LocalDate.parse(line, fmt);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format or out-of-range. Try again.");
                        continue;
                    }
                    if (in.isBefore(today.toLocalDate())) {
                        System.out.println("Date is in the past. Please choose a future date.");
                        continue;
                    }
                    break;
                }

                System.out.print("Nights: ");
                nights = readInt(sc);
                out = in.plusDays(nights);

                if (!hotel.isRoomAvailable(in, out)) {
                    System.out.println("Sorry, the hotel is full for these dates.");
                    hotel.displayAvailabilityMatrix(in.getYear(), in.getMonthValue());
                    System.out.println("Would you like to pick a different check-in date? (yes to retry / no to join waitlist): ");
                    String resp = sc.nextLine().trim();
                    if (resp.equalsIgnoreCase("yes")) {
                        continue;
                    }
                    if (resp.equalsIgnoreCase("no")) {
                        System.out.println("You have been added to the waitlist.");
                        BookingQueue.BookingRequest req =
                                new BookingQueue.BookingRequest(currentUser, hotel, in, out);
                        waitlist.enqueue(req);
                        currentUser.addWaitlistNotification(req);
                        return;
                    }
                    System.out.println("Booking cancelled.");
                    return;
                }
                break;
            }
            double total = hotel.getPricePerNight() * nights;
            System.out.printf("Amount: ₪%.0f%n", total);
            System.out.println("Select payment method:");
            System.out.println("1) Credit Card");
            System.out.println("2) PayPal");

            Payable payer;
            String cardNum = "";
            String email = "";
            String paymentRef = "";
            while (true) {
                int choice = readInt(sc);
                if (choice == 1) {
                    System.out.println("Card Number (16 digits): ");
                    cardNum = sc.nextLine().trim();
                    System.out.println("CVV (3 digits): ");
                    String cvv = sc.nextLine().trim();
                    System.out.println("Expiry (MM/yyyy): ");
                    String expStr = sc.nextLine().trim();

                   payer = new CreditCardPayment(total, today, cardNum, cvv, expStr);
                   break;
                }
                if (choice == 2) {
                    System.out.println("PayPal Email: ");
                    email = sc.nextLine().trim();
                    System.out.println("PayPal Account ID for confirmation: ");
                    String id = sc.nextLine().trim();
                    payer = new PaypalPayment(total, today, email, id);
                    break;
                }
                System.out.println("Invalid choice — please select 1 or 2.");
            }

            if (!payer.processPayment(total, sc)) {
                System.out.println("Payment processing failed. Booking cancelled.");
                return;
            }


            Booking booking = new Booking(currentUser, hotel, in, out);
            booking.create(booking, payer);

            if (booking == null) {
                System.out.println("Payment failed. Reservation not created.");
                return;
            }
            System.out.println("\nBooking confirmed.");
            if (payer instanceof CreditCardPayment) {
                paymentRef = ((CreditCardPayment) payer).getCardNumber().length() > 4 ?
                        ((CreditCardPayment) payer).getCardNumber().
                                substring(((CreditCardPayment) payer).getCardNumber().length() - 4) : cardNum;
                System.out.println("Credit card ends with: XXXX-XXXX-XXXX-" + paymentRef);
            } else if (payer instanceof PaypalPayment) {
                int at = ((PaypalPayment) payer).getPayerEmail().indexOf('@');
                paymentRef = ((PaypalPayment) payer).getPayerEmail().substring(0, Math.min(at,4));
                String domain = ((PaypalPayment) payer).getPayerEmail().substring(((PaypalPayment) payer).getPayerEmail().indexOf('@'));
                System.out.println("PayPal account ref : " + paymentRef + "XX@" + domain);
            }
            System.out.println("Thank you! See you soon :)\n");

            booking.printBookingDetails();

        } catch (HotelSystemPaymentExceptions ex) {
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
    public static void printList(List<Hotel> list, int startIndex) {

        String currentCity = "";
        int i = startIndex;

        for (Hotel h : list) {
            String cityName = h.getLocation().getCity().getDisplayName();

            if (!cityName.equals(currentCity)) {
                currentCity = cityName;
                System.out.println("--- " + currentCity + " ---");
            }

            System.out.printf("%d) %s | ₪%.0f | %.1f★%n",
                    i++, h.getName(), h.getPricePerNight(), h.getRating());
        }
    }

    private int readInt(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a whole number: ");
            }
        }
    }
}