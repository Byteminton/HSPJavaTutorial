package com.chapter17.thread_use;

public class Thread01 {
    public static void main(String[] args) {
        new Cat().start();// start thread

    }
}
// Runnable interface has a run method, and class Thread implements it.
// We can implement run method directly, or override run method in class Thread
class Cat extends Thread{
    @Override
    public void run() {
        while (true) {
            System.out.println("Miao");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
