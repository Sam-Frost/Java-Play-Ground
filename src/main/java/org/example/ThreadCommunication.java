package org.example;

class SharedResource {
    private int data;
    private boolean hasData;
    public synchronized void produce(int data) {
        while(hasData){
            try {
                wait();
            } catch (Exception e){
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Produced : " + data);
        this.data = data;
        hasData = true;
        notify();
    }

    public synchronized int consume(){
        while(!hasData) {
            try {
                wait();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }

        hasData = false;
        System.out.println("Consumed : " + data);
        notify();
        return data;
    }
}

class Producer implements Runnable {

    private final SharedResource resource;

    public Producer(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run(){
        for (int i = 0; i < 10; i++) {
            resource.produce(i);
        }
    }
}

class Consumer implements Runnable {

    private final SharedResource resource;

    public Consumer(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run(){
        for (int i = 0; i < 10; i++) {
            resource.consume();
        }
    }
}

public class ThreadCommunication {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();


        Thread t1 = new Thread(new Consumer(sharedResource), "org.example.Consumer Thread");
        Thread t2 = new Thread(new Producer(sharedResource), "org.example.Producer Thread");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        t1.start();
        t2.start();
    }
}


