package rks.dev.MultiThreadingPracticeQ;

import java.util.List;
import java.util.concurrent.*;

/*
Question 3: Handling Partial Failures

Problem:
- 3 tasks (10, 20, 30)
- Some tasks may fail or timeout
- Ignore failed tasks
- Sum only successful results
- Program should NOT crash

Approach:
✔ Use invokeAll with timeout
✔ Handle cancellation + exceptions
*/

public class Question3 {

    public static void main(String[] args) throws InterruptedException {
        solutionWithTimeout();
    }

    private static void solutionWithTimeout() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            List<Callable<Integer>> tasks = List.of(
                    createTask(1),
                    createTask(2),
                    createTask(3)
            );

            List<Future<Integer>> futures =
                    executor.invokeAll(tasks, 2, TimeUnit.SECONDS);
            int sum = 0;
            for (Future<Integer> future : futures) {
                try {
                    if (!future.isCancelled()) {
                        sum += future.get();
                    }
                } catch (ExecutionException e) {
                    // Ignore failed task
                    System.out.println("Task failed: " + e.getCause());
                }
            }

            System.out.println("Partial Sum = " + sum);

        } finally {
            executor.shutdown();
        }
    }

    // Helper method (clean + correct)
    private static Callable<Integer> createTask(int n) {
        return () -> {
            Thread.sleep(n * 1000L);

            // simulate failure
            if (n == 2) {
                throw new RuntimeException("Failure in task " + n);
            }

            return n * 10;
        };
    }
}