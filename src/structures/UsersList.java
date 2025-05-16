package structures;
import model.User;

public class UsersList
{
    private UserNode head;

    public UsersList() {
        this.head = null;
    }

    public void addUser(User user) {
        UserNode newNode = new UserNode(user);
        if (head == null) {
            head = newNode;
        } else {
            UserNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void removeUser(User user) {
        if (head == null) return;

        if (head.user.equals(user)) {
            head = head.next;
            return;
        }

        UserNode current = head;
        while (current.next != null && !current.next.user.equals(user)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    public static class UserNode {
        public User user;
        public UserNode next;

        public UserNode(User user) {
            this.user = user;
            this.next = null;
        }
    }
    public UserNode getHead() {
        return head;
    }

    public void printUsers() {
        UserNode current = head;
        System.out.println("\n-- Active Users List: -- ");
        if (current == null) {
            System.out.println("No active users found.");
            return;
        }
        while (current != null) {
            System.out.print("\n");
            current.user.PrintUserDetails();
            current = current.next;
        }
    }

}
