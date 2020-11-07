package com.jvm.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 第二个任务依赖第一个任务的结果。1出现异常，2不会执行
 *
 * @author zhongJun
 * @date 2020-10-12 17:24
 */
public class Demo2 {

    public static void main(String[] args) {
        try {
            thenApply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void thenApply() throws Exception {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                long result = new Random().nextInt(100);
                System.out.println("result1=" + result);
                int i = 10 / 0;
                return result;
            }
        }).thenApply(new Function<Long, Long>() {
            @Override
            public Long apply(Long t) {
                long result = t * 5;
                System.out.println("result2=" + result);
                return result;
            }
        });

        long result = future.get();
        System.out.println(result);
    }
}
