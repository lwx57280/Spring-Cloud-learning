package com.learn.discoveryeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class DiscoveryGatewayZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryGatewayZuulApplication.class, args);
	}
}
