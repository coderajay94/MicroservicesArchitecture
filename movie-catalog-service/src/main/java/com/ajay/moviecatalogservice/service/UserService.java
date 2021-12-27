package com.ajay.moviecatalogservice.service;

import java.util.Arrays;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ajay.moviecatalogservice.dto.Rating;
import com.ajay.moviecatalogservice.dto.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UserService {

	@Autowired
	@Qualifier("restTemplate2")
	RestTemplate restTemplate;

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

	//can give hystrixProperties as a hystrix parameters to control
	/*@HystrixCommand(fallbackMethod = "getFallbackUserRatings",
			commandProperties = {
					@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000"),
					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
					@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
					@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000") 
			})*/
	
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	public UserRating getUserRatings(String userId) {
		logger.info("inside the method call UserService.getUserRatings");
		return restTemplate.getForObject("http://ratings-data-service/ratingsData/user/" + userId, UserRating.class);
	}

	public UserRating getFallbackUserRatings(String userId) {
		logger.info("inside the fallback - UserService.getFallbackUserRatings");
		UserRating userRating = new UserRating();
		userRating.setRatings(Arrays.asList(new Rating("rating-fallback", 0)));
		return userRating;
	}

}
