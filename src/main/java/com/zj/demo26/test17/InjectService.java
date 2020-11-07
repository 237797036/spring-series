package com.zj.demo26.test17;

import java.util.Map;

public class InjectService {
    private Map<String, IService> serviceMap1;
    private Map<String, IService> serviceMap2;

    public Map<String, IService> getServiceMap1() {
        return serviceMap1;
    }

    public void setServiceMap1(Map<String, IService> serviceMap1) {
        this.serviceMap1 = serviceMap1;
    }

    public Map<String, IService> getServiceMap2() {
        return serviceMap2;
    }

    public void setServiceMap2(Map<String, IService> serviceMap2) {
        this.serviceMap2 = serviceMap2;
    }

    @Override
    public String toString() {
        return "InjectService{" +
                "serviceMap1=" + serviceMap1 +
                ", serviceMap2=" + serviceMap2 +
                '}';
    }
}
