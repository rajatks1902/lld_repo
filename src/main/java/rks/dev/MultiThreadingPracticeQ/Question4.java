package rks.dev.MultiThreadingPracticeQ;

import java.util.List;
import java.util.concurrent.*;

/*
Question 4: First Completed Task

Problem:
- 3 tasks with different execution times
- Execute in parallel
- Return ONLY the first completed result
- Ignore remaining tasks

Approach:
✔ Use invokeAny()
✔ Automatically cancels remaining tasks
*/

public class Question4 {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            List<Callable<Integer>> tasks = List.of(
                    createTask(5),
                    createTask(2),
                    createTask(1)
            );

            Integer result = executor.invokeAny(tasks);

            System.out.println("First Result = " + result);

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    // Helper method
    private static Callable<Integer> createTask(int seconds) {
        return () -> {
            Thread.sleep(seconds * 1000L);
            return seconds * 10;
        };
    }
}