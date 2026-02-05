package org.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting Spring Boot Main Application!!!");

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CountDownLatch countDownLatch = new CountDownLatch(4);

        executorService.submit(new Dependency("Database", 3000, countDownLatch));
        executorService.submit( new Dependency("Redis", 6000, countDownLatch));
        executorService.submit(new Dependency("API Gateway", 9000, countDownLatch));
        executorService.submit(new Dependency("Maps API", 12000, countDownLatch));

        countDownLatch.await();


        System.out.println("Spring Boot Application Started, Accepting Requests!!!!");
    }
}

class Dependency implements Runnable{
    private final String name;
    private final CountDownLatch latch;
    private final int timeout;

    public Dependency(String name, int timeout, CountDownLatch latch){
        this.name = name;
        this.latch = latch;
        this.timeout = timeout;
    }


    @Override
    public void run() {
        try {
            System.out.println("Starting connection with " + name + " ..........");
            Thread.sleep(timeout);
        } catch (Exception e ) {
            throw new RuntimeException();
        }
        System.out.println("Successfully connected with " + name + "!");
        latch.countDown();
    }
}
