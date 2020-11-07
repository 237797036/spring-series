package com.aop.demo11.test2;

import org.springframework.stereotype.Component;

@Component
public class Service2 {
    public String say(String name) {
        return "你好：" + name;
    }
}
