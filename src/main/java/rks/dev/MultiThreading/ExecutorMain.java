package rks.dev.MultiThreading;

/*
Executor Interface -- accepts a runnable (execute Method)
ExecutorService Interface -- accepts a callable (submit Method) and have other implementation like shutdown or shutdownNow
ThreadPoolExecutor Class -- Concrete implementation of ExecutorService -- allows to define a pool of threads
Executor Class -- provides predefined executor Service
*/

import java.util.concurrent.Executor;

class ExecutorRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("Calling Runnable through Executor");
    }
}

class DummyExecutor implements Executor{

    @Override
    public void execute(Runnable command) {
        Thread t = new Thread(command);
        System.out.println("Executing " + t.getName() + " through Executor");
        t.start();
    }
}

public class ExecutorMain {
 public static void main(String [] args){
     DummyExecutor executorMain = new DummyExecutor();
     executorMain.execute(new ExecutorRunnable());
 }
}



