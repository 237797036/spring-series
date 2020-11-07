package com.aop.demo2;

import org.junit.Test;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.objenesis.instantiator.ObjectInstantiator;

public class CreateObjectTest {

    public static class User {
        private String name;

        // 如果不使用有参构造函数，通过反射无法创建对象！
        public User() {
            System.out.println("默认构造函数");
        }

        public User(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    // 如果不使用有参构造函数，是无法创建对象的
    @Test
    public void test0() throws Exception {
        User user2 = User.class.getConstructor(String.class).newInstance("肖傻逼");
        System.out.println(user2);

        User user = User.class.newInstance();
        System.out.println(user);
    }

    // cglib中提供了一个接口：Objenesis，即使没有空的构造函数都可，它不使用构造方法创建Java对象，所以即使有空的构造方法，也不会执行
    //不会调用默认构造函数
    @Test
    public void test1() {
        Objenesis objenesis = new ObjenesisStd();
        User user = objenesis.newInstance(User.class);
        System.out.println(user);
    }

    // 多次创建User对象
    @Test
    public void test2() {
        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator<User> objectInstantiator = objenesis.getInstantiatorOf(User.class);
        User user1 = objectInstantiator.newInstance();
        System.out.println(user1);
        User user2 = objectInstantiator.newInstance();
        System.out.println(user2);
        System.out.println(user1 == user2);
    }
}
