package com.zj.demo22.test4;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(
		useDefaultFilters = false, //不启用默认过滤器
		includeFilters = {
				@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = IService.class)
		})
public class ScanBean4 {
}
