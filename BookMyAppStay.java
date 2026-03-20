import java.util.*;

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

class Service {
    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return serviceName + " ($" + price + ")";
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    public void addService(String reservationID, Service service) {
        serviceMap.computeIfAbsent(reservationID, k -> new ArrayList<>()).add(service);
        System.out.println("Added " + service + " to reservation " + reservationID);
    }

    public double calculateTotal(String reservationID) {
        List<Service> services = serviceMap.getOrDefault(reservationID, new ArrayList<>());
        return services.stream().mapToDouble(Service::getPrice).sum();
    }

    public void displayServices(String reservationID) {
        List<Service> services = serviceMap.getOrDefault(reservationID, new ArrayList<>());
        if (services.isEmpty()) {
            System.out.println("No add-on services selected for reservation " + reservationID);
        } else {
            System.out.println("Add-On Services for reservation " + reservationID + ":");
            for (Service s : services) {
                System.out.println("- " + s);
            }
            System.out.println("Total additional cost: $" + calculateTotal(reservationID));
        }
    }
}

public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("     Book My Stay App - Version 7.0   ");
        System.out.println("======================================");

        Reservation r1 = new Reservation("Alice", "Single Room", "R-101");
        Reservation r2 = new Reservation("Bob", "Double Room", "R-102");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        Service breakfast = new Service("Breakfast", 15.0);
        Service spa = new Service("Spa Access", 50.0);
        Service airport = new Service("Airport Pickup", 30.0);

        serviceManager.addService(r1.getReservationID(), breakfast);
        serviceManager.addService(r1.getReservationID(), spa);
        serviceManager.addService(r2.getReservationID(), airport);

        System.out.println();
        serviceManager.displayServices(r1.getReservationID());
        System.out.println();
        serviceManager.displayServices(r2.getReservationID());

        System.out.println("\nAdd-On Services processed successfully.");
    }
}
