package com.zj.demo17;

import org.junit.Test;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.CallbackHelper;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.FixedValue;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.NoOp;

import java.lang.reflect.Method;

public class CglibTest {

    /**
     * 拦截所有方法
     */
    @Test
    public void test1() {
        //使用Enhancer给某个类创建代理类：整个过程主要用到了Enhancer类和MethodInterceptor接口。
        //1.创建Enhancer对象
        Enhancer enhancer = new Enhancer();
        //2.通过setSuperclass设置‘代理类的父类’，即需要给哪个类创建代理类
        enhancer.setSuperclass(Service1.class);//为Service1类创建一个代理
        /*3.设置回调，需实现org.springframework.cglib.proxy.Callback接口；
        org.springframework.cglib.proxy.MethodInterceptor接口，实现了Callback接口。
        代理对象所有的方法调用，都会被MethodInterceptor接口的intercept方法拦截*/
        enhancer.setCallback(new MethodInterceptor() {
            /**
             * 代理对象方法拦截器
             *
             * @param target 目标对象
             * @param method 被代理类的方法，即Service1中的方法
             * @param objects 调用方法传递的参数
             * @param methodProxy 方法代理对象
             * @return
             * @throws Throwable
             */
            @Override
            public Object intercept(Object target, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("调用方法:" + method);
                //调用被代理类(父类)，也就是Service1类的具体方法
                //实际对某个类创建代理(子类)，cglib底层通过修改字节码的方式为Service1类创建了一个子类
                Object result = methodProxy.invokeSuper(target, objects);
                return result;
            }
        });
        //4.获取代理对象,调用enhancer.create方法获取代理对象，这个方法返回的是Object类型的，需要强转一下
        Service1 proxy = (Service1) enhancer.create();
        //5.调用代理对象的方法
        proxy.m1();
        proxy.m2();
    }

    /**
     * 拦截所有方法
     */
    @Test
    public void test2() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Service2.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object target, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("调用方法:" + method);
                Object result = methodProxy.invokeSuper(target, objects);
                return result;
            }
        });
        Service2 proxy = (Service2) enhancer.create();
        //虽然只调用了m1方法，m1和m2方法都被拦截器处理了，而m2方法是在Service2的m1方法中调用的，也被拦截处理了
        proxy.m1(); //@1
    }

    /**
     * 拦截所有方法并返回固定值（FixedValue）
     */
    @Test
    public void test3() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Service3.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "路人甲";
            }
        });
        Service3 proxy = (Service3) enhancer.create();
        System.out.println(proxy.m1());
        System.out.println(proxy.m2());
        System.out.println(proxy.toString());
    }

    /**
     * 不同的方法使用不同的拦截器（CallbackFilter）。
     * 要对不同的方法做不同的处理，就需要多个Callback对象
     * 调用代理对象方法，具体走哪个Callback，会通过CallbackFilter中的accept来判断，这个方法返回callbacks数组的索引
     */
    @Test
    public void test4() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Service4.class);

        //1 创建2个Callback
        Callback[] callbacks = {
                //这个用来拦截所有insert开头的方法
                new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        long starTime = System.nanoTime();
                        Object result = methodProxy.invokeSuper(o, objects);
                        long endTime = System.nanoTime();
                        System.out.println(method + "，耗时(纳秒):" + (endTime - starTime));
                        return result;
                    }
                },
                //这个用来拦截所有get开头的方法，返回固定值
                new FixedValue() {
                    @Override
                    public Object loadObject() throws Exception {
                        return "路人甲Java";
                    }
                }
        };
        //2 调用enhancer的setCallbacks，设置Callback数组
        enhancer.setCallbacks(callbacks);

        //3 设置过滤器CallbackFilter
        //通过CallbackFilter判断使用callbacks数组中的哪个Callback来处理当前方法，返回callbacks数组的下标
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                //获取当前调用的方法名称
                String methodName = method.getName();
                //以insert开头的方法，返回callbacks中的第1个Callback对象来处理当前方法，否则使用第二个Callback处理被调用的方法
                return methodName.startsWith("insert") ? 0 : 1;
            }
        });
        Service4 proxy = (Service4) enhancer.create();
        System.out.println("=========");
        proxy.insert1();
        System.out.println("=========");
        proxy.insert2();
        System.out.println("=========");
        System.out.println(proxy.get1());
        System.out.println("=========");
        System.out.println(proxy.get2());

    }

    /**
     * 不同的方法使用不同的拦截器（CallbackHelper）。
     * 可通过CallbackHelper对上边案例优化。
     */
    @Test
    public void test5() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Service4.class);

        //1 创建2个Callback
        Callback costTimeCallback = (MethodInterceptor) (Object o, Method method, Object[] objects, MethodProxy methodProxy) -> {
            long starTime = System.nanoTime();
            Object result = methodProxy.invokeSuper(o, objects);
            long endTime = System.nanoTime();
            System.out.println(method + "，耗时(纳秒):" + (endTime - starTime));
            return result;
        };
        //这个用来拦截所有get开头的方法，返回固定值
        Callback fixedValueCallback = (FixedValue) () -> "路人甲Java";

        CallbackHelper callbackHelper = new CallbackHelper(Service4.class, null) {
            @Override
            protected Object getCallback(Method method) {
                return method.getName().startsWith("insert") ? costTimeCallback : fixedValueCallback;
            }
        };
        //2 调用enhancer的setCallbacks，设置Callback数组
        enhancer.setCallbacks(callbackHelper.getCallbacks());

        //3 设置CallbackFilter,用来判断某个方法走哪个Callback
        enhancer.setCallbackFilter(callbackHelper);

        Service4 proxy = (Service4) enhancer.create();
        System.out.println("---------------");
        proxy.insert1();
        System.out.println("---------------");
        proxy.insert2();
        System.out.println("---------------");
        System.out.println(proxy.get1());
        System.out.println("---------------");
        System.out.println(proxy.get2());

    }

    /**
     * 直接放行，不做任何操作（NoOp.INSTANCE）
     */
    @Test
    public void test6() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Service3.class);
        enhancer.setCallback(NoOp.INSTANCE);
        Service3 proxy = (Service3) enhancer.create();
        System.out.println(proxy.m1());
        System.out.println(proxy.m2());
    }

    @Test
    public void test7() {
        //创建Service1代理
        Service1 proxy1 = CostTimeProxy.createProxy(new Service1());
        proxy1.m1();

        //创建Service3代理
        Service3 proxy2 = CostTimeProxy.createProxy(new Service3());
        System.out.println(proxy2.m1());
    }

}
