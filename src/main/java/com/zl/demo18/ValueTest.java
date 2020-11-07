package com.zl.demo18;

import com.zl.demo18.test1.DbConfig;
import com.zl.demo18.test1.MainConfig1;
import com.zl.demo18.test2.DbUtil;
import com.zl.demo18.test2.MailConfig;
import com.zl.demo18.test2.MainConfig2;
import com.zl.demo18.test3.BeanMyScope;
import com.zl.demo18.test3.MainConfig3;
import com.zl.demo18.test3.User;
import com.zl.demo18.test4.BeanRefreshScope;
import com.zl.demo18.test4.MailService;
import com.zl.demo18.test4.MainConfig4;
import com.zl.demo18.test4.RefreshConfigUtil;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 1 使用@PropertySource注解引入配置文件
 * 语法：@PropertySource({"配置文件路径1","配置文件路径2"...})
 *
 * 2 使用@Value注解引用配置文件的值
 * 语法：@Value("${配置文件中的key:默认值}")，如果key不存在，将123作为默认值
 *       2@Value("${配置文件中的key}")，如果password不存在，值为${password}
 *
 * 3 @Value数据来源
 * 一般是配置文件，也可将文件内容放在db,redis
 * org.springframework.core.env.PropertySource包含key->value，可通过Object getProperty(String name)获取key对应的value
 *
 * org.springframework.core.env.Environment表示环境配置信息：
 * String resolvePlaceholders(String text); //resolvePlaceholders用来解析${text}，@Value注解就是调用这个方法来解析。
 * MutablePropertySources getPropertySources(); //返回MutablePropertySources对象
 *
 * 最终解析@Value过程：
 * a. 将@Value注解的value参数值作为Environment.resolvePlaceholders方法参数进行解析
 * b. Environment内部会访问MutablePropertySources来解析
 * c. MutablePropertySources内部有多个PropertySource，此时会遍历PropertySource列表，调用PropertySource.getProperty方法来解析key对应的值
 *
 * 4 动态刷新@Value
 * 实现的关键是@Scope中proxyMode参数，当值为ScopedProxyMode.TARGET_CLASS时，会生成一个代理，通过代理来实现@Value动态刷新的效果
 */
public class ValueTest {

    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig1.class);
        context.refresh();

        DbConfig dbConfig = context.getBean(DbConfig.class);
        System.out.println(dbConfig);
    }

    // 将配置信息放在db，容器启动时，可将这些信息加载到Environment中，@Value中的值最终是通过Environment来解析
    @Test
    public void test2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

         /*下面这段是关键 start*/
        //模拟从db中获取配置信息
        Map<String, Object> mailInfoFromDb = DbUtil.getMailInfoFromDb();
        //将其丢在MapPropertySource中（MapPropertySource类是spring提供的一个类，是PropertySource的子类）
        MapPropertySource mailPropertySource = new MapPropertySource("mail", mailInfoFromDb);
        //将mailPropertySource丢在Environment中的PropertySource列表的第一个中，让优先级最高
        context.getEnvironment().getPropertySources().addFirst(mailPropertySource);
        /*上面这段是关键 end*/

        context.register(MainConfig2.class);
        context.refresh();
        MailConfig mailConfig = context.getBean(MailConfig.class);
        System.out.println(mailConfig);
    }

    //如果将配置信息放在db中，通过一个界面来修改这些配置信息，希望系统在不重启情况下，让这些值在spring容器中立即生效。
    //@Value动态刷新的问题，springboot中使用@RefreshScope实现
    //自定义bean作用域
    //当@Scope中proxyMode为TARGET_CLASS时，会给当前bean通过cglib生成一个代理对象，通过这个代理对象来访问目标bean对象
    @Test
    public void test3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //将自定义作用域注册到spring容器中
        context.getBeanFactory().registerScope(BeanMyScope.SCOPE_MY, new BeanMyScope());//@1
        context.register(MainConfig3.class);
        context.refresh();

        System.out.println("从容器中获取User对象");
        User user = context.getBean(User.class);//@2 从容器中获取bean时，并没有调用User的构造函数创建User对象
        System.out.println("user对象的class为：" + user.getClass()); //@3 getBean返回的user对象是一个cglib代理对象。

        //当自定义的Scope中proxyMode=ScopedProxyMode.TARGET_CLASS时，会给这个bean创建一个代理对象
        //每次调用user.getUsername方法时，会自动调用自定义Scope#get()方法和User的构造函数。
        //即：调用代理对象的任何方法，都会调用这个自定义作用域的实现类（上面的BeanMyScope）中get方法来重新来获取这个bean对象。
        System.out.println("多次调用user的getUsername感受一下效果\n");
        for (int i = 1; i <= 3; i++) {
            System.out.println(String.format("********\n第%d次开始调用getUsername", i));
            System.out.println(user.getUsername()); //@4
            System.out.println(String.format("第%d次调用getUsername结束\n********\n", i));
        }
    }

    @Test
    public void test4() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getBeanFactory().registerScope(BeanRefreshScope.SCOPE_REFRESH, BeanRefreshScope.getInstance());
        context.register(MainConfig4.class);
        //刷新mail的配置到Environment
        RefreshConfigUtil.refreshMailPropertySource(context, 1);
        context.refresh();

        MailService mailService = context.getBean(MailService.class);
        System.out.println("配置未更新的情况下,输出3次");
        for (int i = 0; i < 3; i++) {
            System.out.println(mailService);
            TimeUnit.MILLISECONDS.sleep(200);
        }
        System.out.println();

        System.out.println("模拟3次更新配置效果");
        for (int i = 11; i <= 13; i++) {
            RefreshConfigUtil.updateDbConfig(context, i);
            System.out.println(mailService);
            TimeUnit.MILLISECONDS.sleep(200);
        }
        System.out.println();

        System.out.println("配置未更新的情况下,再输出3次");
        for (int i = 0; i < 3; i++) {
            System.out.println(mailService);
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }
}
