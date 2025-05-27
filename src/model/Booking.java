package model;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


public class Booking
{
    protected User user;
    protected Hotel hotel;
    protected LocalDate checkIn;
    protected LocalDate checkOut;
    private double totalPrice;
    protected Payable payer;

    public Booking(User user, Hotel hotel, LocalDate checkIn, LocalDate checkOut)
    {
        LocalDate today = LocalDate.now();
        Objects.requireNonNull(user);
        Objects.requireNonNull(hotel);
        if (!hotel.isRoomAvailable(checkIn, checkOut))
            throw new IllegalArgumentException("No rooms available in " + hotel.getName());
        if (checkIn.isBefore(today))
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        if (!checkOut.isAfter(checkIn))
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        this.user = user;
        this.hotel = hotel;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        this.totalPrice = hotel.getPricePerNight() * nights;
    }

    public Booking create(Booking booking, Payable payer)
    {
        LocalDate checkIn = booking.checkIn;
        LocalDate checkOut = booking.checkOut;
        Hotel hotel = booking.hotel;
        User user = booking.user;

        Objects.requireNonNull(user);
        Objects.requireNonNull(hotel);

        if (!checkOut.isAfter(booking.checkIn) || checkIn.isBefore(LocalDate.now())) {
            System.out.println("Invalid date range.");
            return null;
        }

        if (!hotel.isRoomAvailable(checkIn, checkOut)) {
            System.out.println("No rooms available in " + hotel.getName());
            return null;
        }

        Booking b = new Booking(user, hotel, checkIn, checkOut);

        double total = b.getTotalPrice();
        double fee = payer.calculateFee(total);
        double baseAmount = total - fee;

        boolean success = payer.processPayment(user, baseAmount);
        if (!success)
        {
            b.cancelBooking();
            return null;
        }
        booking.payer = payer;
        hotel.updateMatrixForBooking(checkIn, checkOut);
        hotel.addBooking(b);
        user.addBooking(b);

        return b;
    }


    public LocalDate getCheckIn()
    {
        return checkIn;
    }

    public LocalDate getCheckOut()
    {
        return checkOut;
    }

    public long getNights()
    {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public double getTotalPrice()
    {
        return hotel.getPricePerNight() * getNights();
    }

    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = hotel.getPricePerNight() * (checkOut.toEpochDay() - checkIn.toEpochDay());
    }

    public Hotel getHotel()
    {
        return hotel;
    }

    public User getUser()
    {
        return user;
    }

    public Payable getPayer()
    {
        return payer;
    }

    public void printBookingDetails()
    {
        System.out.println("Booking Details:");
        System.out.println("Hotel: " + hotel.getName());
        System.out.println("Region: " + hotel.getRegion());
        System.out.println("City: " + hotel.location.getCity());
        System.out.println("Check-in: " + checkIn);
        System.out.println("Check-out: " + checkOut);
        System.out.println("Nights: " + getNights());
        System.out.println(getPayer());
    }

    public void cancelBooking()
    {
        hotel.removeBooking(this);
    }

    //Mainly For upcoming bookings method in User and more
    public String toString() {
        return String.format("%s | %s → %s | ₪%.0f",
                hotel.getName(), checkIn, checkOut, totalPrice);
    }
}