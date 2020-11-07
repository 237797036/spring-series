package com.zl.test19.demo2;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MainConfig2 {

    @Bean
    public MessageSource messageSource() {
        //可以监控国际化资源文件变化
        ReloadableResourceBundleMessageSource result = new ReloadableResourceBundleMessageSource();
        result.setBasenames("com/zl/demo19/message");
        //设置缓存时间1毫秒
        //大于0,：上次读取配置文件的时间距离当前时间超过了1秒，则重新读取国际化文件
        //-1：表示永远缓存
        //0：每次获取国际化信息时，都会重新读取国际化文件，生产环境禁止
        //线上环境，缓存时间最好设置大一点，性能会好一些
        result.setCacheMillis(1000);
        return result;
    }
}
