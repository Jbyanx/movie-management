package com.bycorp.moviemanagement.services;

import com.bycorp.moviemanagement.dto.request.MovieSearchCriteria;
import com.bycorp.moviemanagement.dto.request.SaveMovie;
import com.bycorp.moviemanagement.dto.response.GetMovie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MovieService {
    Page<GetMovie> findAll(MovieSearchCriteria movieSearchCriteria, Pageable pageable);
    GetMovie findOneById(Long id);
    GetMovie createOne(SaveMovie movieDto);
    GetMovie updateOneById(Long id,SaveMovie movieDto);
    void deleteOneById(Long id);
}
