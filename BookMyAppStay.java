import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String guestName;
    private String roomType;
    private String reservationID;

    public Reservation(String guestName, String roomType, String reservationID) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationID = reservationID;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getReservationID() {
        return reservationID;
    }

    @Override
    public String toString() {
        return "ReservationID: " + reservationID + ", Guest: " + guestName + ", Room: " + roomType;
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

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public boolean decrementAvailability(String roomType) throws InvalidBookingException {
        validateRoomType(roomType);
        int available = inventory.get(roomType);
        if (available <= 0) {
            throw new InvalidBookingException("No availability for room type: " + roomType);
        }
        inventory.put(roomType, available - 1);
        return true;
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

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRoomIDs = new HashSet<>();
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
        try {
            if (inventory.decrementAvailability(reservation.getRoomType())) {
                String roomID = generateRoomID(reservation.getRoomType());
                System.out.println("Reservation Confirmed for " + reservation.getGuestName() +
                                   ". Assigned Room ID: " + roomID);
            }
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed for " + reservation.getGuestName() + ": " + e.getMessage());
        }
    }
}

public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("     Book My Stay App - Version 9.0   ");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        Reservation r1 = new Reservation("Alice", "Single Room", "R-101");
        Reservation r2 = new Reservation("Bob", "Double Room", "R-102");
        Reservation r3 = new Reservation("Charlie", "Presidential Suite", "R-103"); // Invalid type
        Reservation r4 = new Reservation("David", "Suite Room", "R-104");
        Reservation r5 = new Reservation("Eve", "Suite Room", "R-105"); // Should fail if no rooms left

        bookingService.allocateRoom(r1);
        bookingService.allocateRoom(r2);
        bookingService.allocateRoom(r3);
        bookingService.allocateRoom(r4);
        bookingService.allocateRoom(r5);

        inventory.displayInventory();

        System.out.println("\nError handling and validation demo completed.");
    }
}
