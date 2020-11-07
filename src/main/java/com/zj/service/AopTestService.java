package com.zj.service;

/**
 * @Author zhongJun
 * @Date 2020-08-29 0:21
 */
public class AopTestService {

    public String sayHello(String name) {
        System.out.println("hello" + name);
        try {
            int i = 9 / 0;
        } catch (Exception e) {
            System.out.println("occur error");
        }
        /*int i = 9 / 0;*/
        return "hello" + name;
    }

}
