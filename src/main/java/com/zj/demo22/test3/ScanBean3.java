package com.zj.demo22.test3;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(
        useDefaultFilters = false, // 不启用默认过滤器
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyBean.class)})
public class ScanBean3 {
}
