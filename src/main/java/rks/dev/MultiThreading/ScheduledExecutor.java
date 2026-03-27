package rks.dev.MultiThreading;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutor {

    public static void main(String[] args) throws IOException {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable r = () -> {
            System.out.println("Running Thread");
        };

        executor.scheduleAtFixedRate(r, 5, 5, TimeUnit.SECONDS);
    }
}
