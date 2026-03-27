package rks.dev.MultiThreadingPracticeQ;

/*
Question 1: Basic Thread + Runnable

You create 2 threads using Runnable
Each thread prints numbers from:
Thread 1 → 1 to 5
Thread 2 → 6 to 10
Ensure:
👉 Output is always in order (1 → 10)

Constraints (important)
You must use threads (no simple loop)
You must ensure ordering
Avoid using sleep() for ordering (bad practice)

--> Solution1()
 */

/*
To guarantee strict ordering, I used join().
This sacrifices parallelism, but ensures correctness.
 If parallelism was required along with ordering,
 I would use synchronization mechanisms. -- > Solution2();
 */
public class Question1 {

    public static void main(String[] args) throws InterruptedException {

        solution1();
        System.out.println();
        solution2();
    }

    public static void solution1() throws InterruptedException {
        Runnable r = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.print(i + " ");
            }
        };
        Thread t = new Thread(r);
        t.start();
        t.join();
        Runnable r1 = () -> {
            for (int i = 6; i <= 10; i++) {
                System.out.print(i + " ");
            }
        };
        Thread t1 = new Thread(r1);
        t1.start();
        t1.join();
    }

    public static void solution2() throws InterruptedException {
        Runnable r = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.print(i + " ");
            }
        };
        Runnable r1 = () -> {
            for (int i = 6; i <= 10; i++) {
                System.out.print(i + " ");
            }
        };
        Thread t = new Thread(r);
        Thread t1 = new Thread(r1);
        t.start();
        t1.start();
        t.join();
        t1.join();
    }


}
