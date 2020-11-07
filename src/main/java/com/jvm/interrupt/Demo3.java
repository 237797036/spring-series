package com.jvm.interrupt;

import java.util.concurrent.TimeUnit;

public class Demo3 {
    public static class T extends Thread {
        @Override
        public void run() {
            while (true) { //循环处理业务
                //下面模拟阻塞代码
                try {
                    TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 当触发InterruptedException异常时候，线程内部的中断标志又会被清除（变为false）
                    // 所以在catch中又调用了this.interrupt()一次，将中断标志置为true
                    this.interrupt();
                }
                if (this.isInterrupted()) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T t = new T();
        t.start();
        // main方法中调用了t.interrupt()方法,此时线程t内部的中断标志会置为true，
        // 然后会触发run()方法内部的InterruptedException异常，所以运行结果中有异常输出
        t.interrupt();
    }
}