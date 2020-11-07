package com.zj.service;

/**
 * @author zhongJun
 * @date 2020-08-29 21:09
 */
public class ZjDyService {

    public String sayHello(String name, boolean isThrowEx) {
        if (isThrowEx) {
            System.out.println("target method throw an exception");
            throw new RuntimeException("mock a server exception");
        }
        System.out.println("target method normal execute");
        return "hello " + name;
    }

}
