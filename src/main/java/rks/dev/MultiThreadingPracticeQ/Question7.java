package rks.dev.MultiThreadingPracticeQ;

import java.util.concurrent.Semaphore;

/*
Question 7: Semaphore (Concurrency Control)

Problem:
- Total 10 threads
- Only 3 threads should run concurrently

Key Concept:
✔ Semaphore controls access to limited resources
✔ Only 3 permits → max 3 concurrent threads
*/

class Logging {

    private final Semaphore semaphore = new Semaphore(3);

    public void logUser() {
        if (semaphore.tryAcquire()) {
            try {
                System.out.println(Thread.currentThread().getName() + " started");

                // Simulate work
                Thread.sleep(1000);

                System.out.println(Thread.currentThread().getName() + " finished");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                semaphore.release();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " retry later");
        }
    }
}

public class Question7 {

    public static void main(String[] args) {

        Logging logging = new Logging();
        Runnable task = logging::logUser;

        for (int i = 0; i < 10; i++) {
            new Thread(task, "T" + i).start();
        }
    }
}