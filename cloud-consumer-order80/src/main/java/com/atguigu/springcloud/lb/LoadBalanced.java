package com.atguigu.springcloud.lb;


import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

public interface LoadBalanced {
    ServiceInstance getInstance(List<ServiceInstance> instances);
}
