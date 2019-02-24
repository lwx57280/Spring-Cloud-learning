package com.learn.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableConfigServer    // @EnableConfigServer开启配置服务器的支持
@EnableEurekaClient    // @EnableEurekaClient开启作为Eureka Server的客户端的支持
public class MicroserviceConfigServerApplication {

	//http://pc-20161101xhnk:8091/application/dev
	public static void main(String[] args) {
		SpringApplication.run(MicroserviceConfigServerApplication.class, args);
	}
}
