package com.zl.demo18.test3;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@MyScope //使用自定义的作用域@MyScope
public class User {

    private String username;

    public User() {
        System.out.println("---------创建User对象" + this);
        System.out.println("user对象的class为：" + this.getClass());
        this.username = UUID.randomUUID().toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
