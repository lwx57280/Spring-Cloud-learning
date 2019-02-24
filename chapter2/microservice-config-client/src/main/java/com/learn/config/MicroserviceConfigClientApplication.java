package com.learn.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient    // @EnableEurekaClient开启作为Eureka Server的客户端的支持
public class MicroserviceConfigClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceConfigClientApplication.class, args);
	}
}
