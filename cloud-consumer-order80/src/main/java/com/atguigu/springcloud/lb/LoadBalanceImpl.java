package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class LoadBalanceImpl implements LoadBalance{

    private AtomicInteger atomicInteger=new AtomicInteger(0);

    public final int getAngIncrement(){
        int current;
        int next;
        do {
            current=this.atomicInteger.get();
            next = current >= 2147364847 ? 0 : current + 1;

        }while (!this.atomicInteger.compareAndSet(current,next));
        System.out.println("*********next:"+next);
        return next;
    }


    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstance) {
        int i = getAngIncrement() % serviceInstance.size();
        return serviceInstance.get(i);
    }
}
