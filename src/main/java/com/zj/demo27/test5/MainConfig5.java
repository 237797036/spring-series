package com.zj.demo27.test5;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ImportResource;

// @Configurable表示这是个配置类
@Configurable
@ImportResource("classpath:/com/zj/demo27/test5/beans*.xml")
public class MainConfig5 {
}
