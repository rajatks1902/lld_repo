package rks.dev.MultiThread;

import java.util.concurrent.*;

/*
    --> Implementing limitless thread can cause issue and lots of context switching
    ---> Executor manages this problem by limiting it
 */
public class ThreadExecutor {


    /*  -->Creating a Executor Service having 10 Thread
           if the task are over 10 then they wait in the queue managed by ExecutorService
     */
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static  void main(String[] args) throws ExecutionException, InterruptedException {
       for(int i=0;i<25;i++){
           sendEmail("user" + i +"@gmail.com");
       }
    }

    public static void sendEmail(String name) throws ExecutionException, InterruptedException {
        // execute expect a runnable and has a void return type
        executor.execute(
                () -> {
                    System.out.println("Sending email to " + name  + " on " + Thread.currentThread().getName());
                    try{
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Email sent to " + name);
                });

        /*  --> submit() is used for both runnable and callable
            --> it return Future object also allow to handle exceptions
         */

        Future<Integer> future = executor.submit(
                () -> {
                    System.out.println("Running through Submit flow");
                    return 101;
                }
        );
        System.out.println("Value through Future Interface" + future.get());



        /* Used to shut down executor
        .shutdown() when the queue is empty
        .shutdownNow()  : attempts to stop all actively executing tasks , halts the processing of waiting tasks
                            and return a list of the tasks that were waiting to be executed.
        executor.shutdown();
         */

    }

    public void testAtInterval(){

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println("Testing");
        // delay here is the difference b/w 2 Task on the basis of start Time
        scheduledExecutorService.scheduleAtFixedRate(task,0,5,TimeUnit.SECONDS);

        // delay here is termination of 1 task to starting of another
        scheduledExecutorService.scheduleWithFixedDelay(task,0,5,TimeUnit.SECONDS);


    }
}
