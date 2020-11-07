package com.zj.demo24.test5;

import org.springframework.context.annotation.Import;

/**
 * 通过@Import导入MyImportSelector接口实现类
 */
@Import({MyImportSelector.class})
public class MainConfig5 {
}