package com.zj.demo25.test2;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TestBeanConfig.class, DevBeanConfig.class, ProdBeanConfig.class})
public class MainConfig2 {
}
