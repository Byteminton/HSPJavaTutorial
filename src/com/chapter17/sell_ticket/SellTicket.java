package com.chapter17.sell_ticket;

public class SellTicket {
    public static void main(String[] args) {

        RequestTicket requestTicket = new RequestTicket();
        Thread thread1 = new Thread(requestTicket);
        Thread thread2 = new Thread(requestTicket);
        Thread thread3 = new Thread(requestTicket);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
class RequestTicket implements Runnable {
    private static int num = 100;
    @Override
    public synchronized void run() {
        while (num > 0) {
            num--;
            System.out.println("窗口" + Thread.currentThread().getName() + "售出一张票，余票还有" + num + "张");
            try {
                Thread.sleep((int) (Math.random() * 50));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}


