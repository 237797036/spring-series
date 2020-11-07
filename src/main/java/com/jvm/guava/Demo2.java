package com.jvm.guava;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 案例1：异步执行任务完毕之后回调（写法2）
 */
public class Demo2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService delegate = Executors.newFixedThreadPool(5);

        try {
            ListeningExecutorService executorService = MoreExecutors.listeningDecorator(delegate);

            ListenableFuture<Integer> listenableFuture = executorService.submit(() -> {
                System.out.println(System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(4);
//                int i = 10 / 0;
                System.out.println(System.currentTimeMillis());
                return 10;
            });
            Futures.addCallback(listenableFuture, new FutureCallback<Integer>() {
                @Override
                public void onSuccess(@Nullable Integer result) {
                    System.out.println("执行成功:" + result);
                }

                @Override
                public void onFailure(Throwable t) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("执行任务发生异常:" + t);
                }
            }, MoreExecutors.directExecutor());

            System.out.println("result==" + listenableFuture.get());
        } finally {
            delegate.shutdown();
        }
    }
}