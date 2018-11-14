package com.learn.movie.feign;

import com.learn.movie.feign.domain.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("provider-user")
public interface UserFeignClient {

    @GetMapping(value = "/simple/{id}")
    User findById(@PathVariable("id") Long id);


    @PostMapping(value = "/user")
    User postUser(User user);
}
