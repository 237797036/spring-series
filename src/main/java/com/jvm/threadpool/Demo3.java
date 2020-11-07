package com.jvm.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Demo3 {

    static class Task implements Runnable, Comparable<Task> {

        private int i;
        private String name;

        public Task(int i, String name) {
            this.i = i;
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "处理" + this.name);
        }

        // 倒叙排序任务
        @Override
        public int compareTo(Task o) {
            return Integer.compare(o.i, this.i);
        }
    }

    public static void main(String[] args) {
        // 线程池中只有一个线程
        ExecutorService executor = new ThreadPoolExecutor(1, 1,
                60L, TimeUnit.SECONDS,
                new PriorityBlockingQueue());

        // 除了第一个任务，其他任务按照优先级高低按顺序处理
        for (int i = 0; i < 10; i++) {
            String taskName = "任务" + i;
            executor.execute(new Task(i, taskName));
        }
        for (int i = 100; i >= 90; i--) {
            String taskName = "任务" + i;
            executor.execute(new Task(i, taskName));
        }
        executor.shutdown();
    }
}