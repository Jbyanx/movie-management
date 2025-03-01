package com.bycorp.moviemanagement.services;

import com.bycorp.moviemanagement.dto.request.MovieSearchCriteria;
import com.bycorp.moviemanagement.dto.request.SaveMovie;
import com.bycorp.moviemanagement.dto.response.GetMovie;
import com.bycorp.moviemanagement.dto.response.GetMovieStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<GetMovie> findAll(MovieSearchCriteria movieSearchCriteria, Pageable pageable);

    GetMovieStatistic findOneById(Long id);

    GetMovie createOne(SaveMovie movieDto);
    GetMovie updateOneById(Long id,SaveMovie movieDto);
    void deleteOneById(Long id);
}
