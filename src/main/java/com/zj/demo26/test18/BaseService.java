package com.zj.demo26.test18;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseService<T> {
    @Autowired
    private IDao<T> dao; //@1

    public IDao<T> getDao() {
        return dao;
    }

    public void setDao(IDao<T> dao) {
        this.dao = dao;
    }
}
