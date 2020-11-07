package com.zl.demo17;

import com.zl.demo17.module1.Module1Config;
import com.zl.demo17.module2.Module2Config;
import com.zl.demo17.module2.Service3;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Map;

/**
 * 主要问题：
 * 什么是父子容器？
 * 为什么需要用父子容器？
 * 父子容器如何使用？
 */

/**
 * 创建spring容器的时候，可以给当前容器指定一个父容器。
 * 父子容器特点：
 * 1 父容器和子容器是相互隔离的，他们内部可以存在名称相同的bean
 * 2 子容器可以访问父容器中的bean，而父容器不能访问子容器中的bean
 * 3 调用子容器的getBean方法获取bean时，会沿着当前容器开始向上面的容器进行查找，直到找到对应的bean为止
 * 4 子容器可以注入父容器的bean，而父容器不能注入子容器的bean，原因是第2点
 *
 * 父子容器使用注意点：
 * 我们使用容器过程中，经常会用到一些方法，这些方法通常在下面的两个接口中
 *
 * org.springframework.beans.factory.BeanFactory
 * org.springframework.beans.factory.ListableBeanFactory
 *
 * 1 BeanFactory接口，是spring容器的顶层接口，这个接口中的方法支持容器嵌套查找，
 * 比如调用这个接口的getBean方法时，会从沿着当前容器向上查找，直到找到满足条件的bean为止。
 *
 * 2 而ListableBeanFactory这个接口中的方法不支持容器嵌套查找
 */

/**
 * 回头看一下springMvc父子容器的问题
 * 1 springMvc中只使用一个容器是否可以？
 * 只使用一个容器是可以正常运行的。
 *
 * 2：那么springMvc中为什么需要用到父子容器？
 *
 * 通常使用springMvc时，采用3层结构，controller层，service层，dao层；父容器中会包含dao层和service层，而子容器中包含的只有controller层；
 * 这2个容器组成了父子容器的关系，controller层通常会注入service层的bean。
 *
 * 采用父子容器可以避免有些人在service层去注入controller层的bean，导致整个依赖层次是比较混乱的。
 *
 * 父容器和子容器的需求也是不一样的，比如父容器中需要有事务的支持，会注入一些支持事务的扩展组件，
 * 而子容器中controller完全用不到这些，要注入springMvc相关的bean，而这些bean父容器中同样是不会用到，
 * 将这些相互不关心的东西隔开，可以有效的避免一些不必要的错误，而父子容器加载的速度也会快一些。
 *
 * 总结
 * 本文需掌握父子容器的用法，了解父子容器的特点：子容器可以访问父容器中bean，父容器无法访问子容器中的bean
 * BeanFactory接口支持层次查找
 * ListableBeanFactory接口不支持层次查找
 * BeanFactoryUtils工具类中提供了一些非常实用的方法，比如支持bean层次查找的方法等等
 */
public class ParentFactoryTest {

    /**
     * 会报错，因为两个模块中都有Service1，被注册到spring容器时，bean名称会冲突，导致注册失败。
     * 对module1中的Service1进行修改？这个估计是行不通的，module1是别人以jar的方式提供给我们的，源码我们是无法修改的。
     * module2是自己开发的，可以修改一下module2中的Service1，修改一下类名或者这个bean的名称，此时可以解决问题。
     *
     * 不过：如果我们的模块中有很多类都出现这种问题，此时一个个去重构比较痛苦，代码重构后还涉及重新测试问题，工作量蛮大，这些都是风险!
     *
     * 而spring中的父子容器可以很好的解决这种问题。
     */
    @Test
    public void test1() {
        //定义容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册bean
        context.register(Module1Config.class, Module2Config.class);
        //启动容器
        context.refresh();
    }

    @Test
    public void test2() {
        //创建父容器
        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
        //向父容器中注册Module1Config配置类
        parentContext.register(Module1Config.class);
        //启动父容器
        parentContext.refresh();

        //创建子容器
        AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
        //向子容器中注册Module2Config配置类
        childContext.register(Module2Config.class);
        //给子容器设置父容器
        childContext.setParent(parentContext);
        //启动子容器
        childContext.refresh();

        //从子容器中获取Service3
        Service3 service3 = childContext.getBean(Service3.class);
        System.out.println(service3.m1());
        System.out.println(service3.m2());
    }

    @Test
    public void test3() {
        //创建父容器parentFactory
        DefaultListableBeanFactory parentFactory = new DefaultListableBeanFactory();
        //向父容器parentFactory注册一个bean[userName->"路人甲Java"]
        parentFactory.registerBeanDefinition("userName",
                BeanDefinitionBuilder.
                        genericBeanDefinition(String.class).
                        addConstructorArgValue("路人甲Java").
                        getBeanDefinition());

        //创建一个子容器childFactory
        DefaultListableBeanFactory childFactory = new DefaultListableBeanFactory();
        //调用setParentBeanFactory指定父容器
        childFactory.setParentBeanFactory(parentFactory);
        //向子容器parentFactory注册一个bean[address->"上海"]
        childFactory.registerBeanDefinition("address",
                BeanDefinitionBuilder.
                        genericBeanDefinition(String.class).
                        addConstructorArgValue("台湾市").
                        getBeanDefinition());

        // 调用子容器的getBean方法，获取名称为userName的bean，userName这个bean在父容器中定义，
        // 而getBean方法是BeanFactory接口中定义的，支持容器层次查找，所以getBean是可以找到userName这个bean的
        System.out.println("获取bean【userName】：" + childFactory.getBean("userName")); //@1

        //调用子容器的getBeanNamesForType方法，获取所有String类型的bean名称，而getBeanNamesForType方法是ListableBeanFactory接口中定义，
        // 这个接口中方法不支持层次查找，只会在当前容器中查找，所以这个方法只会返回子容器的address
        System.out.println(Arrays.asList(childFactory.getBeanNamesForType(String.class)));// @2

        // 有没有办法解决ListableBeanFactory接口不支持层次查找的问题？
        // org.springframework.beans.factory.BeanFactoryUtils工具类中，名称中包含Ancestors的都支持层次查找。

        //层次查找所有符合类型的bean名称
        String[] beanNamesForTypeIncludingAncestors = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(childFactory, String.class); //@3
        System.out.println(Arrays.asList(beanNamesForTypeIncludingAncestors));

        Map<String, String> beansOfTypeIncludingAncestors = BeanFactoryUtils.beansOfTypeIncludingAncestors(childFactory, String.class);
        System.out.println(Arrays.asList(beansOfTypeIncludingAncestors));
    }
}
