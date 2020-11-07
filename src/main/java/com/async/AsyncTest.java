package com.async;

import com.async.demo1.LogService;
import com.async.demo1.MainConfig1;
import com.async.demo2.GoodsService;
import com.async.demo2.MainConfig2;
import com.async.demo3.MainConfig3;
import com.async.demo4.MainConfig4;
import com.async.demo5.CashOutService;
import com.async.demo5.MainConfig5;
import com.async.demo5.RechargeService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AsyncTest {

    // 方法返回值不是Future类型的，被执行时，会立即返回，并且无法获取方法返回值
    // 前面2行在主线程中执行，后面2行在异步线程中执行。
    @Test
    public void test1() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig1.class);
        context.refresh();
        LogService logService = context.getBean(LogService.class);
        System.out.println(Thread.currentThread() + " logService.log start," + System.currentTimeMillis());
        logService.log("异步执行方法!");
        System.out.println(Thread.currentThread() + " logService.log end," + System.currentTimeMillis());

        //休眠一下，防止@Test退出
        TimeUnit.SECONDS.sleep(3);
    }

    // 3个方法总计耗时500毫秒左右。
    //如果不采用异步的方式，3个方法会同步执行，耗时差不多1.5秒，试试将GoodsService上的@Async去掉，然后再次执行测试案例。
    @Test
    public void test2() throws InterruptedException, ExecutionException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig2.class);
        context.refresh();
        GoodsService goodsService = context.getBean(GoodsService.class);

        long starTime = System.currentTimeMillis();
        System.out.println("开始获取商品的各种信息");

        long goodsId = 1L;
        Future<String> goodsInfoFuture = goodsService.getGoodsInfo(goodsId);
        Future<String> goodsDescFuture = goodsService.getGoodsDesc(goodsId);
        Future<List<String>> goodsCommentsFuture = goodsService.getGoodsComments(goodsId);

        System.out.println(goodsInfoFuture.get());
        System.out.println(goodsDescFuture.get());
        System.out.println(goodsCommentsFuture.get());

        System.out.println("商品信息获取完毕,总耗时(ms)：" + (System.currentTimeMillis() - starTime));

        //休眠一下，防止@Test退出
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void test3() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig3.class);
        context.refresh();
        LogService logService = context.getBean(LogService.class);
        System.out.println(Thread.currentThread() + " logService.log start," + System.currentTimeMillis());
        logService.log("异步执行方法!");
        System.out.println(Thread.currentThread() + " logService.log end," + System.currentTimeMillis());

        //休眠一下，防止@Test退出
        TimeUnit.SECONDS.sleep(3);
    }

    // 自定义线程池
    @Test
    public void test4() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig3.class);
        context.refresh();
        LogService logService = context.getBean(LogService.class);
        System.out.println(Thread.currentThread() + " logService.log start," + System.currentTimeMillis());
        logService.log("异步执行方法!");
        System.out.println(Thread.currentThread() + " logService.log end," + System.currentTimeMillis());

        //休眠一下，防止@Test退出
        TimeUnit.SECONDS.sleep(3);
    }

    // 方法返回值为Future类型，
    @Test
    public void test5() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig1.class);
        context.refresh();
        LogService logService = context.getBean(LogService.class);
        try {
            Future<String> future = logService.mockException();
            System.out.println(future.get());
        } catch (ExecutionException e) {
            System.out.println("捕获 ExecutionException 异常");
            //通过e.getCause获取实际的异常信息
            e.getCause().printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //休眠一下，防止@Test退出
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void test6() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig4.class);
        context.refresh();
        LogService logService = context.getBean(LogService.class);
        logService.mockNoReturnException();
        //休眠一下，防止@Test退出
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void test6_2() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig4.class);
        context.refresh();
        LogService logService = context.getBean(LogService.class);
        logService.mockThrowException(true);
        //休眠一下，防止@Test退出
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void test7() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig5.class);
        context.refresh();

        // 出2个业务使用的是不同的线程池执行
        RechargeService rechargeService = context.getBean(RechargeService.class);
        rechargeService.recharge();
        CashOutService cashOutService = context.getBean(CashOutService.class);
        cashOutService.cashOut();

        //休眠一下，防止@Test退出
        TimeUnit.SECONDS.sleep(3);
    }

}
