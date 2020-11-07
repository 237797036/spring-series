package com.zm.demo1.test3;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 通过@EventListener注解方式创建一个监听器，将注解标注在一个bean的方法上，那这个方法就可以处理感兴趣的事件
 * 用户注册监听器
 */
@Component
public class UserRegisterListener {

    @EventListener
    public void sendMail(UserRegisterEvent event) {
        System.out.println(String.format("给用户【%s】发送注册成功邮件!", event.getUserName()));
    }

    @EventListener
    public void sendCompon(UserRegisterEvent event) {
        System.out.println(String.format("给用户【%s】发送优惠券!", event.getUserName()));
    }
}
