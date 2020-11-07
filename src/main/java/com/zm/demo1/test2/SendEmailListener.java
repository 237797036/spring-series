package com.zm.demo1.test2;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 通过接口的方式创建一个监听器
 * 原理：在bean实例化后置阶段
 * spring容器在创建bean过程中，会判断bean是否为ApplicationListener类型，
 * 进而会将其作为事件监听器注册到AbstractApplicationContext#applicationEventMulticaster中
 * 源码：org.springframework.context.support.ApplicationListenerDetector#postProcessAfterInitialization
 */

/**
 * 用户注册成功发送邮件
 */
@Component
public class SendEmailListener implements ApplicationListener<UserRegisterEvent> {

    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        System.out.println(String.format("给用户【%s】发送注册成功邮件!", event.getUserName()));
    }
}
