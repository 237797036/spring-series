package com.tx.demo6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TxService {

    @Autowired
    private User1Service user1Service;

    @Autowired
    private User2Service user2Service;

    // 一、
    public void notTx_outExcept_reuqired_required() {
        user1Service.required("张三");
        user2Service.required("李四");
        throw new RuntimeException();
    }

    public void notTx_reuqired_requiredExcept() {
        user1Service.required("张三");
        user2Service.required_exception("李四");
    }

    // 二、
    @Transactional(propagation = Propagation.REQUIRED)
    public void hasTx_outExcept_reuqired_required() {
        user1Service.required("张三");
        user2Service.required("李四");
        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void hasTx_reuqired_requiredExcept() {
        user1Service.required("张三");
        user2Service.required_exception("李四");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void hasTx_reuqired_requiredExceptTry() {
        user1Service.required("张三");
        try {
            user2Service.required_exception("李四");
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }

    // 三、
    public void notTx_outExcept_reuqiresNew_reuqiresNew() {
        user1Service.requiresNew("张三");
        user2Service.requiresNew("李四");
        throw new RuntimeException();
    }

    public void notTx_reuqiresNew_reuqiresNewExcept() {
        user1Service.required("张三");
        user2Service.requiresNew_exception("李四");
    }

    // 四、
    @Transactional(propagation = Propagation.REQUIRED)
    public void hasTx_outExcept_reuqired_reuqiresNew_reuqiresNew() {
        user1Service.required("张三");
        user2Service.requiresNew("李四");
        user2Service.requiresNew("王五");
        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void hasTx_reuqired_reuqiresNew_reuqiresNewExcept() {
        user1Service.required("张三");
        user2Service.requiresNew("李四");
        user2Service.requiresNew_exception("王五");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void hasTx_reuqired_reuqiresNew_reuqiresNewExceptTry() {
        user1Service.required("张三");
        user2Service.requiresNew("李四");
        try {
            user2Service.requiresNew_exception("王五");
        } catch (Exception e) {
            System.out.println("回滚");
        }
    }


    // 五、
    public void notTx_outExcept_nested_nested() {
        user1Service.nested("张三");
        user2Service.nested("李四");
        throw new RuntimeException();
    }

    public void notTx_nested_nestedExcept() {
        user1Service.nested("张三");
        user2Service.nested_exception("李四");
    }

    // 六、
    @Transactional(propagation = Propagation.REQUIRED)
    public void hasTx_outExcept_nested_nested() {
        user1Service.nested("张三");
        user2Service.nested("李四");
        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void hasTx_nested_nestedExcept() {
        user1Service.nested("张三");
        user2Service.nested_exception("李四");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void hasTx_nested_nestedExceptTry() {
        user1Service.nested("张三");
        try {
            user2Service.nested_exception("李四");
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }

}
