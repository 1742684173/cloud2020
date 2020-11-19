package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLb implements LoadBalanced{
    private AtomicInteger nextServerCyclicCounter;

    public MyLb() {
        this.nextServerCyclicCounter = new AtomicInteger(0);
    }

    @Override
    public ServiceInstance getInstance(List<ServiceInstance> instances) {
        int index = incrementAndGetModulo()%instances.size();
        return instances.get(index);
    }

    private int incrementAndGetModulo() {
        int current;
        int next;
        do {
            current = this.nextServerCyclicCounter.get();
            next = current > Integer.MAX_VALUE ?0:current+1;
        } while(!this.nextServerCyclicCounter.compareAndSet(current, next));
        System.out.println("******第几次访问，次数next:"+next);
        return next;
    }
}
