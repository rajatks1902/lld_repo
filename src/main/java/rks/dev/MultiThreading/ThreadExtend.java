package rks.dev.MultiThreading;

class MainThread1 extends Thread{

    @Override
    public void run(){
        System.out.println("Thread1 By Extending Started");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Thread1 By Extending Ends");
    }
}

class MainThread2 extends Thread{

    @Override
    public void run(){
        System.out.println("Thread2 By Extending Started");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Thread2 By Extending Ends");
    }
}

public class ThreadExtend {

    public static  void main (String[] args) throws InterruptedException {


        MainThread1 thread1 = new MainThread1();
        MainThread2 thread2 = new MainThread2();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        // if we not use join here next line will be executed and even the main thread can terminate
        System.out.println("Both Thread Ended");
    }
}
