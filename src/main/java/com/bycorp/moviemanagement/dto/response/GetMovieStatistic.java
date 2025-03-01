package com.bycorp.moviemanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record GetMovieStatistic(
        Long id,
        String title,
        String director,
        String genre,
        @JsonProperty("total_ratings")
        int totalRatings,
        @JsonProperty("release_year")
        int releaseYear,
        @JsonProperty("average_rating")
        double averageRating,
        @JsonProperty("lowest_rating")
        int lowestRating,
        @JsonProperty("highest_rating")
        int highestRating
) implements Serializable {
}
