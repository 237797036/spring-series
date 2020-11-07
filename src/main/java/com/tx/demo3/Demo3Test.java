package com.tx.demo3;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Demo3Test {

    // bus1中会先删除数据，然后调用bus2，此时bus1中的所有操作和bus2中的所有操作会被放在一个事务中执行，这是spring内部默认实现的，
    // bus1中调用executeWithoutResult的时候，会开启一个事务，而内部又会调用bus2，而bus2内部也调用了executeWithoutResult，
    // bus内部会先判断一下上线文环境中有没有事务，如果有就直接参与到已存在的事务中，刚好发现有bus1已开启的事务，所以就直接参与到bus1的事务中了，
    // 最终bus1和bus2会在一个事务中运行。
    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig3.class);
        UserService userService = context.getBean(UserService.class);
        userService.bus1();
        System.out.println(userService.userList());
    }

    //上面bus1()方法，代码转换为sql脚本如下：
    //start transaction; //开启事务
    //delete from t_user;
    //insert into t_user (name) VALUE ('java');
    //insert into t_user (name) VALUE ('spring');
    //insert into t_user (name) VALUE ('mybatis');
    //commit;

    // bus1或者bus2中，如果有异常或者执行transactionStatus.setRollbackOnly()，此时整个事务都会回滚
}
