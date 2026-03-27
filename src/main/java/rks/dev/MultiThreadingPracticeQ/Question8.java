package rks.dev.MultiThreadingPracticeQ;

/*
Create deadlock using:

2 threads
2 locks
 */

class Balance {

    int id;
    int balance = 0;

    public Balance(int id) {
        this.id = id;
    }
}

class UpdateBalance implements Runnable {

    final Balance b1;
    final Balance b2;

    public UpdateBalance(Balance b1, Balance b2) {
        // this is help in DeadLock Avoidance
        if (b1.id > b2.id) {
            this.b1 = b1;
            this.b2 = b2;
        } else {
            this.b1 = b2;
            this.b2 = b1;
        }
    }

    @Override
    public void run() {
        synchronized (b1) {
            try {
                System.out.println("Thread Name" + Thread.currentThread().getName());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (b2) {
                System.out.println("Inside Question8");
            }
        }
    }
}

public class Question8 {


    public static void main(String[] args) throws InterruptedException {
        Balance b1 = new Balance(1);
        Balance b2 = new Balance(2);

        Thread t1 = new Thread(new UpdateBalance(b1, b2));
        Thread t2 = new Thread(new UpdateBalance(b2, b1));

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Question8 Avoided");
    }
}
