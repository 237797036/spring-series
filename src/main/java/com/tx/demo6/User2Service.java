package com.tx.demo6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class User2Service {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public void required(String name){
        jdbcTemplate.update("insert into user2(name) values (?)", name);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void required_exception(String name){
        jdbcTemplate.update("insert into user2(name) values (?)", name);
        throw new RuntimeException();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNew(String name){
        jdbcTemplate.update("insert into user2(name) values (?)", name);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNew_exception(String name){
        jdbcTemplate.update("insert into user2(name) values (?)", name);
        throw new RuntimeException();
    }


    @Transactional(propagation = Propagation.NESTED)
    public void nested(String name){
        jdbcTemplate.update("insert into user2(name) values (?)", name);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void nested_exception(String name){
        jdbcTemplate.update("insert into user2(name) values (?)", name);
        throw new RuntimeException();
    }

}
