package com.learn.movie.feign;

import com.learn.config.UserConfiguration;
import com.learn.movie.domain.User;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Feign结合Hystrix实现服务容错保护
 */
@FeignClient(name = "provider-user", configuration = UserConfiguration.class, fallback = UserHystrixFeignClientFallback.class)
public interface UserFeignClient {
    //spring boot 项目需要调用其它项目的接口时使用spring cloud feign声明式调用。
    // 通过@RequestLine指定HTTP协议及URL地址 GET 需要加空格
    @RequestLine(value = "GET /simple/{id}")
    User findById(@Param("id") Long id);

}
