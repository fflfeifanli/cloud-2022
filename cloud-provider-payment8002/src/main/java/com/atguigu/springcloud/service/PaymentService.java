package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;

public interface PaymentService {
    public int creat(Payment payment);

    public Payment getPayment(Long id);
}
