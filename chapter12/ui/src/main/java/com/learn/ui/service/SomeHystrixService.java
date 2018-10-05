package com.learn.ui.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SomeHystrixService {


    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    // 使用@HystrixCommand的fallbackMethod参数指定，当本方法调用失败时，调用后备方法 fallbackSome
    @HystrixCommand(fallbackMethod = "fallbackSome")
    public String getSome(){
        return restTemplate.getForObject("http://some/getsome", String.class);
    }

    public String fallbackSome(){
        return "some Service模块故障";
    }
}
