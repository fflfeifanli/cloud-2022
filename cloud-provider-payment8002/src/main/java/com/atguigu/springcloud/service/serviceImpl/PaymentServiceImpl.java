package com.atguigu.springcloud.service.serviceImpl;

import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.mappeer.PaymentMapper;
import com.atguigu.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    PaymentMapper paymentMapper;
    @Override
    public int creat(Payment payment) {
        return paymentMapper.creat(payment);
    }

    @Override
    public Payment getPayment(Long id) {
        return paymentMapper.getPayment(id);
    }
}
