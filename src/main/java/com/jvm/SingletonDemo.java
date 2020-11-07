package com.jvm;

public class SingletonDemo {
    private SingletonDemo() {
    }

    private static class SingletonDemoHandler {
        private static SingletonDemo instance = new SingletonDemo();
    }

    public static SingletonDemo getInstance() {
        return SingletonDemoHandler.instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            System.out.println(SingletonDemo.getInstance());
        }
    }
}