package com.zj.demo25.test1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig2 {

    @Conditional(OnMissingBeanCondition.class) //@1
    @Bean
    public IService service2() {
        return new Service2();
    }
}
