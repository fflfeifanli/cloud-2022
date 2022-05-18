package com.atguigu.springcloud.mappeer;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentMapper {
    public int creat(Payment payment);

    public Payment getPayment(@Param("id")Long id);
}
