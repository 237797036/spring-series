package com.jvm.getresult6;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 创建了一个FutureTask对象，调用futureTask.get()会阻塞当前线程，子线程中休眠了3秒，然后调用futureTask.run();
 * 当futureTask的run()方法执行完毕之后，futureTask.get()会从阻塞中返回。
 */
public class Demo5 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(System.currentTimeMillis());

        //创建一个FutureTask
        FutureTask<Integer> futureTask = new FutureTask<>(() -> 10);

        //在一个线程内部运行futureTask.run()方法
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            futureTask.run();
        }).start();
        System.out.println(System.currentTimeMillis());
        //futureTask.get()会阻塞当前线程，直到futureTask执行完毕
        Integer result = futureTask.get();
        System.out.println(System.currentTimeMillis() + ":" + result);
    }
}