package rks.dev.MultiThreadingPracticeQ;

import java.util.ArrayDeque;
import java.util.Queue;

/*
Question 9: Producer-Consumer (Bounded Buffer)

- Producer adds items
- Consumer removes items
- Buffer size limited to 5
- Use wait/notify for coordination
*/

class Task {

    private final Queue<Integer> buffer = new ArrayDeque<>();
    private final int capacity = 5;

    // Producer
    public synchronized void produce() throws InterruptedException {
        while (buffer.size() == capacity) {
            wait();
        }

        buffer.add(1);
        int size = buffer.size(); // capture inside lock

        notifyAll();

        System.out.println("Produced. Size: " + size);
    }

    // Consumer
    public void consume() throws InterruptedException {
        int sizeAfterRemove;

        synchronized (this) {
            while (buffer.isEmpty()) {
                wait();
            }

            buffer.remove();
            sizeAfterRemove = buffer.size(); // capture inside lock

            notifyAll();
        }

        // simulate processing outside lock
        System.out.println("Consumed. Size: " + sizeAfterRemove);
        Thread.sleep(1000);
    }
}

public class Question9 {

    public static void main(String[] args) {

        Task task = new Task();

        Runnable producer = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    task.produce();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable consumer = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    task.consume();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread producerThread = new Thread(producer, "Producer");
        Thread consumerThread = new Thread(consumer, "Consumer");

        producerThread.start();
        consumerThread.start();
    }
}