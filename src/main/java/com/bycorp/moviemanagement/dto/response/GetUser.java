package com.bycorp.moviemanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record GetUser(
        String username,
        String name,
        int ratedMovies
) implements Serializable {

    public static record GetRating(
            long id,
            @JsonProperty(value = "movie_title") String movieTitle,
            @JsonProperty(value = "movie_id") long movieId,
            int rating
    ) implements Serializable {

    }
}
