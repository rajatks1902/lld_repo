package rks.dev.MultiThreading;


import java.time.LocalTime;
import java.util.List;
import java.util.Collection;
import java.util.concurrent.*;

class ExecutorCallable implements Callable<String> {

    private final String taskName;

    public ExecutorCallable(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String call() throws Exception {

        String threadName = Thread.currentThread().getName();

        System.out.println(
                "[" + LocalTime.now() + "] "
                        + taskName
                        + " started by "
                        + threadName);

        Thread.sleep(2000);

        return "[" + LocalTime.now() + "] "
                + taskName
                + " completed by "
                + threadName;
    }
}

class DummyExecutorService implements ExecutorService {

    private boolean isShutdown = false;
    private int threadCounter = 1;

    @Override
    public <T> Future<T> submit(Callable<T> task) {

        if (isShutdown) {
            throw new RejectedExecutionException(
                    "ExecutorService already shutdown");
        }

        System.out.println("Submitting Callable Task...");

        FutureTask<T> futureTask = new FutureTask<>(task);

        Thread thread = new Thread(
                futureTask,
                "custom-executor-thread-" + threadCounter++);

        thread.start();

        return futureTask;
    }

    @Override
    public void execute(Runnable command) {

        if (isShutdown) {
            throw new RejectedExecutionException(
                    "ExecutorService already shutdown");
        }

        Thread thread = new Thread(
                command,
                "custom-runnable-thread-" + threadCounter++);

        thread.start();
    }

    @Override
    public void shutdown() {

        isShutdown = true;

        System.out.println(
                "ExecutorService shutdown initiated...");
    }

    @Override
    public List<Runnable> shutdownNow() {

        isShutdown = true;

        System.out.println(
                "Force shutdown initiated...");

        return List.of();
    }

    @Override
    public boolean isShutdown() {
        return isShutdown;
    }

    @Override
    public boolean isTerminated() {
        return isShutdown;
    }

    @Override
    public boolean awaitTermination(
            long timeout,
            TimeUnit unit) {

        return true;
    }

    // Simplified implementations for demo

    @Override
    public <T> Future<T> submit(
            Runnable task,
            T result) {

        FutureTask<T> futureTask =
                new FutureTask<>(task, result);

        execute(futureTask);

        return futureTask;
    }

    @Override
    public Future<?> submit(Runnable task) {

        FutureTask<?> futureTask =
                new FutureTask<>(task, null);

        execute(futureTask);

        return futureTask;
    }

    @Override
    public <T> List<Future<T>> invokeAll(
            Collection<? extends Callable<T>> tasks) {

        return List.of();
    }

    @Override
    public <T> List<Future<T>> invokeAll(
            Collection<? extends Callable<T>> tasks,
            long timeout,
            TimeUnit unit) {

        return List.of();
    }

    @Override
    public <T> T invokeAny(
            Collection<? extends Callable<T>> tasks) {

        return null;
    }

    @Override
    public <T> T invokeAny(
            Collection<? extends Callable<T>> tasks,
            long timeout,
            TimeUnit unit) {

        return null;
    }
}

public class Topic_5_ExecutorServiceMain {

    public static void main(String[] args) throws Exception {

        System.out.println(
                "Main Thread : "
                        + Thread.currentThread().getName());

        DummyExecutorService executorService =
                new DummyExecutorService();

        // Callable Task
        Future<String> future1 =
                executorService.submit(
                        new ExecutorCallable("Email Service"));

        Future<String> future2 =
                executorService.submit(
                        new ExecutorCallable("Payment Service"));

        System.out.println("\nWaiting for results...\n");

        System.out.println(future1.get());
        System.out.println(future2.get());

        // Runnable Task
        executorService.execute(() ->
                System.out.println(
                        "Runnable Task executed by : "
                                + Thread.currentThread().getName()));

        // Shutdown
        executorService.shutdown();

        System.out.println(
                "\nIs Shutdown : "
                        + executorService.isShutdown());

        // New task after shutdown -> exception
        try {

            executorService.submit(
                    new ExecutorCallable("Notification Service"));

        } catch (RejectedExecutionException e) {

            System.out.println(
                    "\nTask Rejected : "
                            + e.getMessage());
        }
    }
}
