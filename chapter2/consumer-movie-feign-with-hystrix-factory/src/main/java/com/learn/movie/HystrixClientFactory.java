package com.learn.movie;

import com.learn.movie.domain.User;
import feign.hystrix.FallbackFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Feign 使用FallbackFactory属性打印fallback异常
 *
 * 说明：
 * 如果需要访问作为回退触发器的原因，则可以使用@FeignClient中的fallbackFactory属性。
 * 为指定的客户端接口定义一个回退工厂。回退工厂必须产生回退类的实例，这些实例实现由FeignClient注释的接口。
 * 如果同时设置fallback和fallbackfactory不可以的，会有有冲突，fallback生效，fallbackfactory不能使用，fallbackFactory
 * 是fallback的一个升级版，注释fallback设置即可；
 */
@Component
public class HystrixClientFactory implements FallbackFactory<UserFeignClient> {


    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixClientFactory.class);

    @Override
    public UserFeignClient create(Throwable cause) {

        HystrixClientFactory.LOGGER.info("fallback: create was:{ }"+cause.getMessage());
        return (UserFeignClientWithFactory) id -> {
            User user = new User();
            user.setId(-1L);
            return user;
        };
    }
}
