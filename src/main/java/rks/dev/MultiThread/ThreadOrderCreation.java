package rks.dev.MultiThread;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
   --> Flow  -- Order Creation -> Sending SMS -> Sending Email -> Calculation ETA
   ---> We Can perform sending sms/email in different thread : both are not dependent

 */
public class ThreadOrderCreation {

    public  static  void main(String[] args) throws ExecutionException, InterruptedException {

        // Order Created

        // Way 1st
        SMSThread smsThread = new SMSThread();
        EmailThread emailThread = new EmailThread();
        System.out.println("Task Started");
        /*
            if We Directly call the run method it wouldn't execute it in another thread
         */
        smsThread.start();
        System.out.println("Task 1 Started");
        emailThread.start();
        System.out.println("Task 2 Started");
        /*
        Expected OutCome :
                Task Started
                Task 1 Started
                Task 2 Started
                SMS Sending Task Done
                Email Sending Task Done
         */

        try{
            smsThread.join();
            emailThread.join();
            System.out.println("Both the Task are done");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



        // 2nd Way
        System.out.println("Task Started");
        System.out.println("Task 1 Started");
        new Thread( new SMSRunnable()).start();
        System.out.println("Task 2 Started");
        new Thread( new EmailRunnable()).start();

         /*
        Expected OutCome :
                Task Started
                Task 1 Started
                Task 2 Started
                SMS  Task Done
                Email  Task Done
         */

        FutureTask<String> eta = new FutureTask<>(new ETACalculator());
        Thread etaThread = new Thread(eta);
        etaThread.start();
        String etaResult =  eta.get();
        System.out.println(etaResult) ;
    }
}

class SMSThread extends Thread{

    public void run(){
        try{
            Thread.sleep(2000);
            System.out.println("SMS Sending Task Done");
        }catch (InterruptedException exp){
            exp.printStackTrace();
        }
    }
}

class EmailThread extends Thread{
    public void run(){
        try{
            Thread.sleep(3000);
            System.out.println("Email Sending Task Done");
        }catch (InterruptedException exp){
            exp.printStackTrace();
        }
    }
}

class SMSRunnable implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("SMS Task Done");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

class EmailRunnable implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("Email Task Done");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

/*
 Callable is a functional interface (like Runnable) introduces in java 5 (java.uti.concurrent)
 ---> allows Returning a result
 ---> Throwing checked Exception
 */

class ETACalculator implements Callable<String>{

    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        System.out.println("ETA Task Done");
        return "ETACalculationDone";
    }
}