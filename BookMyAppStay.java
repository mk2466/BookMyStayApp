import java.util.*;

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

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public boolean decrementAvailability(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class BookingService {
    private RoomInventory inventory;
    private Set<String> allocatedRoomIDs;
    private Map<String, Set<String>> roomAllocations;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRoomIDs = new HashSet<>();
        roomAllocations = new HashMap<>();
    }

    private String generateRoomID(String roomType) {
        String id;
        do {
            id = roomType.substring(0, 1).toUpperCase() + "-" + (int)(Math.random() * 1000 + 1);
        } while (allocatedRoomIDs.contains(id));
        allocatedRoomIDs.add(id);
        return id;
    }

    public void allocateRoom(Reservation reservation) {
        String roomType = reservation.getRoomType();
        if (inventory.decrementAvailability(roomType)) {
            String roomID = generateRoomID(roomType);
            roomAllocations.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomID);
            System.out.println("Reservation Confirmed for " + reservation.getGuestName() +
                               ". Assigned Room ID: " + roomID);
        } else {
            System.out.println("Reservation Failed for " + reservation.getGuestName() +
                               ". No " + roomType + " available.");
        }
    }

    public void displayAllocations() {
        System.out.println("\nRoom Allocations:");
        for (Map.Entry<String, Set<String>> entry : roomAllocations.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class UseCase6RoomAllocationService {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("     Book My Stay App - Version 6.0   ");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> requestQueue = new LinkedList<>();
        requestQueue.add(new Reservation("Alice", "Single Room"));
        requestQueue.add(new Reservation("Bob", "Double Room"));
        requestQueue.add(new Reservation("Charlie", "Suite Room"));
        requestQueue.add(new Reservation("David", "Single Room"));
        requestQueue.add(new Reservation("Eve", "Suite Room"));
        requestQueue.add(new Reservation("Frank", "Double Room"));

        BookingService bookingService = new BookingService(inventory);

        while (!requestQueue.isEmpty()) {
            Reservation next = requestQueue.poll();
            bookingService.allocateRoom(next);
        }

        bookingService.displayAllocations();
        inventory.displayInventory();

        System.out.println("\nAll bookings processed successfully.");
    }
}
