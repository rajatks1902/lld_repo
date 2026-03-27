package rks.dev.MultiThreadingPracticeQ;

/*
You have a list of tasks (say 10 tasks), each returning an integer.

👉 Requirement:

Run all tasks in parallel
As soon as each task completes, process its result immediately
(i.e., don’t wait for all tasks like invokeAll)
Print results in order of completion, NOT submission

⚠️ Constraints
Use ExecutorService
Do NOT:
use invokeAll()
block on all futures at once
Must process results as they complete
 */

import java.util.List;
import java.util.concurrent.*;

public class Question5 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Callable<Integer>> runner = List.of(callable(1), callable(2), callable(3), callable(4), callable(5), callable(6), callable(7), callable(8), callable(9), callable(10));
        ExecutorService executor = Executors.newFixedThreadPool(10);
        ExecutorCompletionService<Integer> exp =
                new ExecutorCompletionService<>(executor);
        for (Callable<Integer> r : runner) {
            exp.submit(r);
        }

        int cnt = 0;
        while (cnt != 10) {
            Future<Integer> f = exp.poll(1, TimeUnit.SECONDS);
            if (f != null && !f.isCancelled()) {
                System.out.println(f.get());
                cnt++;
            }
        }
//        for (int i = 0; i < 10; i++) {
//    Future<Integer> f = exp.take();  // blocks until next completes
//    System.out.println(f.get());
//}
        executor.shutdown();
    }

    public static Callable<Integer> callable(int n) throws InterruptedException {
        return () -> {
            if (n % 2 == 0)
                Thread.sleep(n * 1000L);
            else
                Thread.sleep(2 * n * 1000L);
            return n * 10;
        };

    }
}
