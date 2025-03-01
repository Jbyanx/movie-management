package com.bycorp.moviemanagement.repository;

import com.bycorp.moviemanagement.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Page<Rating> findRatingsByMovieId(Long movieId, Pageable pageable);

    Page<Rating> findRatingsByUserUsername(String user_username, Pageable pageable);

    Boolean existsByMovieIdAndUserUsername(Long movieId, String username);
}
