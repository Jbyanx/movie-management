package com.bycorp.moviemanagement.dto.request;

import com.bycorp.moviemanagement.utils.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record SaveMovie(
        String title,
        String director,
        MovieGenre genre,
        @JsonProperty(value = "release_year") int releaseYear
) implements Serializable {

}
