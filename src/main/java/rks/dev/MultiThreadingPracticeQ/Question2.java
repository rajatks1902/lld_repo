package rks.dev.MultiThreadingPracticeQ;

import java.util.concurrent.*;

/*
Question 2: Callable + ExecutorService + Future

Problem:
- 3 tasks returning 10, 20, 30
- Execute using thread pool
- Collect results and print sum (60)

Key Concept:
✔ Tasks run in parallel
✔ Result retrieval is blocking via Future.get()
*/

public class Question2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            Future<Integer> future1 = executor.submit(() -> 10);
            Future<Integer> future2 = executor.submit(() -> 20);
            Future<Integer> future3 = executor.submit(() -> 30);

            int result = future1.get() + future2.get() + future3.get();

            System.out.println(result);
        } finally {
            executor.shutdown();
        }
    }
}