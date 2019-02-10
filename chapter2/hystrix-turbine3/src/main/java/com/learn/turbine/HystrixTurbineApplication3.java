package com.learn.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
// 启动类添加@EnableTurbine，激活对Turbine的支持
@EnableTurbine
@SpringBootApplication
public class HystrixTurbineApplication3 {

	// 访问Turbine
	// http://192.168.1.101:8031/turbine.stream?cluster=CONSUMER-MOVIE-RIBBON-WITH-HYSTRIX

	public static void main(String[] args) {
		SpringApplication.run(HystrixTurbineApplication3.class, args);
	}

}

