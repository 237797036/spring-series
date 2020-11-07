package com.jvm.blockqueue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class Demo3 {

    // 同步阻塞队列
    static SynchronousQueue<String> queue = new SynchronousQueue<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                long starTime = System.currentTimeMillis();
                queue.put("java高并发系列，路人甲Java!"); //调用queue.put方法向队列中丢入一条数据，产生了阻塞
                long endTime = System.currentTimeMillis();
                System.out.println(String.format("%s,%s,take耗时:%s,%s", starTime, endTime, (endTime - starTime), Thread.currentThread().getName()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        //休眠5秒之后，从队列中take一个元素
        TimeUnit.SECONDS.sleep(5);
        System.out.println(System.currentTimeMillis() + "调用take获取并移除元素," + queue.take()); // 直到take方法被调用，put方法才从阻塞状态恢复
    }
}