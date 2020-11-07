package com.tx.demo2;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.lang.Nullable;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Consumer;

public class Demo2Test {

    @Test
    public void test1() throws Exception {
        //定义一个数据源
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/javacode2018?characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setInitialSize(5);
        //定义一个JdbcTemplate，用来方便执行数据库增删改查
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //1.定义事务管理器，给其指定一个数据源（可以把事务管理器想象为一个人，这个人来负责事务的控制操作）
        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);
        //2.定义事务属性：TransactionDefinition，TransactionDefinition可以用来配置事务的属性信息，比如事务隔离级别、事务超时时间、事务传播方式、是否是只读事务等等。
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setTimeout(10);//如：设置超时时间10s
        //3.创建TransactionTemplate模板对象
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager, transactionDefinition);
        /**
         * 4.通过TransactionTemplate提供的方法执行业务操作
         * 主要有2个方法：
         * （1）.executeWithoutResult(Consumer<TransactionStatus> action)：没有返回值的，需传递一个Consumer对象，在accept方法中做业务操作
         * （2）.<T> T execute(TransactionCallback<T> action)：有返回值的，需要传递一个TransactionCallback对象，在doInTransaction方法中做业务操作
         * 调用execute方法或者executeWithoutResult方法执行完毕之后，事务管理器会自动提交事务或者回滚事务。
         * 那么什么时候事务会回滚，有2种方式：
         * （1）transactionStatus.setRollbackOnly();将事务状态标注为回滚状态
         * （2）execute方法或者executeWithoutResult方法内部抛出异常
         * 什么时候事务会提交？
         * 方法没有异常 && 未调用过transactionStatus.setRollbackOnly();
         */
        transactionTemplate.executeWithoutResult(new Consumer<TransactionStatus>() {
            @Override
            public void accept(TransactionStatus transactionStatus) {
                jdbcTemplate.update("insert into t_user (name) values (?)", "executeWithoutResult-1");
                jdbcTemplate.update("insert into t_user (name) values (?)", "executeWithoutResult-2");

            }
        });
        System.out.println("after:" + jdbcTemplate.queryForList("SELECT * from t_user"));
    }

    @Test
    public void test2() throws Exception {
        //定义一个数据源
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/javacode2018?characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setInitialSize(5);
        //定义一个JdbcTemplate，用来方便执行数据库增删改查
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //1.定义事务管理器，给其指定一个数据源（可以把事务管理器想象为一个人，这个人来负责事务的控制操作）
        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);
        //2.定义事务属性：TransactionDefinition，TransactionDefinition可以用来配置事务的属性信息，比如事务隔离级别、事务超时时间、事务传播方式、是否是只读事务等等。
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setTimeout(10);//如：设置超时时间10s
        //3.创建TransactionTemplate对象
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager, transactionDefinition);
        /**
         * 4.通过TransactionTemplate提供的方法执行业务操作
         * 主要有2个方法：
         * （1）.executeWithoutResult(Consumer<TransactionStatus> action)：没有返回值的，需传递一个Consumer对象，在accept方法中做业务操作
         * （2）.<T> T execute(TransactionCallback<T> action)：有返回值的，需要传递一个TransactionCallback对象，在doInTransaction方法中做业务操作
         * 调用execute方法或者executeWithoutResult方法执行完毕之后，事务管理器会自动提交事务或者回滚事务。
         * 那么什么时候事务会回滚，有2种方式：
         * （1）transactionStatus.setRollbackOnly();将事务状态标注为回滚状态
         * （2）execute方法或者executeWithoutResult方法内部抛出异常
         * 什么时候事务会提交？
         * 方法没有异常 && 未调用过transactionStatus.setRollbackOnly();
         */
        Integer result = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Nullable
            @Override
            public Integer doInTransaction(TransactionStatus status) {
                int result = jdbcTemplate.update("insert into t_user (name) values (?)", "executeWithoutResult-3");
                return result;
            }
        });
        System.out.println(result);
        System.out.println("after:" + jdbcTemplate.queryForList("SELECT * from t_user"));
    }

}
