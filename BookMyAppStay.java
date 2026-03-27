
import java.io.*;
import java.util.*;

class Booking implements Serializable {
    String bookingId;
    String roomType;
    String roomId;

    Booking(String bookingId, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

class SystemState implements Serializable {
    Map<String, Integer> inventory;
    Map<String, Stack<String>> availableRooms;
    Map<String, Booking> bookings;

    SystemState(Map<String, Integer> inventory,
                Map<String, Stack<String>> availableRooms,
                Map<String, Booking> bookings) {
        this.inventory = inventory;
        this.availableRooms = availableRooms;
        this.bookings = bookings;
    }
}

public class UseCase12DataPersistenceRecovery {

    private static final String FILE_NAME = "system_state.ser";

    private static Map<String, Integer> inventory = new HashMap<>();
    private static Map<String, Stack<String>> availableRooms = new HashMap<>();
    private static Map<String, Booking> bookings = new HashMap<>();

    public static void main(String[] args) {

        loadState();

        if (inventory.isEmpty()) {
            initializeSystem();
        }

        createBooking("B201", "DELUXE");
        createBooking("B202", "DELUXE");

        saveState();

        displayState();
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
            System.out.println("Booking Failed: No rooms available");
            return;
        }

        String roomId = availableRooms.get(roomType).pop();
        inventory.put(roomType, inventory.get(roomType) - 1);

        Booking booking = new Booking(bookingId, roomType, roomId);
        bookings.put(bookingId, booking);

        System.out.println("Booking Confirmed: " + bookingId + " | Room: " + roomId);
    }

    private static void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            SystemState state = new SystemState(inventory, availableRooms, bookings);
            oos.writeObject(state);
            System.out.println("System state saved.");
        } catch (IOException e) {
            System.out.println("Error saving state.");
        }
    }

    private static void loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No previous state found. Starting fresh.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) ois.readObject();
            inventory = state.inventory;
            availableRooms = state.availableRooms;
            bookings = state.bookings;
            System.out.println("System state restored.");
        } catch (Exception e) {
            System.out.println("Error loading state. Starting fresh.");
        }
    }

    private static void displayState() {
        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }

        System.out.println("\nBookings:");
        for (String id : bookings.keySet()) {
            Booking b = bookings.get(id);
            System.out.println(id + " -> " + b.roomType + " | " + b.roomId);
        }
    }
}
