package com.zm.demo1.test5;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 给用户发送邮件
 */
@Component
public class SendMailListener {

    @EventListener
    public void onApplicationEvent(UserRegisterEvent event) {
        System.out.println(
                String.format("当前线程【%s】,给用户【%s】发送注册成功邮件!", Thread.currentThread(),
                        event.getUserName()));
    }
}
