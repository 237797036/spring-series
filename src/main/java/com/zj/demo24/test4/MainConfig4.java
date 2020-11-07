package com.zj.demo24.test4;

import org.springframework.context.annotation.Import;

/**
 * 通过@Import导入ImportBeanDefinitionRegistrar接口实现类
 */
@Import({MyImportBeanDefinitionRegistrar.class})
public class MainConfig4 {
}
