package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalanced;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
//    private final static String PAYMENT_URL = "http://localhost:8001";
    private final static String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id")Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    @GetMapping("/consumer/payment/getEntity/{id}")
    public CommonResult<Payment> getPaymentEntityById(@PathVariable("id")Long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            log.info(entity.getStatusCode()+"");
            return entity.getBody();
        }else{
            return new CommonResult(400,"查询失败");
        }
    }

    @GetMapping("/consumer/payment/createEntity")
    public CommonResult<Payment> createPaymentEntity(Payment payment){
        ResponseEntity<CommonResult> entity = restTemplate.postForEntity(PAYMENT_URL+"/payment/create", payment, CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            log.info(entity.getStatusCode()+"");
            return entity.getBody();
        }else{
            return new CommonResult(400,"添加失败");
        }
    }

    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private LoadBalanced loadBalanced;

    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentPort(){
        List<ServiceInstance> serviceInstanceList = this.discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(serviceInstanceList == null || serviceInstanceList.size() == 0){
            return null;
        }

        ServiceInstance serviceInstance = loadBalanced.getInstance(serviceInstanceList);
        URI uri = serviceInstance.getUri();
        String result = restTemplate.getForObject(uri+"/payment/lb",String.class);
        return result;
    }
}
