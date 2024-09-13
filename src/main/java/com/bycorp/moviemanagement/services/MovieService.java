package com.bycorp.moviemanagement.services;

import com.bycorp.moviemanagement.entity.Movie;
import com.bycorp.moviemanagement.utils.MovieGenre;

import java.util.List;

public interface MovieService {
    List<Movie> findAll();
    List<Movie> findAllByTitle(String title);
    List<Movie> findAllByGenre(MovieGenre genre);
    List<Movie> findAllByTitleAndGenre(String title, MovieGenre genre);
    Movie findOneById(Long id);
    Movie createOne(Movie movie);
    Movie updateOneById(Long id,Movie movie);
    void deleteOneById(Long id);
}
