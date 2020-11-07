package com.jvm.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * handle()
 * 任务1出现异常，2继续执行
 * @Author zhongJun
 * @Date 2020-10-12 17:57
 */
public class Demo3 {

    public static void main(String[] args) {
        try {
            handle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handle() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {

            @Override
            public Integer get() {
                Integer r1 = new Random().nextInt(10);
//                int i = 10 / 0;
                System.out.println("result1=" + r1);
                return r1;
            }
        }).handle(new BiFunction<Integer, Throwable, Integer>() {
            @Override
            public Integer apply(Integer param, Throwable throwable) {
                int result = -1;
                if (throwable == null) {
                    result = param * 2;
                } else {
                    System.out.println(throwable.getMessage());
                }
                return result;
            }
        });
        System.out.println(future.get());
    }
}
