package com.scheduled.demo2;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PushJob {

    //推送方法，每秒执行一次
    @Scheduled(fixedRate = 1000)
    public void push1() throws InterruptedException {
        //休眠2秒，模拟耗时操作
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread().getName() + " push1 模拟推送消息，" + System.currentTimeMillis());
    }

    //推送方法，每秒执行一次
    @Scheduled(fixedRate = 1000)
    public void push2() {
        System.out.println(Thread.currentThread().getName() + " push2 模拟推送消息，" + System.currentTimeMillis());
    }

    //2个定时器，500毫秒的，1000毫秒的
    /*@Schedules({@Scheduled(fixedRate = 500), @Scheduled(fixedRate = 1000)})
    public void push3() {
        System.out.println(Thread.currentThread().getName() + " push3 模拟推送消息，" + System.currentTimeMillis());
    }*/

}