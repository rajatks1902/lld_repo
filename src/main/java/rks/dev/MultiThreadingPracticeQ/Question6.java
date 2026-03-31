package rks.dev.MultiThreadingPracticeQ;

import java.util.concurrent.locks.ReentrantLock;

/*
Question 6: ReentrantLock with tryLock

Problem:
- 2 threads increment counter 1000 times each
- Use tryLock()
- If lock NOT acquired → skip increment

Key Concept:
✔ tryLock is non-blocking
✔ Some increments may be skipped
✔ Final count < 2000 (expected)
*/

class Counter {

    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    public void increment() {
        // Try to acquire lock (non-blocking)
        if (lock.tryLock()) {
            try {
                count++;
            } finally {
                lock.unlock();
            }
        }
        // else → skip increment
    }

    public int getCount() {
        return count;
    }
}

public class Question6 {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Thread thread1 = new Thread(task, "T1");
        Thread thread2 = new Thread(task, "T2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final Count = " + counter.getCount());
    }
}