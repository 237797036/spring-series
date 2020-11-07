package com.zl.test19;

import com.zl.test19.demo1.MainConfig1;
import com.zl.test19.demo2.MainConfig2;
import com.zl.test19.demo3.MainConfig3;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * bean名称为什么必须是messageSource
 * 上面我容器启动的时候会调用refresh方法，过程如下：
 *
 * org.springframework.context.support.AbstractApplicationContext#refresh()会调用
 * org.springframework.context.support.AbstractApplicationContext#initMessageSource
 * 这个方法来初始化MessageSource,方法内部会查找当前容器中是否有messageSource名称的bean，如果有就将其作为处理国际化的对象
 * 如果没有找到，此时会注册一个名称为messageSource的MessageSource（空的）
 *
 * 5自定义bean中使用国际化
 * 自定义的bean如果想使用国际化，只需实现MessageSourceAware这个接口，spring容器会自动调用这个方法，将MessageSource注入到bean，
 * 然后就可以使用MessageSource获取国际化信息。
 */
public class MessageSourceTest {

    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig1.class);
        context.refresh();
        //未指定Locale，此时系统会取默认的，本地电脑默认的值中文【中国】，即：zh_CN
        System.out.println(context.getMessage("name", null, null));
        //后面2行，都指定了Locale对象，找到对应的国际化文件，取值。
        System.out.println(context.getMessage("name", null, Locale.CHINA)); //CHINA对应：zh_CN
        System.out.println(context.getMessage("name", null, Locale.UK)); //UK对应en_GB
    }

    // 动态参数
    //配置文件中的personal_introduction，包含了{0},{1},{0}，这是动态参数，调用getMessage时，通过第二个参数传递过去
    @Test
    public void test2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig1.class);
        context.refresh();
        System.out.println(context.getMessage("personal_introduction", new String[]{"spring高手", "java高手"}, Locale.CHINA)); //CHINA对应：zh_CN
        System.out.println(context.getMessage("personal_introduction", new String[]{"spring", "java"}, Locale.UK)); //UK对应en_GB
    }

    @Test
    public void test3() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig2.class);
        context.refresh();
        //输出2次
        for (int i = 0; i < 30; i++) {
            System.out.println(context.getMessage("address", null, Locale.UK));
            TimeUnit.SECONDS.sleep(2);
        }
    }

    @Test
    public void test4() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig3.class);
        context.refresh();
        System.out.println(context.getMessage("desc", null, Locale.CHINA));
        System.out.println(context.getMessage("desc", null, Locale.UK));
    }
}
