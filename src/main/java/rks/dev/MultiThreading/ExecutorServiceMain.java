package rks.dev.MultiThreading;


import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

class ExecutorServiceRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("Running ExecutorServiceRunnable Runnable");
    }
}

class ExecutorCallable implements Callable<String>{

    @Override
    public String call() throws Exception {
        return "Calling ExecutorService Callable Thread";
    }
}

class DummyExecutorService implements ExecutorService{

    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return List.of();
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        System.out.println("Calling DummyExecutorService");
        FutureTask<T> ft = new FutureTask<>(task);
        Thread thread = new Thread(ft);
        thread.start();
       return ft;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return null;
    }

    @Override
    public Future<?> submit(Runnable task) {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return List.of();
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return List.of();
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
public class ExecutorServiceMain {

    public static  void main (String [] args) throws Exception{
        DummyExecutorService dummyExecutorService = new DummyExecutorService();
        Future<String> ft = dummyExecutorService.submit(new ExecutorCallable());
        System.out.println(ft.get());

        // ShutDown Functionality
        ExecutorService dummyExecutorService1 = Executors.newFixedThreadPool(10);
        dummyExecutorService1.execute(() -> System.out.println("Main Lambda Thread "));
        dummyExecutorService1.shutdown();
    }
}
