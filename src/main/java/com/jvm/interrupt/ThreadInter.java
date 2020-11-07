package com.jvm.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author zhongJun
 * @date 2020-09-21 23:25
 */
public class ThreadInter {
    public static void main(String[] args) {
        inter1();
        inter2();
        inter4();
        //inter3();
    }

    public static void inter1() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (this.isInterrupted()) {
                        System.out.println("我要退出了1");
                        break;
                    }
                }
            }
        };
        thread1.setName("thread1");
        thread1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            thread1.interrupt(); //被调用之后，线程的中断标志将被置为true
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static volatile boolean isStop = false;

    public static void inter2() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (isStop) {
                        System.out.println("我要退出了2");
                        break;
                    }
                }
            }
        };
        thread1.setName("thread2");
        thread1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            isStop = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static volatile boolean isStop2 = false;

    public static void inter3() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    // 休眠100秒
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isStop2) {
                        System.out.println("我要退出了3，是不可能的");
                        break;
                    }
                }
            }
        };
        thread1.setName("thread3");
        thread1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            isStop2 = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void inter4() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    // 休眠100秒
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        //注意：此时是无法退出线程的
                        //sleep方法由于中断而抛出异常之后，线程的中断标志会被清除（置为false），
                        // 所以在异常中需要执行this.interrupt()方法，将中断标志位置为true。
                        this.interrupt();
                        e.printStackTrace();
                    }
                    System.out.println(this.isInterrupted());
                    if (this.isInterrupted()) {
                        System.out.println("我要退出了4");
                        break;
                    }
                }
            }
        };
        thread1.setName("thread4");
        thread1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            thread1.interrupt(); //被调用之后，线程的中断标志将被置为true
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}


