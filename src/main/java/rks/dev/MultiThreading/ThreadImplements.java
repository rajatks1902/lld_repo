package rks.dev.MultiThreading;

class MainThreadR1 implements Runnable{

    @Override
    public void run() {
        System.out.println("Thread1 By Extending Started");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Thread1 By Extending Ends");
    }
}

class MainThreadR2 implements Runnable{

    @Override
    public void run() {
        System.out.println("Thread2 By Extending Started");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Thread2 By Extending Ends");
    }
}

public class ThreadImplements {

    public static  void main (String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new MainThreadR1());
        Thread thread2 = new Thread(new MainThreadR2());
        Thread thread = new Thread(() ->{
            System.out.println("Functional Programming");
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        thread.start();
        thread.join();
    }
}
