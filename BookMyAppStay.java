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

class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Reservation recorded in history: " + reservation);
    }

    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(history);
    }
}

class BookingReportService {
    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    public void generateReport() {
        System.out.println("\n--- Booking History Report ---");
        List<Reservation> reservations = history.getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println("No bookings recorded yet.");
        } else {
            for (Reservation r : reservations) {
                System.out.println(r);
            }
            System.out.println("Total bookings: " + reservations.size());
        }
        System.out.println("-------------------------------\n");
    }
}

public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("     Book My Stay App - Version 8.0   ");
        System.out.println("======================================");

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService(history);

        Reservation r1 = new Reservation("Alice", "Single Room", "R-101");
        Reservation r2 = new Reservation("Bob", "Double Room", "R-102");
        Reservation r3 = new Reservation("Charlie", "Suite Room", "R-103");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        reportService.generateReport();

        System.out.println("Booking history and reporting processed successfully.");
    }
}
