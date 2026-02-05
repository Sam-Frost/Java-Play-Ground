package org.example;

import java.util.concurrent.*;

public class ExecutorFramework {
    public static void main(String[] args) {

       int numberOfServices = 3;

       ExecutorService executorService  = Executors.newFixedThreadPool(numberOfServices);
//       CountDownLatch latch = new CountDownLatch(numberOfServices);



    }
}
