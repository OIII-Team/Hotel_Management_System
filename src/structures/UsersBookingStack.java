package structures;

import model.Booking;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class UsersBookingStack
{
    private final Deque<Booking> usersBookingsStack = new ArrayDeque<>();

    public void push(Booking booking) {
        if (booking != null)
            usersBookingsStack.push(booking);
    }

    public Booking pop() {
        return usersBookingsStack.poll();
    }

    public Booking peek() {
        return usersBookingsStack.peek();
    }

    public boolean isEmpty() {
        return usersBookingsStack.isEmpty();
    }

    public int size() {
        return usersBookingsStack.size();
    }

    public List<Booking> asList() {
        return List.copyOf(usersBookingsStack);
    }

    public void printStack() {
        if (usersBookingsStack.isEmpty()) {
            System.out.println("(No bookings found)");
            return;
        }
        System.out.println("User's bookings (latest first):");
        usersBookingsStack.forEach(b -> {System.out.print("  - "); System.out.println(b); });
    }
}