package com.ajay.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableHystrix
//access it here http://localhost:5050/hystrix
//http://localhost:5050/actuator/hystrix.stream
public class MovieCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

	//check the properties for by default lazy initialization using - spring.main.lazy-initialization=true and control the bean using @Lazy(false)
	//@Lazy(false)
	/*@Bean(name="restTemplate")
	public RestTemplate restTemplate() {
		System.out.println("creating rest template bean");
		return new RestTemplate();
	}*/
	
	@LoadBalanced
	@Bean(name="restTemplate2")
	public RestTemplate restTemplate2() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(3000);
		
		System.out.println("creating rest template 2 bean");
		return new RestTemplate(clientHttpRequestFactory);
	}
	
	@Bean 
	public WebClient.Builder getBuilder(){
	WebClient.Builder builder = WebClient.builder();
	return builder;
	}
}
