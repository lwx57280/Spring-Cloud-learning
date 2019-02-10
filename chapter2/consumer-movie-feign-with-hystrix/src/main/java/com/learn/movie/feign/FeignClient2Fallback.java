package com.learn.movie.feign;


import org.springframework.stereotype.Component;

@Component
public class FeignClient2Fallback implements FeignClient2 {

    @Override
    public String findServiceInfoFromEurekaByServiceName(String serviceName) {
        return "haha" + serviceName;
    }
}
