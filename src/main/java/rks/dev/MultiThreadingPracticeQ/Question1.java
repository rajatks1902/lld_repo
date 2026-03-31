package rks.dev.MultiThreadingPracticeQ;

/*
Question 1: Basic Thread + Runnable

Create 2 threads:
Thread 1 → prints 1 to 5
Thread 2 → prints 6 to 10

Ensure:
✔ Ordered output (1 → 10)
✔ Use threads (no simple loop)
✔ Avoid sleep()

Approach 1:
Use join() to enforce strict ordering

Approach 2:
Run in parallel (ordering NOT guaranteed)
*/

public class Question1 {

    public static void main(String[] args) throws InterruptedException {
        solutionWithOrdering();
        System.out.println();
        solutionWithoutOrdering();
    }

    // ✔ Guarantees ordering using join()
    public static void solutionWithOrdering() throws InterruptedException {
        Thread t1 = new Thread(createTask(1, 5), "T1");
        Thread t2 = new Thread(createTask(6, 10), "T2");

        t1.start();
        t1.join(); // wait for t1 to finish

        t2.start();
        t2.join(); // wait for t2 to finish
    }

    // ❗ Runs in parallel (ordering NOT guaranteed)
    public static void solutionWithoutOrdering() throws InterruptedException {
        Thread t1 = new Thread(createTask(1, 5), "T1");
        Thread t2 = new Thread(createTask(6, 10), "T2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    // Helper method to reduce duplication
    private static Runnable createTask(int start, int end) {
        return () -> {
            for (int i = start; i <= end; i++) {
                System.out.print(i + " ");
            }
        };
    }
}