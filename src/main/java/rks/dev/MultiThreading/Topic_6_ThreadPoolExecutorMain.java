package rks.dev.MultiThreading;

import java.util.concurrent.*;

/*
ThreadPoolExecutor
------------------
Core implementation class of ExecutorService.

Controls:
- Thread creation
- Thread reuse
- Queue management
- Task rejection handling

------------------------------------------------------------

ThreadPoolExecutor executor =
    new ThreadPoolExecutor(

        // Minimum threads always alive
        int corePoolSize,

        // Maximum threads allowed in pool
        int maximumPoolSize,

        // Extra thread idle timeout
        long keepAliveTime,

        // Time unit for keepAliveTime
        TimeUnit unit,

        // Queue to store waiting tasks
        BlockingQueue<Runnable> workQueue,

            // Common Queues:
            // LinkedBlockingQueue  -> unbounded queue
            // ArrayBlockingQueue   -> fixed size queue
            // SynchronousQueue     -> direct thread handoff

        // Custom thread creation
        ThreadFactory threadFactory,

        // Handles task rejection when pool overloaded
        RejectedExecutionHandler handler

            // Common Handlers:
            // AbortPolicy : Rejects the task immediately
            // CallerRunsPolicy : Task executes in caller/main thread
            // DiscardPolicy : Silently discards new task
            // DiscardOldestPolicy : Removes oldest waiting task from queue
    );

------------------------------------------------------------

Task Execution Flow
-------------------

1. New task arrives
       ↓

2. If active threads < corePoolSize
       → create new thread

3. Else push task into queue
       ↓

4. If queue full AND active threads < maximumPoolSize
       → create extra thread

5. Else task rejected
       ↓

6. RejectedExecutionHandler invoked

------------------------------------------------------------

Important Notes
---------------

corePoolSize
    → permanent worker threads

maximumPoolSize
    → burst/peak handling threads

keepAliveTime
    → removes extra idle threads

workQueue
    → stores waiting tasks

RejectedExecutionHandler
    → handles overload scenarios

------------------------------------------------------------

Production Common Configurations
--------------------------------

Fixed Thread Pool:
    corePoolSize == maximumPoolSize

Cached Thread Pool:
    uses SynchronousQueue

Large Queue System:
    LinkedBlockingQueue

Backpressure Handling:
    CallerRunsPolicy

Fail Fast Systems:
    AbortPolicy

------------------------------------------------------------

Internal Architecture
---------------------

Runnable/Callable
        ↓
FutureTask
        ↓
ThreadPoolExecutor
        ↓
Worker Threads

*/

public class Topic_6_ThreadPoolExecutorMain {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 1; i <= 18; i++) {
            int taskId = i;
            threadPoolExecutor.execute(() -> {
                System.out.println(
                        "Task " + taskId +
                                " executed by " +
                                Thread.currentThread().getName()
                );
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
            });
        }
        threadPoolExecutor.shutdown();
    }
}