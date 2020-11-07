package com.tx.demo6;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class Demo6Test {

    private TxService txService;

    private JdbcTemplate jdbcTemplate;

    // 每个Test用例执行前启动一下spring容器，并清理下user1、user2中的数据
    @Before
    public void before() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig6.class);
        txService = context.getBean(TxService.class);
        jdbcTemplate = context.getBean(JdbcTemplate.class);
        jdbcTemplate.update("truncate table user1");
        jdbcTemplate.update("truncate table user2");
    }

    @After
    public void after() {
        System.out.println("user1表数据：" + jdbcTemplate.queryForList("select * from user1"));
        System.out.println("user2表数据：" + jdbcTemplate.queryForList("select * from user2"));
    }

    // 一、
    // 外围方法不开启事务，内部方法运行在自己的独立事务，
    // 外围方法抛异常回滚，不影响内部方法独立的事务。
    @Test
    public void test1() {
        txService.notTx_outExcept_reuqired_required();
    }

    // 外围方法不开启事务，内部方法运行在自己的独立事务，
    // 内部某方法抛出异常只会回滚自己的事务，不会影响其他内部方法的事务。
    @Test
    public void test2() {
        txService.notTx_reuqired_requiredExcept();
    }

    /**
     * 以上2个用例的总结：
     * 外围方法‘不开启’事务，Propagation.REQUIRED修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。
     */

    // 二、
    // 外围方法开启事务（Propagation.REQUIRED），内部方法加入外围方法事务（即同一个事务）
    // 外围方法抛出异常回滚，内部方法也要回滚
    @Test
    public void test3() {
        txService.hasTx_outExcept_reuqired_required();
    }

    // 外围方法开启事务（Propagation.REQUIRED），内部方法加入外围方法事务
    // 内部方法抛出异常回滚，外围方法感知到异常，致使整个事务回滚
    @Test
    public void test4() {
        txService.hasTx_reuqired_requiredExcept();
    }

    // 外围方法开启事务（Propagation.REQUIRED），内部方法加入外围方法事务
    // 内部方法抛出异常回滚，即使异常被外围方法catch，不被外围方法感知，整个事务依然回滚
    @Test
    public void test5() {
        txService.hasTx_reuqired_requiredExceptTry();
    }

    /**
     * 以上3个用例的总结：
     * 外围方法‘开启’事务，Propagation.REQUIRED修饰的内部方法会‘加入’到外围方法的事务中，
     * 所有Propagation.REQUIRED修饰的内部方法和外围方法，均属于同一个事务，只要一个方法‘回滚’，整个事务都回滚。
     */


    // 三、
    // 外围方法不开启事务，内部方法运行在自己的独立事务，
    // 外围方法抛异常回滚，不影响内部方法独立的事务。
    @Test
    public void test6() {
        txService.notTx_outExcept_reuqiresNew_reuqiresNew();
    }

    // 外围方法不开启事务，内部方法运行在自己的独立事务，
    // 内部某方法抛出异常只会回滚自己的事务，不会影响其他内部方法的事务。
    @Test
    public void test7() {
        txService.notTx_reuqiresNew_reuqiresNewExcept();
    }

    /**
     * 以上2个用例的总结：
     * 外围方法‘不开启’事务，Propagation.REQUIRES_NEW修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。
     */

    // 四、
    // 外围方法开启事务（Propagation.REQUIRED），插入张三方法和外围方法是同一个事务，
    // 插入李四、王五方法分别在独立的新建事务
    // 外围方法抛出异常回滚，同时回滚和外围方法同一事务的方法
    // 故插入张三的方法回滚
    @Test
    public void test8() {
        txService.hasTx_outExcept_reuqired_reuqiresNew_reuqiresNew();
    }

    // 外围方法开启事务（Propagation.REQUIRED），插入张三方法和外围方法是同一个事务，
    // 插入李四、王五方法分别在独立的新建事务
    // 插入王五方法抛出异常回滚，异常继续抛出被外围方法感知，外围方法事务亦被回滚
    // 故即插入张三方法也被回滚
    @Test
    public void test9() {
        txService.hasTx_reuqired_reuqiresNew_reuqiresNewExcept();
    }

    // 外围方法开启事务（Propagation.REQUIRED），插入张三方法和外围方法是同一个事务，
    // 插入李四、王五方法分别在独立的新建事务
    // 插入王五方法抛出异常回滚，异常被catch不会被外围方法感知，外围方法事务不会回滚
    // 故插入张三方法插入成功
    @Test
    public void test10() {
        txService.hasTx_reuqired_reuqiresNew_reuqiresNewExceptTry();
    }

    /**
     * 以上3个用例的总结：
     * 外围方法‘开启’事务，Propagation.REQUIRES_NEW修饰的内部方法会‘单独开启’独立事务，
     * 内部方法之间、内部方法和外部方法，事务均相互独立，互不干扰
     */

    // 五、
    // 外围方法不开启事务，内部方法运行在自己的独立事务，
    // 外围方法抛异常回滚，不影响内部方法独立的事务。
    @Test
    public void test11() {
        txService.notTx_outExcept_nested_nested();
    }

    // 外围方法不开启事务，内部方法运行在自己的独立事务，
    // 内部某方法抛出异常只会回滚自己的事务，不会影响其他内部方法的事务。
    @Test
    public void test12() {
        txService.notTx_nested_nestedExcept();
    }

    /**
     * 以上2个用例的总结：
     * 外围方法‘不开启’事务，Propagation.NESTED和Propagation.REQUIRED作用相同
     * 他们修饰的内部方法，都会新开启自己的事务，且开启的事务相互独立，互不干扰。
     */


    // 六、
    // 外围方法开启事务（Propagation.REQUIRED），内部方法事务 为 外围方法事务的 子事务
    // 外围方法抛出异常回滚，内部方法也要回滚
    @Test
    public void test13() {
        txService.hasTx_outExcept_nested_nested();
    }

    // 外围方法开启事务（Propagation.REQUIRED），内部方法事务 为 外围方法事务的 子事务
    // 内部方法抛出异常回滚，且外围方法感知到异常，致使整个事务回滚（即，主事务回滚，子事务也要回滚）
    @Test
    public void test14() {
        txService.hasTx_nested_nestedExcept();
    }

    // 外围方法开启事务（Propagation.REQUIRED），内部方法事务 为 外围方法事务的 子事务
    // 内部方法抛出异常回滚，但是异常被外围方法catch，不会被外围方法感知。故外围事务不回滚，只单独回滚子事务
    @Test
    public void test15() {
        txService.hasTx_nested_nestedExceptTry();
    }

    /**
     * 以上3个用例的总结：
     * 外围方法‘开启’事务，Propagation.NESTED修饰的内部方法 属于 外部事务的子事务，
     * 外部事务回滚，子事务一定回滚；子事务回滚（且让外部方法catch异常），而不影响外部主事务 和 其他子事务
     */

    // 内部事务原理：以MySQL为例，MySQL中有个savepoint功能，nested内部事务就是通过这个实现。

    // 1 NESTED 和 REQUIRED 比较：
    // REQUIRED、NESTED修饰的事务，都隶属于 外部事务
    // 外部事务回滚，都会导致 REQUIRED、NESTED修饰的事务回滚
    // 但不同的是：REQUIRED是加入外部事务，跟外部事务是同一个，一旦REQUIRED事务回滚，外部事务也将回滚
    // 而NESTED是外部事务的子事务，有单独的保存点，只要NESTED修饰的方法被外部方法catch，就只会回滚子事务，不会回滚主事务


    // 2 NESTED 和 REQUIRES_NEW 比较：
    // REQUIRES_NEW、NESTED 都可以做到 内部方法事务回滚而不影响外围方法事务
    // 但不同的是：NESTED作为外部事务的子事务，外部事务回滚后，子事务一定也会回滚
    // REQUIRES_NEW是会开启新事务，内部事务和外部事务是两个事务，外部事务回滚不会影响内部事务
    //

}
