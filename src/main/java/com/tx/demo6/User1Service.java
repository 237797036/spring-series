package com.tx.demo6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class User1Service {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public void required(String name){
        jdbcTemplate.update("insert into user1(name) values (?)", name);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNew(String name){
        jdbcTemplate.update("insert into user1(name) values (?)", name);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void nested(String name){
        jdbcTemplate.update("insert into user1(name) values (?)", name);
    }


}
