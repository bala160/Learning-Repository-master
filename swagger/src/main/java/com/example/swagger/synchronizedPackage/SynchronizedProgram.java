package com.example.swagger.synchronizedPackage;

public class SynchronizedProgram {

    int count;

    public synchronized void increment() {
        count++;
    }

    public static void main(String[] args) throws InterruptedException {

        SynchronizedProgram s = new SynchronizedProgram();


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    s.increment();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    s.increment();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.print(s.count);
    }
}
