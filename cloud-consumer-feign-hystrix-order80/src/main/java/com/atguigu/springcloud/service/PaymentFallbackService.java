package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService{

    @Override
    public String payment_ok(Integer id) {
        return "**************PaymentHystrixService fall back-payment_ok";
    }

    @Override
    public String paymentInfo_timeout(Integer id) {
        return "**************PaymentHystrixService fall back-paymentInfo_timeout";
    }
}
