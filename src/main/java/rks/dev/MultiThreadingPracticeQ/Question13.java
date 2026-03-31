package rks.dev.MultiThreadingPracticeQ;

import java.util.concurrent.CompletableFuture;

/*
Question 13: Parallel API Aggregation using CompletableFuture

Problem:
You are building a backend service that calls multiple independent APIs:

- getUser() → returns user information (String)
- getOrders() → returns number of orders (Integer)
- getRecommendations() → returns recommendation score (Integer)

Requirements:

1. Run ALL API calls in parallel
   - Do NOT call APIs sequentially
   - Use CompletableFuture

2. Combine all API results into a single response
   Example:
   "User=Rajat, Orders=10, Score=80"

3. Handle failures gracefully:
   - If any API fails → do NOT fail entire system
   - Use default values instead:
        getUser() → "DefaultUser"
        getOrders() → 0
        getRecommendations() → 0

4. Final result should be printed ONLY after all APIs complete

Constraints:

- MUST use CompletableFuture
- Use:
    ✔ supplyAsync()
    ✔ thenCombine() OR allOf()
    ✔ exceptionally() OR handle()
- Do NOT block early using get()
- Do NOT write sequential logic

Concepts Tested:

✔ Parallel async execution
✔ Combining multiple futures
✔ Exception handling with fallback
✔ Non-blocking design
✔ Real-world API aggregation pattern
*/
public class Question13 {

    public static void main(String[] args) {

        CompletableFuture<String> userFuture =
                CompletableFuture.supplyAsync(() -> getUser())
                        .exceptionally(ex -> "DefaultUser");

        CompletableFuture<Integer> ordersFuture =
                CompletableFuture.supplyAsync(() -> getOrders())
                        .exceptionally(ex -> 0);

        CompletableFuture<Integer> recFuture =
                CompletableFuture.supplyAsync(() -> getRecommendations())
                        .exceptionally(ex -> 0);

        // Combine all results
        CompletableFuture<String> finalResult =
                userFuture.thenCombine(ordersFuture,
                                (user, orders) -> "User=" + user + ", Orders=" + orders)
                        .thenCombine(recFuture,
                                (partial, score) -> partial + ", Score=" + score);

        // Print final result
        finalResult.thenAccept(System.out::println).join();
    }

    private static String getUser() {
        return "Rajat";
    }

    private static int getOrders() {
        throw new RuntimeException("Orders API failed");
    }

    private static int getRecommendations() {
        return 80;
    }
}
