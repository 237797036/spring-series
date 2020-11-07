package com.zm.demo1.test0.userregister;

import com.zm.demo1.test0.event.EventListener;
import com.zm.demo1.test0.event.EventMulticaster;
import com.zm.demo1.test0.event.SimpleEventMulticaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan
public class MainConfig0 {

    /**
     * 注册一个bean：事件发布者
     *
     * @param eventListeners
     * @return
     */
    @Bean // 向spring容器中注册一个bean：事件发布者
    @Autowired(required = false) // 传入EventListener类型的List，将容器中所有事件监听器注入进来，丢到EventMulticaster中
    public EventMulticaster eventMulticaster(List<EventListener> eventListeners) { //@1
        EventMulticaster eventPublisher = new SimpleEventMulticaster();
        if (eventListeners != null) {
            eventListeners.forEach(eventPublisher::addEventListener);
        }
        return eventPublisher;
    }

    /**
     * 注册一个bean：用户注册服务
     *
     * @param eventMulticaster
     * @return
     */
    @Bean //向spring容器中注册了一个bean：用户注册服务
    // 方法上标注了@Bean，并且方法有参数，spring调用这个方法创建bean时，会将参数注入进来
    public UserRegisterService userRegisterService(EventMulticaster eventMulticaster) { //@2
        UserRegisterService userRegisterService = new UserRegisterService();
        userRegisterService.setEventMulticaster(eventMulticaster);
        return userRegisterService;
    }
}
