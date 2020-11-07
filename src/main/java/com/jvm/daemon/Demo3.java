package com.jvm.daemon;

import java.util.concurrent.TimeUnit;

public class Demo3 {
    // 设置守护线程，需要在start()方法之前进行，在后面调用‘会报异常’，并且不起效
    public static void main(String[] args) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        t1.setDaemon(true);
    }
}