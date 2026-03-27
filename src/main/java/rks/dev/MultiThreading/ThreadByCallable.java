package rks.dev.MultiThreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MainThreadC1 implements Callable<String>{

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        System.out.println("MainThreadFT1 Started");
        Thread.sleep(3000);
        return "ThreadFT1 Ends";
    }
}
public class ThreadByCallable {

    public static void main(String [] args) throws ExecutionException, InterruptedException {

        FutureTask<String> ft1 = new FutureTask<>(new MainThreadC1());
        FutureTask<String> ft = new FutureTask<>(() -> {Thread.sleep(1000);
            System.out.println("MainThreadFT Started");
            Thread.sleep(3000);
            return "ThreadFT Ends";});
        Thread thread = new Thread(ft);
        Thread thread1 = new Thread(ft1);
        thread.start();
        thread1.start();


        System.out.println("Main Thread is Running");
        System.out.println(ft.get());
        System.out.println(ft1.get());
    }
}
