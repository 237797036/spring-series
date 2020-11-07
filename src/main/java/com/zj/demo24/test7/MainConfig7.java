package com.zj.demo24.test7;

import org.springframework.context.annotation.Import;

/**
 * 注解@Import中被导入类的顺序：
 * DeferredImportSelector1最后被处理，其他2个按照在value中的先后顺序处理
 */
@Import({
        DeferredImportSelector1.class,
        Configuration3.class,
        ImportSelector1.class,
})
public class MainConfig7 {
}
