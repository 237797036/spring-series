package com.zj.demo26.test19.demo1;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class Demo {
    public static class User implements InitializingBean {

        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("-----------afterPropertiesSet");
        }
    }

    public static class UserFactory {
        public static User createUser() {
            return new User();
        }
    }

    public static void main(String[] args) {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        factory.registerBeanDefinition("user", BeanDefinitionBuilder.rootBeanDefinition(UserFactory.class.getName(),"createUser").getBeanDefinition());

        Object user = factory.getBean("user");
        System.out.println(user);
    }
}
