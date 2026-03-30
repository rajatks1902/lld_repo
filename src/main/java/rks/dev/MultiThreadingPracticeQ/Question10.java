package rks.dev.MultiThreadingPracticeQ;

import java.util.concurrent.CompletableFuture;

/*
Problem 1
Run an async task that returns 10
Multiply result by 2
Add 5
Print final result
Do NOT use:  get() (blocking)

Problem 2
Run 2 async tasks:
Task1 → returns 10
Task2 → returns 20
Run them in parallel
Combine results → sum
Print result
 */
public class Question10 {

    public static void main(String[] args) {
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 10)
                .thenApply(x -> x * 2)
                .thenApply(x -> x + 5);

        cf.thenAccept(System.out::println).join();


    }
}
