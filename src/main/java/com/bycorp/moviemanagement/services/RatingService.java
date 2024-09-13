package com.bycorp.moviemanagement.services;

import com.bycorp.moviemanagement.entity.Rating;

import java.util.List;

public interface RatingService {
    List<Rating> findAll();
    List<Rating> findRatingsByMovieId(Long movieId);
    List<Rating> findRatingsByUserUsername(String user_username);
    Rating findOneById(Long id);
    Rating createOne(Rating rating);
    Rating updateOneById(Long id,Rating rating);
    void deleteOneById(Long id);
}
