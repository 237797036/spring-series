package com.aop.demo2;

public class CglibTest3 {
    public static class UserService {
        public void insert() {
            System.out.println("insert");
        }

        public void update() {
            System.out.println("insert");
        }
    }

    public interface ILog{

    }
}
