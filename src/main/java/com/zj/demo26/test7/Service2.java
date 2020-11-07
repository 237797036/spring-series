package com.zj.demo26.test7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class Service2 {

    @Resource
    private IService service0;

    @Override
    public String toString() {
        return "Service2{" +
                "service0=" + service0 +
                '}';
    }
}
