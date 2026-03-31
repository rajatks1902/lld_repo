package rks.dev.MultiThreadingPracticeQ;

import java.util.concurrent.*;
/*
Question 11: API Aggregator System (Final Phase - Problem 1)

Problem:
You have 10 external API calls.
Each API:
- Takes variable time
- Returns an integer result

Requirements:
1. Use ThreadPoolExecutor with pool size = 5
2. Limit ONLY 3 APIs running concurrently (use Semaphore)
3. Process results as soon as they complete (NOT after all finish)
4. Print results in completion order
5. If any API fails → skip it (do NOT crash system)

Constraints:
- Use ThreadPoolExecutor
- Use Semaphore
- Use CompletionService
- Do NOT use invokeAll or CompletableFuture

Concepts Tested:
✔ Thread pool management
✔ Concurrency throttling (Semaphore)
✔ Async result processing (CompletionService)
✔ Exception handling in concurrent systems
*/

class ApiTask implements Callable<Integer> {

    private final int apiId;
    private final Semaphore semaphore;

    public ApiTask(int apiId, Semaphore semaphore) {
        this.apiId = apiId;
        this.semaphore = semaphore;
    }

    @Override
    public Integer call() {
        try {
            semaphore.acquire(); // ensure only 3 concurrent

            System.out.println("API " + apiId + " started by " + Thread.currentThread().getName());

            // simulate variable delay
            Thread.sleep((apiId % 3 + 1) * 1000);

            if (apiId == 7) {
                throw new RuntimeException("API failed");
            }

            System.out.println("API " + apiId + " completed");

            return apiId * 10;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            semaphore.release();
        }
    }
}

public class Question11 {

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5, 5,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()
        );

        Semaphore semaphore = new Semaphore(3);

        CompletionService<Integer> completionService =
                new ExecutorCompletionService<>(executor);

        int totalTasks = 10;

        // submit tasks
        for (int i = 1; i <= totalTasks; i++) {
            completionService.submit(new ApiLimitTask(i, semaphore));
        }

        // process results as they complete
        for (int i = 0; i < totalTasks; i++) {
            try {
                Future<Integer> future = completionService.take();
                Integer result = future.get();

                if (result != null) {
                    System.out.println("Result = " + result);
                }

            } catch (ExecutionException e) {
                System.out.println("Skipped failed API: " + e.getCause());
            }
        }

        executor.shutdown();
    }
}
