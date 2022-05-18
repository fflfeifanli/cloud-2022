package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    
    @Bean
    @LoadBalanced  //这里注释  是为了测试自己手写的轮询负载
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
