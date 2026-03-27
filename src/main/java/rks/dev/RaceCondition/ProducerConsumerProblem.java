package rks.dev.RaceCondition;

import java.util.LinkedList;
import java.util.Queue;

// Shared resource: CoffeeMachine acts as a buffer of size 1
class CoffeeMachine {
    // Flag to indicate buffer status: true → coffee ready, false → cup empty
    private boolean isCoffeeReady = false;

    // Method to be run by the producer thread
    public synchronized void makeCoffee() throws InterruptedException {
        // If coffee is already ready, producer must wait (buffer is full)
        while (isCoffeeReady) {
            // Releases lock and waits until notified by consumer
            wait();
        }

        // Simulate coffee preparation
        System.out.println("Brewing coffee...");
        Thread.sleep(1000); // Simulate time to make coffee

        // Set the buffer to full (coffee is now ready)
        isCoffeeReady = true;
        System.out.println("Coffee ready!");

        // Notify the consumer thread that coffee is available
        notify();
    }

    // Method to be run by the consumer thread
    public synchronized void drinkCoffee() throws InterruptedException {
        // If no coffee is available, consumer must wait (buffer is empty)
        while (!isCoffeeReady) {
            // Releases lock and waits until notified by producer
            wait();
        }

        // Simulate coffee consumption
        System.out.println("Drinking coffee...");
        Thread.sleep(1000); // Simulate time to drink coffee

        // Set the buffer to empty (coffee has been consumed)
        isCoffeeReady = false;
        System.out.println("Cup emptied - brew next!");

        // Notify the producer thread that it can brew again
        notify();
    }
}

// Driver class
class ProducerConsumerProblem {
    public static void main(String[] args) {
        // Shared CoffeeMachine object acts as the synchronized monitor
        CoffeeMachine machine = new CoffeeMachine();

        // Producer thread that continuously makes coffee
        Thread producer = new Thread(() -> {
            while (true) {
                try {
                    machine.makeCoffee(); // Try to produce coffee
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Consumer thread that continuously drinks coffee
        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    machine.drinkCoffee(); // Try to consume coffee
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start both threads
        producer.start();
        consumer.start();
    }
}


