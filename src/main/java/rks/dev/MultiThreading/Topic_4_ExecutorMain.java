package rks.dev.MultiThreading;

/*
Executor Framework Overview
---------------------------

1. Executor (Interface)
   - Simplest abstraction for task execution.
   - Executes Runnable tasks using:
         void execute(Runnable command)
   - Hides thread creation and management logic.

   Example:
       executor.execute(new MyTask());

------------------------------------------------------------

2. ExecutorService (Interface)
   - Extends Executor.
   - Provides advanced thread management features.
   - Can execute both:
         Runnable
         Callable
   - Supports task lifecycle operations:
         submit()
         shutdown()
         shutdownNow()
         invokeAll()
         invokeAny()

   Important:
       submit() returns a Future object.

   Example:
       Future<String> future =
               executorService.submit(callableTask);

------------------------------------------------------------

3. ThreadPoolExecutor (Class)
   - Concrete implementation of ExecutorService.
   - Manages a pool of reusable worker threads.
   - Improves performance by avoiding
     repeated thread creation.

   Allows configuration of:
       - core pool size
       - maximum pool size
       - keep alive time
       - blocking queue
       - rejection policy

   Used internally by most factory methods
   in Executors utility class.

------------------------------------------------------------

4. Executors (Utility Class)
   - Factory/helper class.
   - Provides predefined ExecutorService objects.

   Common factory methods:
       Executors.newFixedThreadPool()
       Executors.newCachedThreadPool()
       Executors.newSingleThreadExecutor()
       Executors.newScheduledThreadPool()

   Example:
       ExecutorService service =
               Executors.newFixedThreadPool(5);

------------------------------------------------------------

Execution Flow
---------------

Runnable/Callable Task
          ↓
 Executor / ExecutorService
          ↓
 ThreadPoolExecutor
          ↓
 Worker Threads execute tasks

*/

import java.time.LocalTime;
import java.util.concurrent.Executor;

class ExecutorRunnable implements Runnable {

    private final String taskName;

    public ExecutorRunnable(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {

        System.out.println(
                "[" + LocalTime.now() + "] "
                        + taskName
                        + " started by : "
                        + Thread.currentThread().getName());

        try {
            Thread.sleep(2000); // Simulating task execution
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(
                "[" + LocalTime.now() + "] "
                        + taskName
                        + " completed by : "
                        + Thread.currentThread().getName());
    }
}

class DummyExecutor implements Executor {

    private int threadCount = 1;

    @Override
    public void execute(Runnable command) {

        String threadName = "custom-worker-" + threadCount++;

        Thread thread = new Thread(command, threadName);

        System.out.println(
                "Submitting task to thread : "
                        + thread.getName());

        thread.start();
    }
}

public class Topic_4_ExecutorMain {

    public static void main(String[] args) {

        Executor executor = new DummyExecutor();

        System.out.println("Main thread : "
                + Thread.currentThread().getName());

        executor.execute(new ExecutorRunnable("Email Service"));

        executor.execute(new ExecutorRunnable("Payment Service"));

        executor.execute(new ExecutorRunnable("Notification Service"));

        System.out.println("Main thread continues execution...");
    }
}



