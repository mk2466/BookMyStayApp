import java.util.LinkedList;
import java.util.Queue;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Requested Room: " + roomType;
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request added: " + reservation);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void displayQueue() {
        System.out.println("\nCurrent Booking Request Queue:");
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }
}

public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("     Book My Stay App - Version 5.0   ");
        System.out.println("======================================");

        BookingRequestQueue requestQueue = new BookingRequestQueue();

        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        requestQueue.addRequest(r1);
        requestQueue.addRequest(r2);
        requestQueue.addRequest(r3);

        requestQueue.displayQueue();

        System.out.println("\nProcessing booking requests (no allocation yet):");
        while (!requestQueue.isEmpty()) {
            Reservation next = requestQueue.getNextRequest();
            System.out.println("Next request to process: " + next);
        }

        System.out.println("\nAll requests are queued and ready for allocation.");
    }
}
