package com.jvm.threadgroup;

import java.util.concurrent.TimeUnit;

public class Demo1 {

    public static class R1 implements Runnable {
        @Override
        public void run() {
            System.out.println("threadName:" + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("thread-group-1");
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup, "thread-group-2");
        Thread t1 = new Thread(threadGroup, new R1(), "t1");
        Thread t2 = new Thread(threadGroup, new R1(), "t2");
        Thread t3 = new Thread(threadGroup2, new R1(), "t3");
        t1.start();
        t2.start();
        t3.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("活动线程数:" + threadGroup.activeCount());
        System.out.println("活动线程组:" + threadGroup.activeGroupCount());
        System.out.println("线程组名称:" + threadGroup.getName());
        System.out.println();
        System.out.println("threadGroup2活动线程数:" + threadGroup2.activeCount());

    }
}