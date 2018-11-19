package com.learn.movie.feign;

import com.learn.movie.feign.domain.User;
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
