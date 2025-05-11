package model;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import exceptions.HotelSystemExceptions;

public class Booking
{
    public User user;
    public Hotel hotel;
    public LocalDate checkIn;
    public LocalDate checkOut;
    private double totalPrice;
    protected Payment payment;

    public Booking(User user, Hotel hotel, LocalDate checkIn, LocalDate checkOut) throws HotelSystemExceptions
    {
        LocalDate today = LocalDate.now();
        Objects.requireNonNull(user);
        Objects.requireNonNull(hotel);
        if (!hotel.isRoomAvailable(checkIn, checkOut))
            throw new HotelSystemExceptions("No rooms available in " + hotel.getName());
        hotel.addBooking(this);
        if (checkIn.isBefore(today))
            throw new HotelSystemExceptions("Check-in date cannot be in the past.");
        if (!checkOut.isAfter(checkIn))
            throw new HotelSystemExceptions("check-out must be after check-in");
        this.user = user;
        this.hotel = hotel;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        long nights   = ChronoUnit.DAYS.between(checkIn, checkOut);
        this.totalPrice = hotel.getPricePerNight() * nights;
    }

    public static Booking create(User user, Hotel hotel, LocalDate checkIn, LocalDate checkOut) throws HotelSystemExceptions {

        Objects.requireNonNull(user);
        Objects.requireNonNull(hotel);

        if (!checkOut.isAfter(checkIn))
            throw new HotelSystemExceptions("check-out must be after check-in");

        if (!hotel.isRoomAvailable(checkIn, checkOut))
            throw new HotelSystemExceptions("No rooms available in " + hotel.getName());

        Booking booking = new Booking(user, hotel, checkIn, checkOut);

        hotel.addBooking(booking);
        user.addBooking(booking);

        return booking;
    }


    public LocalDate getCheckIn()
    {
        return checkIn;
    }

    public LocalDate getCheckOut()
    {
        return checkOut;
    }

    public long getNights()          { return ChronoUnit.DAYS.between(checkIn, checkOut); }
    public double getTotalPrice()    { return hotel.getPricePerNight() * getNights(); }

    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = hotel.getPricePerNight() * (checkOut.toEpochDay() - checkIn.toEpochDay());
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
        System.out.println("Total Price: " + totalPrice);
    }

    public void cancelBooking()
    {
        hotel.removeBooking(this);
        user.removeBooking();
    }

    //For upcoming bookings method
    public void printLine() {
        System.out.printf("%s | %s → %s | ₪%.0f%n", hotel.getName(), checkIn, checkOut, totalPrice);
    }
}