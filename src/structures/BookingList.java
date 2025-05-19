package structures;
import model.Booking;
import java.util.List;
import java.util.ArrayList;

public class BookingList
{
    private BookingNode head;
    private BookingNode tail;
    private int size;

    public BookingList() {
        this.head = null;
    }

    public void addBooking(Booking booking) {
            BookingNode n = new BookingNode(booking);
            if (isEmpty()) {
                head = tail = n;
            } else {
                tail.next = n;
                tail = n;
            }
            size++;
        }

    public void removeBooking(Booking booking)
    {
        if (isEmpty()) return;

        if (head.booking.equals(booking)) {
            head = head.next;
            if (head == null) tail = null;
            size--;
            return;
        }
        for (BookingNode cur = head; cur.next != null; cur = cur.next) {
            if (cur.next.booking.equals(booking)) {
                cur.next = cur.next.next;
                if (cur.next == null) tail = cur;
                size--;
                break;
            }
        }
    }

    public boolean isEmpty()
    {
        return head == null;
    }

    public int size()
    {
        return size;
    }

    private static class BookingNode {
        Booking booking;
        BookingNode next;

        BookingNode(Booking booking) {
            this.booking = booking;
            this.next = null;
        }
    }

    public List<Booking> asList() {
        List<Booking> lst = new ArrayList<>(size);
        for (BookingNode cur = head; cur != null; cur = cur.next)
            lst.add(cur.booking);
        return List.copyOf(lst);
    }
}
