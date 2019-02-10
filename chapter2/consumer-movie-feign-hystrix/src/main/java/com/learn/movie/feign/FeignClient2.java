package com.learn.movie.feign;

import com.learn.config.Configuration2;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *  1）当然我们也可以单个禁用,只需要在自定义的配置类中加入。
 * 
 *   2）因为：默认支持的是HystrixFeign.Builder.而通过配置之后，返回的是Feign.builder去掉了对Hystrix的支持
 *
 *   3）加上之后，回调函数就没啥用了，访问会直接报错；
 *
 *  4）不加的话，访问返回haha;
 */
@FeignClient(name = "xxxx",url = "http://localhost:8761/",configuration = Configuration2.class, fallback = FeignClient2Fallback.class)
public interface FeignClient2 {

    @RequestMapping(value = "/eureka/apps/{serviceName}", method = RequestMethod.GET)
    String findServiceInfoFromEurekaByServiceName(@PathVariable("serviceName") String serviceName);

}
