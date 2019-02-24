package com.learn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @Date 2019-02-15
 * @Version v1.0
 */
@RestController
public class ConfigClientController {

    /**
     * 服务配置中心config-server获取
     */
    @Value("${profile}")
    private String profile;


    @RequestMapping("/profile")
    public String getProfile(){
        return this.profile;
    }
}
