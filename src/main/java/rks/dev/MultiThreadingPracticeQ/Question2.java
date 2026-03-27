package rks.dev.MultiThreadingPracticeQ;

import java.util.concurrent.*;

/*
Problem
3 tasks:
return 10, 20, 30
Run them using:
Callable
ExecutorService
Future
Print: 60
 */
public class Question2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> c1 = () -> 10;
        Callable<Integer> c2 = () -> 20;
        Callable<Integer> c3 = () -> 30;

        ExecutorService exp = Executors.newFixedThreadPool(3);
        Future<Integer> f1 = exp.submit(c1);
        Future<Integer> f2 = exp.submit(c2);
        Future<Integer> f3 = exp.submit(c3);
        System.out.println(f1.get() + f2.get() + f3.get());

        exp.shutdown();

    }
}
