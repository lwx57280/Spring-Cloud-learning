package com.learn.discoveryeureka;

import com.learn.discoveryeureka.filter.PreZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class DiscoveryGatewayZuulFilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryGatewayZuulFilterApplication.class, args);
	}

	// http://192.168.1.100:8061/provider-user/simple/2
	@Bean
	public PreZuulFilter preZuulFilter(){
		return new PreZuulFilter();
	}
}
