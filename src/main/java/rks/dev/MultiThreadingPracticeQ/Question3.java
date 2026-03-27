package rks.dev.MultiThreadingPracticeQ;

import java.util.List;
import java.util.concurrent.*;

/*
3 tasks (10, 20, 30)
One task may throw exception
You must:
Ignore failed tasks
Sum only successful ones
Program should NOT crash
 */
public class Question3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

//        Solution1();

        //Optimised Solution
        Solution2();

    }

    private static void Solution2() throws InterruptedException, ExecutionException {

        List<Callable<Integer>> runner = List.of(callable(1), callable(1), callable(3));
        ExecutorService exp = Executors.newFixedThreadPool(3);
        List<Future<Integer>> futures =
                exp.invokeAll(runner, 2, TimeUnit.SECONDS);

        int ans = 0;
        for (Future<Integer> f : futures) {
            if (!f.isCancelled()) {
                ans += f.get();
            }
        }

        System.out.println("Partial Sum = " + ans);

        exp.shutdown();

    }

    public static Callable<Integer> callable(int n) throws InterruptedException {

        return () -> {
            Thread.sleep(n * 1000L);
            return n * 10;
        };

    }

    private static void Solution1() throws InterruptedException {

        Callable<Integer> c1 = () -> {
            Thread.sleep(5000);
            return 50;
        };
        Callable<Integer> c2 = () -> {
            Thread.sleep(2000);
            return 20;
        };
        Callable<Integer> c3 = () -> {
            Thread.sleep(1000);
            return 10;
        };
        ExecutorService exp = Executors.newFixedThreadPool(3);
        Future<Integer> f1 = exp.submit(c1);
        Future<Integer> f2 = exp.submit(c2);
        Future<Integer> f3 = exp.submit(c3);
        Thread.sleep(2000);
        exp.shutdownNow();
        int a = 0, b = 0, c = 0;
        try {
            a = f1.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("f1 " + e);
        }

        try {
            b = f2.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("f2 " + e);
        }

        try {
            c = f3.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("f3 " + e);
        }

        System.out.println(a + b + c);

    }
}
