package com.jvm;

public class Volatile_ {
    // 不加volatile，线程T1无法停止，那是因为T1看不到被主线程修改之后的flag。
    public static /*volatile*/ boolean flag = true;

    public static class T1 extends Thread {
        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println("线程" + this.getName() + " in");
            while (flag) {
            }
            System.out.println("线程" + this.getName() + "停止了");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new T1("t1").start();
        //主线程休眠1秒
        Thread.sleep(1000);
        // 将flag置为false
        flag = false;
    }
}