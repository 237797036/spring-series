package com.async.demo1;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
public class LogService {

    @Async
    public void log(String msg) throws InterruptedException {
        System.out.println(Thread.currentThread() + "开始记录日志," + System.currentTimeMillis());
        //模拟耗时2秒
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread() + "日志记录完毕," + System.currentTimeMillis());
    }

    // 返回值为Future类型，捕获方法抛出的异常即可
    @Async
    public Future<String> mockException() {
        //模拟抛出一个异常
        throw new IllegalArgumentException("参数有误!");
    }

    // 无返回值或者返回值不是Future类型，需自定义异常处理器
    @Async
    public void mockNoReturnException() {
        //模拟抛出一个异常
        throw new IllegalArgumentException("无返回值的异常!");
    }

    // 无返回值或者返回值不是Future类型，需自定义异常处理器
    @Async
    public String mockThrowException(boolean isThrowExc) {
        //模拟抛出一个异常
        if (isThrowExc){
            throw new IllegalArgumentException("无返回值的异常!");
        }
        return "返回值为String类型";
    }
}
