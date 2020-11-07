package com.jvm.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Demo6 {
    private static ReentrantLock lock1 = new ReentrantLock(false);
    private static ReentrantLock lock2 = new ReentrantLock(false);

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
                    lock1.lockInterruptibly();
                    TimeUnit.SECONDS.sleep(1);
                    lock2.lockInterruptibly();
                    System.out.println("t1 获取到了 lock2锁");
                } else {
                    lock2.lockInterruptibly();
                    TimeUnit.SECONDS.sleep(1);
                    lock1.lockInterruptibly(); // t2会阻塞于此
                    System.out.println("t2 获取到了 lock1锁");
                }
            } catch (InterruptedException e) {
                System.out.println(this.getName() + "中断标志:" + this.isInterrupted());
                e.printStackTrace();
            } finally {
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                if (lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T t1 = new T("t1", 1);
        T t2 = new T("t2", 2);
        t1.start();
        t2.start();

        // 代码的31行触发了异常，中断标志输出：false
        // t2在31行一直获取不到lock1的锁，主线程中等待了5秒之后，t2线程调用了interrupt()方法，将线程的中断标志置为true，
        // 此时31行会触发 InterruptedException异常，然后线程t2可以继续向下执行，释放了lock2的锁，然后线程t1可以正常获取锁，程序得以继续进行。
        TimeUnit.SECONDS.sleep(3);
        t2.interrupt();
    }
}