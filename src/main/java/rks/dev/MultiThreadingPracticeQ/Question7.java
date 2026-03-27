package rks.dev.MultiThreadingPracticeQ;

/*
Limit to 3 concurrent threads
Total threads = 10
Use Semaphore
 */

import java.util.concurrent.Semaphore;

class Logging {

    Semaphore s = new Semaphore(3);

    public void logUser() {
        if (s.tryAcquire()) {
            System.out.println("Logging Success");
            s.release();
        } else {
            System.out.println("Logging Unsuccessfull : Retry");
        }
    }
}

public class Question7 {

    public static void main(String[] args) {
        Logging l = new Logging();
        Runnable r = l::logUser;
        for (int i = 0; i < 10; i++) {
            new Thread(r).start();
        }
    }
}
