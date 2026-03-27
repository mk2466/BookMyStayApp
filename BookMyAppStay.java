import java.util.*;

class Booking {
    String bookingId;
    String roomType;
    String roomId;
    boolean isCancelled;

    Booking(String bookingId, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }
}

public class UseCase10BookingCancellation {

    private static Map<String, Integer> inventory = new HashMap<>();
    private static Map<String, Stack<String>> availableRooms = new HashMap<>();
    private static Map<String, Booking> bookings = new HashMap<>();
    private static Stack<String> rollbackStack = new Stack<>();

    public static void main(String[] args) {

        initializeSystem();

        createBooking("B101", "DELUXE");
        createBooking("B102", "DELUXE");

        cancelBooking("B101");
        cancelBooking("B101");
        cancelBooking("B999");

        displayInventory();
    }

    private static void initializeSystem() {
        Stack<String> deluxeRooms = new Stack<>();
        deluxeRooms.push("D1");
        deluxeRooms.push("D2");
        deluxeRooms.push("D3");

        availableRooms.put("DELUXE", deluxeRooms);
        inventory.put("DELUXE", deluxeRooms.size());

        System.out.println("System Initialized.");
    }

    private static void createBooking(String bookingId, String roomType) {
        if (!availableRooms.containsKey(roomType) || availableRooms.get(roomType).isEmpty()) {
            System.out.println("No rooms available for type: " + roomType);
            return;
        }

        String allocatedRoom = availableRooms.get(roomType).pop();
        inventory.put(roomType, inventory.get(roomType) - 1);

        Booking booking = new Booking(bookingId, roomType, allocatedRoom);
        bookings.put(bookingId, booking);

        System.out.println("Booking Confirmed: " + bookingId + " | Room: " + allocatedRoom);
    }

    private static void cancelBooking(String bookingId) {

        System.out.println("\nProcessing cancellation for: " + bookingId);

        if (!bookings.containsKey(bookingId)) {
            System.out.println("Cancellation Failed: Booking does not exist.");
            return;
        }

        Booking booking = bookings.get(bookingId);

        if (booking.isCancelled) {
            System.out.println("Cancellation Failed: Booking already cancelled.");
            return;
        }

        rollbackStack.push(booking.roomId);

        inventory.put(booking.roomType, inventory.get(booking.roomType) + 1);

        availableRooms.get(booking.roomType).push(booking.roomId);

        booking.isCancelled = true;

        System.out.println("Cancellation Successful for Booking: " + bookingId);
        System.out.println("Room " + booking.roomId + " released back to inventory.");
    }

    private static void displayInventory() {
        System.out.println("\nFinal Inventory Status:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }
    }
}
