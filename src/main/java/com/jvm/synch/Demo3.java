package com.jvm.synch;

public class Demo3 {
    static int num = 0;

    public static synchronized void m1() {
        for (int i = 0; i < 10000; i++) {
            num++;
        }
    }

    // m1和下面的写法类似
    public static void m2() {
        synchronized (Demo3.class) {
            for (int i = 0; i < 10000; i++) {
                num++;
            }
        }
    }

    public static class T1 extends Thread {
        @Override
        public void run() {
            //Demo3.m1();
            Demo3.m2();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1();
        T1 t2 = new T1();
        T1 t3 = new T1();
        t1.start();
        t2.start();
        t3.start();
        //等待3个线程结束打印num
        t1.join();
        t2.join();
        t3.join();
        System.out.println(Demo3.num);
        // 打印结果：30000
    }
}