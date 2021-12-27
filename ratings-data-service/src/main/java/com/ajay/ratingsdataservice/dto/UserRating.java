package com.ajay.ratingsdataservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserRating {

	List<Rating> ratings;
}
