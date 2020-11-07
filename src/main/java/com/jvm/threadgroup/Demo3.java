package com.jvm.threadgroup;

public class Demo3 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread()); // main线程
        System.out.println(Thread.currentThread().getThreadGroup()); //main线程组
        System.out.println(Thread.currentThread().getThreadGroup().getParent()); // system线程组
        System.out.println(Thread.currentThread().getThreadGroup().getParent().getParent()); // null
    }
}