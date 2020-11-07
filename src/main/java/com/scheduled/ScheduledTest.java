package com.scheduled;

import com.scheduled.demo1.MainConfig1;
import com.scheduled.demo2.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

public class ScheduledTest {
    @Test
    public void test1() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig1.class);
        context.refresh();
        //休眠一段时间，防止junit自动退出
        TimeUnit.SECONDS.sleep(10000);
    }

    @Test
    public void test2() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig2.class);
        context.refresh();
        //休眠一段时间，防止junit自动退出
        TimeUnit.SECONDS.sleep(10000);
    }

}
