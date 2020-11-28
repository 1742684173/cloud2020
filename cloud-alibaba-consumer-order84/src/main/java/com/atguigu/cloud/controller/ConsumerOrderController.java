package com.atguigu.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ConsumerOrderController {
    private final static String SERVER_URL = "http://alibaba-provider-payment";
    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/getPayment/{id}")
//    @SentinelResource(value = "getPayment",blockHandler = "dealBlockHandler")
//    @SentinelResource(value = "getPayment",fallback = "dealFallback")
    @SentinelResource(value = "getPayment",fallback = "dealFallback",blockHandler = "dealBlockHandler")
    public CommonResult getPayment(@PathVariable("id")Long id){
        CommonResult result = restTemplate.getForObject(SERVER_URL+"/getPayment/"+id,CommonResult.class);
        if(id == 4){
            throw new RuntimeException("参数非法，id不能为4");
        }

        if(result.getData() == null){
            throw new NullPointerException("没有记录，id:"+id);
        }
        return result;
    }

    public CommonResult dealBlockHandler(@PathVariable("id")Long id, BlockException blockException){
        return new CommonResult(400,blockException.getClass().getCanonicalName()+" dealBlockHandler");
    }

    public CommonResult dealFallback(@PathVariable("id")Long id, Throwable throwable){
        return new CommonResult(400,throwable.getMessage()+" dealFallback");
    }
}
