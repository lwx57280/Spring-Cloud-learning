package com.learn.movie.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker*/
@SpringCloudApplication	//  Spring Cloud组合注解,该注解中包含了上述所引用的三个注解
public class ConsumerMovieRibbonWithHystrixApplication {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerMovieRibbonWithHystrixApplication.class, args);
	}
}
