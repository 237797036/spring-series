package com.zj.demo24.test3;

import com.zj.demo24.test3.module1.CompontentScanModule1;
import com.zj.demo24.test3.module2.CompontentScanModule2;
import org.springframework.context.annotation.Import;

/**
 * 通过@Import导入多个@CompontentScan标注的配置类
 */
@Import({CompontentScanModule1.class, CompontentScanModule2.class})
public class MainConfig3 {
}
