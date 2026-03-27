package rks.dev.MultiThreadingPracticeQ;

import java.util.List;
import java.util.concurrent.*;

/*
Problem

You have 3 tasks:

Task 1 → takes 5 seconds, returns 50
Task 2 → takes 2 seconds, returns 20
Task 3 → takes 1 second, returns 10


Run all tasks in parallel
Return ONLY the result of the first task that completes
Ignore remaining tasks
 */
public class Question4 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Callable<Integer>> runner = List.of(callable(5), callable(2), callable(1));
        int ans = 0;
        ExecutorService exp = Executors.newFixedThreadPool(3);
        Integer result = exp.invokeAny(runner);

        System.out.println("First Result = " + result);

        exp.shutdown();
//        List<Future<Integer>> result = exp.invokeAll(runner, 1, TimeUnit.SECONDS);
//        for (Future<Integer> f : result) {
//            if (!f.isCancelled()) {
//                ans += f.get();
//            }
//        }
//        exp.shutdown();
//        System.out.println(ans);

    }

    public static Callable<Integer> callable(int n) throws InterruptedException {

        return () -> {
            Thread.sleep(n * 1000L);
            return n * 10;
        };

    }
}
