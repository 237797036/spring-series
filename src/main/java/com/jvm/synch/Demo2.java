package com.jvm.synch;

public class Demo2 {
    int num = 0;

    // m1()方法是实例方法，多个线程操作m1()时，需要先获取“实例对象demo2”的锁，没有获取到锁的，将等待，直到其他线程释放锁为止
    public synchronized void add() {
        num++; //num++实际上是分3步
    }

    public static class T extends Thread {
        private Demo2 demo2;

        public T(Demo2 demo2) {
            this.demo2 = demo2;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                demo2.add();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Demo2 demo2 = new Demo2();
        T t1 = new T(demo2);
        T t2 = new T(demo2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(demo2.num);
    }
}