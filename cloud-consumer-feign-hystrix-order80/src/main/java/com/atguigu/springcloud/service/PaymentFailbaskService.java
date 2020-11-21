package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFailbaskService implements PaymentHystrixService{

    @Override
    public String paymentInfo_Ok(Integer id) {
        return "PaymentFailbaskService fail paymentInfo_Ok";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "PaymentFailbaskService fail paymentInfo_TimeOut";
    }
}
