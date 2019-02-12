package com.learn.discoveryeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 *  Zuul 路由后面的微服务挂了后，Zuul 提供了一种回退机制来应对熔断处理。
 *
 * 注意 EnableZuulProxy 注解能注册到 eureka 服务上，是因为该注解包含了 eureka 客户端的注解，
 * 该 EnableZuulProxy 是一个复合注解。
 *
 * @EnableZuulProxy --> { @EnableCircuitBreaker、@EnableDiscoveryClient } 包含了 eureka 客户端注解，
 * 同时也包含了 Hystrix 断路器模块注解。
 *
 * http://localhost:8150/routes 地址可以查看该zuul微服务网关代理了多少微服务的serviceId。
 */
@EnableZuulProxy
@SpringBootApplication
public class DiscoveryGatewayZuulFallBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryGatewayZuulFallBackApplication.class, args);
	}
}
