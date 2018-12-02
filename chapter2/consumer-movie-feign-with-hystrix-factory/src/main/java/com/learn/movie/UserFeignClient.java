package com.learn.movie;


import com.learn.movie.domain.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Feign结合Hystrix实现服务容错保护
 *
 *  Feign使用fallbackFactory属性打印fallback异常;
 *  fallbackFactory 是fallback的一个升级版；
 *  fallback 不可同时存在否则冲突，不生效
 *  说明：
 *  1）将fallback注释掉；
 *
 *  2）采用fallbackFactory的方式；
 */
@FeignClient(name ="provider-user",/*fallback = UserHystrixFeignClientFallback.class,*/fallbackFactory = HystrixClientFactory.class)
public interface UserFeignClient {

    @RequestMapping(value = "/simple/{id}",method = RequestMethod.GET)
    User findById(@PathVariable("id") Long id);

}
