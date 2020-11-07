package com.jvm.getresult6;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Demo3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println(System.currentTimeMillis());
        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        //关闭线程池
        executorService.shutdown();
        System.out.println(System.currentTimeMillis());

        Integer result = future.get();

        System.out.println(System.currentTimeMillis() + ":" + result);
    }
}