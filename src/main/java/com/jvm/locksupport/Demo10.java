package com.jvm.locksupport;

import java.util.concurrent.locks.LockSupport;

public class Demo10 {
    static class BlockerDemo {
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> LockSupport.park());
        t1.setName("t1");
        t1.start();
        Thread t2 = new Thread(() -> {
            // t2线程的jstack 多了一行：- parking to wait for  <0x0000000782291558> (a com.jvm.locksupport.Demo10$BlockerDemo)
            // 刚好是传入的BlockerDemo对象
            // 方便排查问题，其他暂无他用。
            LockSupport.park(new BlockerDemo());
        });
        t2.setName("t2");
        t2.start();
    }
}