package com.jvm.reentrantlock;

import java.util.concurrent.TimeUnit;

public class Demo6_2 {
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static class T extends Thread {
        int flag;

        public T(String name, int flag) {
            super(name);
            this.flag = flag;
        }

        // ock1被线程t1占用，lock2倍线程t2占用，线程t1在等待获取lock2，线程t2在等待获取lock1，都在相互等待获取对方持有的锁，最终产生了死锁！
        // 如果是在synchronized关键字情况下发生了死锁现象，程序是无法结束的。
        @Override
        public void run() {
            try {
                if (flag == 1) {
                    synchronized (lock1){
                        TimeUnit.SECONDS.sleep(1);
                        synchronized (lock2){
                            System.out.println("t1 获取到了 lock2锁");
                        }
                    }
                } else {
                    synchronized (lock2){
                        TimeUnit.SECONDS.sleep(1);
                        synchronized (lock1){
                            System.out.println("t2 获取到了 lock1锁"); // t2会阻塞于此
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("中断标志:" + this.isInterrupted());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T t1 = new T("t1", 1);
        T t2 = new T("t2", 2);
        t1.start();
        t2.start();

        // 即使打断，程序亦会死锁，因synchronized锁不能响应中断。
        TimeUnit.SECONDS.sleep(5);
        t2.interrupt();
    }
}