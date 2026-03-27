import java.util.*;

class BookingRequest {
    String requestId;
    String roomType;

    BookingRequest(String requestId, String roomType) {
        this.requestId = requestId;
        this.roomType = roomType;
    }
}

class BookingSystem {

    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Stack<String>> availableRooms = new HashMap<>();
    private Queue<BookingRequest> bookingQueue = new LinkedList<>();

    BookingSystem() {
        Stack<String> deluxeRooms = new Stack<>();
        deluxeRooms.push("D1");
        deluxeRooms.push("D2");
        deluxeRooms.push("D3");

        availableRooms.put("DELUXE", deluxeRooms);
        inventory.put("DELUXE", deluxeRooms.size());
    }

    public synchronized void addRequest(BookingRequest request) {
        bookingQueue.add(request);
        System.out.println("Request Added: " + request.requestId);
    }

    public synchronized BookingRequest getRequest() {
        return bookingQueue.poll();
    }

    public void processBooking() {
        while (true) {
            BookingRequest request;

            synchronized (this) {
                request = getRequest();
                if (request == null) {
                    break;
                }
            }

            allocateRoom(request);
        }
    }

    private void allocateRoom(BookingRequest request) {
        synchronized (this) {
            if (!availableRooms.containsKey(request.roomType) ||
                availableRooms.get(request.roomType).isEmpty()) {
                System.out.println("Booking Failed: " + request.requestId + " | No rooms available");
                return;
            }

            String roomId = availableRooms.get(request.roomType).pop();
            inventory.put(request.roomType, inventory.get(request.roomType) - 1);

            System.out.println("Booking Confirmed: " + request.requestId + " | Room: " + roomId);
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }
    }
}

class BookingWorker extends Thread {

    private BookingSystem system;

    BookingWorker(BookingSystem system) {
        this.system = system;
    }

    public void run() {
        system.processBooking();
    }
}

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        system.addRequest(new BookingRequest("R1", "DELUXE"));
        system.addRequest(new BookingRequest("R2", "DELUXE"));
        system.addRequest(new BookingRequest("R3", "DELUXE"));
        system.addRequest(new BookingRequest("R4", "DELUXE"));
        system.addRequest(new BookingRequest("R5", "DELUXE"));

        Thread t1 = new BookingWorker(system);
        Thread t2 = new BookingWorker(system);
        Thread t3 = new BookingWorker(system);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.displayInventory();
    }
}
