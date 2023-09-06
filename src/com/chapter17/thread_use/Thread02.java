package com.chapter17.thread_use;

public class Thread02 {
    public static void main(String[] args) {
        Hello hello = new Hello();
        Hi hi = new Hi();
        Thread thread1 = new Thread(hello);
        Thread thread2 = new Thread(hi);
        thread1.start();
        thread2.start();

    }
}
class Hello implements Runnable {
    int count = 0;
    @Override
    public void run() {
        while (count < 50) {
            System.out.println("hello world " + (++count) + " " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Hi implements Runnable {
    int count = 0;
    @Override
    public void run() {
        while (count < 60) {
            System.out.println("hi " + (++count) + " " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
