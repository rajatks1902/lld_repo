package rks.dev.MultiThreadingPracticeQ;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/*
2 thread counting 1000 each
Use ReentrantLock
Use tryLock()
If lock NOT acquired → skip increment
Do NOT:
use synchronized
use AtomicInteger
 */

class Counter {

    ReentrantLock lock = new ReentrantLock();

    private int cnt = 0;

    public void increaseCount() {
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(1, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            if (isLocked) {
                cnt++;
            }
        } finally {
            if (isLocked) {
                lock.unlock();
            }
        }
    }

    public int getCount() {
        return cnt;
    }
}

public class Question6 {

    public static void main(String[] args) throws InterruptedException {
        Counter cnt = new Counter();

        Runnable r = () -> {
            for (int i = 0; i < 1000; i++)
                cnt.increaseCount();
        };
        Thread t = new Thread(r);
        Thread t1 = new Thread(r);
        t.start();
        t1.start();
        t.join();
        t1.join();
        System.out.println(cnt.getCount());
    }


}

