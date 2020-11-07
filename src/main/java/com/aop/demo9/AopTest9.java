package com.aop.demo9;

import com.aop.demo9.test1.Aspect1;
import com.aop.demo9.test1.Service1;
import com.aop.demo9.test10.AspectTest10;
import com.aop.demo9.test10.S10;
import com.aop.demo9.test11.AspectTest11;
import com.aop.demo9.test11.S11;
import com.aop.demo9.test12.AspectTest12;
import com.aop.demo9.test12.S12;
import com.aop.demo9.test13.BeanService;
import com.aop.demo9.test13.MainConfig13;
import com.aop.demo9.test2.AspectTest2;
import com.aop.demo9.test2.C2;
import com.aop.demo9.test3.AspectTest3;
import com.aop.demo9.test3.I1;
import com.aop.demo9.test3.Service3;
import com.aop.demo9.test4.AspectTest4;
import com.aop.demo9.test5.AspectTest5;
import com.aop.demo9.test5.Service5;
import com.aop.demo9.test6.Ann6;
import com.aop.demo9.test6.AspectTest6;
import com.aop.demo9.test6.S6;
import com.aop.demo9.test7.Ann7;
import com.aop.demo9.test7.AspectTest7;
import com.aop.demo9.test7.S7;
import com.aop.demo9.test8.AspectTest8;
import com.aop.demo9.test8.S8;
import com.aop.demo9.test9.AspectTest9;
import com.aop.demo9.test9.S9;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ClassUtils;

// 使用execution(方法表达式)匹配方法执行。
// (..)代表所有参数，(*,String)代表第一个参数为任何值,第二个为String类型，(..,String)代表最后一个参数是String类型
public class AopTest9 {
    /**
     * execution：用于匹配方法执行的连接点
     */
    @Test
    public void test1() {
        try {
            //对应目标对象
            Service1 target = new Service1();
            //创建AspectJProxyFactory对象
            AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
            //设置被代理的目标对象
            proxyFactory.setTarget(target);
            //设置标注了@Aspect注解的类
            proxyFactory.addAspect(Aspect1.class);
            //生成代理对象
            Service1 proxy = proxyFactory.getProxy();
            //使用代理对象
            proxy.m1(null);
            proxy.m2();
        } catch (Exception e) {
        }
    }

    /**
     * within(类型)：目标对象target的类型是否和within中指定的类型匹配
     */
    @Test
    public void test2() {
        C2 target = new C2(); //注意是C2
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        // 原因：目标对象是C2类型的，C2虽然是C1的子类，但是within中表达式指定的是要求类型必须是C1类型的才匹配。
        // 所以不会拦截
        proxyFactory.setTarget(target);
        proxyFactory.addAspect(AspectTest2.class);

        C2 proxy = proxyFactory.getProxy();
        proxy.m1();
        proxy.m2();
        proxy.m3();
    }

    /**
     * this(类型全限定名)：通过aop创建的代理对象的类型是否和this中指定的类型匹配；
     * 注意判断的目标是代理对象；
     * this中使用的表达式必须是类型全限定名，不支持通配符。
     */
    @Test
    public void test3() {
        Service3 target = new Service3();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        // 使用cglib来创建代理，才行
        // proxyFactory.setProxyTargetClass(true);
        proxyFactory.setTarget(target);
        //获取目标对象上的接口列表
        Class<?>[] allInterfaces = ClassUtils.getAllInterfaces(target);
        //设置需要代理的接口
        proxyFactory.setInterfaces(allInterfaces);
        proxyFactory.addAspect(AspectTest3.class);
        //获取代理对象
        Object proxy = proxyFactory.getProxy();
        //调用代理对象的方法
        ((I1) proxy).m1();

        // this表达式要求代理对象必须是Service3类型的
        System.out.println("proxy对象类型：" + proxy.getClass());
        System.out.println("proxy是否是jdk动态代理对象：" + AopUtils.isJdkDynamicProxy(proxy));
        System.out.println("proxy是否是cglib代理对象：" + AopUtils.isCglibProxy(proxy));
        //判断代理对象是否是Service3类型的
        System.out.println(Service3.class.isAssignableFrom(proxy.getClass()));
    }

    /**
     * target(类型全限定名)：判断目标对象的类型是否和指定的类型匹配；
     * 注意判断的是目标对象的类型；
     * 表达式必须是类型全限定名，不支持通配符。
     */
    @Test
    public void test4() {
        Service3 target = new Service3();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.setTarget(target);
        proxyFactory.addAspect(AspectTest4.class);
        //获取代理对象
        Object proxy = proxyFactory.getProxy();
        //调用代理对象的方法
        ((I1) proxy).m1();
        //判断target对象是否是Service3类型的
        System.out.println("proxy对象类型：" + proxy.getClass());
        System.out.println(Service3.class.isAssignableFrom(target.getClass()));
    }

    /**
     * args：匹配方法传入的实际参数类型
     */
    @Test
    public void test5() {
        Service5 target = new Service5();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAspect(AspectTest5.class);
        Service5 proxy = proxyFactory.getProxy();
        //调用2次，第一次传入一个String类型的，第二次传入一个int类型的，看看效果
        proxy.m1("路人");
        proxy.m1(100);
    }

    /**
     * 注解@target：目标类上有指定的注解
     */
    @Test
    public void test6() {
        S6 target = new S6();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAspect(AspectTest6.class);
        S6 proxy = proxyFactory.getProxy();
        proxy.m1();
        proxy.m2();
        System.out.println("目标类上是否有 @Ann6 注解：" + (target.getClass().getAnnotation(Ann6.class) != null));
    }

    @Test
    public void test7() {
        S7 target = new S7();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        proxyFactory.setTarget(target);//注意：目标类是S7
        proxyFactory.addAspect(AspectTest7.class);
        S7 proxy = proxyFactory.getProxy();
        proxy.m1();
        proxy.m2();
        System.out.println("目标类上是否有 @Ann7 注解：" + (target.getClass().getAnnotation(Ann7.class) != null));
    }

    @Test
    public void test8() {
        S8 target = new S8();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAspect(AspectTest8.class);
        S8 proxy = proxyFactory.getProxy();
        proxy.m1(null);
        proxy.m2("路人甲java", null, 100);
    }

    /**
     * 注解@within(注解类型)：匹配指定注解的类中定义的所有方法
     */
    @Test
    public void test9() {
        S9 target = new S9();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAspect(AspectTest9.class);
        S9 proxy = proxyFactory.getProxy();
        proxy.m1();
        proxy.m2();
    }

    /**
     * 注解@within(注解类型)：匹配指定注解的类中定义的所有方法
     */
    @Test
    public void test10() {
        S10 target = new S10();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        proxyFactory.setTarget(target);// S10是代理的目标类，继承了S10Parent，内部重写了父类的m2方法，并且又新增了一个m3方法
        proxyFactory.addAspect(AspectTest10.class);
        S10 proxy = proxyFactory.getProxy();
        proxy.m1(); //只会对m1()方法拦截
        proxy.m2();// m2方法虽然也在S10Parent中定义了，但是这个方法被子类S10重写了，所以调用目标对象中的m2方法的时候，
        // 发现m2方法是由S10定义的，而S10.class.getAnnotation(Ann10.class)为空，所以这个方法不会被拦截。
        proxy.m3();
    }

    /**
     * 注解@within(注解类型)：匹配指定注解的类中定义的所有方法
     */
    @Test
    public void test11() {
        S11 target = new S11();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAspect(AspectTest11.class);
        S11 proxy = proxyFactory.getProxy();
        proxy.m1();
        proxy.m2();
        proxy.m3();
    }

    /**
     * 注解@annotation(注解类型)：被调用的方法上有指定的注解
     */
    @Test
    public void test12() {
        S12 target = new S12();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        proxyFactory.setTarget(target);// S12作为目标对象，创建代理，然后分别调用4个方法
        proxyFactory.addAspect(AspectTest12.class);
        S12 proxy = proxyFactory.getProxy();
        proxy.m1();
        //重点在于m2方法的执行结果，没有被拦截，m2方法虽然在S12Parent中定义的时候也有@Ann12注解标注，
        // 但是这个方法被S1给重写了，在S1中定义的时候并没有@Ann12注解，
        // 代码中实际上调用的是S1中的m2方法，发现这个方法上并没有@Ann12注解，所以没有被拦截。
        proxy.m2();
        proxy.m3();
        proxy.m4();
    }

    @Test
    public void test13() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig13.class);
        //从容器中获取beanService1
        BeanService beanService1 = context.getBean("beanService1", BeanService.class);
        beanService1.m1();
        //从容器中获取beanService2
        BeanService beanService2 = context.getBean("beanService2", BeanService.class);
        //beanService2的m1方法被拦截了
        beanService2.m1();

        System.out.println("beanService1类型："+ beanService1.getClass());
        System.out.println("beanService2类型："+ beanService2.getClass());
    }
}
