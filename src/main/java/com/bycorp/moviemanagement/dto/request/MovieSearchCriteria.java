package com.bycorp.moviemanagement.dto.request;

import com.bycorp.moviemanagement.utils.MovieGenre;

public record MovieSearchCriteria(
        String title,
        MovieGenre genre,
        Integer minReleaseYear,
        Integer maxReleaseYear,
        Integer minAverageRating,
        String username
) {
}
