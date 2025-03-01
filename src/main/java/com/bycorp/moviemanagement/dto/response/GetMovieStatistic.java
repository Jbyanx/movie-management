package com.bycorp.moviemanagement.dto.response;

import java.io.Serializable;

public record GetMovieStatistic(
        Long id,
        String title,
        String director,
        String genre,
        int totalRatings,
        int releaseYear,
        double averageRating,
        int lowestRating,
        int highestRating
) implements Serializable {
}
