package com.jvm.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

// 主线程等待5秒之后，唤醒t1线程
public class Demo7 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + " start!");
            LockSupport.park(); //t1调用 LockSupport.park();让当前线程t1等待
            // LockSupport.park();无参数，内部直接会让当前线程处于等待中
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + " 被唤醒!");
        });
        t1.setName("t1");
        t1.start();
        //休眠5秒
        TimeUnit.SECONDS.sleep(5);
        LockSupport.unpark(t1); // 主线程休眠了5秒之后，调用 LockSupport.unpark(t1);将t1线程唤醒
        // unpark方法传递了一个线程对象作为参数，表示将对应的线程唤醒
        System.out.println(System.currentTimeMillis() + ",LockSupport.unpark();执行完毕");
    }
}