package rks.dev.MultiThreadingPracticeQ;

import java.util.concurrent.CompletableFuture;

public class Question10 {

    /*
    ==============================
    Q1: Basic Chaining
    - Run async task → 10
    - Multiply by 2
    - Add 5
    - Print result (25)
    ==============================
    */
    public static void question1() {
        CompletableFuture.supplyAsync(() -> 10)
                .thenApply(x -> x * 2)
                .thenApply(x -> x + 5)
                .thenAccept(System.out::println)
                .join(); // ensure main waits
    }

    /*
    ==============================
    Q2: Parallel + Combine
    - Task1 → 10
    - Task2 → 20
    - Run in parallel
    - Sum and print (30)
    ==============================
    */
    public static void question2() {
        CompletableFuture<Integer> c1 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> c2 = CompletableFuture.supplyAsync(() -> 20);

        c1.thenCombine(c2, Integer::sum)
                .thenAccept(System.out::println)
                .join();
    }

    /*
    ==============================
    Q3: allOf (Wait for all)
    - 3 tasks → 10, 20, 30
    - Wait for all
    - Sum results → 60
    ==============================
    */
    public static void question3() {
        CompletableFuture<Integer> f1 = futureTask(1);
        CompletableFuture<Integer> f2 = futureTask(2);
        CompletableFuture<Integer> f3 = futureTask(3);

        CompletableFuture.allOf(f1, f2, f3)
                .thenApply(v -> f1.join() + f2.join() + f3.join()) // collect results
                .thenAccept(System.out::println)
                .join();
    }

    /*
    ==============================
    Q4: Exception Handling
    - Task throws exception
    - Handle using exceptionally
    - Return default value (0)
    ==============================
    */
    public static void question4() {
        CompletableFuture<Integer> f1 = futureTaskWithException(1)
                .exceptionally(ex -> 0);

        CompletableFuture<Integer> f2 = futureTaskWithException(2) // will fail
                .exceptionally(ex -> 0);

        CompletableFuture.allOf(f1, f2)
                .thenApply(v -> f1.join() + f2.join())
                .thenAccept(System.out::println)
                .join();
    }

    /*
    ==============================
    Q5: First Result (Race)
    - Run multiple tasks
    - Return first completed result
    ==============================
    */
    public static void question5() {
        CompletableFuture<Integer> p1 = futureTask(3);
        CompletableFuture<Integer> p2 = futureTask(4);

        // Preferred (type-safe)
        p1.applyToEither(p2, x -> x)
                .thenAccept(System.out::println)
                .join();

        // Alternative (less preferred)
        /*
        CompletableFuture.anyOf(p1, p2)
                .thenAccept(result -> System.out.println((Integer) result))
                .join();
        */
    }

    // ==============================
    // Helper Methods
    // ==============================

    // Simulates async task with delay
    private static CompletableFuture<Integer> futureTask(int n) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(n * 1000L); // simulate delay
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return n * 10;
        });
    }

    // Simulates exception scenario
    private static CompletableFuture<Integer> futureTaskWithException(int n) {
        return CompletableFuture.supplyAsync(() -> {
            if (n % 2 == 0) {
                throw new RuntimeException("Error in task");
            }
            return n * 10;
        });
    }

    // ==============================
    // Main (Run individually)
    // ==============================
    public static void main(String[] args) {
        question1();
        question2();
        question3();
        question4();
        question5();
    }
}