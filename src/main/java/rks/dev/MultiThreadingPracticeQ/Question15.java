package rks.dev.MultiThreadingPracticeQ;

import java.util.concurrent.*;

/*
Question 15: End-to-End Aggregator Service

Scenario:
You are building a backend service for an e-commerce homepage.

You need to fetch:

1. User Info         → getUser()
2. Orders Count      → getOrders()
3. Recommendations   → getRecommendations()

---

Requirements:

1. Run ALL APIs in parallel using CompletableFuture

2. Each API must:
   ✔ Have a TIMEOUT (2 seconds)
   ✔ Have a FALLBACK value on failure

   Defaults:
   - User → "Guest"
   - Orders → 0
   - Recommendations → 0

---

3. LIMIT concurrency:
   👉 At most 2 APIs should run at a time (use Semaphore)

---

4. Use custom Executor (ThreadPoolExecutor)
   👉 Do NOT use default ForkJoinPool

---

5. Combine all results into final response:
   Example:
   "User=Rajat, Orders=5, Score=80"

---

6. Print final result

---

Constraints:

- MUST use:
    ✔ CompletableFuture
    ✔ ThreadPoolExecutor
    ✔ Semaphore

- Do NOT:
    ❌ Block early (no get() before final stage)
    ❌ Use invokeAll
*/
public class Question15 {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        Semaphore semaphore = new Semaphore(2);

        CompletableFuture<String> f1 = loggedInUser("Messi", semaphore, executor)
                .orTimeout(2, TimeUnit.SECONDS)
                .exceptionally(ex -> "Guest");

        CompletableFuture<Integer> f2 = orderCount(10, semaphore, executor)
                .orTimeout(2, TimeUnit.SECONDS)
                .exceptionally(ex -> 0);

        CompletableFuture<Integer> f3 = recommendation(20, semaphore, executor)
                .orTimeout(2, TimeUnit.SECONDS)
                .exceptionally(ex -> 0);

        CompletableFuture.allOf(f1, f2, f3)
                .thenApply(v ->
                        "User=" + f1.join() +
                                ", Orders=" + f2.join() +
                                ", Score=" + f3.join()
                )
                .thenAccept(System.out::println)
                .join();

        executor.shutdown();
    }

    private static CompletableFuture<String> loggedInUser(
            String user, Semaphore semaphore, Executor executor) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                semaphore.acquire();
                simulateDelay(1000);
                return user;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                semaphore.release();
            }
        }, executor);
    }

    private static CompletableFuture<Integer> orderCount(
            int orderCnt, Semaphore semaphore, Executor executor) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                semaphore.acquire();
                simulateDelay(1500);
                return orderCnt;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                semaphore.release();
            }
        }, executor);
    }

    private static CompletableFuture<Integer> recommendation(
            int rnd, Semaphore semaphore, Executor executor) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                semaphore.acquire();

                // simulate timeout scenario
                if (System.currentTimeMillis() % 2 == 0) {
                    simulateDelay(3000); // will timeout
                }

                return rnd;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                semaphore.release();
            }
        }, executor);
    }

    private static void simulateDelay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}