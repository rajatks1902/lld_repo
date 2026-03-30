package rks.dev.MultiThreading;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureMain {

    public static void main(String[] args) {
        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> "Future Task Completed");
        System.out.println("Main Thread Running");
        f.thenAccept(System.out::println);
        System.out.println("Main Thread Running");
    }
}
