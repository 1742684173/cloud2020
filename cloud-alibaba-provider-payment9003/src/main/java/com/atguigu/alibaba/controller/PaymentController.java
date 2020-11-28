package com.atguigu.alibaba.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.sun.applet2.AppletParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    private static Map<Long,Payment> map = new HashMap<>();

    static {
        map.put(1l,new Payment(1,"serial1111"));
        map.put(2l,new Payment(2,"serial2222"));
        map.put(3l,new Payment(3,"serial3333"));
    }

    @GetMapping(value = "/getPayment/{id}")
    public CommonResult getPayment(@PathVariable("id")long id) {
        Payment result = map.get(id);
        return new CommonResult(200,"查询成功，port:+"+serverPort+"\t id:"+id,result);
    }
}
