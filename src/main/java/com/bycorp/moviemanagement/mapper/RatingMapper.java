package com.bycorp.moviemanagement.mapper;

import com.bycorp.moviemanagement.dto.request.SaveRating;
import com.bycorp.moviemanagement.dto.response.GetCompleteRating;
import com.bycorp.moviemanagement.dto.response.GetMovie;
import com.bycorp.moviemanagement.dto.response.GetUser;
import com.bycorp.moviemanagement.entity.Rating;

import java.util.List;

public class RatingMapper {

    public static Rating toRatingEntity(SaveRating rating, Long userId) {
        if(rating == null) return null;

        Rating ratingEntity = new Rating();
        ratingEntity.setMovieId(rating.movieId());
        ratingEntity.setUserId(userId);
        ratingEntity.setRating(rating.rating());

        return ratingEntity;
    }

    public static GetCompleteRating toGetCompleteRatingDto(Rating rating) {
        if(rating == null) return null;

        String movieTitle = rating.getMovie() != null ? rating.getMovie().getTitle() : null;
        String username = rating.getUser() != null ? rating.getUser().getUsername() : null;

        return new GetCompleteRating(
                rating.getId(),
                rating.getMovieId(),
                movieTitle,
                username,
                rating.getRating()
                );
    }

    public static List<GetMovie.GetRating> toGetMovieRatingDtoList(List<Rating> entities) {
        if(entities == null) return null;

        return entities.stream()
                .map(RatingMapper::toGetMovieRatingDto)
                .toList();
    }

    public static GetMovie.GetRating toGetMovieRatingDto(Rating entity) {
        if(entity==null) return null;

        String username = entity.getUser() != null
                ? entity.getUser().getUsername()
                : null;

        return new GetMovie.GetRating(
                entity.getId(),
                entity.getRating(),
                username
        );
    }

    public static GetUser.GetRating toGetUserRatingDto(Rating entity) {
        if(entity==null) return null;

        String movieTitle = entity.getMovie() != null
                ? entity.getMovie().getTitle()
                : null;

        return new GetUser.GetRating(
                entity.getId(),
                movieTitle,
                entity.getMovieId(),
                entity.getRating()
        );
    }

    public static List<GetUser.GetRating> toGetUserRatingDtoList(List<Rating> entities) {
        if(entities == null) return null;

        return entities.stream()
                .map(RatingMapper::toGetUserRatingDto)
                .toList();
    }

    public static void updateEntity(Rating entity, SaveRating dto, Long userId) {
        if(entity==null || dto==null) return;

        entity.setUserId(userId);
        entity.setRating(dto.rating());
        entity.setMovieId(dto.movieId());
    }
}
