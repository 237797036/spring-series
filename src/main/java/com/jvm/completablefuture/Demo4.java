package com.jvm.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * @Author zhongJun
 * @Date 2020-10-12 18:09
 */
public class Demo4 {
    public static void main(String[] args) {
        try {
            thenAccept();
            System.out.println("---------------");
            thenRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void thenAccept() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt(10);
            }
        }).thenAccept(integer -> {
            System.out.println(integer);
        });
        System.out.println(future.get()); //此处会返回null
    }

    public static void thenRun() throws Exception{
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt(10);
            }
        }).thenRun(() -> {
            System.out.println("thenRun ...");
            //不同的是上个任务处理完成后，并不会把计算的结果传给 thenRun 方法。
            // 只是处理玩任务后，执行 thenRun 的后续操作。
        });
        System.out.println(future.get()); //最终返回null
    }
}
