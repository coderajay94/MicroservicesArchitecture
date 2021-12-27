package com.ajay.moviecatalogservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserRating {

	private List<Rating> ratings;
	private String userId;
}
