package com.zj.demo25.test2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnvConditional(EnvConditional.Env.PROD)
public class ProdBeanConfig {
    @Bean
    public String name() {
        return "我是生产环境!";
    }
}
