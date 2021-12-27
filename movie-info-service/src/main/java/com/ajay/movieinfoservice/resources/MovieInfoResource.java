package com.ajay.movieinfoservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ajay.movieinfoservice.dto.Movie;
import com.ajay.movieinfoservice.dto.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MovieInfoResource {

	@Autowired
	@Qualifier("RestTemplate")
	RestTemplate restTemplate;

	@Value("${api.key}")
	private String apiKey;

	@RequestMapping("/{movieId}")
	Movie getMovieInfo(@PathVariable("movieId") String movieId) {
		// return new Movie(movieId, "test movie name");

		MovieSummary movieSummary = restTemplate.getForObject(
				"https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey, MovieSummary.class);

		return new Movie(movieId, movieSummary.getOriginal_title(), movieSummary.getOverview());
	}

	/*
	 * @RequestMapping("user/{userId}") public List<Movie>
	 * getMovieDetails(@PathVariable(value="userId") String userId){ return
	 * Arrays.asList( new Movie("Spider-man", "ajaykumar"), new Movie("Bat-man",
	 * "ajaykumar"), new Movie("Amazing Spider-man", "Raghu") ); }
	 */
}
