package com.learn.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableEurekaClient		// 1 通过@EnableEurekaClient开启feign客户端支持
@EnableDiscoveryClient
public class ProviderUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProviderUserApplication.class, args);
	}
}
