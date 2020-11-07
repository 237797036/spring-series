package com.jvm.guava;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 案例1：异步执行任务完毕之后回调（写法1）
 */
public class Demo1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //创建一个线程池
        ExecutorService delegate = Executors.newFixedThreadPool(5);

        try {
            ListeningExecutorService executorService = MoreExecutors.listeningDecorator(delegate);
            //异步执行一个任务
            ListenableFuture<Integer> listenableFuture = executorService.submit(() -> {
                System.out.println(System.currentTimeMillis());
                //休眠2秒，默认耗时
                TimeUnit.SECONDS.sleep(2);
                System.out.println(System.currentTimeMillis());
                return 10;
            });
            //当任务执行完毕之后回调对应的方法
            listenableFuture.addListener(() -> System.out.println("任务执行完毕了，我被回调了"), MoreExecutors.directExecutor());
            System.out.println(listenableFuture.get()); // 会阻塞当前线程直到任务执行完毕
        } finally {
            delegate.shutdown();
        }
    }
}