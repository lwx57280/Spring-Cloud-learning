package com.learn.movie.feign.controller;

import com.learn.movie.feign.UserFeignClient;
import com.learn.movie.feign.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class MovieController {

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/movie/{id}")
    public User findById(@PathVariable Long id) {
        return userFeignClient.findById(id);
    }


    @PostMapping("/test")
    public User testPost(@RequestBody User user){
        return this.userFeignClient.postUser(user);
    }

}
