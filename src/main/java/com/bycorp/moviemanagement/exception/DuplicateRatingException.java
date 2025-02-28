package com.bycorp.moviemanagement.exception;

public class DuplicateRatingException extends RuntimeException{
    private String username;
    private Long movieId;

    public DuplicateRatingException(String username, Long movieId) {
        this.username = username;
        this.movieId = movieId;
    }

    @Override
    public String getMessage() {
        return "El usuaruio: "+username+" ya califico la pelicula con id "+movieId;
    }
}
