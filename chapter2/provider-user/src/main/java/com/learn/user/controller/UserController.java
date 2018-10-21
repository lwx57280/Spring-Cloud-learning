package com.learn.user.controller;

import com.learn.user.domain.User;
import com.learn.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/simple/{id}")
    public User findById(@PathVariable Long id){
        return this.repository.findOne(id);
    }
}
