package com.zj.demo22.test5;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(
		basePackages = {"com.zj.demo22.test4"},
		useDefaultFilters = false, //不启用默认过滤器
		includeFilters = {
				@ComponentScan.Filter(type = FilterType.CUSTOM, classes = MyFilter.class)
		})
public class ScanBean5 {
}
