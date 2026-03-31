package rks.dev.MultiThreadingPracticeQ;

import java.util.List;
import java.util.concurrent.*;

/*
Question 5: Process Tasks in Completion Order

Problem:
- 10 tasks
- Execute in parallel
- Process results as they complete
- Print in completion order (NOT submission order)

Approach:
✔ Use ExecutorCompletionService
✔ Use take() to block efficiently
*/

public class Question5 {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            List<Callable<Integer>> tasks = List.of(
                    createTask(1), createTask(2), createTask(3), createTask(4),
                    createTask(5), createTask(6), createTask(7), createTask(8),
                    createTask(9), createTask(10)
            );

            CompletionService<Integer> completionService =
                    new ExecutorCompletionService<>(executor);

            // Submit all tasks
            for (Callable<Integer> task : tasks) {
                completionService.submit(task);
            }

            // Process results in completion order
            for (int i = 0; i < tasks.size(); i++) {
                try {
                    Future<Integer> future = completionService.take(); // blocks
                    System.out.println(future.get());
                } catch (ExecutionException e) {
                    System.out.println("Task failed: " + e.getCause());
                }
            }

        } finally {
            executor.shutdown();
        }
    }

    // Helper method
    private static Callable<Integer> createTask(int n) {
        return () -> {
            if (n % 2 == 0)
                Thread.sleep(n * 1000L);
            else
                Thread.sleep(2 * n * 1000L);

            return n * 10;
        };
    }
}