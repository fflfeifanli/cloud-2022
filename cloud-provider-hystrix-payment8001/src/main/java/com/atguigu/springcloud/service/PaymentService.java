package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String payment_ok(Integer id){
        return "线程池： "+Thread.currentThread().getName()+"payment_ok,id: "+id+"\t"+"哈哈";
    }

    //服务降级
    @HystrixCommand(fallbackMethod = "payment_timeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String payment_timeout(Integer id){

        int timeNumbe=3000;
        try {
            TimeUnit.MILLISECONDS.sleep(timeNumbe);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： "+Thread.currentThread().getName()+"payment_timeout,id: "+id+"\t"+"哈哈"+"耗时"+timeNumbe+"秒";
    }

    public String payment_timeoutHandler(Integer id){
        return "线程池： "+Thread.currentThread().getName()+"8001系统繁忙或者运行报错请稍后再试 "+id+"\t"+"/(ㄒoㄒ)/~~";

    }

    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后跳闸
    })
    public String paymentCircuitBreak(Integer id){
        if (id<0){
            throw new RuntimeException("******id 不能是负数");
        }
        String serialNumber= IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调试成功，流水号："+serialNumber;
    }
    public String paymentCircuitBreaker_fallback(Integer id){
        return "id 不能为负数，请稍后重试，  id："+id;
    }
}
