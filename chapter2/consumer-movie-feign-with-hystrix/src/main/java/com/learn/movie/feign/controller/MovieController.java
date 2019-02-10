package com.learn.movie.feign.controller;

import com.learn.movie.feign.FeignClient2;
import com.learn.movie.feign.UserFeignClient;
import com.learn.movie.feign.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


@RestController
public class MovieController {


    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired  // 有bean就注入，没有就不注入 解决办法就是@Autowired(required=false)。
    private FeignClient2 feignClient2;

    @GetMapping("/movie/{id}")
    public User findById(@PathVariable Long id) {
        return userFeignClient.findById(id);
    }

    @GetMapping("/movie/user")
    public User postUser(User user){
        Random random = new Random();
        User tmpUser = new User();
        long id = (long) random.nextInt(100);
        tmpUser.setId(id);
        tmpUser.setName("TempUser" + id);
        tmpUser.setAge((int) id);

        return userFeignClient.postUser(tmpUser);
    }

    @GetMapping("/{serviceName}")
    public String findServiceInfoFromEurekaByServiceName(@PathVariable String serviceName){
        return this.feignClient2.findServiceInfoFromEurekaByServiceName(serviceName);
    }

}
