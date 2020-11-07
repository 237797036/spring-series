package com.zm.demo1.test1;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 负责监听订单创建事件，发送邮件
 */
@Component
public class SendEmailOnOrderCreateListener implements ApplicationListener<OrderCreateEvent> {

    @Override
    public void onApplicationEvent(OrderCreateEvent event) {
        System.out.println(String.format("订单【%d】创建成功，给下单人发送邮件通知!", event.getOrderId()));
    }
}
