package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommenResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    //public static final String PAYMENT_URL="http://localhost:8001";  //单机地址写死的
    public static final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";  //集群地址写的是eureka注册的地址

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private LoadBalance loadBalance;
    @Resource
    private DiscoveryClient discoveryClient;



    @GetMapping("/consumer/payment/creat")
    public CommenResult<Payment> creat(Payment payment){
      return  restTemplate.postForObject(PAYMENT_URL+"/payment/creat",payment,CommenResult.class);
    }

    @GetMapping("/consumer/getPayment/{id}")
    public CommenResult<Payment> getPayment(@PathVariable("id") Long id){
        return  restTemplate.getForObject(PAYMENT_URL+"/payment/getPayment/"+id,CommenResult.class);
    }

    @GetMapping("/consumer/getForEntities/{id}")
    public CommenResult<Payment> getPayment2(@PathVariable("id") Long id){
        ResponseEntity<CommenResult> forEntity = restTemplate.getForEntity(PAYMENT_URL + "/payment/getPayment/" + id, CommenResult.class);
        if (forEntity.getStatusCode().is2xxSuccessful()){
            return forEntity.getBody();
        }else {
            return new CommenResult<>(444,"操作失败");
        }
    }
    @GetMapping(value = "/consumer/payment/lb")
    public  String getPaymentLB(){
        List<ServiceInstance> instances=discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances==null||instances.size()<=0){
            return null;
        }
        ServiceInstance serviceInstance=loadBalance.instances(instances);
        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }

    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin(){
        return  restTemplate.getForObject(PAYMENT_URL+"/payment/zipkin",String.class);
    }
}
