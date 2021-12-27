package com.ajay.moviecatalogservice.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ajay.moviecatalogservice.dto.CatalogItem;
import com.ajay.moviecatalogservice.dto.Movie;
import com.ajay.moviecatalogservice.dto.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class CatalogService {

	@Autowired
	@Qualifier("restTemplate2")
	RestTemplate restTemplate;

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CatalogService.class);

	// bulk head pattern implementation
	//some apis are fast and some are slow
	//create diff thread pool size for different calls
	//create pool by property threadPoolKey="MovieInfoPool"
	//add properties to this thread pool
	
	/*@HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
			//creates thread poool
			threadPoolKey = "CatalogServicePool",
			threadPoolProperties = {
					//thread properties
					@HystrixProperty(name="coreSize", value="20"),
					@HystrixProperty(name="maxQueueSize", value="10"),
			},
			commandProperties = {
					//other properties
					@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000"),
					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
					@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
					@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000") 
			}
			)*/
	
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating r) {
		logger.info("inside the method call CatalogService.getCatalogItem");
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + r.getMovieId(), Movie.class);
		return new CatalogItem(movie.getName(), "description", r.getRating());

	}

	public CatalogItem getFallbackCatalogItem(Rating r) {
		// Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" +
		// r.getMovieId(), Movie.class);
		logger.info("inside the fallback - CatalogService.getFallbackCatalogItem");
		return new CatalogItem("No movie Name-fallback", "description", r.getRating());

	}

}
