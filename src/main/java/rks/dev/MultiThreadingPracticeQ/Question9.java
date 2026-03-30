package rks.dev.MultiThreadingPracticeQ;

import java.util.ArrayDeque;
import java.util.Queue;

class Task {

    private final Queue<Integer> buffer = new ArrayDeque<>();
    private final int capacity = 5;

    public synchronized void assignTask() throws InterruptedException {
        while (buffer.size() == capacity) {
            wait();
        }
        buffer.add(1);
        System.out.println("Produced. Size: " + buffer.size());
        notifyAll();
    }

    public void completeTask() throws InterruptedException {
        int item;

        synchronized (this) {
            while (buffer.isEmpty()) {
                wait();
            }
            item = buffer.remove();
            notifyAll();
        }

        // simulate work outside lock
        System.out.println("Consumed. Size: " + buffer.size());
        Thread.sleep(1000);
    }
}

public class Question9 {
    public static void main(String args[]) {
        Task task = new Task();
        Runnable producer = () -> {
            while (true) {
                try {
                    task.assignTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable consumer = () -> {
            while (true) {
                try {
                    task.completeTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(consumer);
        t1.start();
        t2.start();
    }
}
