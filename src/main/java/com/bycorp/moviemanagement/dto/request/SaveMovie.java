package com.bycorp.moviemanagement.dto.request;

import com.bycorp.moviemanagement.utils.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.io.Serializable;

public record SaveMovie(
        @Size(min = 4, max = 255, message = "{generic.size}")
        @NotBlank(message = "{generic.notBlank}")
        String title, // null - "" - "     "
        @Size(min = 4, max = 255, message = "{generic.size}")
        @NotBlank(message = "{generic.notBlank}")
        String director,
        MovieGenre genre,
        @Min(value = 1900, message = "{generic.min}")
        @JsonProperty(value = "release_year")
        int releaseYear
) implements Serializable {

}
