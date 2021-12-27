package com.ajay.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.ajay.moviecatalogservice.dto.CatalogItem;
import com.ajay.moviecatalogservice.dto.UserRating;
import com.ajay.moviecatalogservice.service.CatalogService;
import com.ajay.moviecatalogservice.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/catalog")

public class MovieCatalogResource {

	@Autowired
	@Qualifier("restTemplate2")
	RestTemplate restTemplate;

	@Autowired
	WebClient.Builder webClientBuilder;

	@Autowired
	DiscoveryClient discoveryClient;

	@Autowired
	UserService userService;

	@Autowired
	CatalogService catalogService;

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MovieCatalogResource.class);

	// we have one api, calling other 2 api;s
	// both 2 api's have granular level of fallback mechanism using hystrix
	// if we put them into one file, it will not work because of proxy/wrapper class
	// mechanism
	// it only works if on class level failure occures then that callback will be
	// picked up, but not in the same file.

	// @HystrixCommand(fallbackMethod = "getFallbackCatalog")
	@RequestMapping("/{userId}")
	//@HystrixCommand(fallbackMethod = "getFallbackCatalog")
	public List<CatalogItem> getCatalog(@PathVariable String userId) {

		System.out.print("inside the catalog");
		// get all the rated movies by userId
		// Movie movies =
		// restTemplate.getForObject("http://localhost:6060/movies/"+userId,
		// Movie.class);

		// hardcoding for now
		// List<Rating> ratings = Arrays.asList(new Rating("Spider-man", 4), new
		// Rating("Bat-man", 5));
		// instead make an api call
		UserRating ratings = userService.getUserRatings(userId);

		return ratings.getRatings().stream().map(r -> catalogService.getCatalogItem(r)).collect(Collectors.toList());

		// webClient way of making a rest call
		/*
		 * return ratings.stream().map(r -> { //Movie movie =
		 * restTemplate.getForObject("http://localhost:6060/movies/" + r.getMovieId(),
		 * Movie.class); //rest template is gonna deprecate //chaining method for this
		 * builder method from reactive web for making call to rest api Movie movie =
		 * webClientBuilder.build().get() .uri("http://localhost:6060/movies/" +
		 * r.getMovieId()) .retrieve() .bodyToMono(Movie.class) .block();
		 * 
		 * return new CatalogItem(movie.getName(), "description", r.getRating());
		 * 
		 * }).collect(Collectors.toList());
		 */

		// for each movie id, call the movie info and get details

		// put them all together

		// returns only single object
		/*
		 * return Collections.singletonList( new CatalogItem("Spider-man",
		 * "spider man into the multiverse", 5));
		 */
	}

	public List<CatalogItem> getFallbackCatalog(@PathVariable String userId) {
		// fallback methods should not be another api calls
		// these should be hard coded methods
		// not like you;re making another api call in here
		// so we need to write fallback for another fallback method
		return Arrays.asList(new CatalogItem("No Movie", userId, 0));
		// we need to reduce errors
	}

}
