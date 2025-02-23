package com.bycorp.moviemanagement.repository;

import com.bycorp.moviemanagement.dto.request.SaveMovie;
import com.bycorp.moviemanagement.dto.response.GetMovie;
import com.bycorp.moviemanagement.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    Optional<Movie> findOneById(Long id);
    Movie createOne(Movie movie);
    Movie updateOneById(Long id,Movie movie);

}
