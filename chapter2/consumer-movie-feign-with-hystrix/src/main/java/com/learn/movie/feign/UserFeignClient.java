package com.learn.movie.feign;

import com.learn.movie.feign.domain.User;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Feign结合Hystrix实现服务容错保护
 */
@FeignClient(name = "provider-user", fallback = UserHystrixFeignClientFallback.class)
public interface UserFeignClient {

    /**
     * 这里有两个坑需要注意：
     *  1、这里需要设置请求的方式为 RequestMapping 注解，用 GetMapping 注解是运行不成功的，即 GetMapping 不支持。
     *
     *  2、注解 PathVariable 里面需要填充变量的名字，不然也是运行不成功的。
     *
     * @param id
     * @return
     */
//    @RequestMapping(value = "/simple/{id}", method = RequestMethod.GET)
// 通过@RequestLine指定HTTP协议及URL地址 GET 需要加空格
    @RequestLine(value = "GET /simple/{id}")
    User findById(@Param("id") Long id);

    /**
     * 这里也有一个坑需要注意：
     * 如果入参是一个对象的话，那么这个方法在 feign 里面默认为 POST 方法，就算你写成 GET 方式也无济于事。
     * @param user
     * @return
     */
    @RequestLine(value = "POST /user")
    User postUser(@RequestBody User user);
}
