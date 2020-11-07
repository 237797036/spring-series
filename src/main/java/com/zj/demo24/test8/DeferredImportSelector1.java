package com.zj.demo24.test8;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;

@Order(2)
public class DeferredImportSelector1 implements DeferredImportSelector/*, Ordered*/ {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{Configuration1.class.getName()};
    }

    /*@Override
    public int getOrder() {
        return 2;
    }*/
}
