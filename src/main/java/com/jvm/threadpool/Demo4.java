package com.jvm.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo4 {
    static AtomicInteger threadNum = new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("自定义线程-" + threadNum.getAndIncrement());
                    return thread;
                });
        for (int i = 0; i < 5; i++) {
            TimeUnit.SECONDS.sleep(1); //就算池中有线程，若未达到corePoolSize个数，则会新建线程执行当前任务
            String taskName = "任务-" + i;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "处理" + taskName);
            });
        }
        // 先给注释掉让程序先不退出，然后通过jstack查看
        executor.shutdown();
    }
}