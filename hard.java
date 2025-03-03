package com.capgemini;
import java.util.*;

class TicketBookingSystem {
    private final Map<Integer, String> bookedSeats = new HashMap<>();

    // Synchronized method to check seat availability
    public synchronized boolean isSeatAvailable(int seatNumber) {
        return !bookedSeats.containsKey(seatNumber);
    }

    // Synchronized method to book a seat
    public synchronized void bookSeat(int seatNumber, String userName, String userType) {
        if (isSeatAvailable(seatNumber)) {
            bookedSeats.put(seatNumber, userName);
            System.out.println(userType + " " + userName + " successfully booked seat " + seatNumber);
        }
    }
}

class BookingThread extends Thread {
    private final TicketBookingSystem bookingSystem;
    private final Scanner scanner;
    private final String userType;

    public BookingThread(TicketBookingSystem bookingSystem, String userType, int priority, Scanner scanner) {
        this.bookingSystem = bookingSystem;
        this.userType = userType;
        this.scanner = scanner;
        this.setPriority(priority);
    }

    @Override
    public void run() {
        int seatNumber;
        synchronized (bookingSystem) {
            while (true) {
                System.out.print(userType + " - Enter seat number to book: ");
                seatNumber = scanner.nextInt();

                if (bookingSystem.isSeatAvailable(seatNumber)) {
                    System.out.print(userType + " - Seat available! Enter your name: ");
                    String userName = scanner.next();
                    bookingSystem.bookSeat(seatNumber, userName, userType);
                    break; // Exit the loop once the seat is booked
                } else {
                    System.out.println(userType + " - Seat " + seatNumber + " is already booked. Try another.");
                }
            }
        }
    }
}

public class TicketBookingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicketBookingSystem system = new TicketBookingSystem();

        System.out.print("Enter the number of users trying to book seats: ");
        int n = scanner.nextInt();

        Thread[] users = new Thread[n];

        for (int i = 0; i < n; i++) {
            String userType;
            while (true) {
                System.out.print("Enter user type (VIP/Regular) for user " + (i + 1) + ": ");
                userType = scanner.next();

                // Validate userType input
                if (userType.equalsIgnoreCase("VIP") || userType.equalsIgnoreCase("Regular")) {
                    break; // Valid input, exit loop
                } else {
                    System.out.println("Invalid input! Please enter 'VIP' or 'Regular' only.");
                }
            }

            int priority = userType.equalsIgnoreCase("VIP") ? Thread.MAX_PRIORITY : Thread.MIN_PRIORITY;
            users[i] = new BookingThread(system, userType, priority, scanner);
        }

        // Start all threads
        for (Thread user : users) {
            user.start();
        }

        // Wait for all threads to finish
        for (Thread user : users) {
            try {
                user.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        scanner.close();
        System.out.println("Booking process completed!");
    }
}
