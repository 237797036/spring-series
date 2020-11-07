package com.aop.demo11.test1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan //@1 @ComponentScan 会扫描当前包中的类，将标注有 @Component 的类注册到spring容器
@EnableAspectJAutoProxy //@2 启用自动代理的创建
// 找到容器中所有标注有@Aspect注解的bean以及Advisor类型的bean，会将他们转换为Advisor集合，
// spring会通过Advisor集合对容器中满足切入点表达式的bean生成代理对象，
// 整个都是spring容器启动的过程中自动完成
public class MainConfig1 {
}
