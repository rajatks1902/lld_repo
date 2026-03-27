package rks.dev.RaceCondition;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLocked {

    private static int availableSeats = 1;

    private static final ReentrantLock lock = new ReentrantLock();

    public static void bookTicket(String user) {
        System.out.println(user + " is trying to book...");

        // Acquire the lock - blocks until available
        lock.lock();
        try {
            System.out.println(user + " acquired lock.");
            // Thread.sleep(1000);

            // Critical section: check and update shared state
            if (availableSeats > 0) {
                System.out.println(user + " successfully booked the ticket.");
                availableSeats--;
            } else {
                System.out.println(user + " could not book the ticket. No seats left.");
            }
        } finally {
            // Always release the lock in a finally block
            System.out.println(user + " is releasing the lock.");
            lock.unlock();
        }
    }


    public static void main(String[] args) {


        // Create two threads representing two users trying to book at the same time
        Thread user1 = new Thread(() -> bookTicket("User 1"));
        Thread user2 = new Thread(() -> bookTicket("User 2"));

        // Start both threads
        user1.start();
        user2.start();

        // Wait for both threads to finish
        try {
            user1.join();
            user2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }
        System.out.println(availableSeats);
    }

}
