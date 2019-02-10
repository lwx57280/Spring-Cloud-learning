package com.learn.config;

import feign.Contract;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
    /**
     * 假设我们现在定义了一个Configuration1这个类，使用feign默认的契约，而不是使用SpringMvcContract
     * @return
     */
    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }

    /**
     * Feign Logging 日志
     *     FULL, 记录请求和响应的头信息，正文和元数据。
     * Spring Cloud Feign日志输出
     *    查阅Spring Cloud官方文档后，需要先配置一个Bean,设定Feign输出的日志内容。
     *
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
