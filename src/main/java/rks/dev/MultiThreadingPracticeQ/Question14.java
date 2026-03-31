package rks.dev.MultiThreadingPracticeQ;

/*
Question 14: CompletableFuture with Timeout + Fallback

Problem:
You are calling an external API:

- getPrice() → returns price (Integer)
- API may be slow or fail

Requirements:

1. Call API asynchronously using CompletableFuture

2. Add TIMEOUT:
   - If API does NOT respond within 2 seconds → use default value

3. Handle failure:
   - If API throws exception → return default value (e.g., 100)

4. Print final result

Constraints:

- MUST use CompletableFuture
- Use:
    ✔ supplyAsync()
    ✔ orTimeout() OR completeOnTimeout()
    ✔ exceptionally() OR handle()
- Do NOT block early

*/

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Question14 {

    public static void main(String[] args) {

        CompletableFuture<Integer> priceFuture =
                CompletableFuture.supplyAsync(() -> getPrice())

                        // If takes more than 2 seconds → fallback to default
                        .completeOnTimeout(100, 2, TimeUnit.SECONDS)

                        // If exception occurs → fallback
                        .exceptionally(ex -> {
                            System.out.println("API failed: " + ex.getMessage());
                            return 100;
                        });

        // Final result
        priceFuture.thenAccept(price ->
                System.out.println("Final Price = " + price)
        ).join();
    }

    private static int getPrice() {
        try {
            // Simulate delay
            Thread.sleep(3000); // change to test behavior

            // Simulate failure
            if (true) {
                throw new RuntimeException("Price API error");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return 500;
    }
}
