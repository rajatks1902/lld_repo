package rks.dev.MultiThreading;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureMain {

    public static void main(String[] args) {
        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Future Task Completed";
        });
        System.out.println("Main Thread Running");
        /*
        Main Thread Does Not Wait for Worker Thread to Complete
         */
        f.thenAccept(System.out::println);

    }
}
