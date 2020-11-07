package com.zm.demo1.test0.userregister;

import com.zm.demo1.test0.event.EventMulticaster;

/**
 * 用户注册服务
 */
public class UserRegisterService {

    //事件发布者
    private EventMulticaster eventMulticaster; //@0 事件发布者

    /**
     * 注册用户
     *
     * @param userName 用户名
     */
    public void registerUser(String userName) { //@1
        //用户注册(模拟用户信息入库)
        System.out.println(String.format("用户【%s】注册成功", userName)); //@2
        //广播事件
        this.eventMulticaster.multicastEvent(new UserRegisterSuccessEvent(this, userName));
        //@3 使用事件发布者eventPublisher发布用户注册成功的消息
    }

    public EventMulticaster getEventMulticaster() {
        return eventMulticaster;
    }

    public void setEventMulticaster(EventMulticaster eventMulticaster) {
        this.eventMulticaster = eventMulticaster;
    }
}