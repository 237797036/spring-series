package com.jvm.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作，要求只能初始化一次
 */
public class Demo5 {

    /**
     * isInit属性必须要volatile修饰，可以确保变量的可见性
     */
    //isInit用来标注是否被初始化过
    volatile Boolean isInit = Boolean.FALSE;

    static Demo5 demo5 = new Demo5();

    private AtomicReferenceFieldUpdater<Demo5, Boolean> updater = AtomicReferenceFieldUpdater.newUpdater(Demo5.class, Boolean.class, "isInit");

    /**
     * 模拟初始化工作
     */
    public void init() throws InterruptedException {
        // isInit为false的时候，才进行初始化，并将isInit采用原子操作置为true
        // 多个线程同时到达updater.compareAndSet，只有一个会成功
        if (updater.compareAndSet(demo5, Boolean.FALSE, Boolean.TRUE)) {
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "，开始初始化!");
            //模拟休眠3秒
            TimeUnit.SECONDS.sleep(3);
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "，初始化完毕!");
        } else {
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "，有其他线程已经执行了初始化!");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    demo5.init();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}