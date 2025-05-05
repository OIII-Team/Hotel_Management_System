package structures;
import model.Amenities;
import model.Booking;

public class BookingList
{
    private BookingNode head;

    public BookingList() {
        this.head = null;
    }

    public void addBooking(Booking booking) {
        BookingNode newNode = new BookingNode(booking);
        if (head == null) {
            head = newNode;
        } else {
            BookingNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void removeBooking(Booking booking) {
        if (head == null) return;

        if (head.booking.equals(booking)) {
            head = head.next;
            return;
        }

        BookingNode current = head;
        while (current.next != null && !current.next.booking.equals(booking)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    private static class BookingNode {
        Booking booking;
        BookingNode next;

        BookingNode(Booking booking) {
            this.booking = booking;
            this.next = null;
        }
    }
}
