package com.jvm.interrupt;

import java.util.concurrent.TimeUnit;

public class Demo1 {
    // exit变量必须通过volatile修饰，如果去掉，程序无法正常退出。volatile控制了变量在多线程中的可见性。
    public volatile static boolean exit = false;

    public static class T extends Thread {
        @Override
        public void run() {
            while (true) { //循环处理业务
                if (exit) {
                    break;
                }
            }
        }
    }

    public static void setExit() {
        exit = true;
    }

    public static void main(String[] args) throws InterruptedException {
        T t = new T();
        t.start();
        TimeUnit.SECONDS.sleep(3);
        setExit();
    }
}