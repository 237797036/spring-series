package com.zl.demo18.test4;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

public class RefreshConfigUtil {
    /**
     * 模拟修改db中配置时需要调用的方法，
     * 并调用refreshMailPropertySource方法修改容器中邮件的配置信息
     */
    public static void updateDbConfig(AbstractApplicationContext context, int i) {
        // 更新context容器中mailPropertySource配置信息
        refreshMailPropertySource(context, i);

        // 清除BeanRefreshScope中所有已经缓存的bean，那么调用bean的任意方法时，会重新出发到spring容器创建bean，
        // spring容器重新创建bean时，会重新解析@Value的信息，此时容器中的邮件配置信息是新的，所以@Value注入的信息也是新的
        BeanRefreshScope.clean();
    }

    // 刷新mail的配置到Environment
    public static void refreshMailPropertySource(AbstractApplicationContext context, int i) {
        Map<String, Object> mailInfoFromDb = DbUtil.getMailInfoFromDb(i);
        // 将其丢在MapPropertySource中（MapPropertySource类是spring提供的一个类，是PropertySource的子类）
        MapPropertySource mailPropertySource = new MapPropertySource("mail", mailInfoFromDb);
        // 将mailPropertySource丢在Environment中的PropertySource列表的第一个中，让优先级最高
        context.getEnvironment().getPropertySources().addFirst(mailPropertySource);
    }

}
