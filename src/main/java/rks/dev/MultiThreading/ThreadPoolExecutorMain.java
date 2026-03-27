package rks.dev.MultiThreading;

import java.util.concurrent.*;

// ThreadPoolExecutor is a core implementation of ExecutorService
/*
ThreadPoolExecutor executor =
    new ThreadPoolExecutor(
        int corePoolSize,
        int maximumPoolSize,
        long keepAliveTime,
        TimeUnit unit,
        BlockingQueue<Runnable> workQueue,  [LinkedBlockingQueue ,ArrayBlockingQueue ,SynchronousQueue]
        ThreadFactory threadFactory,
        RejectedExecutionHandler handler
    );
 */
public class ThreadPoolExecutorMain {

    public static void main(String [] args){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,10, TimeUnit.SECONDS,new LinkedBlockingQueue<>(4), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 1; i <= 8; i++) {
            int taskId = i;
            threadPoolExecutor.execute(() -> {
                System.out.println(
                        "Task " + taskId +
                                " executed by " +
                                Thread.currentThread().getName()
                );
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            });
        }
        threadPoolExecutor.shutdown();
    }
}