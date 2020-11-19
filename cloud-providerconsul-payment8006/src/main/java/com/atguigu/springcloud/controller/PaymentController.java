package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/payment/consul")
    public String paymentConsul(){
        return "spring cloud with consul:" + port + "\t" + UUID.randomUUID().toString();
    }
}
