package com.bycorp.moviemanagement.dto.response;

import com.bycorp.moviemanagement.utils.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public record GetMovie(
        long id,
        String title,
        String director,
        MovieGenre genre,
        @JsonProperty(value = "release_year") int releaseYear,
        @JsonProperty("total_ratings")
        int totalRatings
) implements Serializable {

    public static record GetRating(
            long id,
            int rating,
            String username
    ) implements Serializable {

    }

}
