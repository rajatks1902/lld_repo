package rks.dev.MultiThreadingPracticeQ;

class Balance {

    public final int id;     // used for lock ordering
    public int amount;       // actual balance (if needed)

    public Balance(int id) {
        this.id = id;
        this.amount = 0;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void updateAmount(int delta) {
        this.amount += delta;
    }
}

class DeadlockTask implements Runnable {

    private final Balance first;
    private final Balance second;

    public DeadlockTask(Balance first, Balance second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public void run() {
        synchronized (first) {
            System.out.println(Thread.currentThread().getName() + " locked " + first.id);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            synchronized (second) {
                System.out.println("This will never print (deadlock)");
            }
        }
    }
}

class SafeTask implements Runnable {

    private final Balance first;
    private final Balance second;

    public SafeTask(Balance b1, Balance b2) {
        // Enforce consistent lock ordering
        if (b1.id < b2.id) {
            this.first = b1;
            this.second = b2;
        } else {
            this.first = b2;
            this.second = b1;
        }
    }

    @Override
    public void run() {
        synchronized (first) {
            System.out.println(Thread.currentThread().getName() + " locked " + first.id);

            synchronized (second) {
                System.out.println(Thread.currentThread().getName() + " completed safely");
            }
        }
    }
}

public class Question8 {

    public static void main(String[] args) throws InterruptedException {

        Balance account1 = new Balance(1);
        Balance account2 = new Balance(2);

        // 🔴 Deadlock Scenario
        Thread t1 = new Thread(new DeadlockTask(account1, account2), "T1");
        Thread t2 = new Thread(new DeadlockTask(account2, account1), "T2");

        t1.start();
        t2.start();

        Thread.sleep(3000);
        System.out.println("Deadlock likely occurred\n");

        // 🟢 Avoidance Scenario
        Thread t3 = new Thread(new SafeTask(account1, account2), "T3");
        Thread t4 = new Thread(new SafeTask(account2, account1), "T4");

        t3.start();
        t4.start();

        t3.join();
        t4.join();

        System.out.println("Deadlock avoided");
    }
}
