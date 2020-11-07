package com.jvm.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// SynchronousQueue队列的线程池
public class Demo2 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        // 从输出结果看出：系统创建了50个线程处理任务
        // 需要注意，如果需要处理的任务比较耗时，会导致新来的任务都会创建新的线程进行处理，
        // 可能会导致创建非常多的线程，最终耗尽系统资源，触发OOM。
        for (int i = 0; i < 50; i++) {
            int j = i;
            String taskName = "任务" + j;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "处理" + taskName);
                //模拟任务内部处理耗时
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        //关闭线程池
        executor.shutdown();
    }
}