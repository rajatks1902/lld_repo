package rks.dev.RaceCondition;

import java.util.concurrent.atomic.AtomicInteger;

class PurchaseCounter {
    // Shared count value
    private final Object lock = new Object();
    private final AtomicInteger count = new AtomicInteger();
    private int cnt = 0;

    // Increment the counter
    public void increment() {
        // READ current value
        // INCREMENT it
        // WRITE it back

        count.getAndIncrement();
        synchronized (lock) {
            cnt++;
        }
    }

    // Fetch the current count
    public int getCount() {
//        return count.get();
        return cnt;
    }
}

public class RaceConditionDemo {

    public static void main(String[] args) throws InterruptedException {

        AtomicInteger cnt = new AtomicInteger();
        PurchaseCounter c = new PurchaseCounter();
        cnt.compareAndSet(0, 2000);

        Runnable r = () -> {
            for (int i = 0; i < 1000; i++)
                cnt.getAndDecrement();
        };


        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

//        System.out.println(cnt);

        Runnable r1 = () -> {
            for (int i = 0; i < 1000; i++)
                c.increment();
        };

        Thread t3 = new Thread(r1);
        Thread t4 = new Thread(r1);
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        // This will not give correct Value
        System.out.println(c.getCount());

    }
}
