package com.learn.movie.ribbon.controller;


import com.learn.movie.ribbon.domain.User;
import com.learn.movie.ribbon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @Autowired
    private UserService userService;


    @GetMapping("/movie/{id}")
    public User findById(@PathVariable Long id){

        return userService.findById(id);
    }

}
