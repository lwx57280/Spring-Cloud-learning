package com.learn.discoveryeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class DiscoveryGatewayZuulPrefixApplication {

	// http://192.168.1.102:8040/routes
	public static void main(String[] args) {
		SpringApplication.run(DiscoveryGatewayZuulPrefixApplication.class, args);
	}
}
