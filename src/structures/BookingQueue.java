package structures;

import exceptions.HotelSystemExceptions;
import model.Booking;
import model.User;
import model.Hotel;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;


public class BookingQueue {

    public static class BookingRequest {
        private final User user;
        private final Hotel hotel;
        private final LocalDate checkIn;
        private final LocalDate checkOut;

        public BookingRequest(User user, Hotel hotel, LocalDate checkIn, LocalDate checkOut) {
            this.user    = user;
            this.hotel   = hotel;
            this.checkIn = checkIn;
            this.checkOut= checkOut;
        }

        public User getUser() { return user; }
        public Hotel getHotel() { return hotel; }
        public LocalDate getCheckIn() { return checkIn; }
        public LocalDate getCheckOut() { return checkOut; }

        public boolean tryExecute() throws HotelSystemExceptions {
            if (!hotel.isRoomAvailable(checkIn, checkOut)) {
                return false;
            }
            //creates booking without payment
            Booking booking = new Booking(user, hotel, checkIn, checkOut);
            hotel.addBooking(booking);
            user.addBooking(booking);
            return true;
        }
    }

    private final Queue<BookingRequest> queue = new LinkedList<>();

    public void enqueue(BookingRequest request) throws HotelSystemExceptions {
        if (request == null) throw new HotelSystemExceptions("Request cannot be null");
        queue.offer(request);
    }

    public void processNext() {
        BookingRequest req = queue.poll();
        if (req == null) return;
        try {
            boolean success = req.tryExecute();
            if (!success) {
                queue.offer(req);
            }
        } catch (HotelSystemExceptions ex) {
        }
    }

    public BookingRequest dequeue() {
        return queue.poll();
    }

    public BookingRequest peek() {
        return queue.peek();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }
}
