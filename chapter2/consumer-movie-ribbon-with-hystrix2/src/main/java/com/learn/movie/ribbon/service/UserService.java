package com.learn.movie.ribbon.service;

import com.learn.movie.ribbon.domain.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {


    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "${user.userServicePath}")
    private String userServicePath;


    /**
     *  使用@HystrixCommand的fallbackMethod参数指定，当本方法调用失败时，调用后备方法findByIdFallBack
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "findByIdFallBack")
    public User findById(Long id) {
        return this.restTemplate.getForObject(this.userServicePath + id, User.class);
    }


    private User findByIdFallBack(Long id){
        User user = new User();
        user.setId(0L);
        return user;
    }
}
