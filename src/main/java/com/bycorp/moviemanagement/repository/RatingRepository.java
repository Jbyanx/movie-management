package com.bycorp.moviemanagement.repository;

import com.bycorp.moviemanagement.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findRatingsByMovieId(Long movieId);

    List<Rating> findRatingsByUserUsername(String user_username);

}
