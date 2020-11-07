package com.zj.demo25.test7;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Conditional(MyCondition1.class)
@Conditional(MyConfigurationCondition1.class)
public class BeanConfig2 {
    @Bean
    public String name() {
        return "路人甲Java";
    }
}
