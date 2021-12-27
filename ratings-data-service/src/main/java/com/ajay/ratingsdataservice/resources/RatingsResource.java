package com.ajay.ratingsdataservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajay.ratingsdataservice.dto.Rating;
import com.ajay.ratingsdataservice.dto.UserRating;

@RestController
@RequestMapping("/ratingsData")
public class RatingsResource {

	@RequestMapping("{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId, 4);
	}

	@RequestMapping("/user/{userId}")
	public UserRating getUserRatings(@PathVariable(value = "userId") String userId) {
		List<Rating> ratings = Arrays.asList(new Rating("100", 4), new Rating("200", 5),
				new Rating("300", 3));
		UserRating userRating = new UserRating();
		userRating.setRatings(ratings);
		return userRating;
	}
}
