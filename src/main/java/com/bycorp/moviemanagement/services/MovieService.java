package com.bycorp.moviemanagement.services;

import com.bycorp.moviemanagement.dto.request.SaveMovie;
import com.bycorp.moviemanagement.dto.response.GetMovie;
import com.bycorp.moviemanagement.entity.Movie;
import com.bycorp.moviemanagement.utils.MovieGenre;

import java.util.List;

public interface MovieService {
    List<GetMovie> findAll();
    List<GetMovie> findAllByTitle(String title);
    List<GetMovie> findAllByGenre(MovieGenre genre);
    List<GetMovie> findAllByTitleAndGenre(String title, MovieGenre genre);
    GetMovie findOneById(Long id);
    GetMovie createOne(SaveMovie movieDto);
    GetMovie updateOneById(Long id,SaveMovie movieDto);
    void deleteOneById(Long id);
}
