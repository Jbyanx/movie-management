package com.bycorp.moviemanagement.repository;

import com.bycorp.moviemanagement.entity.Movie;
import com.bycorp.moviemanagement.utils.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitleContaining(String title);

    List<Movie> findByGenre(MovieGenre genre);

    List<Movie> findByTitleContainingAndGenre(String title, MovieGenre genre);

}
