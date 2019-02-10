package com.learn.movie.controller;

import com.learn.movie.domain.User;
import com.learn.movie.feign.FeignClient2;
import com.learn.movie.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class MovieController {

    @Autowired
    private RestTemplate restTemplate;

//    @Value(value = "${user.userServicePath}")
//    private String userServicePath;


    @Autowired
    private UserFeignClient userFeignClient;


//    @Qualifier(value = "xxxx")
    @Autowired  // 有bean就注入，没有就不注入 解决办法就是@Autowired(required=false)。
    private FeignClient2 feignClient2;

//    @GetMapping("/movie/{id}")
//    public User findById(@PathVariable Long id) {
//        return this.restTemplate.getForObject(this.userServicePath + id, User.class);
//    }

    @GetMapping("/movie/{id}")
    public User findById(@PathVariable Long id) {
        return userFeignClient.findById(id);
    }

    @GetMapping("/{serviceName}")
    public String findServiceInfoFromEurekaByServiceName(@PathVariable String serviceName){
        return this.feignClient2.findServiceInfoFromEurekaByServiceName(serviceName);
    }

}
