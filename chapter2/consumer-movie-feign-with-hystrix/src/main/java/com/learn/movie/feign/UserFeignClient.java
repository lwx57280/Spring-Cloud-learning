package com.learn.movie.feign;

import com.learn.movie.feign.domain.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Feign结合Hystrix实现服务容错保护
 */
@FeignClient(name ="provider-user",fallback = UserHystrixFeignClientFallback.class)
public interface UserFeignClient {

    @RequestMapping(value = "/simple/{id}",method = RequestMethod.GET)
    User findById(@PathVariable("id") Long id);

}
