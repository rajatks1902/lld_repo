package rks.dev.MultiThreadingPracticeQ;

import java.util.concurrent.*;

/*
Question 12: API Aggregator with Global Timeout

Problem:
You have 10 external API calls.
Each API:
- Takes variable time
- Returns an integer

Requirements:
1. Use ThreadPoolExecutor (pool size = 5)
2. Limit ONLY 3 APIs running concurrently (Semaphore)
3. Process results as they complete (CompletionService)
4. TOTAL time limit = 3 seconds (global timeout)
5. After timeout:
   - Stop collecting results
   - Cancel remaining tasks
6. If any API fails → skip it
7. Print results in completion order

Constraints:
- Do NOT use invokeAll(timeout)
- Do NOT use CompletableFuture
- Must handle timeout manually

Concepts:
✔ ThreadPoolExecutor
✔ Semaphore (concurrency control)
✔ CompletionService (async result handling)
✔ Global timeout handling
✔ Graceful shutdown
*/

class ApiLimitTask implements Callable<Integer> {

    private final int apiId;
    private final Semaphore semaphore;

    public ApiLimitTask(int apiId, Semaphore semaphore) {
        this.apiId = apiId;
        this.semaphore = semaphore;
    }

    @Override
    public Integer call() {
        try {
            // Acquire permit → only 3 APIs run concurrently
            semaphore.acquire();

            System.out.println("API " + apiId + " started by " + Thread.currentThread().getName());

            // Simulate variable API delay
            Thread.sleep((apiId % 3 + 1) * 1000);

            // Simulate failure
            if (apiId == 7) {
                throw new RuntimeException("API failed: " + apiId);
            }

            System.out.println("API " + apiId + " completed");

            return apiId * 10;

        } catch (InterruptedException e) {
            // Restore interrupt status
            Thread.currentThread().interrupt();
            return null;
        } finally {
            semaphore.release();
        }
    }
}

public class Question12 {

    public static void main(String[] args) throws InterruptedException {

        // ThreadPoolExecutor (fixed size = 5)
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5, 5,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()
        );

        // Semaphore → max 3 concurrent API calls
        Semaphore semaphore = new Semaphore(3);

        // CompletionService → process results as they complete
        CompletionService<Integer> completionService =
                new ExecutorCompletionService<>(executor);

        int totalTasks = 10;

        // Submit all API tasks
        for (int i = 1; i <= totalTasks; i++) {
            completionService.submit(new ApiLimitTask(i, semaphore));
        }

        // GLOBAL timeout logic (3 seconds)
        long deadline = System.currentTimeMillis() + 3000;

        int received = 0;

        while (System.currentTimeMillis() < deadline && received < totalTasks) {

            long remainingTime = deadline - System.currentTimeMillis();

            // Wait only for remaining time
            Future<Integer> future =
                    completionService.poll(remainingTime, TimeUnit.MILLISECONDS);

            if (future == null) {
                System.out.println("Global timeout reached. Stopping result collection.");
                break;
            }

            try {
                Integer result = future.get();

                if (result != null) {
                    System.out.println("Result = " + result);
                }

            } catch (ExecutionException e) {
                System.out.println("Skipped failed API: " + e.getCause());
            }

            received++;
        }

        // Cancel remaining running tasks
        executor.shutdownNow();

        System.out.println("System shutdown complete.");
    }
}