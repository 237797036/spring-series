package com.aop.demo9.test14;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Aspect
public class Aspect14 {

    @Pointcut("AspectPcDefine.pc1()")
    public void pointcut1() {
    }

    @Pointcut("AspectPcDefine.pc1() || AspectPcDefine.pc2()")
    public void pointcut2() {
    }

    @Test
    public void test1() throws InterruptedException {
        System.out.println(Thread.currentThread());
        System.out.println(Thread.currentThread().isDaemon());
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean flag = true;
                while (flag) {

                }
                System.out.println("哈哈");
            }
        };
        thread.setName("test-thread");
        System.out.println(thread.isDaemon());
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("ok");
        System.out.println(thread.isDaemon());
        Thread.currentThread().getThreadGroup().list();

    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Thread thread1 = new Thread() {
                    @Override
                    public void run() {
                        while (true) {
                        }
                    }
                };
                thread1.setDaemon(false);
                System.out.println(thread1.isDaemon());
                thread1.start();
            }
        };
        thread.setDaemon(true);
        thread.start();
        TimeUnit.SECONDS.sleep(1);
    }

}
