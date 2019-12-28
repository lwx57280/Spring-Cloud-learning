package com.learn.userlike.solo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
//@ComponentScan(basePackages = {"com.learn.userlike.solo.*"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class UserLikeApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserLikeApplication.class, args);
    }



}
