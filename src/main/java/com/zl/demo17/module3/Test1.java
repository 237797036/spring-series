package com.zl.demo17.module3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Map;

public class Test1 {

    @Configurable
    public static class Config1 {
        @Bean
        public String address() {
            return "java";
        }
    }

    @Configurable
    @Import(S1.class)
    public static class Config2 {
        @Bean
        public String name() {
            return "spring";
        }
    }

    public static class S1 {

        @Autowired
        private List<String> list;
        @Autowired
        private Map<String, String> map;

        @Override
        public String toString() {
            return "S1{" +
                    "list=" + list +
                    ", map=" + map +
                    '}';
        }
    }

    public static void main(String[] args) {

        //创建父容器
        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
        parentContext.register(Config1.class);
        //启动父容器
        parentContext.refresh();

        //创建子容器
        AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
        childContext.register(Config2.class);
        //给子容器设置父容器
        childContext.setParent(parentContext);
        //启动子容器
        childContext.refresh();

        System.out.println(childContext.getBean(S1.class));
    }
}
