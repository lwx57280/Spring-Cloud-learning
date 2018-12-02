package com.learn.movie;


import com.learn.movie.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserHystrixFeignClientFallback implements UserFeignClient {
    @Override
    public User findById(Long id) {

        User user = new User();
        user.setId(0L);

        return user;
    }
}
